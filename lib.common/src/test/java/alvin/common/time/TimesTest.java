package alvin.common.time;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TimesTest {

    private Date date;

    private LocalDateTime dateTime;

    private ZoneId zoneId;

    @Before
    public void setUp() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(2017, Calendar.OCTOBER, 1, 6, 10, 22);
        c.set(Calendar.MILLISECOND, 0);
        date = c.getTime();

        dateTime = LocalDateTime.of(2017, 10, 1, 6, 10, 22);

        zoneId = ZoneOffset.UTC;
    }

    @Test
    public void test_toLocalDateTime_with_zone() throws Exception {
        LocalDate actual = Times.toLocalDate(date, zoneId);

        assertThat(actual.toString(), is("2017-09-30"));
    }

    @Test
    public void test_toLocalDateTime_without_zone() throws Exception {
        LocalDate localDate = Times.toLocalDate(date, zoneId);

        assertThat(localDate.toString(), is("2017-09-30"));
    }

    @Test
    public void test_toLocalDate_with_zone() throws Exception {
        LocalDateTime actual = Times.toLocalDateTime(date, zoneId);

        assertThat(actual.toString(), is("2017-09-30T22:10:22"));
    }

    @Test
    public void test_toLocalDate_without_zone() throws Exception {
        LocalDateTime actual = Times.toLocalDateTime(date, zoneId);

        assertThat(actual.toString(), is("2017-09-30T22:10:22"));
    }

    @Test
    public void test_toDate_from_local_date_with_zone() throws Exception {
        Date date = Times.toDate(dateTime.toLocalDate(), zoneId);

        assertThat(date.toString(), is("Sun Oct 01 08:00:00 CST 2017"));
    }

    @Test
    public void test_toDate_from_local_date_without_zone() throws Exception {
        Date date = Times.toDate(dateTime.toLocalDate());

        assertThat(date.toString(), is("Sun Oct 01 00:00:00 CST 2017"));
    }

    @Test
    public void test_toDate_from_local_datetime_with_zone() throws Exception {
        Date date = Times.toDate(dateTime, zoneId);

        assertThat(date.toString(), is("Sun Oct 01 14:10:22 CST 2017"));
    }

    @Test
    public void test_toDate_from_local_datetime_without_zone() throws Exception {
        Date date = Times.toDate(dateTime);

        assertThat(date.toString(), is("Sun Oct 01 06:10:22 CST 2017"));
    }
}