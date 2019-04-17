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


public class MonthScopeDateChoiceDialog extends ExpandedBottomSheetDialog implements MonthScopeDateChoiceRVAdapter.RVItemEventListener {

    private ChooseResultListener resultListener;
    private MonthScopeDateChoiceRVAdapter rvAdapter;
    private TextView yearTV;
    private ImageButton lastYearIB;
    private ImageButton nextYearIB;
    private Calendar maxDate;
    private Calendar minDate;
    private Calendar currentDate;
    private ChooserConfiguration configuration;

    public void sendCallback(){

        if (resultListener != null && rvAdapter != null){

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

            resultListener.choiceResult(resultItem);

        }

    }

    public MonthScopeDateChoiceDialog(@NonNull Context context, ChooserConfiguration configuration) {
        super(context);
        this.configuration = configuration;
        this.resultListener = configuration.getListener();
        this.maxDate = configuration.getMaxDate();
        this.minDate = configuration.getMinDate();

        View dialogV = LayoutInflater.from(context).inflate(R.layout.month_date_choice_dialog, null);
        setContentView(dialogV);

        TextView confirmBtn = dialogV.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvAdapter.getStartDate() != null || rvAdapter.getEndDate() != null){

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

        Calendar startDate = configuration.getSelectedDateItem().getStartDate();
        Calendar endDate = configuration.getSelectedDateItem().getEndDate();

        rvAdapter = new MonthScopeDateChoiceRVAdapter(context, startDate, endDate, minDate, maxDate, this);
        recyclerView.setAdapter(rvAdapter);
        setupData(endDate != null ? endDate : (startDate != null ? startDate : Calendar.getInstance()));
    }

    public void currentDate() {
        sendCallback();
    }

    private void setupData(Calendar calendar){
        currentDate = Calendar.getInstance();
        currentDate.setTime(calendar.getTime());
        int min = calendar.getActualMinimum(Calendar.MONTH);
        int max = calendar.getActualMaximum(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        List<Calendar> datas = new ArrayList<>();
        for (int i = min; i <= max; i++){
            Calendar citem = Calendar.getInstance();
            citem.set(year, i, 1);
            datas.add(citem);
        }
        rvAdapter.setItems(datas);
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
    public void selectScopeDate(Calendar selectStartDate, Calendar selectEndDate) {

    }
}
