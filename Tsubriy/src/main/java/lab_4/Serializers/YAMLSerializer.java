package lab_4.Serializers;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lab_4.Interfaces.ISerializable;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class YAMLSerializer<T> implements ISerializable<T> {
    private final YAMLMapper yamlMapper;
    private final Class<T> tClass;

    public YAMLSerializer(Class<T> tClass) {
        yamlMapper = new YAMLMapper();
        this.yamlMapper.registerModule(new JavaTimeModule());
        this.tClass = tClass;
    }

    @Override
    public String serialize(T object) throws IOException{
        return yamlMapper.writeValueAsString(object);
    }

    @Override
    public String serialize(List<T> objectCollection) throws IOException {
        return yamlMapper.writeValueAsString(objectCollection);
    }

    @Override
    public T deserialize(String object) throws IOException {
        return yamlMapper.readValue(object, tClass);
    }

    @Override
    public List<T> deserializeToList(String objectCollection) throws IOException {
        return yamlMapper.readValue(objectCollection, yamlMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }

    @Override
    public void writeToFile(T object, String fileName) throws IOException {
        yamlMapper.writeValue(new File(fileName), object);
    }

    @Override
    public void writeToFile(List<T> objectCollection, String fileName) throws IOException {
        yamlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), objectCollection);
    }

    @Override
    public List<T> readFromFile(String fileName) throws IOException {
        return yamlMapper.readValue(new File(fileName), yamlMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }
}