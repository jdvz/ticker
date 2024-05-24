package de.zera.test.excelsior.solutions.ticker.reader;

import org.junit.jupiter.api.Test;
import de.zera.test.excelsior.solutions.ticker.processor.TickerDataProcessor;

import java.nio.file.Path;

import static org.mockito.Mockito.*;

class BigSizeFileReaderTest {
    private final BigSizeFileReader classUnderTest = new BigSizeFileReader(Path.of("src/test/resources/example.csv").toFile());

    @Test
    void read_file_OK() {
        final TickerDataProcessor processor = mock(TickerDataProcessor.class);
        classUnderTest.readAndProcess(processor);
        verify(processor, atLeastOnce()).push(any());
    }
}