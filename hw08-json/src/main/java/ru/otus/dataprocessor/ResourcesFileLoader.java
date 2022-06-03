package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static SimpleModule module = new SimpleModule();
    private final String fullFileName;

    static {
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        objectMapper.registerModule(module);
    }

    public ResourcesFileLoader(String fileName) {
        fullFileName = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile();
    }

    @Override
    public List<Measurement> load() throws IOException {
        //читает файл, парсит и возвращает результат
        List<Measurement> measurementList;
        try (var buffer = new BufferedInputStream(new FileInputStream(fullFileName))) {
            measurementList = objectMapper.readValue(buffer, new TypeReference<List<Measurement>>() {
            });
        }
        return measurementList;
    }
}
