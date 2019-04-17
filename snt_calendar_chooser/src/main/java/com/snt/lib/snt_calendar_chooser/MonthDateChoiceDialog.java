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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MonthDateChoiceDialog extends ExpandedBottomSheetDialog implements MonthDateChoiceRVAdapter.RVItemEventListener {

    private ChooseResultListener resultListener;
    private MonthDateChoiceRVAdapter rvAdapter;
    private TextView yearTV;
    private ImageButton lastYearIB;
    private ImageButton nextYearIB;
    private Calendar maxDate;
    private Calendar minDate;
    private Calendar currentDate;
    private Calendar selectedDate;
    private Calendar nowDate;
    private ChooserConfiguration configuration;


    public MonthDateChoiceDialog(@NonNull Context context, ChooserConfiguration configuration) {
        super(context);
        this.resultListener = configuration.getListener();
        this.maxDate = configuration.getMaxDate();
        this.minDate = configuration.getMinDate();
        this.nowDate = configuration.getCurrentDate();
        this.configuration = configuration;
        View dialogV = LayoutInflater.from(context).inflate(R.layout.month_date_choice_dialog, null);
        setContentView(dialogV);
        selectedDate = nowDate;
        TextView confirmBtn = dialogV.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate != null){

                    sendCallback();
                    dismiss();
                }else {
                    Toast.makeText(getContext(), "请点击选择月份", Toast.LENGTH_SHORT).show();
                }
            }
        });
        yearTV = dialogV.findViewById(R.id.txt_year);

        lastYearIB = dialogV.findViewById(R.id.ib_last_year);
        nextYearIB = dialogV.findViewById(R.id.ib_next_year);
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

        RecyclerView recyclerView = dialogV.findViewById(R.id.rv_month_choice);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        rvAdapter = new MonthDateChoiceRVAdapter(context, nowDate, maxDate, minDate, this);
        recyclerView.setAdapter(rvAdapter);
        setupData(nowDate != null ? nowDate : Calendar.getInstance());
    }

    private void setupData(Calendar calendar){
        currentDate = Calendar.getInstance();
        currentDate.setTime(calendar.getTime());
        int min = calendar.getActualMinimum(Calendar.MONTH) + 1;
        int max = calendar.getActualMaximum(Calendar.MONTH) + 1;
        List<Integer> datas = new ArrayList<>();
        for (int i = min; i <= max; i++){
            datas.add(i);
        }
        int year = calendar.get(Calendar.YEAR);
        rvAdapter.setItems(year, datas);
        rvAdapter.notifyDataSetChanged();
        yearTV.setText(year+"年");
        if (maxDate != null){
            if (year >= maxDate.get(Calendar.YEAR)){
                nextYearIB.setVisibility(View.INVISIBLE);
            }else {
                nextYearIB.setVisibility(View.VISIBLE);
            }
        }
        if (minDate != null){
            if (year <= minDate.get(Calendar.YEAR)){
                lastYearIB.setVisibility(View.INVISIBLE);
            }else {
                lastYearIB.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void selectADate(Calendar calendar) {
        selectedDate = calendar;
    }

    @Override
    public void show() {
        super.show();
        if (rvAdapter != null && selectedDate != null){
            rvAdapter.updateSelectUI(selectedDate);
        }
    }

    public void sendCallback(){

        if (resultListener != null && selectedDate != null){
            SelectedDateItem selectedDateItem = new SelectedDateItem();
            selectedDateItem.setChooserMode(configuration.getMode());
            selectedDateItem.setCurrentDate(selectedDate);
            selectedDateItem.setCurrentDateValue(configuration.getValueDateformat().format(selectedDate.getTime()));
            selectedDateItem.setDisplayStr(configuration.getDisplayDateformat().format(selectedDate.getTime()));
            resultListener.choiceResult(selectedDateItem);
        }
    }

    public void currentDate(){
        sendCallback();
    }

    public void selectLastDate(){

        if (minDate != null && DateUtil.dateEquals(minDate, selectedDate) >= 0){
            return;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(selectedDate.getTime());
        date.add(Calendar.MONTH, -1);
        selectedDate = date;
        setupData(selectedDate);
        sendCallback();
    }

    public void selectNextDate(){

        if (maxDate != null && DateUtil.dateEquals(maxDate, selectedDate) <= 0){
            return;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(selectedDate.getTime());
        date.add(Calendar.MONTH, +1);
        selectedDate = date;
        setupData(selectedDate);
        sendCallback();
    }
}
