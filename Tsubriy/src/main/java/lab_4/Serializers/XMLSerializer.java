package lab_4.Serializers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lab_4.Interfaces.ISerializable;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class XMLSerializer<T> implements ISerializable<T> {
    private final XmlMapper xmlMapper;
    private final Class<T> tClass;

    public XMLSerializer(Class<T> tClass) {
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.registerModule(new JavaTimeModule());
        this.tClass = tClass;
    }

    @Override
    public String serialize(T object) throws IOException {
        return xmlMapper.writeValueAsString(object);

    }

    @Override
    public String serialize(List<T> objectCollection) throws IOException {
        return xmlMapper.writeValueAsString(objectCollection);
    }

    @Override
    public T deserialize(String object) throws IOException {
        return xmlMapper.readValue(object, tClass);
    }

    @Override
    public List<T> deserializeToList(String objectCollection) throws IOException {
        return xmlMapper.readValue(objectCollection, xmlMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }


    @Override
    public void writeToFile(T object, String fileName) throws IOException {
        xmlMapper.writeValue(new File(fileName), object);
    }


    @Override
    public void writeToFile(List<T> objectCollection, String fileName) throws IOException {
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), objectCollection);
    }


    @Override
    public List<T> readFromFile(String fileName) throws IOException {
        return xmlMapper.readValue(new File(fileName), xmlMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }
}