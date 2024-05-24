package de.zera.test.excelsior.solutions.ticker.processor;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import de.zera.test.excelsior.solutions.ticker.data.Ticker;
import de.zera.test.excelsior.solutions.ticker.data.TickerCollector;
import org.jetbrains.annotations.NotNull;
import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * store data
 */
public class DefaultTickerDataProcessor implements TickerDataProcessor {
    // according to initial requirements I don't think it's necessary to use concurrent hash map here
    // but it might be a case if we want to process another incoming stream
    private final Map<String, TickerCollector> dataStorage = new ConcurrentHashMap<>();
    private final int days;
    private final int number;


    public DefaultTickerDataProcessor(int days, int number) {
        this.days = days;
        this.number = number;
    }

    @Override
    public void push(@NotNull Ticker ticker) {
        if (ticker.getName() == null) {
            throw new TickerProcessException(TickerConstants.INCORRECT_TICKER_FORMAT);
        }
        final TickerCollector tickerCollector = dataStorage.get(ticker.getName());

        if (tickerCollector == null) {
            dataStorage.put(ticker.getName(), new TickerCollector(ticker.getName(), days, number).addTicker(ticker));
        } else {
            tickerCollector.addTicker(ticker);
        }
    }

    @Override
    public Collection<TickerCollector> status() {
        return dataStorage.values();
    }
}
