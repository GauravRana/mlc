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

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CancelledBookHistoryAdapter extends RecyclerView.Adapter<CancelledBookHistoryAdapter.ViewHolder> {

    private Context mContext;
    List<CompletedBookingResponse.Booking> lv_bookings;

    public CancelledBookHistoryAdapter(Context mContext, List<CompletedBookingResponse.Booking> lv_bookings) {
        this.mContext = mContext;
        this.lv_bookings = lv_bookings;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_book_complete_history, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull CancelledBookHistoryAdapter.ViewHolder holder, int position) {

        if (lv_bookings.get(position).getName() != null) {
            holder.tvHead.setVisibility(View.VISIBLE);
            holder.tvHead.setText(lv_bookings.get(position).getName());
        } else {
            holder.tvHead.setVisibility(View.GONE);
        }

        if (lv_bookings.get(position).getRate() != null) {
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(lv_bookings.get(position).getRate());
        } else {
            holder.tvPrice.setVisibility(View.GONE);
        }

        if (lv_bookings.get(position).getDate() != null) {
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvDate.setText(lv_bookings.get(position).getDate());
        } else {
            holder.tvDate.setVisibility(View.GONE);
        }

        if (lv_bookings.get(position).getTimeRange() != null) {
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(lv_bookings.get(position).getTimeRange());
        } else {
            holder.tvTime.setVisibility(View.GONE);
        }


        if (lv_bookings.get(position).getTown() != null) {
            holder.tvPlace.setVisibility(View.VISIBLE);
            holder.tvPlace.setText(lv_bookings.get(position).getTown());
        } else {
            holder.tvPlace.setVisibility(View.GONE);
        }

        holder.tvLab.setVisibility(View.GONE);
        holder.tvPriceLab.setVisibility(View.GONE);



        if (lv_bookings.get(position).getLogo() != null) {
            if (lv_bookings.get(position).getLogo() instanceof String) {
                if (!((String) lv_bookings.get(position).getLogo()).equals("") && !((String) lv_bookings.get(position).getLogo()).equals(null)) {
                    Glide.with(mContext).load((String) lv_bookings.get(position).getLogo()).into(holder.ivProfile);
                } else {
                    Glide.with(mContext).load(R.drawable.pharmacy_placeholder).into(holder.ivProfile);
                }
            } else {
                Glide.with(mContext).load(R.drawable.pharmacy_placeholder).into(holder.ivProfile);
            }
        } else {
            Glide.with(mContext).load(R.drawable.pharmacy_placeholder).into(holder.ivProfile);
        }


        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext,BookingDetailsActivity.class);
                mIntent.putExtra("isFrom","cancelled");
                mIntent.putExtra("id",lv_bookings.get(position).getId());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lv_bookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        /**
         * ButterKnife Code
         **/
        @BindView(R.id.iv_profile)
        CircleImageView ivProfile;
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

        /**
         * ButterKnife Code
         **/

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}

