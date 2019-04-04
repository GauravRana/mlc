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
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.MapActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.ShiftDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BookingHistoryCompleteAdapter extends RecyclerView.Adapter<BookingHistoryCompleteAdapter.ViewHolder> {


    private Context mContext;
    int type = 0;
    List<CompletedBookingResponse.Booking> lv_bookings;
    ListItemClickListener listItemClickListener;

    public BookingHistoryCompleteAdapter(Context mContext, List<CompletedBookingResponse.Booking> lv_bookings, ListItemClickListener listItemClickListener) {
        this.mContext = mContext;
        this.lv_bookings = lv_bookings;
        this.listItemClickListener = listItemClickListener;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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

        if (lv_bookings.get(position).getPaid()) {
            holder.tvLab.setVisibility(View.VISIBLE);
            holder.tvLab.setText(lv_bookings.get(position).getTotalAmount());
            holder.tvLab.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tvPriceLab.setVisibility(View.VISIBLE);
        }else{
            holder.tvLab.setText("Unpaid");
            holder.tvLab.setTextColor(mContext.getResources().getColor(R.color.grey));
            holder.tvPriceLab.setVisibility(View.GONE);
        }


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
                listItemClickListener.onClick(position);
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

    /*// Create new views (invoked by the layout manager)
    @Override
    public BookingHistoryCompleteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_book_complete_history, parent, false);
        // set the view's size, margins, paddings and layout parameters

        final BookingHistoryCompleteAdapter.ViewHolder vh = new BookingHistoryCompleteAdapter.ViewHolder(v);
        return vh;
    }

<<<<<<< HEAD
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if(type == 1) {
            holder.tvLab.setVisibility(View.GONE);
            holder.tvPriceLab.setVisibility(View.GONE);


        }else{
            holder.tvLab.setVisibility(View.VISIBLE);
            holder.tvPriceLab.setVisibility(View.VISIBLE);

        }


        if(position == 0){
            holder.tvHead.setText("Well");
            holder.tvDate.setText("Jul 29, 2018");
            Glide.with(mContext).load(R.drawable.well).into(holder.ivProfile);
        }if(position == 1){
            holder.tvHead.setText("Cohens");
            holder.tvDate.setText("Jul 30, 2018");
            Glide.with(mContext).load(R.drawable.cohens).into(holder.ivProfile);
        }
    }
=======
>>>>>>> be420ce0d999ff473b0f4950396441bb4e420a31

    @OnClick(R.id.iv_loc)
    public void onLocClick() {

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BookingHistoryCompleteAdapter.ViewHolder holder, final int position) {
        if(type == 1) {
            holder.tvLab.setVisibility(View.GONE);
            holder.tvPriceLab.setVisibility(View.GONE);
        }else{
            holder.tvLab.setVisibility(View.VISIBLE);
            holder.tvPriceLab.setVisibility(View.VISIBLE);
        }

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext, ShiftDetailActivity.class);
                mIntent.putExtra("isFrom", "CompleteBookHistoryFragment");
                mContext.startActivity(mIntent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return 2;
    }

        return lv_bookings.size();
    }*/
}

