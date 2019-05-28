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
public class MonthScopeDateChoiceRVAdapter extends RecyclerView.Adapter<MonthScopeDateChoiceRVAdapter.MViewHolder> {

    private static final int TYPE_NORMAL = 0;

    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<Calendar> items;
    private RVItemEventListener rvItemEventListener;
    private Calendar maxDate;
    private Calendar minDate;
    private Calendar startDate;
    private Calendar endDate;
    private int tintColor;
    private int tintAlpha;
    private DateItemThemeSetupCallback themeSetupCallback;
    private int itemLayout;

    public interface RVItemEventListener{
        void selectScopeDate(Calendar selectStartDate, Calendar selectEndDate);
    }

    public MonthScopeDateChoiceRVAdapter(Context context, Calendar startDate, Calendar endDate
            , Calendar minDate, Calendar maxDate, int tintColor, int tintAlpha, RVItemEventListener rvItemEventListener, DateItemThemeSetupCallback themeSetupCallback, int itemLayout) {
        this.maxDate = maxDate;
        this.minDate = minDate;
        this.tintColor = tintColor;
        this.tintAlpha = tintAlpha;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.rvItemEventListener = rvItemEventListener;
        this.items = new ArrayList<>();
        this.context = context;
        this.itemLayout = itemLayout != 0 ? itemLayout : R.layout.item_month_date_choice;
        this.themeSetupCallback = themeSetupCallback;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public List<Calendar> getItems() {
        return items;
    }

    public void setItems(List<Calendar> items) {
        if (items == null){
            items = new ArrayList<>();
        }
        this.items = items;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(itemLayout,parent,false);
        return new NormalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {

        onBindNormalView(holder,position);

    }


    private void onBindNormalView(MViewHolder holder, int position){

        if (holder instanceof NormalViewHolder){
            Calendar item = items.get(position);
            ((NormalViewHolder) holder).titleTV.setText((item.get(Calendar.MONTH) + 1)+"");
//            ((NormalViewHolder) holder).currentMonth = item;
            if ((startDate != null && DateUtil.dateMonthEquals(item, startDate) == 0)
                    || (endDate != null && DateUtil.dateMonthEquals(endDate, item) == 0)
                    || (startDate != null && endDate != null && DateUtil.dateMonthEquals(startDate, item) < 0 && DateUtil.dateMonthEquals(endDate, item) > 0)){

                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SELECT_SINGLE);
                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(tintColor);
                    ((NormalViewHolder) holder).unitTV.setTextColor(tintColor);
                    ((NormalViewHolder) holder).contentV.setBackgroundColor(tintColor);
                    ((NormalViewHolder) holder).contentV.getBackground().setAlpha(tintAlpha);
                }

                ((NormalViewHolder) holder).disable = false;
            }else if (maxDate != null && DateUtil.dateMonthEquals(item, maxDate) > 0){

                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);
                }else {


                    ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                    ((NormalViewHolder) holder).unitTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                    ((NormalViewHolder) holder).contentV.setBackgroundColor(Color.TRANSPARENT);
                }
                ((NormalViewHolder) holder).disable = true;
            }else if (minDate != null && DateUtil.dateMonthEquals(item, minDate) < 0){
                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);
                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                    ((NormalViewHolder) holder).unitTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                    ((NormalViewHolder) holder).contentV.setBackgroundColor(Color.TRANSPARENT);
                }
                ((NormalViewHolder) holder).disable = true;
            }else {
                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_NORMAL);
                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtColor));
                    ((NormalViewHolder) holder).unitTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtColor));
                    ((NormalViewHolder) holder).contentV.setBackgroundColor(Color.TRANSPARENT);
                }
                ((NormalViewHolder) holder).disable = false;
            }
            ((NormalViewHolder) holder).itemPosition = position;
            ((NormalViewHolder) holder).itemDate = item;

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
        Calendar itemDate;
        int itemPosition;
        boolean disable;

        NormalViewHolder(View itemView){

            super(itemView);
            contentV = itemView.findViewById(R.id.ll_content);
            contentV.setOnClickListener(this);
            titleTV = itemView.findViewById(R.id.txt_title);
            unitTV = itemView.findViewById(R.id.txt_unit);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (disable){
                return;
            }
//            selectYear = currentYear;
//            selectMonth = currentMonth;
//            nowDate = Calendar.getInstance();
//            nowDate.set(selectYear, currentMonth - 1, 1);
//            if (rvItemEventListener != null){
//                rvItemEventListener.selectADate(nowDate);
//            }
//            if (selectItemPosition >= 0){
//                notifyItemChanged(selectItemPosition);
//            }
//            selectItemPosition = itemPosition;
//            titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtSelectColor));
//            unitTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtSelectColor));
//            contentV.setBackgroundColor(ContextCompat.getColor(context, R.color.dateMonthSelectBgColor));

            if (startDate != null && endDate != null){

                startDate = itemDate;
                endDate = null;

//                if (DateUtil.dateMonthEquals(itemDate, endDate) == 0){
//                    endDate = null;
//                }else if (DateUtil.dateMonthEquals(itemDate, startDate) == 0){
//                    startDate = null;
//                }else if (DateUtil.dateMonthEquals(itemDate, endDate) < 0){
//                    startDate = itemDate;
//                }else if (DateUtil.dateMonthEquals(itemDate, endDate) > 0){
//                    endDate = itemDate;
//                }

            }else {
                if (startDate == null && endDate == null){
                    startDate = itemDate;
                }else if (startDate == null){//startDate == null && endDate != null
                    if (DateUtil.dateMonthEquals(itemDate, endDate) == 0){
                        endDate = null;
                    }else if (DateUtil.dateMonthEquals(itemDate, endDate) > 0){
                        startDate = endDate;
                        endDate = itemDate;
                    }else {
                        startDate = itemDate;
                    }

                }else{//startDate != null && endDate == null
                    if (DateUtil.dateMonthEquals(itemDate, startDate) == 0){
                        startDate = null;
                    }else if (DateUtil.dateMonthEquals(itemDate, startDate) > 0){
                        endDate = itemDate;
                    }else {
                        endDate = startDate;
                        startDate = itemDate;
                    }
                }
            }
            notifyDataSetChanged();

        }
    }


}
