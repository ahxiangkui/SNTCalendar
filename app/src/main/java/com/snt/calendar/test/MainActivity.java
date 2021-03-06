package com.snt.calendar.test;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.snt.lib.snt_calendar_chooser.CalendarChooser;
import com.snt.lib.snt_calendar_chooser.ChooseResultListener;
import com.snt.lib.snt_calendar_chooser.ChooserMode;
import com.snt.lib.snt_calendar_chooser.DateItemThemeSetupCallback;
import com.snt.lib.snt_calendar_chooser.SelectedDateItem;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView singleDayTV;
    private TextView singleDayLastTV;
    private TextView singleDayNextTV;
    private TextView singleMonthTV;
    private TextView singleMonthLastTV;
    private TextView singleMonthNextTV;
    private TextView singleWeekTV;
    private TextView singleWeekLastTV;
    private TextView singleWeekNextTV;
    private TextView scopeDayTV;
    private TextView scopeMonthTV;
    private TextView singleDayInWeekendTV;

    private CalendarChooser singleDayChooser;
    private CalendarChooser singleMonthChooser;
    private CalendarChooser singleWeekChooser;
    private CalendarChooser scopeDayChooser;
    private CalendarChooser scopeMonthChooser;
    private CalendarChooser singleDayInWeekendChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singleDayTV       = findViewById(R.id.txt_single_day);
        singleDayLastTV   = findViewById(R.id.txt_single_day_last);
        singleDayNextTV   = findViewById(R.id.txt_single_day_next);
        setupSingleDay();

        singleMonthTV     = findViewById(R.id.txt_single_month);
        singleMonthLastTV = findViewById(R.id.txt_single_month_last);
        singleMonthNextTV = findViewById(R.id.txt_single_month_next);
        setupSingleMonth();

        singleWeekTV      = findViewById(R.id.txt_single_week);
        singleWeekLastTV  = findViewById(R.id.txt_single_week_last);
        singleWeekNextTV  = findViewById(R.id.txt_single_week_next);
        setupSingleWeek();

        scopeDayTV        = findViewById(R.id.txt_scope_day);
        setupScopeDay();

        scopeMonthTV      = findViewById(R.id.txt_scope_month);
        setupScopeMonth();

        singleDayInWeekendTV = findViewById(R.id.txt_single_day_in_weekend);
        setupSingleDayInWeekend();
    }

    //////////////////////// --单选日-- /////////////////////////
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
                    .tintAlpha(15)
                    .tintColor(Color.parseColor("#1777CB"))
                    .confirmBtnColor(Color.parseColor("#DDBA76"))
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
    /////////////////////// --单选日-- //////////////////////////



    ////////////////////// --单选月-- ///////////////////////////
    private void setupSingleMonth(){

        singleMonthTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingleMonthChooser().show();//显示日历
            }
        });
        singleMonthLastTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingleMonthChooser().selectLastDate();//显示上一月
            }
        });
        singleMonthNextTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingleMonthChooser().selectNextDate();//显示下一月
            }
        });

        getSingleMonthChooser().currentDate();//显示当前日期

    }
    private CalendarChooser getSingleMonthChooser(){
        if (singleMonthChooser == null){
            Calendar maxDate = Calendar.getInstance();
            maxDate.add(Calendar.MONTH, 10);
            Calendar minDate = Calendar.getInstance();
            minDate.add(Calendar.MONTH, -10);
            CalendarChooser.Builder builder = new CalendarChooser.Builder(this)
                    .mode(ChooserMode.MONTH)
                    .currentDate(Calendar.getInstance())
                    .maxDate(maxDate)
                    .minDate(minDate)
                    .tintAlpha(15)
                    .tintColor(Color.parseColor("#1777CB"))
                    .confirmBtnColor(Color.parseColor("#DDBA76"))
                    .displayDateFormat(new SimpleDateFormat("yyyy-MM", Locale.CHINA))
                    .calendarDialogLayout(R.layout.m_month_date_choice_dialog)
                    .dateItemLayout(R.layout.item_month_date_choice_m)
                    .themeSetupCallback(new DateItemThemeSetupCallback() {
                        @Override
                        public void setupItemTheme(int itemLayoutRes, View holderItemView, int themeType) {

                            View contentV = holderItemView.findViewById(R.id.ll_content);
                            View selectV = holderItemView.findViewById(R.id.fl_select);
                            TextView titleTV = holderItemView.findViewById(R.id.txt_title);
                            TextView unitTV = holderItemView.findViewById(R.id.txt_unit);

                            switch (themeType){
                                case CalendarChooser.THEME_ITEM_SELECT_SINGLE:
                                    titleTV.setTextColor(Color.WHITE);
                                    unitTV.setTextColor(Color.WHITE);
                                    contentV.setBackgroundColor(Color.TRANSPARENT);
                                    selectV.setBackgroundResource(R.drawable.bg_date_item_select);
                                    break;
                                case CalendarChooser.THEME_ITEM_DISABLE:
                                    titleTV.setTextColor(ContextCompat.getColor(MainActivity.this, com.snt.lib.snt_calendar_chooser.R.color.dateTxtDisableColor));
                                    unitTV.setTextColor(ContextCompat.getColor(MainActivity.this, com.snt.lib.snt_calendar_chooser.R.color.dateTxtDisableColor));
                                    contentV.setBackgroundColor(Color.TRANSPARENT);
                                    selectV.setBackgroundColor(Color.TRANSPARENT);
                                    break;
                                default:
                                    titleTV.setTextColor(ContextCompat.getColor(MainActivity.this, com.snt.lib.snt_calendar_chooser.R.color.dateTxtColor));
                                    unitTV.setTextColor(ContextCompat.getColor(MainActivity.this, com.snt.lib.snt_calendar_chooser.R.color.dateTxtColor));
                                    contentV.setBackgroundColor(Color.TRANSPARENT);
                                    selectV.setBackgroundColor(Color.TRANSPARENT);
                                    break;
                            }
                        }
                    })
                    .resultListener(new ChooseResultListener() {
                        @Override
                        public void choiceResult(SelectedDateItem result) {
                            singleMonthTV.setText(result.getDisplayStr());
                        }
                    });

            singleMonthChooser = builder.build();
        }

        return singleMonthChooser;
    }
    /////////////////////// --单选月-- //////////////////////////




    ////////////////////// --单选周-- ///////////////////////////
    private void setupSingleWeek(){

        singleWeekTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingleWeekChooser().show();//显示日历
            }
        });
        singleWeekLastTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingleWeekChooser().selectLastDate();//显示上一周
            }
        });
        singleWeekNextTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingleWeekChooser().selectNextDate();//显示下一周
            }
        });

        getSingleWeekChooser().currentDate();//显示当前日期
    }
    private CalendarChooser getSingleWeekChooser(){
        if (singleWeekChooser == null){

            Calendar maxC = Calendar.getInstance();
            maxC.add(Calendar.DATE, 10);
            Calendar minC = Calendar.getInstance();
            minC.add(Calendar.DATE, -20);

            CalendarChooser.Builder builder = new CalendarChooser.Builder(this)
                    .mode(ChooserMode.WEEK)
                    .tintAlpha(15)
                    .tintColor(Color.parseColor("#1777CB"))
                    .confirmBtnColor(Color.parseColor("#DDBA76"))
                    .currentDate(Calendar.getInstance())
                    .resultListener(new ChooseResultListener() {
                        @Override
                        public void choiceResult(SelectedDateItem result) {
                            singleWeekTV.setText(result.getDisplayStr());
                        }
                    });

            singleWeekChooser = builder.build();
        }

        return singleWeekChooser;
    }
    ////////////////////// --单选周-- ///////////////////////////




    ////////////////////// -- 日-区域选择 -- ///////////////////////////
    private void setupScopeDay(){

        scopeDayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScopeDayChooser().show();
            }
        });


        getScopeDayChooser().currentDate();
    }

    private CalendarChooser getScopeDayChooser(){
        if (scopeDayChooser == null){
            Calendar maxC = Calendar.getInstance();
            maxC.add(Calendar.DATE, 10);
            Calendar minC = Calendar.getInstance();
            minC.add(Calendar.DATE, -20);
            Calendar endC = Calendar.getInstance();
            endC.add(Calendar.DATE, 5);

            CalendarChooser.Builder builder = new CalendarChooser.Builder(this)
                    .mode(ChooserMode.DAY_SCOPE)
                    .currentDate(Calendar.getInstance())
                    .minDate(minC)
                    .maxDate(maxC)
                    .tintAlpha(15)
                    .tintColor(Color.parseColor("#1777CB"))
                    .confirmBtnColor(Color.parseColor("#DDBA76"))
                    .selectStartDate(Calendar.getInstance())
                    .selectEndDate(endC)
                    .resultListener(new ChooseResultListener() {
                        @Override
                        public void choiceResult(SelectedDateItem result) {
                            scopeDayTV.setText(result.getDisplayStr());
                        }
                    });

            scopeDayChooser = builder.build();
        }

        return scopeDayChooser;
    }
    ////////////////////// -- 日-区域选择 -- ///////////////////////////




    ////////////////////// -- 月-区域选择 -- ///////////////////////////
    private void setupScopeMonth(){

        scopeMonthTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScopeMonthChooser().show();
            }
        });


        getScopeMonthChooser().currentDate();
    }

    private CalendarChooser getScopeMonthChooser(){
        if (scopeMonthChooser == null){
            Calendar maxC = Calendar.getInstance();
            maxC.add(Calendar.MONTH, 10);
            Calendar minC = Calendar.getInstance();
            minC.add(Calendar.MONTH, -20);
            Calendar endC = Calendar.getInstance();
            endC.add(Calendar.MONTH, 5);

            CalendarChooser.Builder builder = new CalendarChooser.Builder(this)
                    .mode(ChooserMode.MONTH_SCOPE)
                    .currentDate(Calendar.getInstance())
                    .minDate(minC)
                    .maxDate(maxC)
                    .tintAlpha(15)
                    .tintColor(Color.parseColor("#1777CB"))
                    .confirmBtnColor(Color.parseColor("#DDBA76"))
                    .selectStartDate(Calendar.getInstance())
                    .selectEndDate(endC)
                    .displayDateFormat(new SimpleDateFormat("yyyy-MM", Locale.CHINA))
                    .resultListener(new ChooseResultListener() {
                        @Override
                        public void choiceResult(SelectedDateItem result) {
                            scopeMonthTV.setText(result.getDisplayStr());
                        }
                    });

            scopeMonthChooser = builder.build();
        }

        return scopeMonthChooser;
    }
    ////////////////////// -- 月-区域选择 -- ///////////////////////////

    //////////////////////// --单选 仅选周末-- /////////////////////////
    private void setupSingleDayInWeekend(){

        singleDayInWeekendTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingleDayInWeekendChooser().show();//显示日历
            }
        });

        getSingleDayInWeekendChooser().currentDate();//显示当前日期

    }
    private CalendarChooser getSingleDayInWeekendChooser(){
        if (singleDayInWeekendChooser == null){
//            Calendar maxDate = Calendar.getInstance();
//            maxDate.add(Calendar.DATE, 10);
            CalendarChooser.Builder builder = new CalendarChooser.Builder(this)
                    .mode(ChooserMode.DAY_IN_WEEKEND)
                    .currentDate(Calendar.getInstance())
                    .minDate(Calendar.getInstance())
                    .enableWeekDays(Arrays.asList(new Integer[] {Calendar.SATURDAY, Calendar.WEDNESDAY}))
//                    .maxDate(maxDate)
                    .tintAlpha(15)
                    .tintColor(Color.parseColor("#1777CB"))
                    .confirmBtnColor(Color.parseColor("#DDBA76"))
                    .displayDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA))
                    .resultListener(new ChooseResultListener() {
                        @Override
                        public void choiceResult(SelectedDateItem result) {
                            singleDayInWeekendTV.setText(result.getDisplayStr());
                        }
                    });

            singleDayInWeekendChooser = builder.build();
        }

        return singleDayInWeekendChooser;
    }
    /////////////////////// --单选 仅选周末-- //////////////////////////
}
