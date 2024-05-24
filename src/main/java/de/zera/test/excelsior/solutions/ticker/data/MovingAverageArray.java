package de.zera.test.excelsior.solutions.ticker.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * fifo array
 *
 * @param <T> it makes no sense to do this generic according to the task, but still it looks prettier
 */
public class MovingAverageArray<T> {
    private final Map<LocalDate, T> values;
    private final int days;
    private final int number;
    private boolean complete; // speedup
    private LocalDate earlyDate; // speedup

    public MovingAverageArray(int days, int number) {
        this.days = days;
        this.number = number;
        // store last n elements for m sequential days
        // probably we can use TreeMap here, but without calculation I suppose that quantity of inserts might be much
        // more significant than collecting operations
        this.values = new HashMap<>(number + days);
        complete = false; // default value only for better understanding
    }

    /**
     * push value for date, it'd be stored only if this date is in the M (days) + N (number) last days.
     * @param value value to store
     * @param date  date to store
     */
    public void push(@NotNull T value, @NotNull LocalDate date) {
        final boolean currentHeadIsBefore = earlyDate == null || earlyDate.isBefore(date);
        if (!complete || currentHeadIsBefore) {
            complete = values.size() == number + days;
            if (complete) {
                values.remove(earlyDate);
            }
            values.put(date, value);
            earlyDate = currentHeadIsBefore ? date : earlyDate;
        }
    }

    /**
     * retrieve aggregated data with aggregation function
     * @param aggregator means average in this case
     * @return prepared data
     */
    public Map<LocalDate, T> pull(Function<List<T>, T> aggregator) {
        final LinkedHashMap<LocalDate, T> sortedValues = values.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Collections.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        return convert(sortedValues, aggregator);
    }

    private Map<LocalDate, T> convert(LinkedHashMap<LocalDate, T> sortedValues, Function<List<T>, T> aggregator) {
        final Map<LocalDate, T> result = new LinkedHashMap<>(days, 1f);
        final Iterator<Map.Entry<LocalDate, T>> sortedValuesIterator = sortedValues.entrySet().iterator();
        final List<T> values = new ArrayList<>(sortedValues.values());
        int maxSize = values.size();
        for (int day = 0; day < days; day++) {
            // prevent data less than required report size
            if (sortedValuesIterator.hasNext()) {
                final Map.Entry<LocalDate, T> lastNDaysValue = sortedValuesIterator.next();
                final int topBorder = number + day;
                if (topBorder <= maxSize) {
                    result.put(lastNDaysValue.getKey(), aggregator.apply(values.subList(day, topBorder)));
                } else if (day == maxSize) {
                    // undefined behavior, we don't have enough numbers of records to calculate average
                    // because this is not a business task simpy ignore
                } else {
                    // undefined behavior, we don't have enough numbers of records to calculate average
                    // because this is not a business task simpy cut the number
                    result.put(lastNDaysValue.getKey(), aggregator.apply(values.subList(day, maxSize)));
                }
            }
        }
        return result;
    }
}
