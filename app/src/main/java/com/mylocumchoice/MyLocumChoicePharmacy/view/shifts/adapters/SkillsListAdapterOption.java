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

public class SkillsListAdapterOption extends RecyclerView.Adapter<SkillsListAdapterOption.ViewHolder> {
    private Context context;
    private ShiftDetailsRes response;
    private RecyclerView.ViewHolder viewHolder;
    private ArrayList<ShiftDetailsRes.ListOption> arrayListOption = new ArrayList<>();
    private ArrayList<ShiftDetailsRes.List> arrayList;
    private ArrayList<String> arrayListComplete = new ArrayList<>();
    private String from;




    public SkillsListAdapterOption(Context context, ShiftDetailsRes response, ArrayList<ShiftDetailsRes.List> arrayList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayList = arrayList;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_skills_list_option, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if(arrayList.get(position).getListOptions() != null) {
                arrayListOption = arrayList.get(position).getListOptions();
                holder.tvTitle.setText(arrayList.get(position).getTitle());
                SkillsListAdapterComplete adapter = new SkillsListAdapterComplete(context, response, arrayListOption);
                holder.recyclerView.setAdapter(adapter);
            }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
