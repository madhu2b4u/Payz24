package com.payz24.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mahesh on 1/8/2018.
 */

public class CommonMethods {

    /**
     * setting tag name as app name to display in logcat
     */
    public static final String TAG = "===Payz24";

    private CommonMethods() {
    }

    /**
     * @param message message to print in logcat
     */
    public static void displayLogsInLogCat(String message) {
        Log.i(TAG, "====:::: " + message + " ::::====");
    }

    /**
     * to hide the keyboard
     *
     * @param context context of the activity or fragment
     * @param view    any view in that particular activity or fragment
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * to convert jsonobject to inputstream
     *
     * @param response json object of service
     * @return returns the input stream through reader
     */
    public static Reader getInputStream(String response) {
        InputStream result = new ByteArrayInputStream(response.toString().getBytes(Charset.forName("UTF-8")));
        Reader reader = new InputStreamReader(result);
        return reader;
    }

    public static String convertDateFormat(String dateString) {
        String formattedDate = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(dateString);
            SimpleDateFormat outputFormat = null;
            outputFormat = new SimpleDateFormat("EEEE,MMMM dd,yyyy");
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String convertTimeFormat(String timeToConvert) {
        String formattedTime = null;
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(timeToConvert);
            formattedTime = _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public static String currentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE,MMMM dd,yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String currentDateForServiceCalls() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }


    public static String previousNextDates(int count) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE,MMMM dd,yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, count);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String previousNextDatesForServiceCalls(int count) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_MONTH, count);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static final Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    public static final void setDrawableToView(Context context, int id, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(getDrawable(context, id));
        } else {
            view.setBackgroundDrawable(getDrawable(context, id));
        }
    }

    public static boolean isValidEmailFormat(String emailTxt) {
        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(emailTxt);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isValidPhoneNumber(String phoneTxt) {
        try {
            Pattern pattern = Pattern.compile("^[0-9]\\d{9}$");
            Matcher matcher = pattern.matcher(phoneTxt);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static int getWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
}
