package de.zera.test.excelsior.solutions.ticker.processor;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import org.junit.jupiter.api.Test;
import de.zera.test.excelsior.solutions.ticker.data.Ticker;
import de.zera.test.excelsior.solutions.ticker.data.TickerCollector;
import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static de.zera.test.excelsior.solutions.ticker.TickerTestUtils.createTestTicker;

class DefaultTickerDataProcessorTest {
    private final DefaultTickerDataProcessor classUnderTest = new DefaultTickerDataProcessor(2, 2);

    @Test
    void test_push_OK() {
        Ticker expected = createTestTicker();
        classUnderTest.push(expected);
        var result = classUnderTest.status();
        assertEquals(1, result.size());
        TickerCollector actual = result.iterator().next();
        assertEquals(expected.getName(), actual.getName());
        assertEquals(1, actual.getDaysCount());
    }

    @Test
    void test_push_fail() {
        TickerProcessException e = assertThrows(TickerProcessException.class, () -> classUnderTest.push(mock(Ticker.class)));
        assertEquals(TickerConstants.INCORRECT_TICKER_FORMAT, e.getReturnStatus());
    }
}