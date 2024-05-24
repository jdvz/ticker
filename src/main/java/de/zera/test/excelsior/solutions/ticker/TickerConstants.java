package de.zera.test.excelsior.solutions.ticker;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * common constants holder
 */
public class TickerConstants {
    public static final int ARGUMENTS_MISMATCH = -1;
    public static final int INCORRECT_ARGUMENTS = -2;
    public static final int FILE_PATH_ERROR = -3;
    public static final int FILE_ERROR = -4;
    public static final int FILE_PROCESSING_ERROR = -8;
    public static final int INCORRECT_CSV_FORMAT = -12;
    public static final int INCORRECT_TICKER_FORMAT = -16;
    public static final String CSV_FIELD_DELIMITER = ",";

    public static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    public static final Pattern FILE_PATH_PATTERN = Pattern.compile("^([a-zA-Z]\\\\:)?[/\\\\]?([._a-zA-Z0-9])+.*\\.csv$");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");

    private TickerConstants() {
        // prevent instantiation
    }
}
