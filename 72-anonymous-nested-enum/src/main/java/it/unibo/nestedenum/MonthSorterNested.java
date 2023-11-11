package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    public enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        Month(final int days) {
            this.days = days;
        }

        public static Month fromString(String monthString) {
            Objects.requireNonNull(monthString);
            Month match = null;
            monthString = monthString.toLowerCase();
            //Check if monthString matches one or more months in Month enum.
            for (Month month : Month.values()) {
                final String lowercaseMonth = month.toString().toLowerCase();
                if (lowercaseMonth.equals(monthString)) {
                    return month;
                } else if (lowercaseMonth.startsWith(monthString)) {
                    if (match != null) {
                        throw new IllegalArgumentException("Argument is ambiguous, there is more than one month starting with " + monthString);
                    }
                    match = month;
                }
            }

            if (match != null) {
                return match;
            } else {
                throw new IllegalArgumentException("No month found starting with " + monthString);
            }
        }
    }

    private static class SortByMonthOrder implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return Month.fromString(s1).compareTo(Month.fromString(s2));
        }

    }

    private static class SortByDate implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return Integer.compare(Month.fromString(s1).days, Month.fromString(s2).days);
        }

    }
    
    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }
}
