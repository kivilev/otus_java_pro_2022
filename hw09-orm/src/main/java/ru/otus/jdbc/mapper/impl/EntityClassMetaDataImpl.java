package ru.otus.jdbc.mapper.impl;

import ru.otus.crm.annotation.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> genericClass;

    public EntityClassMetaDataImpl(Class<T> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public String getName() {
        return genericClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return genericClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(genericClass.getDeclaredFields()).filter(field ->
                field.isAnnotationPresent(Id.class)
        ).findFirst().orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(genericClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(genericClass.getDeclaredFields()).filter(field ->
                !field.isAnnotationPresent(Id.class)
        ).collect(Collectors.toList());
    }
}
