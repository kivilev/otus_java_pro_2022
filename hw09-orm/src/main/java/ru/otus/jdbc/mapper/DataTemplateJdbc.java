package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                rs -> {



                   /* try {
                        if (rs.next()) {
                            return new Client(rs.getLong("id"), rs.getString("name"));
                        }
                        return null;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }*/
                    return null;
                }
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        //T object;
        //object.getClass().getDeclaredConstructor().newInstance();


        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(),
                rs -> {

                   /* try {
                        if (rs.next()) {
                            return new Client(rs.getLong("id"), rs.getString("name"));
                        }
                        return null;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }*/
                    return null;
                }
        );
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    Collections.singletonList(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }
    /*

        @Override
    public long insert(Connection connection, Client client) {
        try {
            return dbExecutor.executeStatement(connection, "insert into client(name) values (?)",
                    Collections.singletonList(client.getName()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, Client client) {
        try {
            dbExecutor.executeStatement(connection, "update client set name = ? where id = ?",
                    List.of(client.getName(), client.getId()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

     */
}
