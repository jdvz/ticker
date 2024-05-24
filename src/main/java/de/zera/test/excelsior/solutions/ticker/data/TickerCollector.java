package de.zera.test.excelsior.solutions.ticker.data;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * collect information for tickers
 */
public class TickerCollector {
    private static final Logger log = Logger.getLogger(TickerCollector.class.getName());
    private final String name;

    private int daysCount;
    private BigDecimal maximumPrice;
    private BigDecimal minimumPrice;
    private final MovingAverageArray<BigDecimal> collector;

    public TickerCollector(String name, int days, int number) {
        this.name = name;
        this.collector = new MovingAverageArray<>(days, number);
    }

    /**
     * add ticker data to reduced holder
     * @param ticker
     */
    public TickerCollector addTicker(Ticker ticker) {
        validateTicker(ticker);
        // suppose we have only one record per day
        this.daysCount++;
        this.maximumPrice = (this.maximumPrice == null || this.maximumPrice.compareTo(ticker.getPriceHigh()) < 0)
                ? ticker.getPriceHigh() : this.maximumPrice;
        this.minimumPrice = (this.minimumPrice == null || this.minimumPrice.compareTo(ticker.getPriceLow()) > 0)
                ? ticker.getPriceLow() : this.minimumPrice;
        this.collector.push(ticker.getPriceClose(), ticker.getDate());
        return this;
    }

    private void validateTicker(Ticker ticker) {
        if (ticker.getName() == null || ticker.getDate() == null
                || ticker.getPriceHigh() == null || ticker.getPriceLow() == null || ticker.getPriceClose() == null) {
            log.log(Level.SEVERE,
                    () -> String.format("Ticker %s has null value", (ticker.getName() == null ? "'null'" : ticker.getName())));
            throw new TickerProcessException(TickerConstants.INCORRECT_TICKER_FORMAT);
        }
    }

    public String getName() {
        return name;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public BigDecimal getMaximumPrice() {
        return maximumPrice;
    }

    public BigDecimal getMinimumPrice() {
        return minimumPrice;
    }

    public Map<LocalDate, BigDecimal> getMovingAverage(Function<List<BigDecimal>, BigDecimal> aggregator) {
        return collector.pull(aggregator);
    }
}