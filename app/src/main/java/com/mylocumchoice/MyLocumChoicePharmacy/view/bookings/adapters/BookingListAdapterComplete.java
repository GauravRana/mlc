package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingListAdapterComplete extends RecyclerView.Adapter<BookingListAdapterComplete.ViewHolder>{

    private Context context;
    private BookingDetailResponse response;
    private RecyclerView.ViewHolder viewHolder;
    private List<BookingDetailResponse.ListOption> arrayListOption;
    private List<String> arrayListComplete = new ArrayList<>();
    private String from;



    public BookingListAdapterComplete(Context context, BookingDetailResponse response, List<BookingDetailResponse.ListOption> arrayListOption) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListOption = arrayListOption;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_skill_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvBulet_1.setText(/*"\u2022" + " " + */arrayListOption.get(position).getName());
        }catch (Exception e){
            e.printStackTrace();
        }


        if (arrayListOption.get(position).getOptions()!=null||arrayListOption.get(position).getOptions().size()>0) {
            holder.rv_other.setVisibility(View.VISIBLE);

            List<Object> othersList=arrayListOption.get(position).getOptions();
            BookingListOtherAdapter bookingListAdapter = new BookingListOtherAdapter(context, response, othersList,position,arrayListOption.size());
            holder.rv_other.setLayoutManager(new LinearLayoutManager(context));
            holder.rv_other.setAdapter(bookingListAdapter);
        } else {
            holder.rv_other.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayListOption.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;
        private RecyclerView recyclerView;
        @BindView(R.id.tv_bullet_1)
        TextView tvBulet_1;
        @BindView(R.id.layout)
        LinearLayout layout;

        @BindView(R.id.rv_other)
        RecyclerView rv_other;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
