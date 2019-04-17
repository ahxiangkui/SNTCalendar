package com.snt.lib.snt_calendar_chooser;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarChooser {

    private ChooserConfiguration configuration;
    private ExpandedBottomSheetDialog dialog;

    public CalendarChooser(@NonNull Context context, ChooserConfiguration configuration) {
        this.configuration = configuration;
        switch (configuration.getMode()) {
            case DAY:
                dialog = new DateChoiceDialog(context, configuration, false);
                break;

            case WEEK:
                dialog = new DateChoiceDialog(context, configuration, true);
                break;

            case MONTH:
                dialog = new MonthDateChoiceDialog(context, configuration);
                break;
            case DAY_SCOPE:
                dialog = new DateScopeChoiceDialog(context, configuration);
                break;
            case MONTH_SCOPE:
                dialog = new MonthScopeDateChoiceDialog(context, configuration);
                break;
        }
    }

    public void currentDate() {
        if (dialog instanceof DateChoiceDialog) {
            ((DateChoiceDialog) dialog).currentDate();
        } else if (dialog instanceof MonthDateChoiceDialog) {
            ((MonthDateChoiceDialog) dialog).currentDate();
        }else if (dialog instanceof DateScopeChoiceDialog)  {
            ((DateScopeChoiceDialog) dialog).currentDate();
        }else if (dialog instanceof MonthScopeDateChoiceDialog){
            ((MonthScopeDateChoiceDialog) dialog).currentDate();
        }
    }

    public void selectLastDate() {

        switch (configuration.getMode()) {
            case DAY:
                if (dialog != null) {
                    ((DateChoiceDialog) dialog).selectLastDate();
                }
                break;
            case MONTH:
                if (dialog != null) {
                    ((MonthDateChoiceDialog) dialog).selectLastDate();
                }
                break;
            case WEEK:
                if (dialog != null){
                    ((DateChoiceDialog) dialog).selectLastDate();
                }
                break;
        }
    }

    public void selectNextDate() {

        switch (configuration.getMode()) {
            case DAY:
                if (dialog != null) {
                    ((DateChoiceDialog) dialog).selectNextDate();
                }
                break;
            case MONTH:
                if (dialog != null) {
                    ((MonthDateChoiceDialog) dialog).selectNextDate();
                }
                break;
            case WEEK:
                if (dialog != null){
                    ((DateChoiceDialog) dialog).selectNextDate();
                }
                break;
        }

    }

    public void show() {
        if (dialog != null) {

            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {

            dialog.dismiss();
        }
    }

    public static final class Builder {

        private Context context;
        private ChooserMode mode;
        private Calendar currentDate;//单选类控件该字段为默认当前日期，区域类选择控件不需此字段
        private Calendar maxDate;
        private boolean autoDismissDate;
        private Calendar minDate;
        private Calendar selectStartDate;//区域类选择控件默认选择开始日期
        private Calendar selectEndDate;  //区域类选择控件默认选择结束日期
        private SimpleDateFormat valueDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        private SimpleDateFormat displayDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        private ChooseResultListener listener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder mode(ChooserMode mode) {
            this.mode = mode;
            return this;
        }

        public Builder valueDateFormat(SimpleDateFormat valueDateFormat) {
            this.valueDateFormat = valueDateFormat;
            return this;
        }

        public Builder displayDateFormat(SimpleDateFormat displayDateFormat) {
            this.displayDateFormat = displayDateFormat;
            return this;
        }

        //单选类控件该字段为默认当前日期，区域类选择控件不需此字段
        public Builder currentDate(Calendar currentDate) {
            this.currentDate = currentDate;
            return this;
        }

        public Builder maxDate(Calendar maxDate) {
            this.maxDate = maxDate;
            return this;
        }

        /**
         * 是否需要覆写dismiss方法
         */
        public Builder isNeedOverrideDismiss(boolean isAutoDismissDate) {
            this.autoDismissDate = isAutoDismissDate;
            return this;
        }

        public Builder minDate(Calendar minDate) {
            this.minDate = minDate;
            return this;
        }

        //区域类选择控件默认选择开始日期
        public Builder selectStartDate(Calendar startDate) {
            this.selectStartDate = startDate;
            return this;
        }

        //区域类选择控件默认选择结束日期
        public Builder selectEndDate(Calendar endDate) {
            this.selectEndDate = endDate;
            return this;
        }

        public Builder resultListener(ChooseResultListener listener) {
            this.listener = listener;
            return this;
        }

        public CalendarChooser build() {

            if (mode == null) {
                mode = ChooserMode.DAY;
            }
            if (currentDate == null) {
                currentDate = Calendar.getInstance();
            }

            ChooserConfiguration config = new ChooserConfiguration(mode, currentDate, listener);
            if (minDate != null) {
                config.setMinDate(minDate);
            }
            if (maxDate != null) {
                config.setMaxDate(maxDate);
            }
            if (autoDismissDate)
                config.setAutoDismissDate(autoDismissDate);

            config.setDisplayDateformat(displayDateFormat);
            config.setValueDateformat(valueDateFormat);

            SelectedDateItem selectedDateItem = new SelectedDateItem();
            if (selectStartDate != null){
                selectedDateItem.setStartDate(selectStartDate);
            }
            if (selectEndDate != null){
                selectedDateItem.setEndDate(selectEndDate);
            }
            config.setSelectedDateItem(selectedDateItem);
            CalendarChooser chooser = new CalendarChooser(context, config);
            return chooser;

        }
    }
}
