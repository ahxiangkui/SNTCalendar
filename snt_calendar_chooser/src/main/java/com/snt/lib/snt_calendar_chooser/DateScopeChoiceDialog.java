package com.snt.lib.snt_calendar_chooser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DateScopeChoiceDialog extends ExpandedBottomSheetDialog implements DateScopeChoiceRVAdapter.RVItemEventListener, RadioGroup.OnCheckedChangeListener {

    private ChooseResultListener resultListener;
    private DateScopeChoiceRVAdapter rvAdapter;
    private TextView yearTV;
    private ImageButton lastYearIB;
    private ImageButton nextYearIB;

    private ImageButton lastMonthIB;
    private ImageButton nextMonthIB;

    private Calendar maxDate;
    private Calendar minDate;
    private Calendar currentDate;

    private RadioGroup fastBtnsRG;
    private RadioButton lastDayRB;
    private RadioButton day7RB;
    private RadioButton day30RB;
    private RadioButton day90RB;

    private ChooserConfiguration configuration;

    public void sendCallback(){

        if (resultListener != null && rvAdapter != null) {

            SelectedDateItem resultItem = new SelectedDateItem();
            resultItem.setChooserMode(configuration.getMode());

            if (rvAdapter.getStartDate() == null && rvAdapter.getEndDate() != null){

                Calendar selectDate = rvAdapter.getEndDate();

                resultItem.setStartDate(selectDate);
                resultItem.setStartDateValue(configuration.getValueDateformat().format(selectDate.getTime()));
                resultItem.setDisplayStr(configuration.getDisplayDateformat().format(selectDate.getTime()));
                resultItem.setEndDate(null);

            }else if (rvAdapter.getStartDate() != null && rvAdapter.getEndDate() == null){

                Calendar selectDate = rvAdapter.getStartDate();

                resultItem.setStartDate(selectDate);
                resultItem.setStartDateValue(configuration.getValueDateformat().format(selectDate.getTime()));
                resultItem.setDisplayStr(configuration.getDisplayDateformat().format(selectDate.getTime()));
                resultItem.setEndDate(null);

            }else if (rvAdapter.getStartDate() != null && rvAdapter.getEndDate() != null){

                resultItem.setStartDate(rvAdapter.getStartDate());
                resultItem.setStartDateValue(configuration.getValueDateformat().format(rvAdapter.getStartDate().getTime()));
                resultItem.setEndDate(rvAdapter.getEndDate());
                resultItem.setEndDateValue(configuration.getValueDateformat().format(rvAdapter.getEndDate().getTime()));
                resultItem.setDisplayStr(configuration.getDisplayDateformat().format(rvAdapter.getStartDate().getTime())
                        +" ~ "+configuration.getDisplayDateformat().format(rvAdapter.getEndDate().getTime()));

            }else {
                return;
            }

            if (configuration.getAutoDismissDate()) {
                resultItem.setNeedAutoDismissScopeDialog(DateScopeChoiceDialog.this);
            }
            resultListener.choiceResult(resultItem);
            if (!configuration.getAutoDismissDate()) dismiss();
        }

    }


    public DateScopeChoiceDialog(@NonNull Context context, final ChooserConfiguration configuration) {
        super(context);
        this.resultListener = configuration.getListener();
        this.maxDate = configuration.getMaxDate();
        this.minDate = configuration.getMinDate();
        this.configuration = configuration;

        View dialogV = LayoutInflater.from(context).inflate(R.layout.date_scope_choice_dialog, null);
        setContentView(dialogV);

        TextView confirmBtn = dialogV.findViewById(R.id.btn_confirm);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvAdapter.getStartDate() != null || rvAdapter.getEndDate() != null) {
                    sendCallback();
                } else {
                    Toast.makeText(getContext(), "请点击选择日期", Toast.LENGTH_SHORT).show();
                }
            }
        });

        confirmBtn.setBackgroundColor(configuration.getConfirmBtnColor());

        fastBtnsRG = dialogV.findViewById(R.id.rg_fast);
        lastDayRB = dialogV.findViewById(R.id.rb_lastday);
        day7RB = dialogV.findViewById(R.id.rb_7days);
        day30RB = dialogV.findViewById(R.id.rb_30days);
        day90RB = dialogV.findViewById(R.id.rb_90days);

        fastBtnsRG.setOnCheckedChangeListener(this);

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

        Calendar startDate = configuration.getSelectedDateItem().getStartDate();
        Calendar endDate = configuration.getSelectedDateItem().getEndDate();

        rvAdapter = new DateScopeChoiceRVAdapter(context, startDate, endDate, minDate, maxDate, configuration.getTintColor(), configuration.getTintAlpha(), this);
        recyclerView.setAdapter(rvAdapter);
        setupData(endDate != null ? endDate : (startDate != null ? startDate : Calendar.getInstance()));

        calculateDateScope(startDate, endDate);
    }

    public void currentDate() {
        sendCallback();
    }

    public void resetDate(Calendar start, Calendar end) {
        rvAdapter.setStartDate(start);
        rvAdapter.setEndDate(end);
        setupData(end != null ? end : (start != null ? start : Calendar.getInstance()));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.rb_lastday) {

            Calendar endDate = Calendar.getInstance();
            endDate.add(Calendar.DATE, -1);
            resetDate(null, endDate);

        } else if (checkedId == R.id.rb_7days) {

            Calendar endDate = Calendar.getInstance();
            endDate.add(Calendar.DATE, -1);
            Calendar startDate = Calendar.getInstance();
            startDate.add(Calendar.DATE, -7);
            resetDate(startDate, endDate);

        } else if (checkedId == R.id.rb_30days) {

            Calendar endDate = Calendar.getInstance();
            endDate.add(Calendar.DATE, -1);
            Calendar startDate = Calendar.getInstance();
            startDate.add(Calendar.DATE, -30);
            resetDate(startDate, endDate);

        } else if (checkedId == R.id.rb_90days) {

            Calendar endDate = Calendar.getInstance();
            endDate.add(Calendar.DATE, -1);
            Calendar startDate = Calendar.getInstance();
            startDate.add(Calendar.DATE, -90);
            resetDate(startDate, endDate);

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
    public void selectScopeDate(Calendar selectStartDate, Calendar selectEndDate) {

        calculateDateScope(selectStartDate, selectEndDate);
    }

    private void calculateDateScope(Calendar startDate, Calendar endDate) {
        fastBtnsRG.setOnCheckedChangeListener(null);
        Calendar scopeEndDate = Calendar.getInstance();
        scopeEndDate.add(Calendar.DATE, -1);
        Calendar scopeStart7Date = Calendar.getInstance();
        scopeStart7Date.add(Calendar.DATE, -7);
        Calendar scopeStart30Date = Calendar.getInstance();
        scopeStart30Date.add(Calendar.DATE, -30);
        Calendar scopeStart90Date = Calendar.getInstance();
        scopeStart90Date.add(Calendar.DATE, -90);

        if (startDate == null && endDate == null) {
            fastBtnsRG.clearCheck();
        } else if (startDate != null && endDate != null) {

            if (DateUtil.dateEquals(startDate, endDate) == 0) {

                if (DateUtil.dateEquals(endDate, scopeEndDate) == 0) {
                    fastBtnsRG.check(R.id.rb_lastday);
                } else {
                    fastBtnsRG.clearCheck();
                }
                setupData(startDate);

            } else {

                if (DateUtil.dateEquals(startDate, scopeStart7Date) == 0 && DateUtil.dateEquals(endDate, scopeEndDate) == 0) {
                    fastBtnsRG.check(R.id.rb_7days);
                } else if (DateUtil.dateEquals(startDate, scopeStart30Date) == 0 && DateUtil.dateEquals(endDate, scopeEndDate) == 0) {
                    fastBtnsRG.check(R.id.rb_30days);
                } else if (DateUtil.dateEquals(startDate, scopeStart90Date) == 0 && DateUtil.dateEquals(endDate, scopeEndDate) == 0) {
                    fastBtnsRG.check(R.id.rb_90days);
                } else {
                    fastBtnsRG.clearCheck();
                }
            }


        } else {
            Calendar tempDate = endDate == null ? startDate : endDate;
            if (DateUtil.dateEquals(tempDate, scopeEndDate) == 0) {
                fastBtnsRG.check(R.id.rb_lastday);
            } else {
                fastBtnsRG.clearCheck();
            }
            setupData(tempDate);
        }
        fastBtnsRG.setOnCheckedChangeListener(this);
    }
}
