package de.zera.test.excelsior.solutions.ticker.data;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import org.junit.jupiter.api.Test;
import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static de.zera.test.excelsior.solutions.ticker.TickerTestUtils.createTestTicker;

class TickerCollectorTest {
    private static final Function<List<BigDecimal>, BigDecimal> average = (values) -> values.stream().map(Objects::requireNonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(values.size()), RoundingMode.HALF_UP);

    private final TickerCollector classUnderTest = new TickerCollector("test", 1, 1);

    @Test
    void add_ticket_should_not_fail() {
        classUnderTest.addTicker(createTestTicker());
        assertEquals("test", classUnderTest.getName());
        var result = classUnderTest.getMovingAverage(average);
        assertNotNull(result);
    }

    @Test
    void add_ticket_should_fail_on_validation() {
        TickerProcessException exception = assertThrows(TickerProcessException.class, () -> classUnderTest.addTicker(Ticker.builder().build()));
        assertEquals(TickerConstants.INCORRECT_TICKER_FORMAT, exception.getReturnStatus());
    }
}