package com.snt.lib.snt_calendar_chooser;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
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
public class DateScopeChoiceRVAdapter extends RecyclerView.Adapter<DateScopeChoiceRVAdapter.MViewHolder> {

    private static final int TYPE_NORMAL = 0;

    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<Calendar> datas;
    private RVItemEventListener rvItemEventListener;
    private Calendar maxDate;
    private Calendar minDate;

    private Calendar startDate;
    private Calendar endDate;

    private Calendar currentDate;//日历当前月

    private int tintColor;
    private int tintAlpha;

    private DateItemThemeSetupCallback themeSetupCallback;
    private int itemLayout;


    public interface RVItemEventListener{
        void selectScopeDate(Calendar selectStartDate, Calendar selectEndDate);
    }

    public DateScopeChoiceRVAdapter(Context context, Calendar startDate, Calendar endDate
            , Calendar minDate, Calendar maxDate, int tintColor, int tintAlpha
            , RVItemEventListener rvItemEventListener, DateItemThemeSetupCallback themeSetupCallback, int itemLayout) {
        this.maxDate = maxDate;
        this.minDate = minDate;
        this.tintColor = tintColor;
        this.tintAlpha = tintAlpha;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.rvItemEventListener = rvItemEventListener;
        this.datas = new ArrayList<>();
        this.context = context;
        this.startDate = startDate;
        this.endDate = endDate;
        this.itemLayout = itemLayout != 0 ? itemLayout : R.layout.item_date_choice;
        this.themeSetupCallback = themeSetupCallback;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public List<Calendar> getDatas() {
        return datas;
    }

    public void setDatas(Calendar currentDate, List<Calendar> datas) {
        if (datas == null){
            datas = new ArrayList<>();
        }
        this.datas = datas;
        this.currentDate = currentDate;
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
            Calendar item = datas.get(position);
            ((NormalViewHolder) holder).titleTV.setText(item.get(Calendar.DAY_OF_MONTH)+"");

            ((NormalViewHolder) holder).leftSelectBgV.setVisibility(View.INVISIBLE);
            ((NormalViewHolder) holder).rightSelectBgV.setVisibility(View.INVISIBLE);
            ((NormalViewHolder) holder).disable = false;

            if (startDate != null && DateUtil.dateEquals(startDate, item) == 0){

                if (themeSetupCallback != null){

                    if (endDate != null){

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SCOPE_START);
                    }else {

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SELECT_SINGLE);
                    }

                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(Color.WHITE);
                    ((NormalViewHolder) holder).selectBgV.setVisibility(View.VISIBLE);
                    ViewCompat.setBackgroundTintList(((NormalViewHolder) holder).selectBgV, ColorStateList.valueOf(tintColor));
                    ((NormalViewHolder) holder).leftSelectBgV.setVisibility(View.INVISIBLE);
                    if (endDate != null){
                        ((NormalViewHolder) holder).rightSelectBgV.setVisibility(View.VISIBLE);
                        ((NormalViewHolder) holder).rightSelectBgV.setBackgroundColor(tintColor);
                        ((NormalViewHolder) holder).rightSelectBgV.getBackground().setAlpha(tintAlpha);
                    }
                }


            }else if (endDate != null && DateUtil.dateEquals(endDate, item) == 0){

                if (themeSetupCallback != null){

                    if (startDate != null){

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SCOPE_END);
                    }else {

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SELECT_SINGLE);
                    }

                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(Color.WHITE);
                    ((NormalViewHolder) holder).selectBgV.setVisibility(View.VISIBLE);
                    ViewCompat.setBackgroundTintList(((NormalViewHolder) holder).selectBgV, ColorStateList.valueOf(tintColor));
                    ((NormalViewHolder) holder).rightSelectBgV.setVisibility(View.INVISIBLE);
                    if (startDate != null){
                        ((NormalViewHolder) holder).leftSelectBgV.setVisibility(View.VISIBLE);
                        ((NormalViewHolder) holder).leftSelectBgV.setBackgroundColor(tintColor);
                        ((NormalViewHolder) holder).leftSelectBgV.getBackground().setAlpha(tintAlpha);
                    }

                }


            }else if (startDate != null && endDate != null && DateUtil.dateEquals(startDate, item) < 0 && DateUtil.dateEquals(endDate, item) > 0){

                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SCOPE_INNER);

                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtColor));
                    ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                    ((NormalViewHolder) holder).leftSelectBgV.setVisibility(View.VISIBLE);
                    ((NormalViewHolder) holder).rightSelectBgV.setVisibility(View.VISIBLE);

                    ((NormalViewHolder) holder).leftSelectBgV.setBackgroundColor(tintColor);
                    ((NormalViewHolder) holder).leftSelectBgV.getBackground().setAlpha(tintAlpha);
                    ((NormalViewHolder) holder).rightSelectBgV.setBackgroundColor(tintColor);
                    ((NormalViewHolder) holder).rightSelectBgV.getBackground().setAlpha(tintAlpha);
                }

            }else if (maxDate != null && DateUtil.dateEquals(item, maxDate) > 0){//超过最大日期的样式

                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);

                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                    ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                }
                ((NormalViewHolder) holder).disable = true;

            }else if (minDate != null && DateUtil.dateEquals(item, minDate) < 0){
                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);

                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                    ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                }
                ((NormalViewHolder) holder).disable = true;

            }else if (currentDate != null && DateUtil.dateMonthEquals(item, currentDate) != 0){//上一个月日期样式
                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);

                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                    ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                }

            }else {
                if (themeSetupCallback != null){

                    themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_NORMAL);

                }else {

                    ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtColor));
                    ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                }

            }


            ((NormalViewHolder) holder).itemPosition = position;
            ((NormalViewHolder) holder).itemDate = item;
        }

    }

    private int getWeekIndex(Calendar date){
        Calendar tempDate = Calendar.getInstance();
        tempDate.setTime(date.getTime());
        if (tempDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            tempDate.add(Calendar.DATE, -1);
        }
        return tempDate.get(Calendar.WEEK_OF_YEAR);
    }

    @Override
    public int getItemCount() {
        return datas.size();
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
        int itemPosition;
        boolean disable;
        Calendar itemDate;
        View selectBgV;
        View leftSelectBgV;
        View rightSelectBgV;

        NormalViewHolder(View itemView){

            super(itemView);
            contentV = itemView.findViewById(R.id.ll_content);
            contentV.setOnClickListener(this);
            selectBgV = itemView.findViewById(R.id.v_select_bg);
            titleTV = itemView.findViewById(R.id.txt_title);
            leftSelectBgV = itemView.findViewById(R.id.v_select_bg_left);
            rightSelectBgV = itemView.findViewById(R.id.v_select_bg_right);
        }

        @Override
        public void onClick(View v) {
            if (disable){
                return;
            }

            if (startDate != null && endDate != null){

                startDate = itemDate;
                endDate = null;

//                if (DateUtil.dateEquals(itemDate, endDate) == 0){
//                    endDate = null;
//                }else if (DateUtil.dateEquals(itemDate, startDate) == 0){
//                    startDate = null;
//                }else if (DateUtil.dateEquals(itemDate, endDate) < 0){
//                    startDate = itemDate;
//                }else if (DateUtil.dateEquals(itemDate, endDate) > 0){
//                    endDate = itemDate;
//                }
            }else {
                if (startDate == null && endDate == null){
                    startDate = itemDate;
                }else if (startDate == null){//startDate == null && endDate != null
                    if (DateUtil.dateEquals(itemDate, endDate) == 0){
                        endDate = null;
                    }else if (DateUtil.dateEquals(itemDate, endDate) > 0){
                        startDate = endDate;
                        endDate = itemDate;
                    }else {
                        startDate = itemDate;
                    }

                }else{//startDate != null && endDate == null
                    if (DateUtil.dateEquals(itemDate, startDate) == 0){
                        startDate = null;
                    }else if (DateUtil.dateEquals(itemDate, startDate) > 0){
                        endDate = itemDate;
                    }else {
                        endDate = startDate;
                        startDate = itemDate;
                    }
                }
            }

            if (rvItemEventListener != null){
                rvItemEventListener.selectScopeDate(startDate, endDate);
            }

            notifyDataSetChanged();
        }
    }


}
