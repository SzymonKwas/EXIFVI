package sk.exif_vi.Utils;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatterUtil {


    public static Date parseStringToDate(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        try {
            return StringUtils.isNotEmpty(dateString) ? format.parse(dateString) : null;
        } catch (ParseException e) {
            Log.e("Error", "Could not parse date " + dateString);
            return null;
        }
    }
}
