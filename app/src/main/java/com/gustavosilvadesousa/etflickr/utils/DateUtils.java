package com.gustavosilvadesousa.etflickr.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatTimestamp(String timestamp) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        long dateLong = Long.parseLong(timestamp);
        return df.format(new Date(dateLong*1000));
    }
}
