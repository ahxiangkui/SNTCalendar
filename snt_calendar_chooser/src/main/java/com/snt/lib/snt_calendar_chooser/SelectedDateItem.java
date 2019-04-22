package com.snt.lib.snt_calendar_chooser;

import java.util.Calendar;

public class SelectedDateItem {

    private ChooserMode chooserMode;

    private DateChoiceDialog needAutoDismissDilog; //是否处理关闭弹窗 此处直接存储dialog实体对象 交给下层处理
    private DateScopeChoiceDialog needAutoDismissScopeDialog; //是否处理关闭弹窗 此处直接存储dialog实体对象 交给下层处理 另一种弹框

    private Calendar currentDate;//单选的日期
    private String currentDateValue;//单选的日期传递给后台格式

    private Calendar startDate;//区域选择-开始日期
    private Calendar endDate;//区域选择-结束日期
    private String startDateValue;//区域选择-开始日期传递给后台格式
    private String endDateValue;//区域选择-结束日期传递给后台格式

    private String displayStr;//文本框显示内容

    public ChooserMode getChooserMode() {
        return chooserMode;
    }

    public void setChooserMode(ChooserMode chooserMode) {
        this.chooserMode = chooserMode;
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentDateValue() {
        return currentDateValue;
    }

    public void setCurrentDateValue(String currentDateValue) {
        this.currentDateValue = currentDateValue;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getStartDateValue() {
        return startDateValue;
    }

    public void setStartDateValue(String startDateValue) {
        this.startDateValue = startDateValue;
    }

    public String getEndDateValue() {
        return endDateValue;
    }

    public void setEndDateValue(String endDateValue) {
        this.endDateValue = endDateValue;
    }

    public String getDisplayStr() {
        return displayStr;
    }

    public void setDisplayStr(String displayStr) {
        this.displayStr = displayStr;
    }

    public DateChoiceDialog getNeedAutoDismissDilog() {
        return needAutoDismissDilog;
    }

    public void setNeedAutoDismissDilog(DateChoiceDialog needAutoDismissDilog) {
        this.needAutoDismissDilog = needAutoDismissDilog;
    }

    public DateScopeChoiceDialog getNeedAutoDismissScopeDialog() {
        return needAutoDismissScopeDialog;
    }

    public void setNeedAutoDismissScopeDialog(DateScopeChoiceDialog needAutoDismissScopeDialog) {
        this.needAutoDismissScopeDialog = needAutoDismissScopeDialog;
    }
}
