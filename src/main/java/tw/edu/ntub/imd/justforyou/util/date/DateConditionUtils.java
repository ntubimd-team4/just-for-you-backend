package tw.edu.ntub.imd.justforyou.util.date;

import tw.edu.ntub.imd.justforyou.exception.DateParseException;

import java.util.Calendar;
import java.util.Date;

public class DateConditionUtils {

    private DateConditionUtils() {

    }

    public static boolean isBetween(String dateString, String firstCondition, String secondCondition) throws DateParseException {
        return isBetween(DateUtils.parse(dateString), DateUtils.parse(firstCondition), DateUtils.parse(secondCondition));
    }

    public static boolean isBetween(Date date, Date firstCondition, Date secondCondition) {
        return isBetween(CalendarUtils.getCalendar(date), CalendarUtils.getCalendar(firstCondition), CalendarUtils.getCalendar(secondCondition));
    }

    public static boolean isBetween(Calendar calendar, Calendar firstCondition, Calendar secondCondition) {
        if (isLarge(firstCondition, secondCondition)) {
            return calendar.before(firstCondition) && calendar.after(secondCondition);
        } else if (isLess(firstCondition, secondCondition)) {
            return calendar.after(firstCondition) && calendar.before(secondCondition);
        } else {
            return isEqual(calendar, firstCondition, secondCondition);
        }
    }

    public static boolean isLarge(Calendar calendar, Calendar condition) {
        return calendar.getTimeInMillis() > condition.getTimeInMillis();
    }

    public static boolean isLess(Calendar calendar, Calendar condition) {
        return calendar.getTimeInMillis() < condition.getTimeInMillis();
    }

    public static boolean isEqual(String dateString, String... dateStringArray) throws DateParseException {
        Date[] dateArray = new Date[dateStringArray.length];
        for (int k = 0; k < dateStringArray.length; k = k + 1) {
            dateArray[k] = DateUtils.parse(dateStringArray[k]);
        }
        return isEqual(DateUtils.parse(dateString), dateArray);
    }

    public static boolean isEqual(Date date, Date... dateArray) {
        Calendar[] calendarArray = new Calendar[dateArray.length];
        for (int k = 0; k < dateArray.length; k = k + 1) {
            calendarArray[k] = CalendarUtils.getCalendar(dateArray[k]);
        }
        return isEqual(CalendarUtils.getCalendar(date), calendarArray);
    }

    public static boolean isEqual(Calendar calendar, Calendar... calendarArray) {
        for (Calendar condition : calendarArray) {
            if (calendar.getTimeInMillis() != condition.getTimeInMillis()) {
                return false;
            }
        }
        return true;
    }
}
