package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkillsListOtherAdapter extends RecyclerView.Adapter<SkillsListOtherAdapter.ViewHolder> {
    private Context context;
    private ShiftDetailsRes response;
    private RecyclerView.ViewHolder viewHolder;
    private ArrayList<Object> arrayListOption;
    private ArrayList<String> arrayListComplete = new ArrayList<>();
    private String from;
    int pos,lastpos;

    public SkillsListOtherAdapter(Context context, ShiftDetailsRes response, ArrayList<Object> arrayListOption,int pos,int lastpos) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListOption = arrayListOption;
        this.pos = pos;
        this.lastpos = lastpos;
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
    public SkillsListOtherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_skill_list_other, parent, false);
        SkillsListOtherAdapter.ViewHolder viewHolder = new SkillsListOtherAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SkillsListOtherAdapter.ViewHolder holder, int position) {
        // arrayListComplete.add(arrayListOption.get(position).getName().toString());

        try {
            holder.tvBulet.setText(/*"\u2022" +  " " + */arrayListOption.get(position)+"");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(pos==lastpos) {
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
