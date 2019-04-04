package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowEventsAdapter extends RecyclerView.Adapter<ShowEventsAdapter.ViewHolder>{

    Context mcontext;
    List<CalendarEventsRes.Event> lv_events;
    List<CalendarEventsRes.CalendarEvent> lv_calendar;


    public ShowEventsAdapter(Context mcontext, List<CalendarEventsRes.Event> lv_events, List<CalendarEventsRes.CalendarEvent> lv_calendar) {
        this.mcontext = mcontext;
        this.lv_events = lv_events;
        this.lv_calendar = lv_calendar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.show_events_adapter, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvTitle.setText(lv_events.get(position).getTitle().toString());
            if(lv_events.get(position).getSubTitle() != null) {
                holder.tvDesc.setText(lv_events.get(position).getSubTitle().toString());
            }
            if(lv_events.get(position).getSubTitle() != null) {
                holder.tvStartTime.setText(lv_events.get(position).getSubTitle().toString());
            }
            if(lv_events.get(position).getStartTime() != null) {
                holder.tvStartTime.setText(lv_events.get(position).getStartTime().toString());
            }

            if(lv_events.get(position).getEndTime() != null) {
                holder.tvEndTime.setText(lv_events.get(position).getEndTime().toString());
            }

            if(lv_events.get(position).getTitle() != null){
                holder.tvTitle.setText(lv_events.get(position).getTitle().toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /** ButterKnife Code **/
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.tv_startTime)
        TextView tvStartTime;
        @BindView(R.id.tv_endTime)
        TextView tvEndTime;
        @BindView(R.id.view_divide)
        View viewDivide;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        /** ButterKnife Code **/

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
