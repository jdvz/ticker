package de.zera.test.excelsior.solutions.ticker.reader;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import org.jetbrains.annotations.NotNull;
import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;
import de.zera.test.excelsior.solutions.ticker.processor.TickerDataProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * implements big size files reading
 *
 * it might be modified to infinite stream reader, it might be more suitable solution for a real task.
 */
public class BigSizeFileReader extends AbstractFileReader {

    public BigSizeFileReader(@NotNull File file) {
        super(file);
    }

    @Override
    public void readAndProcess(@NotNull TickerDataProcessor processor) {
        try (FileInputStream inputStream = new FileInputStream(file);
             Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            if (sc.hasNextLine()) {
                sc.nextLine(); // skip header
            }
            while (sc.hasNextLine()) {
                processor.push(lineReader.readLine(sc.nextLine()));
            }
            // Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw new TickerProcessException(TickerConstants.FILE_PROCESSING_ERROR, sc.ioException());
            }
        } catch (IOException e) {
            // log error here
            // rethrowing isn't the best case usually, but it's fine for the example
            throw new TickerProcessException(TickerConstants.FILE_PROCESSING_ERROR, e);
        }
    }
}
