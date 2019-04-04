package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingAdapterOptions extends RecyclerView.Adapter<BookingAdapterOptions.ViewHolder> {

    private Context context;
    private BookingDetailResponse response;
    private RecyclerView.ViewHolder viewHolder;
    private List<BookingDetailResponse.ListOption> arrayListOption = new ArrayList<>();
    private List<BookingDetailResponse.Lists> arrayList;
    private List<String> arrayListComplete = new ArrayList<>();
    private String from;



    public BookingAdapterOptions(Context context, BookingDetailResponse response, List<BookingDetailResponse.Lists> arrayList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_skills_list_option, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        arrayListOption = arrayList.get(position).getListOptions();
        holder.tvTitle.setText(arrayList.get(position).getTitle());
        BookingListAdapterComplete adapter = new BookingListAdapterComplete(context, response, arrayListOption);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.tvTitle)
        TextView tvTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
