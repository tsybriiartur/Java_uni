package lab_3.Serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lab_3.Interfaces.ISerializable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONSerializer<T> implements ISerializable<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> tClass;

    public JSONSerializer(Class<T> tClass) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.tClass = tClass;
    }

    @Override
    public String serialize(T object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public String serialize(List<T> objectCollection) throws IOException {
        return objectMapper.writeValueAsString(objectCollection);
    }

    @Override
    public T deserialize(String object) throws IOException {
        return objectMapper.readValue(object, tClass);
    }

    @Override
    public List<T> deserializeToList(String objectCollection) throws IOException {
        return objectMapper.readValue(objectCollection, objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }

    @Override
    public void writeToFile(T object, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), object);
    }

    @Override
    public void writeToFile(List<T> objectCollection, String fileName) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), objectCollection);
    }

    @Override
    public List<T> readFromFile(String fileName) throws IOException {
        return objectMapper.readValue(new File(fileName), objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }
}