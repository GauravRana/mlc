package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class BookingListOtherAdapter extends RecyclerView.Adapter<BookingListOtherAdapter.ViewHolder> {
    private Context context;
    private BookingDetailResponse response;
    private RecyclerView.ViewHolder viewHolder;
    private List<Object> arrayListOption;
    private ArrayList<String> arrayListComplete = new ArrayList<>();
    private String from;
    int pos,lastPos;

    public BookingListOtherAdapter(Context context, BookingDetailResponse response, List<Object> arrayListOption,int pos,int lastPos) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListOption = arrayListOption;
        this.pos = pos;
        this.lastPos = lastPos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;

        @BindView(R.id.tv_bullet)
        TextView tvBulet;

        @BindView(R.id.layout)
        LinearLayout layout;

        @BindView(R.id.ll_other)
        LinearLayout ll_other;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public BookingListOtherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_skill_list_other, parent, false);
        BookingListOtherAdapter.ViewHolder viewHolder = new BookingListOtherAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingListOtherAdapter.ViewHolder holder, int position) {
        // arrayListComplete.add(arrayListOption.get(position).getName().toString());

        try {
            holder.tvBulet.setText(/*"\u2022" +  " " + */arrayListOption.get(position)+"");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(pos==lastPos) {
            if (position == arrayListOption.size() - 1) {
                holder.ll_other.setPadding(0, 0, 0, 0);
            }
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

}
