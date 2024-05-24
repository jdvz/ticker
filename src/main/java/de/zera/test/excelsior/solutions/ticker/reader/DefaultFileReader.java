package de.zera.test.excelsior.solutions.ticker.reader;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import de.zera.test.excelsior.solutions.ticker.processor.TickerDataProcessor;
import org.jetbrains.annotations.NotNull;
import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * implements simple file reading.
 * it solves the task in the simplest and direct way
 */
public class DefaultFileReader extends AbstractFileReader {
    public DefaultFileReader(@NotNull File file) {
        super(file);
    }

    @Override
    public void readAndProcess(@NotNull TickerDataProcessor processor) {
        try {
            Files.readAllLines(file.toPath())
                    .stream()
                    .skip(1) // probably we can read headers and use field order from here
                    .forEach(line -> processor.push(lineReader.readLine(line)));
        } catch (IOException e) {
            throw new TickerProcessException(TickerConstants.FILE_PROCESSING_ERROR, e);
        }
    }
}
