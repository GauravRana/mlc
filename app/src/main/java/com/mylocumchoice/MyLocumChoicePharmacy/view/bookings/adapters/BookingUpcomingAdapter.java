package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Booking;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.MapActivity;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookingUpcomingAdapter extends RecyclerView.Adapter<BookingUpcomingAdapter.ViewHolder> {

    private Context mContext;
    List<Booking> lv_bookings;
    ListItemClickListener mListener;


    public BookingUpcomingAdapter(Context mContext, List<Booking> lv_bookings,ListItemClickListener mListener) {
        this.mContext = mContext;
        this.lv_bookings = lv_bookings;
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
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
        @BindView(R.id.mainLayout)
        LinearLayout mainLayout;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookingUpcomingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_booking, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ButterKnife.bind(this, v);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }



//    @OnClick(R.id.mainLayout)
//    public void onClick(){
//        Intent intent = new Intent(mContext, MapActivity.class);
//        mContext.startActivity(intent);
//    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

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
            holder.tvPlace.setText(lv_bookings.get(position).getTown() + "");
        } else {
            holder.tvPlace.setVisibility(View.GONE);
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(position);
            }
        });
        if(lv_bookings.get(position).getAddressLatitude()!=null &&lv_bookings.get(position).getAddressLongitude()!=null) {
            holder.ivTeal.setVisibility(View.VISIBLE);
        }else{
            holder.ivTeal.setVisibility(View.GONE);
        }

            holder.ivTeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGoogleMapsInstalled()) {
                    if(lv_bookings.get(position).getAddressLatitude()!=null &&lv_bookings.get(position).getAddressLongitude()!=null) {
                        if(lv_bookings.get(position).getAddressLatitude() instanceof String && lv_bookings.get(position).getAddressLongitude() instanceof String)
                        {
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + (String)lv_bookings.get(position).getAddressLatitude() + "," + (String)lv_bookings.get(position).getAddressLongitude() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            mContext.startActivity(intent);

//                            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble((String)lv_bookings.get(position).getAddressLatitude()), Double.parseDouble((String)lv_bookings.get(position).getAddressLongitude()));
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                            mContext.startActivity(intent);
                        }
                    }
                }else{
                    final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getResources().getString(R.string.applicationId))));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + mContext.getResources().getString(R.string.applicationId))));
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return lv_bookings.size();
    }

    public boolean isGoogleMapsInstalled()
    {
        try
        {
            ApplicationInfo info = mContext.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }


}