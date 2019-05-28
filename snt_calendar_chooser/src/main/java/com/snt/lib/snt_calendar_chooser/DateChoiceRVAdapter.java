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
public class DateChoiceRVAdapter extends RecyclerView.Adapter<DateChoiceRVAdapter.MViewHolder> {

    private static final int TYPE_NORMAL = 0;

    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<Calendar> datas;
    private RVItemEventListener rvItemEventListener;
    private Calendar maxDate;
    private Calendar minDate;
    private Calendar selectDate;
    private Calendar currentDate;
    private boolean weekmode;
    private int tintColor;
    private int tintAlpha;
    private DateItemThemeSetupCallback themeSetupCallback;
    private int itemLayout;

    private int selectItemPosition = -1;

    public interface RVItemEventListener{
        void selectADate(Calendar calendar, boolean needRefresh);
    }

    public DateChoiceRVAdapter(Context context, Calendar selectDate, Calendar minDate, Calendar maxDate
            , boolean weekmode, int tintColor, int tintAlpha, RVItemEventListener rvItemEventListener, DateItemThemeSetupCallback themeSetupCallback, int itemLayout) {
        this.maxDate = maxDate;
        this.minDate = minDate;
        this.tintColor = tintColor;
        this.tintAlpha = tintAlpha;
        this.selectDate = selectDate;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.rvItemEventListener = rvItemEventListener;
        this.datas = new ArrayList<>();
        this.context = context;
        this.weekmode = weekmode;
        this.itemLayout = itemLayout != 0 ? itemLayout : R.layout.item_date_choice;
        this.themeSetupCallback = themeSetupCallback;
    }

    public List<Calendar> getDatas() {
        return datas;
    }

    public void setDatas(Calendar nowDate, List<Calendar> datas) {
        if (datas == null){
            datas = new ArrayList<>();
        }
        this.datas = datas;
        if (nowDate != null){

            this.currentDate = Calendar.getInstance();
            this.currentDate.setTime(nowDate.getTime());
        }
    }

    public void updateSelectUI(Calendar selectDate){
        this.selectDate = selectDate;
        notifyDataSetChanged();
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

            if (weekmode){

                ((NormalViewHolder) holder).leftSelectBgV.setVisibility(View.INVISIBLE);
                ((NormalViewHolder) holder).rightSelectBgV.setVisibility(View.INVISIBLE);

                if (selectDate != null && sameWeek(selectDate, item)){

                    if (item.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){

                        if (themeSetupCallback != null){

                            themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SCOPE_START);
                        }else {

                            ((NormalViewHolder) holder).titleTV.setTextColor(Color.WHITE);
                            ((NormalViewHolder) holder).selectBgV.setVisibility(View.VISIBLE);
                            ((NormalViewHolder) holder).leftSelectBgV.setVisibility(View.INVISIBLE);
                            ((NormalViewHolder) holder).rightSelectBgV.setVisibility(View.VISIBLE);

                            ViewCompat.setBackgroundTintList(((NormalViewHolder) holder).selectBgV, ColorStateList.valueOf(tintColor));
                            ((NormalViewHolder) holder).rightSelectBgV.setBackgroundColor(tintColor);
                            ((NormalViewHolder) holder).rightSelectBgV.getBackground().setAlpha(tintAlpha);
                        }

                    }else if (item.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){

                        if (themeSetupCallback != null){

                            themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SCOPE_END);
                        }else {

                            ((NormalViewHolder) holder).titleTV.setTextColor(Color.WHITE);
                            ((NormalViewHolder) holder).selectBgV.setVisibility(View.VISIBLE);
                            ((NormalViewHolder) holder).leftSelectBgV.setVisibility(View.VISIBLE);
                            ((NormalViewHolder) holder).rightSelectBgV.setVisibility(View.INVISIBLE);

                            ViewCompat.setBackgroundTintList(((NormalViewHolder) holder).selectBgV, ColorStateList.valueOf(tintColor));
                            ((NormalViewHolder) holder).leftSelectBgV.setBackgroundColor(tintColor);
                            ((NormalViewHolder) holder).leftSelectBgV.getBackground().setAlpha(tintAlpha);
                        }


                    }else {
                        if (themeSetupCallback != null){

                            themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SCOPE_INNER);
                        }else {


                            ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtColor));
                            ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                            ((NormalViewHolder) holder).leftSelectBgV.setVisibility(View.VISIBLE);
                            ((NormalViewHolder) holder).rightSelectBgV.setVisibility(View.VISIBLE);

