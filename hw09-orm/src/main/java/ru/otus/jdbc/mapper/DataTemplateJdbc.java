package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                rs -> {
                    try {
                        if (rs.next()) {
                            return rowMapper(rs);
                        }
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                    return null;
                }
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(),
                rs -> {
                    var objectList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            objectList.add(rowMapper(rs));
                        }
                        return objectList;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            List<Object> values = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                values.add(field.get(object));
            }

            return dbExecutor.executeStatement(connection,
                    entitySQLMetaData.getInsertSql(),
                    values
            );
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            List<Object> values = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                values.add(field.get(object));
            }
            Field fieldId = entityClassMetaData.getIdField();
            fieldId.setAccessible(true);
            values.add(fieldId.get(object));

            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), values);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }


    private T rowMapper(ResultSet rs) {
        try {
            T object = entityClassMetaData.getConstructor().newInstance();
            for (var field : entityClassMetaData.getAllFields()) {
                var changeField = object.getClass().getDeclaredField(field.getName());
                changeField.setAccessible(true);
                changeField.set(object, rs.getObject(field.getName()));
            }
            return object;
        } catch (SQLException e) {
            throw new DataTemplateException(e);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
