package com.batchdemo.quotegenerator.boot.model;

import com.batchdemo.quotegenerator.boot.dao.TextReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Repository("PropertiesReader")
@Scope("singleton")
public class PropertiesReader implements TextReader {

    private Map<String, String> props;

    public PropertiesReader() {
        this.read();
    }

    @Override
    public Map<String, String> read() {
        if(props == null) {

            final String fileLocation = "/custom.properties";
            props = new HashMap<>();

            //Using getResourceAsStream instead of FileReader to work in the jar file
            try (InputStream in = PropertiesReader.class.getResourceAsStream(fileLocation)) {
                if(in != null) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

                        Properties properties = new Properties();
                        properties.load(reader);

                        for (Map.Entry<Object, Object> set : properties.entrySet()) {
                            props.put(set.getKey().toString(), set.getValue().toString());
                        }
                    }
                }
            } catch (IOException io) {
                System.out.println(io.getMessage());
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
