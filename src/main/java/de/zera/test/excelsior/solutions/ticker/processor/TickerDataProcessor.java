package de.zera.test.excelsior.solutions.ticker.processor;

import de.zera.test.excelsior.solutions.ticker.data.Ticker;
import de.zera.test.excelsior.solutions.ticker.data.TickerCollector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * provides aggregated ticker storage
 */
public interface TickerDataProcessor {
    /**
     * push ticker to aggregated storage
     * @param ticker ticker to add
     */
    void push(@NotNull Ticker ticker);

    /**
     * status of storage
     * @return aggregated ticker data
     */
    Collection<TickerCollector> status();
}
