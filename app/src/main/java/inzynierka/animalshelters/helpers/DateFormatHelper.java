package inzynierka.animalshelters.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatHelper {
    public static final String FORMAT_DATE = "dd-MM-yyyy";
    public static final String FORMAT_TIME = "h:mm a";
    public static final String FORMAT_TIME_SEC = "h:mm:ss a";
    public static final String FORMAT_DATE_TIME = "dd-MM-yyyy h:mm a";
    public static final String FORMAT_DATE_TIME_SEC = "dd-MM-yyyy h:mm:ss a";

    public static final String FORMAT_POSTGRES_TIME = "H:mm:ss";
    public static final String FORMAT_POSTGRES_DATE = "yyyy-MM-dd";
    public static final String FORMAT_POSTGRES_TIMESTAMP = "yyyy-MM-dd H:mm:ss";

    public static final int YEAR = Calendar.YEAR;
    public static final int HOUR = Calendar.HOUR;
    public static final int MINUTE = Calendar.MINUTE;
    public static final int SECOND = Calendar.SECOND;
    public static final int DAY = Calendar.DAY_OF_MONTH;
    public static final int MONTH = Calendar.MONTH;

    public static Date dateFromString(String dateStr, String format)
    {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
