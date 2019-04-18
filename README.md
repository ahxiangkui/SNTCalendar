# SNTCalendar
多功能日历选择控件

![测试首页](screenshot/test.png)

## 单选日
```Java
private void setupSingleDay(){
    singleDayTV.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSingleDayChooser().show();//显示日历
        }
    });
    singleDayLastTV.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSingleDayChooser().selectLastDate();//显示上一日
        }
    });
    singleDayNextTV.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSingleDayChooser().selectNextDate();//显示下一日
        }
    });
    getSingleDayChooser().currentDate();//显示当前日期
}
private CalendarChooser getSingleDayChooser(){
    if (singleDayChooser == null){
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DATE, 10);
        CalendarChooser.Builder builder = new CalendarChooser.Builder(this)
                .mode(ChooserMode.DAY)
                .currentDate(Calendar.getInstance())
                .maxDate(maxDate)
                .displayDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA))
                .resultListener(new ChooseResultListener() {
                    @Override
                    public void choiceResult(SelectedDateItem result) {
                        singleDayTV.setText(result.getDisplayStr());
                    }
                });
        singleDayChooser = builder.build();
    }
    return singleDayChooser;
}
```
![单选日](screenshot/single_day.png)

## 单选月
![单选月](screenshot/single_month.png)

## 选择周
![单选周](screenshot/a_week.png)

## 选择日范围
![选择日范围](screenshot/scope_days.png)

## 选择月范围
![选择月范围](screenshot/scope_month.png)
