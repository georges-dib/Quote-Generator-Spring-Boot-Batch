package com.batchdemo.quotegenerator.boot.model;

import com.batchdemo.quotegenerator.boot.dao.TextReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Repository("PropertiesReader")
@Scope("singleton")
public class PropertiesReader implements TextReader {

    private final String fileLocation = "src/main/resources/custom.properties";
    private Map<String, String> props;

    public PropertiesReader() {
        this.read();
    }

    @Override
    public Map<String, String> read() {
        if(props == null) {
            props = new HashMap<>();

            try (FileReader reader = new FileReader(fileLocation)) {
                Properties properties = new Properties();
                properties.load(reader);

                for (Map.Entry<Object, Object> set : properties.entrySet()) {
                    props.put(set.getKey().toString(), set.getValue().toString());
                }
            } catch (IOException io) {
                System.out.println("File at " + fileLocation + " could not be found");
            }
        }

        return Map.copyOf(props);
    }

    public Map<String, String> getPoperties() {
        if(props == null) {
            props = this.read();
        }
        return Map.copyOf(props);
    }

    public String getProperty(String property) {
        return props.getOrDefault(property, "");
    }
}
