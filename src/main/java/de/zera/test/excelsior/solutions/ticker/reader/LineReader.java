package de.zera.test.excelsior.solutions.ticker.reader;

import de.zera.test.excelsior.solutions.ticker.TickerConstants;
import de.zera.test.excelsior.solutions.ticker.data.Ticker;
import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * reads csv line
 */
public class LineReader {

    public Ticker readLine(String line) {
        try(final Scanner lineScanner = new Scanner(line)) {
            lineScanner.useDelimiter(TickerConstants.CSV_FIELD_DELIMITER);

            return Ticker.builder()
                    .withPriceClose(lineScanner.nextBigDecimal())
                    .withPriceHigh(lineScanner.nextBigDecimal())
                    .withPriceLow(lineScanner.nextBigDecimal())
                    .withPriceOpen(lineScanner.nextBigDecimal())
                    .withName(lineScanner.next())
                    .withDate(LocalDate.parse(lineScanner.next(), TickerConstants.DATE_TIME_FORMATTER))
                    .build();
        } catch (Exception e) {
            throw new TickerProcessException(TickerConstants.INCORRECT_CSV_FORMAT, e);
        }
    }
}
