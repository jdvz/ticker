package de.zera.test.excelsior.solutions.ticker;

import de.zera.test.excelsior.solutions.ticker.data.Ticker;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TickerTestUtils {
    private TickerTestUtils() {
        // prevent instantiation
    }

    public static Ticker createTestTicker() {
        return Ticker.builder()
                .withName("test")
                .withDate(LocalDate.now())
                .withPriceClose(BigDecimal.ZERO)
                .withPriceHigh(BigDecimal.TEN)
                .withPriceLow(BigDecimal.ZERO)
                .withPriceOpen(BigDecimal.ONE)
                .build();
    }
}
