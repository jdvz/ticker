package de.zera.test.excelsior.solutions.ticker.reader;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * provides constructor for implementations
 */
public abstract class AbstractFileReader implements FileReader {
    protected final File file;
    protected final LineReader lineReader;

    protected AbstractFileReader(@NotNull File file) {
        this.file = file;
        this.lineReader = new LineReader();
    }
}
