package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ApplicationProperties.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties não encontrado!");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao carregar application.properties", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
