package de.zera.test.excelsior.solutions.ticker;

import de.zera.test.excelsior.solutions.ticker.exception.TickerProcessException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runs application, check incoming parameters
 */
public class MainApp {
    private static final Logger log = Logger.getLogger(MainApp.class.getName());
    private static final String APP_NAME = "Ticker";

    public static void main(String ... argv) {
        log.setLevel(Level.INFO);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        log.addHandler(consoleHandler);

        if (argv.length < 3) {
            usage("arguments don't match");
            System.exit(TickerConstants.ARGUMENTS_MISMATCH);  // not sure if it's OK to return negative value or not
        }
        // negative comparison for a case of processing steps
        if (!(TickerConstants.NUMBER_PATTERN.matcher(argv[1]).matches() && TickerConstants.NUMBER_PATTERN.matcher(argv[2]).matches())) {
            usage("numbers and days are numbers");
            System.exit(TickerConstants.INCORRECT_ARGUMENTS);
        }

        if (!TickerConstants.FILE_PATH_PATTERN.matcher(argv[0]).matches()) {
            usage("file path incorrect");
            System.exit(TickerConstants.FILE_PATH_ERROR);
        }

        if (!Files.exists(Path.of(argv[0]))) {
            usage("file not found");
            System.exit(TickerConstants.FILE_ERROR);
        }

        try {
            new TickerProcessor.TickerProcessorBuilder().with(argv[0], Integer.parseInt(argv[1]), Integer.parseInt(argv[2])).run();
            System.exit(0);
        } catch (TickerProcessException e) {
            System.exit(e.getReturnStatus());
        }
    }

    private static void usage(String description) {
        log.log(Level.WARNING, String.format("Usage %s <path-to-data-file> <numbers> <days>%n%s", APP_NAME, description));
    }
}
