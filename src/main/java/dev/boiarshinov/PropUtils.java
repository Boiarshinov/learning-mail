package dev.boiarshinov;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropUtils {
    public static Properties getMailProperties() {
        try {
            final InputStream inputStream = ClassLoader.getSystemResourceAsStream("javamail.properties");
            final InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);
            final Properties mailProperties = new Properties();
            mailProperties.load(reader);
            return mailProperties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPassword() {
        try {
            final InputStream inputStream = ClassLoader.getSystemResourceAsStream("confidential.properties");
            final InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);
            final Properties props = new Properties();
            props.load(reader);
            return props.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
