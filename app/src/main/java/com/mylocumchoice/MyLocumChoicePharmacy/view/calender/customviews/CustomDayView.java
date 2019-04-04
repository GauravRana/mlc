package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.State;
import com.ldf.calendar.interf.IDayRenderer;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.DayView;
import com.mylocumchoice.MyLocumChoicePharmacy.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ldf on 17/6/26.
 */

@SuppressLint("ViewConstructor")
public class CustomDayView extends DayView {

    private TextView dateTv;
    private ImageView marker;
    private View selectedBackground;
    private View todayBackground;
    private final CalendarDate today = new CalendarDate();

    /**
     * 构造器
     *
     * @param context 上下文
     * @param layoutResource 自定义DayView的layout资源
     */
    public CustomDayView(Context context, int layoutResource) {
        super(context, layoutResource);
        dateTv = (TextView) findViewById(R.id.date);
        marker = (ImageView) findViewById(R.id.maker);
        selectedBackground = findViewById(R.id.selected_background);
        todayBackground = findViewById(R.id.today_background);
    }

    @Override
    public void refreshContent() {
        renderToday(day.getDate());
        renderSelect(day.getDate(), day.getState());
        renderMarker(day.getDate(), day.getState());
        super.refreshContent();
    }

    private void renderMarker(CalendarDate date, State state) {
        if (Utils.loadMarkData().containsKey(date.toString())) {
            try{
                if (state == State.SELECT || date.toString().equals(today.toString())) {
                    marker.setVisibility(VISIBLE);
                    if (Utils.loadMarkData().get(date.toString()).equals("0")) {
                        marker.setImageDrawable(getResources().getDrawable(R.drawable.view_syllabus_active_mark_background));
                    } else if (Utils.loadMarkData().get(date.toString()).equals("1")) {
                        marker.setImageDrawable(getResources().getDrawable(R.drawable.view_syllabus_unactive_mark_background));
                    }
                } else {
                    marker.setVisibility(VISIBLE);

                    if (Utils.loadMarkData().get(date.toString()).equals("0")) {
                        marker.setImageDrawable(getResources().getDrawable(R.drawable.view_syllabus_active_mark_background));
                    } else if (Utils.loadMarkData().get(date.toString()).equals("1")) {
                        marker.setImageDrawable(getResources().getDrawable(R.drawable.view_syllabus_unactive_mark_background));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            marker.setVisibility(GONE);
        }
    }

    private void renderSelect(CalendarDate date, State state) {

        java.util.Calendar currentCal= java.util.Calendar.getInstance();
        currentCal.set(java.util.Calendar.MONTH,date.month-1);
        currentCal.set(java.util.Calendar.DAY_OF_MONTH,date.day);
        currentCal.set(java.util.Calendar.YEAR,date.year);
        Date currentDate=currentCal.getTime();

        java.util.Calendar futureCal= java.util.Calendar.getInstance();
        futureCal.set(java.util.Calendar.MONTH, (futureCal.get(java.util.Calendar.MONTH)+6));
        Date futureDate=futureCal.getTime();

        java.util.Calendar pastCal= java.util.Calendar.getInstance();
        pastCal.set(java.util.Calendar.MONTH, (pastCal.get(java.util.Calendar.MONTH)-3));
        Date pastDate=pastCal.getTime();

        if (state == State.SELECT) {
            if(currentDate.before(pastDate)||currentDate.after(futureDate)){
                selectedBackground.setVisibility(GONE);
                dateTv.setTextColor(Color.parseColor("#d5d5d5"));
                dateTv.setClickable(false);
                dateTv.setEnabled(false);
            }else {
                selectedBackground.setVisibility(VISIBLE);
                selectedBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_background_other));

                dateTv.setTextColor(Color.WHITE);
            }
        } else if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
            if(currentDate.before(pastDate)||currentDate.after(futureDate)) {
                selectedBackground.setVisibility(GONE);
                dateTv.setTextColor(Color.parseColor("#d5d5d5"));
                dateTv.setClickable(false);
                dateTv.setEnabled(false);
            }else{
                selectedBackground.setVisibility(GONE);
                dateTv.setTextColor(Color.parseColor("#d5d5d5"));
                dateTv.setClickable(false);
                dateTv.setEnabled(false);
            }
        } else {
            selectedBackground.setVisibility(GONE);

            if(currentDate.before(pastDate)||currentDate.after(futureDate)){
                dateTv.setTextColor(Color.parseColor("#d5d5d5"));
                dateTv.setClickable(false);
                dateTv.setEnabled(false);
            }else if(date.equals(today)) {
                dateTv.setTextColor(Color.WHITE);
               // selectedBackground.setVisibility(VISIBLE);
                //selectedBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_background));
            }else{
                dateTv.setTextColor(Color.parseColor("#111111"));
            }
        }


    }

    private void renderToday(CalendarDate date) {
        if (date != null) {
            if (date.equals(today)) {
                dateTv.setTextColor(Color.WHITE);
                dateTv.setText(date.day + "");
                todayBackground.setVisibility(VISIBLE);
                selectedBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_background));
            } else {
                dateTv.setTextColor(Color.WHITE);
                dateTv.setText(date.day + "");
                todayBackground.setVisibility(GONE);
            }
        }
    }

    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context, layoutResource);
    }
}
