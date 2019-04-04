package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.PharmacyFilterAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PharmaComplianceDetails;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.CountryTagListAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.CustomFilter;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class PharmacyCompAdapter extends  RecyclerView.Adapter<PharmacyCompAdapter.ViewHolder> implements Filterable {
    private final Context context;
    List<ComplianceRes.Client> lv_clients;
    private CustomFilter filter;
    private List<ComplianceRes.Client> filteredData = null;

    public PharmacyCompAdapter(Context context,List<ComplianceRes.Client> lv_clients) {
        this.context = context;
        this.lv_clients = lv_clients;
        this.filteredData = lv_clients;
    }


    @NonNull
    @Override
    public PharmacyCompAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_compliance, parent, false);
        final PharmacyCompAdapter.ViewHolder vh = new PharmacyCompAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyCompAdapter.ViewHolder holder, int position) {
        if(lv_clients.get(position).getName() != null) {
            holder.tvTitle.setText(lv_clients.get(position).getName());
        }


//        if(lv_clients.get(position).getWarning()){
//            holder.ivWarning.setVisibility(View.GONE);
//        }else{
//            holder.ivWarning.setVisibility(View.VISIBLE);
//        }

        if(lv_clients.get(position).getStatus() != null) {
            if (lv_clients.get(position).getStatus().getName() != null) {
                holder.tvSub.setText(" "+lv_clients.get(position).getStatus().getName());
                holder.tvSub.setTextColor(Color.parseColor(lv_clients.get(position).getStatus().getColour()));
            }
        }
        if(lv_clients.get(position).getLogo() != null){
            Glide.with(context).load(lv_clients.get(position).getLogo().toString()).into(holder.ivIcon);
        }else{
            Glide.with(context).load(R.drawable.pharmacy_placeholder).into(holder.ivIcon);
        }


        if(lv_clients.get(position).getWarning()){
            holder.ivWarning.setVisibility(View.VISIBLE);
        }else{
            holder.ivWarning.setVisibility(View.GONE);
        }



        holder.layoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",lv_clients.get(position).getId());
                Intent intent = new Intent(context, PharmaComplianceDetails.class);
                intent.putExtra("from", "compliance");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return lv_clients.size();
    }

    @Override
    public Filter getFilter() {
        try {
            if (filter == null) {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence charSequence) {
                        String charString = charSequence.toString();
                        if (charString.isEmpty()) {
                            filteredData = lv_clients;
                        } else {
                            List<ComplianceRes.Client> filteredList = new ArrayList<>();
                            for (ComplianceRes.Client row : lv_clients) {

                                // name match condition. this might differ depending on your requirement
                                // here we are looking for name or phone number match
                                if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                                    filteredList.add(row);
                                }
                            }

                            filteredData = filteredList;
                        }

                        FilterResults filterResults = new FilterResults();
                        filterResults.values = filteredData;
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                        filteredData = (List<ComplianceRes.Client>) filterResults.values;
                        notifyDataSetChanged();
                    }
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filter;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        /** ButterKnife Code **/
        @BindView(R.id.layout_list)
        LinearLayout layoutList;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_sub)
        TextView tvSub;
        @BindView(R.id.iv_add)
        ImageView ivAdd;
        @BindView(R.id.iv_icon)
        CircleImageView ivIcon;
        @BindView(R.id.iv_warning)
        ImageView ivWarning;

        /** ButterKnife Code **/
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void filterList(List<ComplianceRes.Client> filterdNames) {
        try{
            this.lv_clients = filterdNames;
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
