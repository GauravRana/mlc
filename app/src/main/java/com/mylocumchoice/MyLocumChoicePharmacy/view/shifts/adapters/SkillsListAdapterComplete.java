package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

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
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkillsListAdapterComplete extends RecyclerView.Adapter<SkillsListAdapterComplete.ViewHolder> {
    private Context context;
    private ShiftDetailsRes response;
    private RecyclerView.ViewHolder viewHolder;
    private ArrayList<ShiftDetailsRes.ListOption> arrayListOption;
    private ArrayList<String> arrayListComplete = new ArrayList<>();
    private String from;



    public SkillsListAdapterComplete(Context context, ShiftDetailsRes response, ArrayList<ShiftDetailsRes.ListOption> arrayListOption) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListOption = arrayListOption;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_skill_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // arrayListComplete.add(arrayListOption.get(position).getName().toString());

        try {
            holder.tvBulet_1.setText(/*"\u2022" + " " + */arrayListOption.get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (arrayListOption.get(position).getOptions()!=null||arrayListOption.get(position).getOptions().size()>0) {
            holder.rv_other.setVisibility(View.VISIBLE);

            ArrayList<Object> othersList=arrayListOption.get(position).getOptions();
            SkillsListOtherAdapter skillsListOtherAdapter = new SkillsListOtherAdapter(context, response, othersList,position,arrayListOption.size());
            holder.rv_other.setLayoutManager(new LinearLayoutManager(context));
            holder.rv_other.setAdapter(skillsListOtherAdapter);
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
}
