package de.zera.test.excelsior.solutions.ticker.data;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class MovingAverageArrayTest {
    private final MovingAverageArray<BigDecimal> classUnderTest = new MovingAverageArray<>(2, 2);

    @Test
    void test_OK() {
        final LocalDate now = LocalDate.now();
        classUnderTest.push(BigDecimal.TEN, now);
        final Function<List<BigDecimal>, BigDecimal> testAggregator = (array) -> array.get(0);

        assertEquals(BigDecimal.TEN, classUnderTest.pull(testAggregator).get(now));
    }
}