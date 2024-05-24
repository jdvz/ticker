package de.zera.test.excelsior.solutions.ticker;

import org.junit.jupiter.api.Test;

class MainAppTest {
    @Test
    void testPatternFailOnArgumentCount() {
        ExitAssertions.assertExits(-1, () -> MainApp.main("test failed"));
    }

    @Test
    void testPatternFailOnNumberPattern() {
        ExitAssertions.assertExits(-2, () -> MainApp.main("test failed", "1", "0A8"));
    }

    @Test
    void testPatternFailOnFile() {
        ExitAssertions.assertExits(-3, () -> MainApp.main("test failed", "1", "11"));
    }

    @Test
    void testPatternFailOnFileName() {
        ExitAssertions.assertExits(-3, () -> MainApp.main("D:/My Documents/Files/1.xls", "1", "11"));
    }

    @Test
    void testStartingProcessWinLikeFileNotFound() {
        ExitAssertions.assertExits(-4, () -> MainApp.main("D:\\My Documents\\Files\\1.csv", "1", "11"));
    }

    @Test
    void testStartingProcessUnixLikeFileNotFound() {
        ExitAssertions.assertExits(-4, () -> MainApp.main("/pub/files/2.csv", "2", "11"));
    }

// *** next methods provides simple integration test

    @Test
    void testStartingProcessUnixLikeSuccess() {
        ExitAssertions.assertExits(0, () -> MainApp.main("src/test/resources/example.csv", "2", "11"));
    }

    @Test
    void testStartingProcessUnixLikeSmallFileSuccess() {
        ExitAssertions.assertExits(0, () -> MainApp.main("src/test/resources/1.csv", "4", "4"));
    }
}