package ch.so.agi.mgdm2oereb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Util {
    public static void loadFile(String resourcePath, File copyFile) throws IOException {
        InputStream is = Util.class.getClassLoader().getResourceAsStream(resourcePath);
        Files.copy(is, copyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
