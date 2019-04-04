package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventList;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.EventsDetailsActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    Context mContext;
    List<EventList> lv_events;
    ListItemClickListener mListener;

    public EventsAdapter(Context mContext, List<EventList> lv_events, ListItemClickListener mListener) {
        this.mContext = mContext;
        this.lv_events = lv_events;
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /** ButterKnife Code **/
        @BindView(R.id.tv_head)
        TextView tvHead;
        @BindView(R.id.tv_lab)
        TextView tvLab;
        @BindView(R.id.iv_free)
        ImageView ivFree;
        @BindView(R.id.iv_cal)
        ImageView ivCal;
        @BindView(R.id.tv_Date)
        TextView tvDate;
        @BindView(R.id.iv_clock)
        ImageView ivClock;
        @BindView(R.id.tv_Time)
        TextView tvTime;
        @BindView(R.id.iv_loc)
        ImageView ivLoc;
        @BindView(R.id.tv_Place)
        TextView tvPlace;
        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.layout)
        LinearLayout layout;


        /** ButterKnife Code **/

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_events, parent, false);
        // set the view's size, margins, paddings and layout parameters

        final EventsAdapter.ViewHolder vh = new EventsAdapter.ViewHolder(v);

        return vh;
    }


    @OnClick(R.id.iv_loc)
    public void onLocClick() {

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, final int position) {
        if(lv_events.get(position).getName()!=null){
            if(lv_events.get(position).getName() instanceof String) {
                holder.tvHead.setText((String)lv_events.get(position).getName());
            }
        }

        if(lv_events.get(position).getPrice()!=null
                &&!lv_events.get(position).getPrice().equalsIgnoreCase("0.0")
        &&!lv_events.get(position).getPrice().equalsIgnoreCase("free")){
            holder.ivFree.setVisibility(View.GONE);
            holder.tvLab.setVisibility(View.VISIBLE);
            holder.tvLab.setText(lv_events.get(position).getPrice());
        }else{
            holder.ivFree.setVisibility(View.VISIBLE);
        }

        if(lv_events.get(position).getDate()!=null && !lv_events.get(position).getDate().equals("")){
            holder.tvDate.setText(lv_events.get(position).getDate());
        }else{
            holder.tvDate.setVisibility(View.GONE);
        }

        if(lv_events.get(position).getTime()!=null && !lv_events.get(position).getTime().equals("")){
            holder.tvTime.setText(lv_events.get(position).getTime());
        }else{
            holder.tvTime.setVisibility(View.GONE);
        }

        if(lv_events.get(position).getTown()!=null && !lv_events.get(position).getTown().equals("")){
            holder.tvPlace.setText(lv_events.get(position).getTown());
        }else{
            holder.tvPlace.setVisibility(View.GONE);
        }


        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(lv_events.get(position));
                Intent mIntent=new Intent(mContext,EventsDetailsActivity.class);
                mContext.startActivity(mIntent);
            }
        });


//        if(position == 0){
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.llMain.getLayoutParams();
//            params.setMargins(0, 60, 0, 0);
//        }
//        if(position == lv_events.size() - 1){
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.llMain.getLayoutParams();
//            params.setMargins(0, 0, 0, 60);
//        }

    }


    @Override
    public int getItemCount() {
        return lv_events.size();
    }
}
