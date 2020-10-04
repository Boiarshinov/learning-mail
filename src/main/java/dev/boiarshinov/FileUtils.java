package dev.boiarshinov;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class FileUtils {

    public static File getFile() throws URISyntaxException {
        final URL resource = ClassLoader.getSystemResource("java_new_features.txt");
        return Paths.get(resource.toURI()).toFile();
    }

    public static File getImage() throws URISyntaxException {
        final URL resource = ClassLoader.getSystemResource("picture.jpg");
        return Paths.get(resource.toURI()).toFile();
    }
}
