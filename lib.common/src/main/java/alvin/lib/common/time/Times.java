package alvin.lib.common.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.annotation.Nonnull;

public final class Times {

    private Times() {
    }

    @Nonnull
    public static LocalDateTime toLocalDateTime(@Nonnull Date date, @Nonnull ZoneId zone) {
        return LocalDateTime.ofInstant(date.toInstant(), zone);
    }

    @Nonnull
    public static LocalDateTime toLocalDateTime(@Nonnull Date date) {
        return toLocalDateTime(date, ZoneId.systemDefault());
    }

    @Nonnull
    public static LocalDate toLocalDate(@Nonnull Date date, @Nonnull ZoneId zone) {
        return toLocalDateTime(date, zone).toLocalDate();
    }

    @Nonnull
    public static LocalDate toLocalDate(@Nonnull Date date) {
        return toLocalDate(date, ZoneId.systemDefault());
    }

    @Nonnull
    public static Date toDate(@Nonnull LocalDateTime dateTime, @Nonnull ZoneId zoneId) {
        return Date.from(dateTime.atZone(zoneId).toInstant());
    }

    @Nonnull
    public static Date toDate(@Nonnull LocalDateTime dateTime) {
        return toDate(dateTime, ZoneId.systemDefault());
    }

    @Nonnull
    public static Date toDate(@Nonnull LocalDate date, @Nonnull ZoneId zoneId) {
        return toDate(date.atStartOfDay(), zoneId);
    }

    @Nonnull
    public static Date toDate(@Nonnull LocalDate date) {
        return toDate(date, ZoneId.systemDefault());
    }
}
