package tw.edu.ntub.imd.justforyou.util.date;

import tw.edu.ntub.imd.justforyou.exception.DateParseException;
import tw.edu.ntub.imd.justforyou.exception.NullParameterException;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
    private CalendarUtils() {

    }

    public static LocalDateTimeUtils.LocalDateTimeWrapper createWrapper() {
        return new LocalDateTimeUtils.LocalDateTimeWrapper();
    }

    public static LocalDateTimeUtils.LocalDateTimeWrapper createWrapper(Calendar calendar) throws NullParameterException {
        return new LocalDateTimeUtils.LocalDateTimeWrapper(calendar);
    }

    public static Calendar now() {
        return Calendar.getInstance();
    }

    public static Calendar getCalendar(long millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        return calendar;
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String format(Calendar calendar, DatePatternBuilder builder) {
        return calendar != null ? DateUtils.format(calendar.getTime(), builder) : "";
    }

    public static Calendar parseIgnoreException(String dateString) {
        try {
            return parse(dateString);
        } catch (DateParseException e) {
            return null;
        }
    }

    public static Calendar parse(String dateString) throws DateParseException {
        for (DatePatternBuilder builder : DatePatternBuilder.DEFAULT_BUILDER_ARRAY) {
            try {
                return parse(dateString, builder);
            } catch (DateParseException ignore) {

            }
        }
        throw new DateParseException(dateString);
    }

    public static Calendar parseIgnoreException(String dateString, DatePatternBuilder builder) {
        try {
            return parse(dateString, builder);
        } catch (DateParseException ignore) {
            return null;
        }
    }

    public static Calendar parse(String dateString, DatePatternBuilder builder) throws DateParseException {
        Calendar calendar = now();
        calendar.setTime(DateUtils.parse(dateString, builder));
        return calendar;
    }
}
