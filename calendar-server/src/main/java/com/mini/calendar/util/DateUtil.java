package com.mini.calendar.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author songjiuhua
 * Created by 2021/1/11 11:28
 */
public class DateUtil {

    public final static String FILE_NAME = "MMddHHmmssSSS";
    public final static String DEFAULT_PATTERN = "yyyy-MM-dd";
    public final static String DIR_PATTERN = "yyyy/MM/dd/";
    public final static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String TIMESTAMP_CHILD_PATTERN = "yyyy/MM/dd HH:mm";
    public final static String TIMES_PATTERN = "HH:mm:ss";

    public static String formatDate(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