                            ((NormalViewHolder) holder).leftSelectBgV.setBackgroundColor(tintColor);
                            ((NormalViewHolder) holder).rightSelectBgV.setBackgroundColor(tintColor);
                            ((NormalViewHolder) holder).leftSelectBgV.getBackground().setAlpha(tintAlpha);
                            ((NormalViewHolder) holder).rightSelectBgV.getBackground().setAlpha(tintAlpha);
                        }

                    }


                    ((NormalViewHolder) holder).disable = false;
                    selectItemPosition = position;
                }else if (maxDate != null && dateEquals(item, maxDate) > 0){
                    if (themeSetupCallback != null){

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);
                    }else {

                        ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                        ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                    }
                    ((NormalViewHolder) holder).disable = true;
                }else if (minDate != null && dateEquals(item, minDate) < 0){
                    if (themeSetupCallback != null){

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);
                    }else {

                        ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                        ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                    }
                    ((NormalViewHolder) holder).disable = true;
                }else if (currentDate != null && dateMonthEquals(item, currentDate) != 0){
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
                    ((NormalViewHolder) holder).disable = false;
                }

            }else {

                if (selectDate != null && dateEquals(selectDate, item) == 0){
                    if (themeSetupCallback != null){

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_SELECT_SINGLE);
                    }else {

                        ((NormalViewHolder) holder).titleTV.setTextColor(Color.WHITE);
                        ((NormalViewHolder) holder).selectBgV.setVisibility(View.VISIBLE);
                        ViewCompat.setBackgroundTintList(((NormalViewHolder) holder).selectBgV, ColorStateList.valueOf(tintColor));
                    }
                    ((NormalViewHolder) holder).disable = false;
                    selectItemPosition = position;
                }else if (maxDate != null && dateEquals(item, maxDate) > 0){
                    if (themeSetupCallback != null){

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);
                    }else {

                        ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                        ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                    }
                    ((NormalViewHolder) holder).disable = true;
                }else if (minDate != null && dateEquals(item, minDate) < 0){
                    if (themeSetupCallback != null){

                        themeSetupCallback.setupItemTheme(itemLayout, holder.itemView, CalendarChooser.THEME_ITEM_DISABLE);
                    }else {

                        ((NormalViewHolder) holder).titleTV.setTextColor(ContextCompat.getColor(context, R.color.dateTxtDisableColor));
                        ((NormalViewHolder) holder).selectBgV.setVisibility(View.GONE);
                    }
                    ((NormalViewHolder) holder).disable = true;
                }else if (currentDate != null && dateMonthEquals(item, currentDate) != 0){
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
                    ((NormalViewHolder) holder).disable = false;
                }

            }


            ((NormalViewHolder) holder).itemPosition = position;
            ((NormalViewHolder) holder).itemDate = item;
        }

    }

    private int dateEquals(Calendar date1, Calendar date2){

        if (date1.get(Calendar.YEAR) > date2.get(Calendar.YEAR)){
            return 1;
        }else if (date1.get(Calendar.YEAR) < date2.get(Calendar.YEAR)){
            return -1;
        }else {
            if (date1.get(Calendar.MONTH) > date2.get(Calendar.MONTH)){
                return 1;
            }else if (date1.get(Calendar.MONTH) < date2.get(Calendar.MONTH)){
                return -1;
            }else {

                return Integer.compare(date1.get(Calendar.DAY_OF_MONTH), date2.get(Calendar.DAY_OF_MONTH));
            }
        }
    }

    private int dateMonthEquals(Calendar date1, Calendar date2){

        if (date1.get(Calendar.YEAR) > date2.get(Calendar.YEAR)){
            return 1;
        }else if (date1.get(Calendar.YEAR) < date2.get(Calendar.YEAR)){
            return -1;
        }else {
            return Integer.compare(date1.get(Calendar.MONTH), date2.get(Calendar.MONTH));
        }
    }

    private boolean sameWeek(Calendar date1, Calendar date2){
//        if (date1.get(Calendar.YEAR) != date2.get(Calendar.YEAR)){
//            int compare1 = date1.compareTo(date2);
//            int compare2 = date2.compareTo(date1);
//
//            Log.e("daxiang", "compare1 : "+ Math.abs(compare1) +", compare2 : "+ Math.abs(compare2));
//            return false;
//        }else {

            return getWeekIndex(date1) == getWeekIndex(date2);
//        }
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
            selectDate = Calendar.getInstance();
            selectDate.setTime(itemDate.getTime());

            if (rvItemEventListener != null){
                rvItemEventListener.selectADate(selectDate, (currentDate != null && dateMonthEquals(itemDate, currentDate) != 0));
            }
            if (weekmode){

                notifyDataSetChanged();
            }else {

                if (selectItemPosition >= 0){
                    notifyItemChanged(selectItemPosition);
                }
                selectItemPosition = itemPosition;
                notifyItemChanged(itemPosition);
            }

        }
    }


}
