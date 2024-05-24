package de.zera.test.excelsior.solutions.ticker.exception;

/**
 * for any runtime errors as corrupted file, incorrect format of data and so on
 */
public class TickerProcessException extends RuntimeException {
    private final int returnStatus;

    /**
     * common exception for the application doesn't need to have a stacktrace
     * @param returnStatus it supposed to be an application, so we return status to the system
     */
    public TickerProcessException(int returnStatus) {
        this(returnStatus, null);
    }

    /**
     * common exception for the application doesn't need to have a stacktrace, but if we have exception we can use it
     * @param returnStatus it supposed to be an application, so we return status to the system
     */
    public TickerProcessException(int returnStatus, Exception e) {
        super("Process failure with error code: " + returnStatus, e, false, false);
        this.returnStatus = returnStatus;
    }

    public int getReturnStatus() {
        return returnStatus;
    }
}
