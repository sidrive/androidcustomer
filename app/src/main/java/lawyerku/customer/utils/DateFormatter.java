package lawyerku.customer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String getDate(String sDate, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = new Date();

        try {
            date = formatter.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
        return formatdate.format(date);
    }
}
