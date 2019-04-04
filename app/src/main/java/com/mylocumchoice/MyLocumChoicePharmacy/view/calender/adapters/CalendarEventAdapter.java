package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.MyListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarEventAdapter extends RecyclerView.Adapter<CalendarEventAdapter.ViewHolder> {

        Context mcontext;
        List<CalendarEventsRes.Event> lv_events;
        List<CalendarEventsRes.CalendarEvent> lv_calendar;
        private String dateFromCal;
        private String datefromAPI;
        private Activity mActivity;
        private CalendarListEventsAdapter adapter;

        private MyListener ml;

        public CalendarEventAdapter(Activity mActivity, Context mcontext, List<CalendarEventsRes.Event> lv_events, List<CalendarEventsRes.CalendarEvent> lv_calendar,
                                    String dateFromCal, MyListener ml) {
            this.mcontext = mcontext;
            this.lv_events = lv_events;
            this.lv_calendar = lv_calendar;
            this.mActivity = mActivity;
            this.dateFromCal = dateFromCal;
            this.ml = ml;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.calendar_event_adapter, parent, false);
            final ViewHolder vh = new ViewHolder(v);
            vh.recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            lv_events = lv_calendar.get(position).getEvents();
                try {
                    if (lv_calendar.get(position).getDate().equalsIgnoreCase(modifyDateLayout(dateFromCal))) {
                        datefromAPI = lv_calendar.get(position).getDate().toString();
                        adapter = new CalendarListEventsAdapter(mActivity, mcontext, lv_events, lv_calendar, datefromAPI, ml);
                        holder.recyclerView.setAdapter(adapter);
                    }else{
                        holder.recyclerView.setAdapter(null);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }

        @Override
        public int getItemCount() {
            return lv_calendar.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            /**
             * ButterKnife Code
             **/
            @BindView(R.id.recyclerView)
            RecyclerView recyclerView;

            @BindView(R.id.tv_noData)
            TextView tvNoData;


            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        private String modifyDateLayout(String inputDate) throws ParseException {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-M-d");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format1.parse(inputDate);
            return format2.format(date);

        }

}
