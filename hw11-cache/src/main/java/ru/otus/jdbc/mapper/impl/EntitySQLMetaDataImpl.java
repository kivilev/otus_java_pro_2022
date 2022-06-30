package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format(
                "select %s from %s",
                String.join(", ", getJoinedFieldNames(entityClassMetaData.getAllFields())),
                entityClassMetaData.getName()
        );
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "select %s from %s where %s = ?",
                getJoinedFieldNames(entityClassMetaData.getAllFields()),
                entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName()
        );
    }

    @Override
    public String getInsertSql() {
        List<String> paramValues =
                Collections.nCopies(entityClassMetaData.getFieldsWithoutId().size(), "?");

        return String.format(
                "insert into %s (%s) values (%s)",
                entityClassMetaData.getName(),
                getJoinedFieldNames(entityClassMetaData.getFieldsWithoutId()),
                String.join(", ", paramValues)
        );
    }

    @Override
    public String getUpdateSql() {
        List<String> params =
                entityClassMetaData.getAllFields()
                        .stream()
                        .filter(it -> !it.getName().equals(entityClassMetaData.getIdField().getName()))
                        .map(field -> String.format("%s = ?", field.getName())).toList();

        return String.format(
                "update %s\n" +
                        "   set %s \n" +
                        " where %s = ?",
                entityClassMetaData.getName(),
                String.join("\t,\n", params),
                entityClassMetaData.getIdField().getName()
        );
    }

    private String getJoinedFieldNames(List<Field> fields) {
        List<String> paramNames = fields.stream().map(Field::getName).collect(Collectors.toList());
        return String.join(", ", paramNames);
    }
}
