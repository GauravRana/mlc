package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.CustomFilter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.CountryTagListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PharmacyFilterAdapter extends RecyclerView.Adapter<PharmacyFilterAdapter.ViewHolder> implements Filterable {

    private final Context context;

    List<CompletedBookingResponse.Client> lv_clients;

    private List<CompletedBookingResponse.Client> filteredData = null;
    private CustomFilter filter;
    private onClientClick onClick;
    private PharmacyFilterAdapter adapter = null;

    public interface onClientClick {
        void onItemClick(int position, List<CompletedBookingResponse.Client> lv_client);
    }

    public PharmacyFilterAdapter(Context context, PharmacyFilterAdapter adapter, List<CompletedBookingResponse.Client> lv_clients, onClientClick onClick) {
        this.lv_clients = lv_clients;
        this.context = context;
        this.onClick = onClick;
        this.adapter = adapter;
        // TODO Auto-generated constructor stub
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_pharma_list, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position==0){
            holder.shadow_up.setVisibility(View.GONE);
        }else{
            holder.shadow_up.setVisibility(View.VISIBLE);
        }
        holder.tvTitle.setText(lv_clients.get(position).getName());

        if(lv_clients.get(position).getLogo()!=null){
            if(lv_clients.get(position).getLogo() instanceof String){
                if(!((String)lv_clients.get(position).getLogo()).equals("")&&!((String)lv_clients.get(position).getLogo()).equals(null)){
                    Glide.with(context).load((String)lv_clients.get(position).getLogo()).into(holder.iv_img);
                }else{
                    Glide.with(context).load(R.drawable.pharmacy_placeholder).into(holder.iv_img);
                }
            }else{
                Glide.with(context).load(R.drawable.pharmacy_placeholder).into(holder.iv_img);
            }
        }else{
            Glide.with(context).load(R.drawable.pharmacy_placeholder).into(holder.iv_img);
        }

        holder.layoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position,lv_clients);
            }
        });

        /*try {
            if (position == lv_clients.size()-1) {
                holder.shadow4.setVisibility(View.VISIBLE);
            } else {
                holder.shadow4.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }


    @Override
    public int getItemCount() {
        return lv_clients.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        filteredData = lv_clients;
                    } else {
                        List<CompletedBookingResponse.Client> filteredList = new ArrayList<>();
                        for (CompletedBookingResponse.Client row : lv_clients) {

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
                    filteredData = (ArrayList<CompletedBookingResponse.Client>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        return filter;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_add)
        ImageView ivAdd;

        @BindView(R.id.shadow_up)
        View shadow_up;

        @BindView(R.id.iv_img)
        CircleImageView iv_img;
        @BindView(R.id.layout_list)
        LinearLayout layoutList;

        /*@BindView(R.id.shadow4)
        View shadow4;
*/
        /**
         * ButterKnife Code
         **/
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void filterList(ArrayList<CompletedBookingResponse.Client> filterdNames) {
        this.lv_clients = filterdNames;
        notifyDataSetChanged();
    }
}
