package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventModel;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.ShowCalendarEvents;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.MapActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.MarkAsBusyActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.MyListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.ShowEventsActivity;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarListEventsAdapter extends RecyclerView.Adapter<CalendarListEventsAdapter.ViewHolder> {


    Context mcontext;
    List<CalendarEventsRes.Event> lv_events;
    List<CalendarEventsRes.CalendarEvent> lv_calendar_events;
    private String datefromAPI;
    private Activity mActivity;

    MyListener ml;

    int REQUEST_DETAIL_CODE = 12;


    public CalendarListEventsAdapter(Activity mActivity, Context mcontext,
                                     List<CalendarEventsRes.Event> lv_events,
                                     List<CalendarEventsRes.CalendarEvent> lv_calendar_events,
                                     String datefromAPI,
                                     MyListener ml) {
        this.mcontext = mcontext;
        this.lv_events = lv_events;
        this.lv_calendar_events = lv_calendar_events;
        this.datefromAPI = datefromAPI;
        this.mActivity = mActivity;
        this.ml = ml;
    }

    public CalendarListEventsAdapter(Activity mActivity, Context mcontext,
                                     String datefromAPI,
                                     MyListener ml) {
        this.mcontext = mcontext;
        this.datefromAPI = datefromAPI;
        this.mActivity = mActivity;
        this.ml = ml;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_calendar_events, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if(lv_events!=null || lv_events.size()!=0) {
                holder.row_item.setVisibility(View.VISIBLE);
                holder.row_item.setBackgroundColor(mcontext.getResources().getColor(R.color.white));
                holder.view_down.setVisibility(View.VISIBLE);
                holder.tvTitle.setTextColor(mcontext.getResources().getColor(R.color.black));
                holder.tvTitle.setTextSize(16);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tvTitle.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                holder.tvTitle.setLayoutParams(params);

                holder.tvTitle.setText(lv_events.get(position).getTitle().toString());
                if (lv_events.get(position).getSubTitle() != null) {
                    holder.tvDesc.setText(lv_events.get(position).getSubTitle().toString());
                } else {
                    holder.tvDesc.setVisibility(View.GONE);
                }
                if (lv_events.get(position).getSubTitle() != null) {
                    holder.tvStartTime.setText(lv_events.get(position).getSubTitle().toString());
                }
                if (lv_events.get(position).getStartTime() != null) {
                    holder.tvStartTime.setText(lv_events.get(position).getStartTime().toString());
                }

                if (lv_events.get(position).getEndTime() != null) {
                    holder.tvEndTime.setText(lv_events.get(position).getEndTime().toString());
                }

                if (lv_events.get(position).getTitle() != null) {
                    holder.tvTitle.setText(lv_events.get(position).getTitle().toString());
                }

                if (position == 0) {
                    holder.view_up.setVisibility(View.VISIBLE);
                    holder.view_white.setVisibility(View.VISIBLE);
                } else {
                    holder.view_up.setVisibility(View.GONE);
                    holder.view_white.setVisibility(View.GONE);
                }
            }else{
                holder.row_item.setBackgroundColor(mcontext.getResources().getColor(R.color.grey_background));
                holder.view_down.setVisibility(View.GONE);
                /*if(position!=0){
                    holder.view_up.setVisibility(View.GONE);
                    holder.view_white.setVisibility(View.GONE);*/
                    holder.tvTitle.setText("No Events");
                    holder.tvTitle.setTextColor(mcontext.getResources().getColor(R.color.grey));
                    holder.tvTitle.setTextSize(15);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tvTitle.getLayoutParams();
                    params.setMargins((int)mcontext.getResources().getDimension(R.dimen._40sdp), 0, 0, 0);
                    holder.tvTitle.setLayoutParams(params);
               // }
            }

        }catch (Exception e){
            e.printStackTrace();
            holder.view_divide.setVisibility(View.GONE);
            holder.row_item.setBackgroundColor(mcontext.getResources().getColor(R.color.grey_background));
            holder.view_down.setVisibility(View.GONE);
           /* if(position!=0){
                holder.view_up.setVisibility(View.GONE);
                holder.view_white.setVisibility(View.GONE);*/
                holder.tvTitle.setText("No Events");
                holder.tvTitle.setTextColor(mcontext.getResources().getColor(R.color.grey));
                holder.tvTitle.setTextSize(15);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tvTitle.getLayoutParams();
                params.setMargins((int)mcontext.getResources().getDimension(R.dimen._40sdp), 0, 0, 0);
                holder.tvTitle.setLayoutParams(params);
            //}
        }


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ShowCalendarEvents events = new ShowCalendarEvents();
                    events.setDate(datefromAPI);
                    events.setId(lv_events.get(position).getId());
                    events.setTitle(lv_events.get(position).getTitle());
                    events.setSub_title(lv_events.get(position).getSubTitle());
                    events.setStart_time(lv_events.get(position).getStartTime());
                    events.setEnd_time(lv_events.get(position).getEndTime());
                    events.setRecurring(lv_events.get(position).isRecurring());
                    events.setRecurringType(lv_events.get(position).getRecurring_type());
                    events.setMcontext(mcontext);
                    events.setLv_events(lv_events);
                    EventBus.getDefault().postSticky(events);

                    if (lv_events.get(position).getBookingId() == null) {
                        ml.callback();

                    }else{
                        Intent mIntent = new Intent(mcontext, MapActivity.class);
                        try{
                            double d = (double)lv_events.get(position).getBookingId();
                            int i = (int)d;
                            mIntent.putExtra("id", i);
                            mIntent.putExtra("from", "Calendar");
                            mcontext.startActivity(mIntent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            if (lv_events.size() != 0 || lv_events != null)
                return lv_events.size();
        }catch (Exception e){
            e.printStackTrace();
            return 1;
        }
       return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.tv_startTime)
        TextView tvStartTime;
        @BindView(R.id.tv_endTime)
        TextView tvEndTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.view_divide)
        View view_divide;
        @BindView(R.id.view_up)
        View view_up;
        @BindView(R.id.view_down)
        View view_down;
        @BindView(R.id.view_white)
        View view_white;
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.ll_title)
        LinearLayout ll_title;
        @BindView(R.id.row_item)
        LinearLayout row_item;
        @BindView(R.id.tv_noData)
        TextView tvNoData;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
