package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final String fullFileName;

    public FileSerializer(String fileName) {
        fullFileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (var buffer = new BufferedWriter(new FileWriter(fullFileName))) {
            Map<String, Double> orderedMap = new TreeMap<>(data);// TODO: переделать на streams
            String jsonResult = objectMapper.writeValueAsString(orderedMap);
            buffer.write(jsonResult);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
