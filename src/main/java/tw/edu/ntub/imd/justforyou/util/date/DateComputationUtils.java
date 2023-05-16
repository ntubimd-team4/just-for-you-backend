package tw.edu.ntub.imd.justforyou.util.date;

import java.util.Calendar;
import java.util.Date;

public class DateComputationUtils {
    private static final float ONE_DAY_HOUR = 24.0f;
    private static final float ONE_HOUR_MINUTE = 60.0f;
    private static final float ONE_MINUTE_SECOND = 60.0f;
    private static final float ONE_SECOND_MILLISECOND = 1_000.0f;

    private DateComputationUtils() {

    }

    // 計算兩日期差了幾天
    public static double getTwoDateSubtractionDay(Calendar first, Date second) {
        return getTwoDateSubtractionDay(first, CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾天
    public static double getTwoDateSubtractionDay(Date first, Calendar second) {
        return getTwoDateSubtractionDay(CalendarUtils.getCalendar(first), second);
    }

    // 計算兩日期差了幾天
    public static double getTwoDateSubtractionDay(Date first, Date second) {
        return getTwoDateSubtractionDay(CalendarUtils.getCalendar(first), CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾天
    public static double getTwoDateSubtractionDay(Calendar first, Calendar second) {
        return getTwoDateSubtractionHour(first, second) / ONE_DAY_HOUR;
    }

    // 計算兩日期差了幾小時
    public static double getTwoDateSubtractionHour(Calendar first, Date second) {
        return getTwoDateSubtractionHour(first, CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾小時
    public static double getTwoDateSubtractionHour(Date first, Calendar second) {
        return getTwoDateSubtractionHour(CalendarUtils.getCalendar(first), second);
    }

    // 計算兩日期差了幾小時
    public static double getTwoDateSubtractionHour(Date first, Date second) {
        return getTwoDateSubtractionHour(CalendarUtils.getCalendar(first), CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾小時
    public static double getTwoDateSubtractionHour(Calendar first, Calendar second) {
        return getTwoDateSubtractionMinute(first, second) / ONE_HOUR_MINUTE;
    }

    // 計算兩日期差了幾分鐘
    public static double getTwoDateSubtractionMinute(Calendar first, Date second) {
        return getTwoDateSubtractionMinute(first, CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾分鐘
    public static double getTwoDateSubtractionMinute(Date first, Calendar second) {
        return getTwoDateSubtractionMinute(CalendarUtils.getCalendar(first), second);
    }

    // 計算兩日期差了幾分鐘
    public static double getTwoDateSubtractionMinute(Date first, Date second) {
        return getTwoDateSubtractionMinute(CalendarUtils.getCalendar(first), CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾分鐘
    public static double getTwoDateSubtractionMinute(Calendar first, Calendar second) {
        return getTwoDateSubtractionSecond(first, second) / ONE_MINUTE_SECOND;
    }

    // 計算兩日期差了幾秒
    public static double getTwoDateSubtractionSecond(Calendar first, Date second) {
        return getTwoDateSubtractionSecond(first, CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾秒
    public static double getTwoDateSubtractionSecond(Date first, Calendar second) {
        return getTwoDateSubtractionSecond(CalendarUtils.getCalendar(first), second);
    }

    // 計算兩日期差了幾秒
    public static double getTwoDateSubtractionSecond(Date first, Date second) {
        return getTwoDateSubtractionSecond(CalendarUtils.getCalendar(first), CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾秒
    public static double getTwoDateSubtractionSecond(Calendar first, Calendar second) {
        return getTwoDateSubtractionMS(first, second) / ONE_SECOND_MILLISECOND;
    }

    // 計算兩日期差了幾豪秒
    public static long getTwoDateSubtractionMS(Calendar first, Date second) {
        return getTwoDateSubtractionMS(first, CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾豪秒
    public static long getTwoDateSubtractionMS(Date first, Calendar second) {
        return getTwoDateSubtractionMS(CalendarUtils.getCalendar(first), second);
    }

    // 計算兩日期差了幾豪秒
    public static long getTwoDateSubtractionMS(Date first, Date second) {
        return getTwoDateSubtractionMS(CalendarUtils.getCalendar(first), CalendarUtils.getCalendar(second));
    }

    // 計算兩日期差了幾豪秒
    public static long getTwoDateSubtractionMS(Calendar first, Calendar second) {
        return first.getTimeInMillis() - second.getTimeInMillis();
    }
}
