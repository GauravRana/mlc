package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.app.Activity;
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
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.AddOtherReasonActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectReasonAdapter extends RecyclerView.Adapter<SelectReasonAdapter.ViewHolder> {

    Context mContext;
    List<BookingDetailResponse.CancelReason> lv_reason;
    int bookingId;
    ListItemClickListener mListener;
    int requestCode;
    Activity mActivity;


    public SelectReasonAdapter(Activity mActivity, Context mContext, List<BookingDetailResponse.CancelReason> lv_reason, int bookingId, ListItemClickListener mListener,int requestCode) {
        this.mContext = mContext;
        this.lv_reason = lv_reason;
        this.bookingId = bookingId;
        this.mListener = mListener;
        this.mActivity = mActivity;
        this.requestCode = requestCode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.rv_radio_button, parent, false);
        // set the view's size, margins, paddings and layout parameters

        final ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvTitle.setText(lv_reason.get(position).getName());
        if (lv_reason.get(position).isSelected()){
            holder.rbPub.setImageResource(R.drawable.rb_on);
        }else{
            holder.rbPub.setImageResource(R.drawable.rb_off);
        }

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<lv_reason.size();i++) {
                        if (i == position) {
                            if (lv_reason.get(position).isSelected()) {
                                lv_reason.get(position).setSelected(false);
                            } else {
                                lv_reason.get(position).setSelected(true);
                            }

                            if(lv_reason.get(position).getInputRequired()){
                                Intent mIntent=new Intent(mContext, AddOtherReasonActivity.class);
                                mIntent.putExtra("bookingId",bookingId);
                                mIntent.putExtra("reasonId",lv_reason.get(position).getId());
                                mActivity.startActivityForResult(mIntent,requestCode);
                            }else{
                                mListener.onClick(position);
                            }
                        } else {
                            lv_reason.get(i).setSelected(false);
                        }
                        notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return lv_reason.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.rb_Pub)
        ImageView rbPub;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_pub)
        LinearLayout llPub;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.tvOther)
        TextView tvOther;


        /**
         * ButterKnife Code
         **/

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
