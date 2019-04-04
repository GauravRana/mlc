package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Response;

public class SkillsRadioAdapter extends RecyclerView.Adapter<SkillsRadioAdapter.ViewHolder>{

    private Context context;
    private Response<PrefernceRes> response;
    private  RecyclerView.ViewHolder viewHolder;
    private int groupSize = 0;
    private ArrayList<PrefernceRes.Group> arrayList = new ArrayList<>();

    public SkillsRadioAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_radio_button, parent, false);
        SkillsRadioAdapter.ViewHolder viewHolder = new SkillsRadioAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
