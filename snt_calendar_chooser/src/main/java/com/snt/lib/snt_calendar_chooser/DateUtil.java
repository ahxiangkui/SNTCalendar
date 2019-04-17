package com.snt.lib.snt_calendar_chooser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static int dateEquals(Calendar date1, Calendar date2){
        if (date1.get(Calendar.YEAR) > date2.get(Calendar.YEAR)){
            return 1;
        }else if (date1.get(Calendar.YEAR) < date2.get(Calendar.YEAR)){
            return -1;
        }else {
            if (date1.get(Calendar.MONTH) > date2.get(Calendar.MONTH)){
                return 1;
            }else if (date1.get(Calendar.MONTH) < date2.get(Calendar.MONTH)){
                return -1;
            }else {

                return Integer.compare(date1.get(Calendar.DAY_OF_MONTH), date2.get(Calendar.DAY_OF_MONTH));
            }
        }
    }


    public static int dateMonthEquals(Calendar date1, Calendar date2){

        if (date1.get(Calendar.YEAR) > date2.get(Calendar.YEAR)){
            return 1;
        }else if (date1.get(Calendar.YEAR) < date2.get(Calendar.YEAR)){
            return -1;
        }else {
            return Integer.compare(date1.get(Calendar.MONTH), date2.get(Calendar.MONTH));
        }
    }


    public static int monthDistance(Calendar date1, Calendar date2){

        Calendar startDate;
        Calendar endDate;
        if (date1.getTimeInMillis() < date2.getTimeInMillis()){
            startDate = date1;
            endDate = date2;
        }else {
            startDate = date2;
            endDate = date1;
        }
        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR)) * 12 + endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);

    }

    public static int daysDistance(Calendar date1, Calendar date2){
        Calendar startDate;
        Calendar endDate;
        if (date1.getTimeInMillis() < date2.getTimeInMillis()){
            startDate = Calendar.getInstance();
            startDate.set(date1.get(Calendar.YEAR), date1.get(Calendar.MONTH), date1.get(Calendar.DAY_OF_MONTH), 0, 0);
            endDate = Calendar.getInstance();
            endDate.set(date2.get(Calendar.YEAR), date2.get(Calendar.MONTH), date2.get(Calendar.DAY_OF_MONTH), 1, 0);
        }else {
            startDate = Calendar.getInstance();
            startDate.set(date2.get(Calendar.YEAR), date2.get(Calendar.MONTH), date2.get(Calendar.DAY_OF_MONTH), 0, 0);

            endDate = Calendar.getInstance();
            endDate.set(date1.get(Calendar.YEAR), date1.get(Calendar.MONTH), date1.get(Calendar.DAY_OF_MONTH), 1, 0);
        }

        long msDiff = endDate.getTimeInMillis() - startDate.getTimeInMillis();
        return (int) TimeUnit.MILLISECONDS.toDays(msDiff);
    }

//    public static SelectedDateItem showLastDate(ChooserConfiguration configuration, SelectedDateItem selectedDateItem){
//
//        if (configuration.getMode() != ChooserMode.DAY
//                && configuration.getMode() != ChooserMode.MONTH){
//            return selectedDateItem;
//        }
//
//        if (configuration.getMode() == ChooserMode.MONTH){
//
//            if (configuration.getMinDate() != null
//                    && DateUtil.dateMonthEquals(configuration.getMinDate(), selectedDateItem.getStartDate()) >= 0){
//                return selectedDateItem;
//            }
//        }else {
//
//            if (configuration.getMinDate() != null
//                    && DateUtil.dateEquals(configuration.getMinDate(), selectedDateItem.getStartDate()) >= 0){
//                return selectedDateItem;
//            }
//        }
//
//
//        Calendar date = Calendar.getInstance();
//        date.setTime(selectedDateItem.getStartDate().getTime());
//        if (configuration.getMode() == ChooserMode.MONTH){
//
//            date.add(Calendar.MONTH, -1);
//        }else {
//
//            date.add(Calendar.DATE, -1);
//        }
//
//        selectedDateItem.setStartDate(date);
//        selectedDateItem.setDisplayStr(configuration.getDisplayDateformat().format(date.getTime()));
//        selectedDateItem.setStartDateValue(configuration.getValueDateformat().format(date.getTime()));
//
//        return selectedDateItem;
//    }

//    public static SelectedDateItem showNextDate(ChooserConfiguration configuration, SelectedDateItem selectedDateItem){
//
//        if (configuration.getMode() != ChooserMode.DAY
//                && configuration.getMode() != ChooserMode.MONTH){
//            return selectedDateItem;
//        }
//
//        if (configuration.getMode() == ChooserMode.MONTH){
//
//            if (configuration.getMaxDate() != null
//                    && DateUtil.dateMonthEquals(configuration.getMaxDate(), selectedDateItem.getStartDate()) <= 0){
//                return selectedDateItem;
//            }
//        }else {
//
//            if (configuration.getMaxDate() != null
//                    && DateUtil.dateEquals(configuration.getMaxDate(), selectedDateItem.getStartDate()) <= 0){
//                return selectedDateItem;
//            }
//        }
//
//
//        Calendar date = Calendar.getInstance();
//        date.setTime(selectedDateItem.getStartDate().getTime());
//        if (configuration.getMode() == ChooserMode.MONTH){
//
//            date.add(Calendar.MONTH, +1);
//        }else {
//
//            date.add(Calendar.DATE, +1);
//        }
//
//        selectedDateItem.setStartDate(date);
//        selectedDateItem.setDisplayStr(configuration.getDisplayDateformat().format(date.getTime()));
//        selectedDateItem.setStartDateValue(configuration.getValueDateformat().format(date.getTime()));
//
//        return selectedDateItem;
//    }
}
