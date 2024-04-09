package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationFile {
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    
}
