package de.zera.test.excelsior.solutions.ticker.reader;

import de.zera.test.excelsior.solutions.ticker.processor.TickerDataProcessor;
import org.jetbrains.annotations.NotNull;

/**
 * provides reading and processing
 */
public interface FileReader {
    void readAndProcess(@NotNull TickerDataProcessor processor);
}
