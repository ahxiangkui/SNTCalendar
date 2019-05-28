package com.snt.lib.snt_calendar_chooser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DateChoiceDialog extends ExpandedBottomSheetDialog implements DateChoiceRVAdapter.RVItemEventListener {

    private ChooseResultListener resultListener;
    private DateChoiceRVAdapter rvAdapter;
    private TextView yearTV;
    private ImageButton lastYearIB;
    private ImageButton nextYearIB;

    private ImageButton lastMonthIB;
    private ImageButton nextMonthIB;

    private Calendar maxDate;
    private boolean isNeedAutoDismissDate;
    private Calendar minDate;
    private Calendar currentDate;
    private Calendar selectedDate;
    private Calendar nowDate;
    private boolean weekmode;
    private ChooserConfiguration configuration;


    public DateChoiceDialog(@NonNull Context context, final ChooserConfiguration configuration, boolean weekmode) {
        super(context);
        this.resultListener = configuration.getListener();
        this.maxDate = configuration.getMaxDate();
        this.isNeedAutoDismissDate = configuration.getAutoDismissDate();
        this.minDate = configuration.getMinDate();
        this.nowDate = configuration.getCurrentDate();
        this.configuration = configuration;
        this.weekmode = weekmode;
        View dialogV = LayoutInflater.from(context).inflate(configuration.getCalendarDialogLayout() != 0 ? configuration.getCalendarDialogLayout() : R.layout.date_choice_dialog, null);
        setContentView(dialogV);
        selectedDate = nowDate;
        TextView confirmBtn = dialogV.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate != null) {

                    sendCallback();

                    if (!isNeedAutoDismissDate)
                        dismiss();
                } else {
                    Toast.makeText(getContext(), "请点击选择日期", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (configuration.getCalendarDialogLayout() == 0){

            confirmBtn.setBackgroundColor(configuration.getConfirmBtnColor());
        }
        yearTV = dialogV.findViewById(R.id.txt_year);

        lastYearIB = dialogV.findViewById(R.id.ib_last_year);
        nextYearIB = dialogV.findViewById(R.id.ib_next_year);

        lastMonthIB = dialogV.findViewById(R.id.ib_last_month);
        nextMonthIB = dialogV.findViewById(R.id.ib_next_month);

        lastYearIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.YEAR, -1);
                setupData(currentDate);
            }
        });
        nextYearIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.YEAR, 1);
                setupData(currentDate);
            }
        });
        lastMonthIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                setupData(currentDate);
            }
        });
        nextMonthIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                setupData(currentDate);
            }
        });

        RecyclerView recyclerView = dialogV.findViewById(R.id.rv_month_choice);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 7));
        rvAdapter = new DateChoiceRVAdapter(context, nowDate, minDate, maxDate, weekmode
                , configuration.getTintColor(), configuration.getTintAlpha(), this, configuration.getThemeSetupCallback(), configuration.getDateItemLayout());
        recyclerView.setAdapter(rvAdapter);
        setupData(nowDate != null ? nowDate : Calendar.getInstance());
    }

    public void currentDate() {
        sendCallback();
    }

    public void selectLastDate() {

        if (weekmode) {

            if (minDate != null && DateUtil.dateEquals(minDate, selectedDate) >= 0) {
                return;
            }

            Calendar date = Calendar.getInstance();
            date.setTime(selectedDate.getTime());
            date.add(Calendar.DATE, -7);
            selectedDate = date;
            setupData(selectedDate);
            sendCallback();

        } else {
            if (minDate != null && DateUtil.dateEquals(minDate, selectedDate) >= 0) {
                return;
            }

            Calendar date = Calendar.getInstance();
            date.setTime(selectedDate.getTime());
            date.add(Calendar.DATE, -1);
            selectedDate = date;
            setupData(selectedDate);
            sendCallback();

        }
    }

    public void selectNextDate() {

        if (weekmode) {

            if (maxDate != null && DateUtil.dateEquals(maxDate, selectedDate) <= 0) {
                return;
            }

            Calendar date = Calendar.getInstance();
            date.setTime(selectedDate.getTime());
            date.add(Calendar.DATE, +7);
            selectedDate = date;
            setupData(selectedDate);
            sendCallback();

        } else {

            if (maxDate != null && DateUtil.dateEquals(maxDate, selectedDate) <= 0) {
                return;
            }

            Calendar date = Calendar.getInstance();
            date.setTime(selectedDate.getTime());
            date.add(Calendar.DATE, +1);
            selectedDate = date;
            setupData(selectedDate);
            sendCallback();

        }
    }

    @Override
    public void show() {
        super.show();
        if (rvAdapter != null && selectedDate != null) {
            rvAdapter.updateSelectUI(selectedDate);
        }
    }

    public void sendCallback() {

        if (weekmode) {

            if (resultListener != null && selectedDate != null) {

                SelectedDateItem selectedDateItem = new SelectedDateItem();

                selectedDateItem.setChooserMode(configuration.getMode());


                if (selectedDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    selectedDate.add(Calendar.DATE, -1);
                }

                Calendar weekBeginDate = Calendar.getInstance();
                weekBeginDate.setTime(selectedDate.getTime());

                if (Calendar.MONDAY == weekBeginDate.get(Calendar.DAY_OF_WEEK)) {

                }
                if (Calendar.TUESDAY == weekBeginDate.get(Calendar.DAY_OF_WEEK)) {
                    weekBeginDate.add(Calendar.DATE, -1);
                }
                if (Calendar.WEDNESDAY == weekBeginDate.get(Calendar.DAY_OF_WEEK)) {
                    weekBeginDate.add(Calendar.DATE, -2);
                }
                if (Calendar.THURSDAY == weekBeginDate.get(Calendar.DAY_OF_WEEK)) {
                    weekBeginDate.add(Calendar.DATE, -3);
                }
                if (Calendar.FRIDAY == weekBeginDate.get(Calendar.DAY_OF_WEEK)) {
                    weekBeginDate.add(Calendar.DATE, -4);
                }
                if (Calendar.SATURDAY == weekBeginDate.get(Calendar.DAY_OF_WEEK)) {
                    weekBeginDate.add(Calendar.DATE, -5);
                }
                if (Calendar.SUNDAY == weekBeginDate.get(Calendar.DAY_OF_WEEK)) {
                    weekBeginDate.add(Calendar.DATE, -6);
                }

                Calendar weekEndDate = Calendar.getInstance();
                weekEndDate.setTime(weekBeginDate.getTime());
                weekEndDate.add(Calendar.DATE, 6);

                weekBeginDate.setFirstDayOfWeek(Calendar.MONDAY);
                weekEndDate.setFirstDayOfWeek(Calendar.MONDAY);

                int weekvalue = weekBeginDate.get(Calendar.WEEK_OF_YEAR);

                SimpleDateFormat showformat = new SimpleDateFormat("MM.dd", Locale.CHINA);
                String showStr = weekvalue + "周 [" + showformat.format(weekBeginDate.getTime()) + "-" + showformat.format(weekEndDate.getTime()) + "]";

                selectedDateItem.setStartDate(weekBeginDate);
                selectedDateItem.setStartDateValue(configuration.getValueDateformat().format(weekBeginDate.getTime()));
                selectedDateItem.setEndDate(weekEndDate);
                selectedDateItem.setEndDateValue(configuration.getValueDateformat().format(weekEndDate.getTime()));
                selectedDateItem.setDisplayStr(showStr);

                selectedDateItem.setNeedAutoDismissDilog(DateChoiceDialog.this);

                resultListener.choiceResult(selectedDateItem);
            }

        } else {

            if (resultListener != null && selectedDate != null) {

                SelectedDateItem selectedDateItem = new SelectedDateItem();

                selectedDateItem.setChooserMode(configuration.getMode());
                selectedDateItem.setCurrentDate(selectedDate);
                selectedDateItem.setCurrentDateValue(configuration.getValueDateformat().format(selectedDate.getTime()));
                selectedDateItem.setDisplayStr(configuration.getDisplayDateformat().format(selectedDate.getTime()));

                selectedDateItem.setNeedAutoDismissDilog(DateChoiceDialog.this);

                resultListener.choiceResult(selectedDateItem);
            }
        }

    }

    private void setupData(Calendar calendar) {
        currentDate = Calendar.getInstance();
        currentDate.setTime(calendar.getTime());

        Calendar monthMinDate = Calendar.getInstance();
        monthMinDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Calendar monthMaxDate = Calendar.getInstance();
        monthMaxDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(monthMinDate.getTime());
        while (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            startDate.add(Calendar.DATE, -1);
        }
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(monthMaxDate.getTime());
        while (endDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            endDate.add(Calendar.DATE, 1);
        }

        List<Calendar> datas = new ArrayList<>();
        Calendar tempDate = Calendar.getInstance();
        tempDate.setTime(startDate.getTime());
        for (int i = 0; tempDate.get(Calendar.YEAR) != endDate.get(Calendar.YEAR)
                || tempDate.get(Calendar.MONTH) != endDate.get(Calendar.MONTH)
                || tempDate.get(Calendar.DAY_OF_MONTH) != endDate.get(Calendar.DAY_OF_MONTH); i++) {

            tempDate.setTime(startDate.getTime());
            tempDate.add(Calendar.DATE, i);

            Calendar dateItem = Calendar.getInstance();
            dateItem.setTime(tempDate.getTime());
            datas.add(dateItem);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        rvAdapter.setDatas(calendar, datas);
        rvAdapter.notifyDataSetChanged();
        yearTV.setText(year + "年" + month + "月");
        if (maxDate != null) {
            if (year == maxDate.get(Calendar.YEAR)) {
                nextYearIB.setVisibility(View.INVISIBLE);
                if (month >= maxDate.get(Calendar.MONTH) + 1) {
                    nextMonthIB.setVisibility(View.INVISIBLE);
                } else {
                    nextMonthIB.setVisibility(View.VISIBLE);
                }

            } else if (year > maxDate.get(Calendar.YEAR)) {
                nextYearIB.setVisibility(View.INVISIBLE);
                nextMonthIB.setVisibility(View.INVISIBLE);

            } else {
                nextMonthIB.setVisibility(View.VISIBLE);
                nextYearIB.setVisibility(View.VISIBLE);
            }
        }

        if (minDate != null) {
            if (year == minDate.get(Calendar.YEAR)) {
                lastYearIB.setVisibility(View.INVISIBLE);
                if (month <= minDate.get(Calendar.MONTH) + 1) {
                    lastMonthIB.setVisibility(View.INVISIBLE);
                } else {
                    lastMonthIB.setVisibility(View.VISIBLE);
                }

            } else if (year < minDate.get(Calendar.YEAR)) {
                lastYearIB.setVisibility(View.INVISIBLE);
                lastMonthIB.setVisibility(View.INVISIBLE);

            } else {
                lastMonthIB.setVisibility(View.VISIBLE);
                lastYearIB.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void selectADate(Calendar calendar, boolean needRefresh) {
        selectedDate = calendar;
        if (needRefresh) {
            setupData(selectedDate);
        }
    }
}
