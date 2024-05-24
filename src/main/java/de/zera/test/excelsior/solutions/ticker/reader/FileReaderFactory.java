package de.zera.test.excelsior.solutions.ticker.reader;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import org.jetbrains.annotations.NotNull;
import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;

import java.io.File;
import java.nio.file.Path;

/**
 * creates file reader
 */
public class FileReaderFactory {
    private FileReaderFactory() {
        // prevent instantiation
    }

    public static FileReader createFileReader(@NotNull String filePath) {
        try {
            final File file = Path.of(filePath).toFile();
            // not a calculation, but only as concept
            if (file.length() > Short.MAX_VALUE) {
                return new BigSizeFileReader(file);
            } else {
                return new DefaultFileReader(file);
            }
        } catch (UnsupportedOperationException e) {
            throw new TickerProcessException(TickerConstants.FILE_ERROR, e);
        }
    }
}
