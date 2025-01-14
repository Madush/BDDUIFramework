package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private static Properties properties;

    public static Properties loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                FileInputStream input = new FileInputStream("src/test/resources/config.properties");
                properties.load(input);
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public static String getProperty(String key) {
        return loadProperties().getProperty(key);
    }
}

