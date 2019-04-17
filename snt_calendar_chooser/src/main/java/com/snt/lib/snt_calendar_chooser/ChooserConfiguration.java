package com.snt.lib.snt_calendar_chooser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChooserConfiguration {

    private ChooserMode mode;
    private Calendar currentDate;
    private Calendar maxDate;
    private Calendar minDate;
    private boolean autoDismissDate; //默认不处理 需要自行处理dismiss(), 请在构建的时候设置.isNeedOverrideDismiss(true) 已将弹窗的实体对象存储在ResultItem对象中, 关闭直接取出dismiss即可
    private SelectedDateItem selectedDateItem;
    private ChooseResultListener listener;

    private SimpleDateFormat displayDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private SimpleDateFormat valueDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public ChooserConfiguration(ChooserMode mode, Calendar currentDate, ChooseResultListener listener) {
        this.mode = mode;
        this.currentDate = currentDate;
        this.listener = listener;
    }

    public void setDisplayDateformat(SimpleDateFormat displayDateformat) {
        this.displayDateformat = displayDateformat;
    }

    public void setValueDateformat(SimpleDateFormat valueDateformat) {
        this.valueDateformat = valueDateformat;
    }

    public SimpleDateFormat getDisplayDateformat() {
        return displayDateformat;
    }

    public SimpleDateFormat getValueDateformat() {
        return valueDateformat;
    }

    public SelectedDateItem getSelectedDateItem() {
        return selectedDateItem;
    }

    public void setSelectedDateItem(SelectedDateItem selectedDateItem) {
        this.selectedDateItem = selectedDateItem;
    }

    public ChooserMode getMode() {
        return mode;
    }

    public void setMode(ChooserMode mode) {
        this.mode = mode;
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    public Calendar getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Calendar maxDate) {
        this.maxDate = maxDate;
    }

    public Calendar getMinDate() {
        return minDate;
    }

    public void setMinDate(Calendar minDate) {
        this.minDate = minDate;
    }

    public ChooseResultListener getListener() {
        return listener;
    }

    public void setListener(ChooseResultListener listener) {
        this.listener = listener;
    }

    public boolean getAutoDismissDate() {
        return autoDismissDate;
    }

    public void setAutoDismissDate(boolean autoDismissDate) {
        this.autoDismissDate = autoDismissDate;
    }
}
