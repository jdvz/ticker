package de.zera.test.excelsior.solutions.ticker.data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ticker dto
 * we can convert it to a record
 */
public class Ticker {
    private final BigDecimal priceClose;
    private final BigDecimal priceHigh;
    private final BigDecimal priceLow;
    private final BigDecimal priceOpen;
    private final String name;
    private final LocalDate date;

    public static Ticker.TickerBuilder builder() {
        return new TickerBuilder();
    }

    protected Ticker(BigDecimal priceClose, BigDecimal priceHigh, BigDecimal priceLow, BigDecimal priceOpen, String name, LocalDate date) {
        this.priceClose = priceClose;
        this.priceHigh = priceHigh;
        this.priceLow = priceLow;
        this.priceOpen = priceOpen;
        this.name = name;
        this.date = date;
    }

    public BigDecimal getPriceClose() {
        return priceClose;
    }

    public BigDecimal getPriceHigh() {
        return priceHigh;
    }

    public BigDecimal getPriceLow() {
        return priceLow;
    }

    public BigDecimal getPriceOpen() {
        return priceOpen;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public static class TickerBuilder {
        private BigDecimal priceClose;
        private BigDecimal priceHigh;
        private BigDecimal priceLow;
        private BigDecimal priceOpen;
        private String name;
        private LocalDate date;

        public TickerBuilder withPriceClose(BigDecimal priceClose) {
            this.priceClose = priceClose;
            return this;
        }

        public TickerBuilder withPriceHigh(BigDecimal priceHigh) {
            this.priceHigh = priceHigh;
            return this;
        }

        public TickerBuilder withPriceLow(BigDecimal priceLow) {
            this.priceLow = priceLow;
            return this;
        }

        public TickerBuilder withPriceOpen(BigDecimal priceOpen) {
            this.priceOpen = priceOpen;
            return this;
        }

        public TickerBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TickerBuilder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Ticker build() {
            return new Ticker(priceClose, priceHigh, priceLow, priceOpen, name, date);
        }
    }
}
