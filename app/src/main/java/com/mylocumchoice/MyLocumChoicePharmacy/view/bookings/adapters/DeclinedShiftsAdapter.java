package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.text.Line;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.AppliedShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.ShiftDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeclinedShiftsAdapter extends RecyclerView.Adapter<DeclinedShiftsAdapter.ViewHolder>{

    List<AppliedShiftResponse.Shift> lv_shifts;
    Context mContext;
    ListItemClickListener mListener;
    public DeclinedShiftsAdapter(Context mContext, List<AppliedShiftResponse.Shift> lv_shifts,ListItemClickListener mListener) {
        this.mContext=mContext;
        this.lv_shifts=lv_shifts;
        this.mListener=mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_book_complete_history, parent, false);

        // set the view's size, margins, paddings and layout parameters
        final DeclinedShiftsAdapter.ViewHolder vh = new DeclinedShiftsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvLab.setVisibility(View.GONE);
        holder.tvPriceLab.setVisibility(View.GONE);

        holder.tvLab.setVisibility(View.GONE);
        holder.tvPriceLab.setVisibility(View.GONE);

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(position);
            }
        });

        if (lv_shifts.get(position).getName() != null) {
            holder.tvHead.setVisibility(View.VISIBLE);
            holder.tvHead.setText(lv_shifts.get(position).getName());
        } else {
            holder.tvHead.setVisibility(View.GONE);
        }

        if (lv_shifts.get(position).getRate() != null) {
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(lv_shifts.get(position).getRate());
        } else {
            holder.tvPrice.setVisibility(View.GONE);
        }
        if (lv_shifts.get(position).getDate() != null) {
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvDate.setText(lv_shifts.get(position).getDate());
        } else {
            holder.tvDate.setVisibility(View.GONE);
        }

        if (lv_shifts.get(position).getTimeRange() != null) {
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(lv_shifts.get(position).getTimeRange());
        } else {
            holder.tvTime.setVisibility(View.GONE);
        }
        if (lv_shifts.get(position).getCounty() != null) {
            holder.tvPlace.setVisibility(View.VISIBLE);
            holder.tvPlace.setText(lv_shifts.get(position).getCounty() + "");
        } else {
            holder.tvPlace.setVisibility(View.GONE);
        }

        if (lv_shifts.get(position).getLogo() != null) {
            if (lv_shifts.get(position).getLogo() instanceof String) {
                if (!((String) lv_shifts.get(position).getLogo()).equals("") && !((String) lv_shifts.get(position).getLogo()).equals(null)) {
                    Glide.with(mContext).load((String) lv_shifts.get(position).getLogo()).into(holder.ivProfile);
                } else {
                    Glide.with(mContext).load(R.drawable.pharmacy_placeholder).into(holder.ivProfile);
                }
            } else {
                Glide.with(mContext).load(R.drawable.pharmacy_placeholder).into(holder.ivProfile);
            }
        } else {
            Glide.with(mContext).load(R.drawable.pharmacy_placeholder).into(holder.ivProfile);
        }

    }

    @Override
    public int getItemCount() {
        return lv_shifts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        /** ButterKnife Code **/
        @BindView(R.id.iv_profile)
        de.hdodenhof.circleimageview.CircleImageView ivProfile;
        @BindView(R.id.tv_head)
        TextView tvHead;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_lab)
        TextView tvLab;
        @BindView(R.id.tv_price_lab)
        TextView tvPriceLab;
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
        /** ButterKnife Code **/

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
