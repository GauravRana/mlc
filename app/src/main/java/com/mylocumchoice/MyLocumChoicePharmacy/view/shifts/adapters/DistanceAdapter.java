package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DistanceAdapter extends RecyclerView.Adapter<DistanceAdapter.ViewHolder> {


    Context mContext;
    List<OpenShiftResponse.Distance> lv_distance;
    ListItemClickListener mListener;


    public DistanceAdapter(Context mContext, List<OpenShiftResponse.Distance> lv_distance, ListItemClickListener mListener) {
        this.mContext = mContext;
        this.lv_distance = lv_distance;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_distance, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDistance.setText(lv_distance.get(position).getName());

        holder.llDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lv_distance.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.ll_distance)
        LinearLayout llDistance;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
