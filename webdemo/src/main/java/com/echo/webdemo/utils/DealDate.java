package com.echo.webdemo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DealDate {
    public static String TimeStampToString(long timestamp){
        String date = "";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = sdf.format(timestamp*1000);

        return date;

    }
}
