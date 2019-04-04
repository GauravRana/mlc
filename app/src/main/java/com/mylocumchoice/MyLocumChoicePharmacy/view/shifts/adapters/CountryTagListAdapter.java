package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.CustomFilter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

    public class CountryTagListAdapter extends RecyclerView.Adapter<CountryTagListAdapter.ViewHolder> /*implements Filterable*/ {
        private List<OpenShiftResponse.County> arrayList = new LinkedList<>();
       // private List<OpenShiftResponse.County> originalData = null;
       // private List<OpenShiftResponse.County> filteredData = null;
        private CountryTagListAdapter adapter = null;

        private onCountryClick onClick;
        private CustomFilter filter;

        public interface onCountryClick {
            void onItemClick(int position, List<OpenShiftResponse.County> lv_county);
        }

        private Context context;

        public CountryTagListAdapter(Context context, CountryTagListAdapter adapter, List<OpenShiftResponse.County> arrayList, onCountryClick onClick) {
            //this.originalData = arrayList;
            //this.filteredData = arrayList;
            this.arrayList = arrayList;
            this.context = context;
            this.onClick = onClick;
            this.adapter = adapter;
            // TODO Auto-generated constructor stub
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            /**
             * ButterKnife Code
             **/
            @BindView(R.id.layout_list)
            LinearLayout layoutList;
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.iv_add)
            ImageView ivAdd;

            @BindView(R.id.shadow_up)
            View shadow_up;

            /**
             * ButterKnife Code
             **/
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

       /* @Override
        public Filter getFilter() {
            if(filter==null)
            {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence charSequence) {
                        String charString = charSequence.toString();
                        if (charString.isEmpty()) {
                            originalData = arrayList;
                        } else {
                            List<OpenShiftResponse.County> filteredList = new ArrayList<>();
                            for (OpenShiftResponse.County row : arrayList) {

                                // name match condition. this might differ depending on your requirement
                                // here we are looking for name or phone number match
                                if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                                    filteredList.add(row);
                                }
                            }

                            originalData = filteredList;
                        }

                        FilterResults filterResults = new FilterResults();
                        filterResults.values = originalData;
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                        originalData = (ArrayList<OpenShiftResponse.County>) filterResults.values;
                        notifyDataSetChanged();
                    }
                };
            }

            return filter;
        }*/

        @NonNull
        @Override
        public CountryTagListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.adapter_country_tag_list, parent, false);
            final CountryTagListAdapter.ViewHolder vh = new CountryTagListAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvTitle.setText(arrayList.get(position).getName());
            holder.layoutList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onItemClick(position,arrayList);
                }
            });

            if(position==0){
                holder.shadow_up.setVisibility(View.GONE);
            }else{
                holder.shadow_up.setVisibility(View.VISIBLE);
            }
        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public void filterList(ArrayList<OpenShiftResponse.County> filterdNames) {
            this.arrayList = filterdNames;
            notifyDataSetChanged();
        }

    }



