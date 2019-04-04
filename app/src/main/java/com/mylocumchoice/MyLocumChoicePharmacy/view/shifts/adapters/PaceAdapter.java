package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.PaceItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaceAdapter extends RecyclerView.Adapter<PaceAdapter.ViewHolder> {


    Context mContext;
    List<OpenShiftResponse.Pace> lv_pace;
    PaceItemClickListener mListener;

    public PaceAdapter(Context mContext, List<OpenShiftResponse.Pace> lv_pace, PaceItemClickListener mListener) {
        this.mContext=mContext;
        this.lv_pace=lv_pace;
        this.mListener=mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_pace, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(lv_pace.get(position).isSelected()){
            holder.ivPace.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cb_on));
        }else{
            holder.ivPace.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cb_off));
        }

        holder.tvPace.setText(lv_pace.get(position).getName());
        holder.llPace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lv_pace.get(position).isSelected()){
                    lv_pace.get(position).setSelected(false);
                    holder.ivPace.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cb_off));
                    mListener.onPaceSelected(position,false);
                }else{
                    lv_pace.get(position).setSelected(true);
                    holder.ivPace.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cb_on));
                    mListener.onPaceSelected(position,true);
                }
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lv_pace.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.tv_pace)
        TextView tvPace;
        @BindView(R.id.iv_pace)
        ImageView ivPace;
        @BindView(R.id.ll_pace)
        LinearLayout llPace;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
