package de.zera.test.excelsior.solutions.ticker.reader;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineReaderTest {
    private final LineReader classUnderTest = new LineReader();

    @Test
    void read_OK() {
        var ticker = classUnderTest.readLine("774.39,774.83,744.1,753.99,TSLA,9/24/2021");
        assertEquals("TSLA", ticker.getName());
        assertEquals(new BigDecimal("774.39"), ticker.getPriceClose());
        assertEquals(new BigDecimal("774.83"), ticker.getPriceHigh());
        assertEquals(new BigDecimal("744.1"), ticker.getPriceLow());
        assertEquals(new BigDecimal("753.99"), ticker.getPriceOpen());
        assertEquals(LocalDate.parse("9/24/2021", TickerConstants.DATE_TIME_FORMATTER), ticker.getDate());
    }
}