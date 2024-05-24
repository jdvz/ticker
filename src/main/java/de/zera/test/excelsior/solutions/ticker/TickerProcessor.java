package de.zera.test.excelsior.solutions.ticker;

import de.zera.test.excelsior.solutions.ticker.processor.DefaultTickerDataProcessor;
import de.zera.test.excelsior.solutions.ticker.processor.TickerDataProcessor;
import de.zera.test.excelsior.solutions.ticker.reader.FileReaderFactory;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * process data
 */
public class TickerProcessor {
    private static final Logger log = Logger.getLogger(TickerProcessor.class.getName());

    private static final Function<List<BigDecimal>, BigDecimal> average = (values) -> values.stream().map(Objects::requireNonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(values.size()), RoundingMode.HALF_UP);

    private final String filePath;
    private final int days;
    private final int elements;

    protected TickerProcessor(String filePath, int days, int elements) {
        this.filePath = filePath;
        this.days = days;
        this.elements = elements;
    }

    public void run() {
        final long start = System.currentTimeMillis();
        // data processor is here to allow access during stream processing
        // it's not a part of the task I'm solving now, but probably it might be useful in the future
        TickerDataProcessor dataProcessor = new DefaultTickerDataProcessor(days, elements);
        FileReaderFactory.createFileReader(filePath).readAndProcess(dataProcessor);

        dataProcessor.status().forEach(tc -> {
            // I'd rather use logger here, but it makes console result very messy without log configuring
            System.out.printf("name: %s\tdays: %d\tmax price: %.2f\t min price: %.2f%n",
                    tc.getName(), tc.getDaysCount(), format(tc.getMaximumPrice()), format(tc.getMinimumPrice()));
            tc.getMovingAverage(average)
                    .forEach((key, value) -> System.out.printf("%s: MA = %.2f%n", key, format(value)));
        });
        log.info(String.format("Finished in %s ms", System.currentTimeMillis() - start));
    }

    /**
     * simple Big Decimal formatter, for the only test purpose it placed here, but not in the utils class
     * @param price to scale
     * @return scaled
     */
    private static BigDecimal format(@NotNull BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * build processor
     */
    public static class TickerProcessorBuilder {
        public TickerProcessor with(String filePath, int elements, int days) {
            return new TickerProcessor(filePath, days, elements);
        }
    }
}
