package com.snt.lib.snt_calendar_chooser;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by xiangkui on 16/5/24.
 */
public class MonthDateChoiceRVAdapter extends RecyclerView.Adapter<MonthDateChoiceRVAdapter.MViewHolder> {

    private static final int TYPE_NORMAL = 0;

    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<Integer> items;
    private RVItemEventListener rvItemEventListener;
    private int maxYear;
    private int minYear;
    private int maxMonth;
    private int minMonth;
    private Calendar maxDate;
    private Calendar minDate;
    private int currentYear;
    private int selectYear;
    private int selectMonth;
    private int selectItemPosition = -1;
    private Calendar nowDate;
    private int tintColor;
    private int tintAlpha;

    public interface RVItemEventListener{
        void selectADate(Calendar calendar);
    }

    public MonthDateChoiceRVAdapter(Context context, Calendar nowDate, Calendar maxDate, Calendar minDate, int tintColor, int tintAlpha, RVItemEventListener rvItemEventListener) {
        this.maxDate = maxDate;
        this.minDate = minDate;
        this.tintColor = tintColor;
        this.tintAlpha = tintAlpha;
        if (maxDate != null){

            this.maxYear = maxDate.get(Calendar.YEAR);
            this.maxMonth = maxDate.get(Calendar.MONTH) + 1;
        }
        if (minDate != null){

            this.minYear = minDate.get(Calendar.YEAR);
            this.minMonth = minDate.get(Calendar.MONTH) + 1;
        }
        this.nowDate = nowDate;
        if (nowDate != null){
            this.selectYear = nowDate.get(Calendar.YEAR);
            this.selectMonth = nowDate.get(Calendar.MONTH) + 1;
        }

        this.mLayoutInflater = LayoutInflater.from(context);
        this.rvItemEventListener = rvItemEventListener;
        this.items = new ArrayList<>();
        this.context = context;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(int currentYear, List<Integer> items) {
        this.currentYear = currentYear;
        this.items = items;
    }

    public void updateSelectUI(Calendar selectDate){
        selectYear = selectDate.get(Calendar.YEAR);
        selectMonth = selectDate.get(Calendar.MONTH) + 1;
        notifyDataSetChanged();
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.item_month_date_choice,parent,false);
        return new NormalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {

        onBindNormalView(holder,position);

    }


    private void onBindNormalView(MViewHolder holder, int position){

        if (holder instanceof NormalViewHolder){
            Integer item = items.get(position);
            ((NormalViewHolder) holder).titleTV.setText(item+"");
            ((NormalViewHolder) holder).currentMonth = item;
            if (nowDate != null && selectYear == currentYear && selectMonth == item){
                ((NormalViewHolder) holder).titleTV.setTextColor(tintColor);
                ((NormalViewHolder) holder).unitTV.setTextColor(tintColor);
                ((NormalViewHolder) holder).contentV.setBackgroundColor(tintColor);
                ((NormalViewHolder) holder).contentV.getBackground().setAlpha(tintAlpha);

                ((NormalViewHolder) holder).disable = false;
                selectItemPosition = position;
            }else if (maxDate != null && (currentYear > maxYear || (currentYear == maxYear && item > maxMonth))){
                ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                ((NormalViewHolder) holder).unitTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                ((NormalViewHolder) holder).contentV.setBackgroundColor(Color.TRANSPARENT);
                ((NormalViewHolder) holder).disable = true;
            }else if (minDate != null && (currentYear < minYear || (currentYear == minYear && item < minMonth))){
                ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                ((NormalViewHolder) holder).unitTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                ((NormalViewHolder) holder).contentV.setBackgroundColor(Color.TRANSPARENT);
                ((NormalViewHolder) holder).disable = true;
            }else {
                ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtColor));
                ((NormalViewHolder) holder).unitTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtColor));
                ((NormalViewHolder) holder).contentV.setBackgroundColor(Color.TRANSPARENT);
                ((NormalViewHolder) holder).disable = false;
            }
            ((NormalViewHolder) holder).itemPosition = position;

        }

    }


    @Override
    public int getItemCount() {
        return items.size();
//        return 20;
    }

    @Override
    public int getItemViewType(int position) {

        return TYPE_NORMAL;
    }

    public class MViewHolder extends RecyclerView.ViewHolder{

        public MViewHolder(final View itemView) {
            super(itemView);
        }
    }

    public class NormalViewHolder extends MViewHolder implements View.OnClickListener{

        View contentV;
        TextView titleTV;
        TextView unitTV;
        int currentMonth;
        int itemPosition;
        boolean disable;

        NormalViewHolder(View itemView){

            super(itemView);
            contentV = itemView.findViewById(R.id.ll_content);
            contentV.setOnClickListener(this);
            titleTV = itemView.findViewById(R.id.txt_title);
            unitTV = itemView.findViewById(R.id.txt_unit);
        }

        @Override
        public void onClick(View v) {
            if (disable){
                return;
            }
            selectYear = currentYear;
            selectMonth = currentMonth;
            nowDate = Calendar.getInstance();
            nowDate.set(selectYear, currentMonth - 1, 1);
            if (rvItemEventListener != null){
                rvItemEventListener.selectADate(nowDate);
            }
            if (selectItemPosition >= 0){
                notifyItemChanged(selectItemPosition);
            }
            selectItemPosition = itemPosition;
            titleTV.setTextColor(tintColor);
            unitTV.setTextColor(tintColor);
            contentV.setBackgroundColor(tintColor);
            contentV.getBackground().setAlpha(tintAlpha);

        }
    }


}
