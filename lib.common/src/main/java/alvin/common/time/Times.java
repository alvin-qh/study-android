package alvin.common.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class Times {

    private Times() {
    }

    public static LocalDateTime toLocalDateTime(Date date, ZoneId zone) {
        return LocalDateTime.ofInstant(date.toInstant(), zone);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date, ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(Date date, ZoneId zone) {
        return toLocalDateTime(date, zone).toLocalDate();
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date, ZoneId.systemDefault());
    }

    public static Date toDate(LocalDateTime dateTime, ZoneId zoneId) {
        return Date.from(dateTime.atZone(zoneId).toInstant());
    }

    public static Date toDate(LocalDateTime dateTime) {
        return toDate(dateTime, ZoneId.systemDefault());
    }

    public static Date toDate(LocalDate date, ZoneId zoneId) {
        return toDate(date.atStartOfDay(), zoneId);
    }

    public static Date toDate(LocalDate date) {
        return toDate(date, ZoneId.systemDefault());
    }
}
