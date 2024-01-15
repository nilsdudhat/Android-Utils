package com.udemy.javaexample.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateUtils {

    public static boolean validateDateFormat(String date, String dateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(date);
            return true;
        } catch (Exception e) {
            Log.d("--date--", "isDateFormat: " + Log.getStackTraceString(e));
            return false;
        }
    }

    public static String manageFullDateForLast7Days(String inputDate, String dateFormat) {
        Log.d("--date--", "manageForYouDateForLast7Days: " + inputDate);
        String outputDate = "";

        SimpleDateFormat todayFormat = new SimpleDateFormat("'Today'", Locale.ENGLISH);
        SimpleDateFormat yesterdayFormat = new SimpleDateFormat("'Yesterday'", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat onlyDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

        Date currentdate = new Date();
        long oneDay = 1000 * 3600 * 24;

        boolean isLast7Days = false;

        for (int i = 0; i < 7; i++) {
            Date tmp = new Date();
            tmp.setTime(currentdate.getTime() - oneDay * i);
            String tempDate = onlyDateFormat.format(tmp);
            if (tempDate.equals(inputDate)) {
                if (i == 0) {
                    outputDate = todayFormat.format(tmp);
                    isLast7Days = true;
                    break;
                } else if (i == 1) {
                    outputDate = yesterdayFormat.format(tmp);
                    isLast7Days = true;
                    break;
                } else {
                    outputDate = dayFormat.format(tmp);
                    isLast7Days = true;
                    break;
                }
            }
        }

        if (!isLast7Days) {
            outputDate = inputDate;
        }
        return outputDate;
    }

    public static ArrayList<String> getRecent7DaysList() {
        ArrayList<String> last7DaysList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String date = dateFormat.format(cal.getTime());
            try {
                Date day = dateFormat.parse(date);
                long oneDay = 1000 * 3600 * 24;

                if (day != null) {
                    day.setTime(day.getTime() - oneDay * i);
                    String strDay = dateFormat.format(day);
                    last7DaysList.add(strDay);
                    Log.d("--last_days--", "createLastWeekForYou: " + strDay);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return last7DaysList;
    }

    public static String getRecent12Months() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);

        Calendar calYesterday = Calendar.getInstance();
        calYesterday.add(Calendar.MONTH, -1);
        String lastMonth = dateFormat.format(calYesterday.getTime());

        Calendar calFirstMonth = Calendar.getInstance();
        calFirstMonth.add(Calendar.MONTH, -12);
        String firstMonth = dateFormat.format(calFirstMonth.getTime());

        Log.d("--week--", "getRecentWeekDate: " + firstMonth + " to " + lastMonth);

        return firstMonth + " to " + lastMonth;
    }


    public static ArrayList<String> getRecent12MonthsList() {
        ArrayList<String> recent12MonthsList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);

        for (int i = 12; i >= 1; i--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);
            String date = dateFormat.format(cal.getTime());
            recent12MonthsList.add(date);
            Log.d("--recent_months----", "getRecent12MonthsList: " + date);
        }

        return recent12MonthsList;
    }

    public static String getFullDateFromLong(long longDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy - HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(new Date(longDate * 1000));
    }


    public static String convertDateFormat(String currentFormat, String requiredFormat, String dateString) {
        String result = "";
        SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());
        Date date = null;
        try {
            date = formatterOld.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;
    }
}
