package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkillsListAdapter extends RecyclerView.Adapter<SkillsListAdapter.ViewHolder> {
    private Context context;
    private ShiftDetailsRes response;
    private RecyclerView.ViewHolder viewHolder;
    private ArrayList<ShiftDetailsRes.Detail> arrayListDetail = new ArrayList<>();
    private ArrayList<ShiftDetailsRes.List> arrayListOption;
    private String from;


    public SkillsListAdapter(Context context, ShiftDetailsRes response, ArrayList<ShiftDetailsRes.List> arrayListOption) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListOption = arrayListOption;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;
        private RecyclerView recyclerView;
        private TextView tvTitle;

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
        viewHolder.recyclerView = view.findViewById(R.id.recyclerView);
        viewHolder.tvTitle = view.findViewById(R.id.tvTitle);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        for(int i = 0; i < arrayListDetail.size(); i++){
            arrayListOption = arrayListDetail.get(i).getLists();
        }
        holder.tvTitle.setText(arrayListOption.get(position).getTitle());
        SkillsListAdapterOption adapter = new SkillsListAdapterOption(context, response, arrayListOption);
        holder.recyclerView.setAdapter(adapter);
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
