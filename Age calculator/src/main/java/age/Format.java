package age;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

class Format {
    static PeriodFormatter yearsMonthsDays = new PeriodFormatterBuilder()
            .printZeroAlways()
            .appendYears()
            .appendSuffix(" год", " года")
            .appendSeparator(", ")
            .printZeroAlways()
            .appendMonths()
            .appendSuffix(" месяц", " месяцев")
            .appendSeparator(" и ")
            .printZeroAlways()
            .appendDays()
            .appendSuffix(" день", " дней")
            .toFormatter();

    static PeriodFormatter yearsMonthsDays2 = new PeriodFormatterBuilder()
            .printZeroAlways()
            .appendYears()
            .appendSuffix(" лет")
            .appendSeparator(", ")
            .printZeroAlways()
            .appendMonths()
            .appendSuffix(" месяц", " месяцев")
            .appendSeparator(" и ")
            .printZeroAlways()
            .appendDays()
            .appendSuffix(" день", " дней")
            .toFormatter();

    static PeriodFormatter yearsMonthsDays3 = new PeriodFormatterBuilder()
            .printZeroAlways()
            .appendYears()
            .appendSuffix(" год")
            .appendSeparator(", ")
            .printZeroAlways()
            .appendMonths()
            .appendSuffix(" месяц", " месяцев")
            .appendSeparator(" и ")
            .printZeroAlways()
            .appendDays()
            .appendSuffix(" день", " дней")
            .toFormatter();
}
