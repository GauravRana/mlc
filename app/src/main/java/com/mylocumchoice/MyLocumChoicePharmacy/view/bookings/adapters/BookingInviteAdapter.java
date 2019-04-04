package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingInviteAdapter extends RecyclerView.Adapter<BookingInviteAdapter.ViewHolder> {
    private RecyclerViewClickListener mListener;
    private Context mContext;
    private BaseFragment fragment;
    private String from;

    List<OpenShiftResponse.Shift> al_shift;
    ListItemClickListener listListener;

    /*public BookingInviteAdapter(BaseFragment fragment, Context mContext,RecyclerViewClickListener listener) {
        this.mListener = listener;
        this.mContext = mContext;
        this.fragment = fragment;
        this.from = from;
    }*/

    public BookingInviteAdapter(Context mContext, List<OpenShiftResponse.Shift> al_shift, ListItemClickListener listListener) {
        this.mContext = mContext;
        this.al_shift = al_shift;
        this.listListener = listListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.iv_profile)
        de.hdodenhof.circleimageview.CircleImageView ivProfile;
        @BindView(R.id.tv_head)
        TextView tvHead;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_Notify)
        ImageView ivNotify;
        @BindView(R.id.iv_teal)
        ImageView ivTeal;
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
        @BindView(R.id.ll_pace)
        LinearLayout llPace;
        @BindView(R.id.tv_Pace)
        TextView tvPace;
        @BindView(R.id.tv_subLoc)
        TextView tvSubLoc;
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

    // Create new views (invoked by the layout manager)
    @Override
    public BookingInviteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_openshift, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ButterKnife.bind(this, v);
        final ViewHolder vh = new ViewHolder(v);
        /*v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v,vh.getAdapterPosition());
            }
        });*/

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (al_shift.get(position).getName() != null) {
            holder.tvHead.setVisibility(View.VISIBLE);
            holder.tvHead.setText(al_shift.get(position).getName());
        } else {
            holder.tvHead.setVisibility(View.GONE);
        }

        if (al_shift.get(position).getRate() != null) {
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(al_shift.get(position).getRate());
        } else {
            holder.tvPrice.setVisibility(View.GONE);
        }

        if (al_shift.get(position).getDate() != null) {
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvDate.setText(al_shift.get(position).getDate());
        } else {
            holder.tvDate.setVisibility(View.GONE);
        }

        if (al_shift.get(position).getTimeRange() != null) {
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(al_shift.get(position).getTimeRange());
        } else {
            holder.tvTime.setVisibility(View.GONE);
        }

        if (al_shift.get(position).getPace() != null) {
            holder.tvPace.setVisibility(View.VISIBLE);
            holder.tvPace.setText(mContext.getResources().getString(R.string.pace) +" "+ al_shift.get(position).getPace());
        } else {
            holder.tvPace.setVisibility(View.GONE);
        }

        if (al_shift.get(position).getCounty() != null) {
            holder.tvPlace.setVisibility(View.VISIBLE);
            holder.tvPlace.setText(al_shift.get(position).getCounty());
        } else {
            holder.tvPlace.setVisibility(View.GONE);
        }

        if (al_shift.get(position).getDistance() != null) {
            holder.tvSubLoc.setVisibility(View.VISIBLE);
            holder.tvSubLoc.setText(al_shift.get(position).getDistance() + "");
        } else {
            holder.tvSubLoc.setVisibility(View.GONE);
        }

        if (al_shift.get(position).getEmergency()) {
            holder.ivNotify.setVisibility(View.VISIBLE);
        } else {
            holder.ivNotify.setVisibility(View.GONE);
        }

        if (al_shift.get(position).isBest_match()) {
            holder.ivTeal.setVisibility(View.VISIBLE);
        } else {
            holder.ivTeal.setVisibility(View.GONE);
        }

        if (al_shift.get(position).getLogo() != null) {
            if (al_shift.get(position).getLogo() instanceof String) {
                if (!((String) al_shift.get(position).getLogo()).equals("") && !((String) al_shift.get(position).getLogo()).equals(null)) {
                    Glide.with(mContext).load((String) al_shift.get(position).getLogo()).into(holder.ivProfile);
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
                listListener.onClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return al_shift.size();
    }


}
