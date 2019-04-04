package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class BookingDetailSections extends RecyclerView.Adapter<BookingDetailSections.ViewHolder>  implements ItemClickListener{


    private Context context;
    private BookingDetailResponse response;
    private RecyclerView.ViewHolder viewHolder;
    private List<BookingDetailResponse.Detail> arrayListDetail = new ArrayList<>();
    private List<BookingDetailResponse.Section> arrayListSection;
    boolean isCompleted;
    ItemClickListener itemClickListener=null;

    public BookingDetailSections(Context context, BookingDetailResponse response, List<BookingDetailResponse.Section> arrayListSection,boolean isCompleted) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListSection = arrayListSection;
        this.isCompleted = isCompleted;
    }

    public BookingDetailSections(Context context, BookingDetailResponse response, List<BookingDetailResponse.Section> arrayListSection, boolean isCompleted, ItemClickListener itemClickListener) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListSection = arrayListSection;
        this.isCompleted = isCompleted;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public BookingDetailSections.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_booking_sections, parent, false);
        BookingDetailSections.ViewHolder viewHolder = new BookingDetailSections.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailSections.ViewHolder holder, int position) {
        arrayListDetail = arrayListSection.get(position).getDetails();
        BookingDetailsAdapter adapter = new BookingDetailsAdapter(context, response, arrayListDetail,this);
        holder.recyclerView.setAdapter(adapter);

        switch (context.getResources().getDisplayMetrics().densityDpi) {

            case DisplayMetrics.DENSITY_XHIGH:
                if(isCompleted) {
                    if (position == arrayListSection.size() - 1) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                        params.setMargins(0, 0, 0, 40);
                    }
                }else{
                    if (position == arrayListSection.size() - 1) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                        params.setMargins(0, 0, 0, 30);
                    }
                }
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                if (position == arrayListSection.size() - 1) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                    params.setMargins(0, 0, 0, 50);
                }
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                if (position == arrayListSection.size() - 1) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                    params.setMargins(0, 0, 0, 70);
                }
                break;
            default:
                if (position == arrayListSection.size() - 1) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                    params.setMargins(0, 0, 0, 50);
                }
                break;
        }


        /*if(position == 0){
            holder.shadowUp.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public int getItemCount() {
        return arrayListSection.size();
    }

    @Override
    public void onClick(String data) {
        itemClickListener.onClick(data);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;
        private RecyclerView recyclerView;
        private View shadow;
        private View shadowUp;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            shadow = itemView.findViewById(R.id.shadow);
            shadowUp = itemView.findViewById(R.id.shadowUp);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setNestedScrollingEnabled(true);
        }
    }



}
