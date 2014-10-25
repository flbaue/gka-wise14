package aufgabe1.utils;

import java.io.File;
import java.util.Objects;

/**
 * Created by schlegel11 on 25.10.14.
 */
public final class FileUtils {

    private static final String PREFIX_SUFFIX = "%s.%s";

    private FileUtils() {
    }

    public static File setFileSuffix(File file, String extension) {
        Objects.nonNull(file);
        Objects.nonNull(extension);
        return new File(String.format(PREFIX_SUFFIX, file.getAbsolutePath(), extension));
    }
}
