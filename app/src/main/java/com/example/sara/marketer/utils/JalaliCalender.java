package com.example.sara.marketer.utils;

import android.util.Log;

/**
 * Created by Software1 on 1/10/2017.
 */

public class JalaliCalender {

    public static int gregorianDaysInMonth[] = {31, 28, 31, 30, 31, 30, 31,
            31, 30, 31, 30, 31};
    public static int jalaliDaysInMonth[] = {31, 31, 31, 31, 31, 31, 30, 30,
            30, 30, 30, 29};

    public static YearMonthDate gregorianToJalali(YearMonthDate gregorian) {

        if (gregorian.getMonth() > 11 || gregorian.getMonth() < -11) {
            throw new IllegalArgumentException();
        }
        int jalaliYear;
        int jalaliMonth;
        int jalaliDay;

        int gregorianDayNo, jalaliDayNo;
        int jalaliNP;
        int i;

        gregorian.setYear(gregorian.getYear() - 1600);
        gregorian.setDate(gregorian.getDate() - 1);

        gregorianDayNo = 365 * gregorian.getYear()
                + (int) Math.floor((gregorian.getYear() + 3) / 4)
                - (int) Math.floor((gregorian.getYear() + 99) / 100)
                + (int) Math.floor((gregorian.getYear() + 399) / 400);
        for (i = 0; i < gregorian.getMonth(); ++i) {
            gregorianDayNo += gregorianDaysInMonth[i];
        }

        if (gregorian.getMonth() > 1
                && ((gregorian.getYear() % 4 == 0 && gregorian.getYear() % 100 != 0) || (gregorian
                .getYear() % 400 == 0))) {
            ++gregorianDayNo;
        }

        gregorianDayNo += gregorian.getDate();

        jalaliDayNo = gregorianDayNo - 79;

        jalaliNP = (int) Math.floor(jalaliDayNo / 12053);
        jalaliDayNo = jalaliDayNo % 12053;

        jalaliYear = 979 + 33 * jalaliNP + 4 * (int) (jalaliDayNo / 1461);
        jalaliDayNo = jalaliDayNo % 1461;

        if (jalaliDayNo >= 366) {
            jalaliYear += (int) Math.floor((jalaliDayNo - 1) / 365);
            jalaliDayNo = (jalaliDayNo - 1) % 365;
        }

        for (i = 0; i < 11 && jalaliDayNo >= jalaliDaysInMonth[i]; ++i) {
            jalaliDayNo -= jalaliDaysInMonth[i];
        }
        jalaliMonth = i;
        jalaliDay = jalaliDayNo + 1;

        return new YearMonthDate(jalaliYear, jalaliMonth + 1, jalaliDay);
    }

    public static YearMonthDate jalaliToGregorian(YearMonthDate jalali) {
        if (jalali.getMonth() > 11 || jalali.getMonth() < -11) {
            throw new IllegalArgumentException();
        }

        int gregorianYear;
        int gregorianMonth;
        int gregorianDay;

        int gregorianDayNo, jalaliDayNo;
        int leap;

        int i;
        jalali.setYear(jalali.getYear() - 979);
        jalali.setDate(jalali.getDate() - 1);

        jalaliDayNo = 365 * jalali.getYear() + (int) (jalali.getYear() / 33)
                * 8 + (int) Math.floor(((jalali.getYear() % 33) + 3) / 4);
        for (i = 0; i < jalali.getMonth(); ++i) {
            jalaliDayNo += jalaliDaysInMonth[i];
        }

        jalaliDayNo += jalali.getDate();

        gregorianDayNo = jalaliDayNo + 79;

        gregorianYear = 1600 + 400 * (int) Math.floor(gregorianDayNo / 146097); /*
                                                                                 * 146097
                                                                                 * =
                                                                                 * 365
                                                                                 * *
                                                                                 * 400
                                                                                 * +
                                                                                 * 400
                                                                                 * /
                                                                                 * 4
                                                                                 * -
                                                                                 * 400
                                                                                 * /
                                                                                 * 100
                                                                                 * +
                                                                                 * 400
                                                                                 * /
                                                                                 * 400
                                                                                 */
        gregorianDayNo = gregorianDayNo % 146097;

        leap = 1;
        if (gregorianDayNo >= 36525) /* 36525 = 365*100 + 100/4 */ {
            gregorianDayNo--;
            gregorianYear += 100 * (int) Math.floor(gregorianDayNo / 36524); /*
                                                                             * 36524
                                                                             * =
                                                                             * 365
                                                                             * *
                                                                             * 100
                                                                             * +
                                                                             * 100
                                                                             * /
                                                                             * 4
                                                                             * -
                                                                             * 100
                                                                             * /
                                                                             * 100
                                                                             */
            gregorianDayNo = gregorianDayNo % 36524;

            if (gregorianDayNo >= 365) {
                gregorianDayNo++;
            } else {
                leap = 0;
            }
        }

        gregorianYear += 4 * (int) Math.floor(gregorianDayNo / 1461); /*
                                                                     * 1461 =
                                                                     * 365*4 +
                                                                     * 4/4
                                                                     */
        gregorianDayNo = gregorianDayNo % 1461;

        if (gregorianDayNo >= 366) {
            leap = 0;

            gregorianDayNo--;
            gregorianYear += (int) Math.floor(gregorianDayNo / 365);
            gregorianDayNo = gregorianDayNo % 365;
        }

        for (i = 0; gregorianDayNo >= gregorianDaysInMonth[i]
                + ((i == 1 && leap == 1) ? i : 0); i++) {
            gregorianDayNo -= gregorianDaysInMonth[i]
                    + ((i == 1 && leap == 1) ? i : 0);
        }
        gregorianMonth = i;
        gregorianDay = gregorianDayNo + 1;

        return new YearMonthDate(gregorianYear, gregorianMonth, gregorianDay);

    }

    public static class YearMonthDate {

        public YearMonthDate(int year, int month, int date) {
            this.year = year;
            this.month = month;
            this.date = date;
        }

        private int year;
        private int month;
        private int date;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public String toString() {
            return getYear() + "/" + (month > 9 ? month : "0" + month) + "/" + (date > 9 ? date : "0" + date);
        }
    }


    public static String convertJalaliDate(String dateTime) {
        int year = Integer.valueOf(dateTime.substring(0, 4));
        int month = Integer.valueOf(dateTime.substring(5, 7));
        int day = Integer.valueOf(dateTime.substring(8, 10));
//        String time = dateTime.substring(12, 20);
        Log.i("", "convertDate: " + year + " " + month + " " + day);
        String jYear = String.valueOf(jalaliToGregorian(new JalaliCalender.YearMonthDate(year, month, day)).getYear());
        String jMonth = String.valueOf(jalaliToGregorian(new JalaliCalender.YearMonthDate(year, month, day)).getMonth()+1);
        if (jMonth.length() == 1) {
            jMonth = "0" + jMonth;
        }
        String jDay = String.valueOf(jalaliToGregorian(new JalaliCalender.YearMonthDate(year, month, day)).getDate()+1);
        if (jDay.length() == 1) {
            jDay = "0" + jDay;
        }
        return String.valueOf(jYear + "-" + jMonth + "-" + jDay);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//            return format.parse(String.valueOf(jYear + "-" + jMonth + "-" + jDay));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

}
