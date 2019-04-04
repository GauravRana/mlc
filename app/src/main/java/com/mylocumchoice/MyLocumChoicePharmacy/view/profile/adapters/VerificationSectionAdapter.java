package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.VerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.AccreditationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SkillActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.AccountDetActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class VerificationSectionAdapter extends RecyclerView.Adapter<VerificationSectionAdapter.ViewHolder> {
    private Context context;
    private Response<VerificationRes> response;
    private ArrayList<VerificationRes.DetailsRequired> arrayList = new ArrayList<>();
    private Activity mActivity;
    private VerificationDetailAdapter adapter;
    int viewPosition;

    public VerificationSectionAdapter(Activity mActivity,
                                     Context context,
                                     Response<VerificationRes> response) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.mActivity = mActivity;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        /** ButterKnife Code **/
        @BindView(R.id.ll_field)
        LinearLayout llField;
        @BindView(R.id.tv_field)
        TextView tvField;
        @BindView(R.id.rv_verification)
        RecyclerView rvVerification;
        @BindView(R.id.ll_text)
        LinearLayout llText;
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.shadow)
        View shadow;
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;


        /** ButterKnife Code **/

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
        View view = inflater.inflate(R.layout.adapter_steps_verify_section, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rvVerification.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.rvVerification.setNestedScrollingEnabled(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(response.body().getSections().get(position).getName() != null){
            holder.tvField.setText(response.body().getSections().get(position).getName().toString());
        }

        if(response.body().getSections().get(position).getText() != null){
            holder.tvText.setText(response.body().getSections().get(position).getText().toString());
        }else{
            holder.llText.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.llField.getLayoutParams();
            params.setMargins(0, 0, 0, (int) context.getResources().getDimension(R.dimen._15sdp));
        }

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(response.body().getSections().get(position).getKey() != null){
                    if(response.body().getSections().get(position).getKey().equalsIgnoreCase("basic_detail")){
                        Intent intent = new Intent(context, BasicDetailsActivity.class);
                        intent.putExtra("from", "basic_det");
                        context.startActivity(intent);
                    }else if(response.body().getSections().get(position).getKey().equalsIgnoreCase("right_to_work")){
                        Intent intent = new Intent(context, RightToWorkActivity.class);
                        intent.putExtra("from", "r2w");
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }else if(response.body().getSections().get(position).getKey().equalsIgnoreCase("accreditation")){
                        Intent intent = new Intent(context, AccreditationActivity.class);
                        intent.putExtra("from", "acc");
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else if(response.body().getSections().get(position).getKey().equalsIgnoreCase("skill")){
                        Intent intent = new Intent(context, SkillActivity.class);
                        intent.putExtra("from", "skill");
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
//                        accreditation
                    }
                }
            }
        });

        arrayList = response.body().getSections().get(position).getDetailsRequired();
        adapter = new VerificationDetailAdapter(mActivity, context, arrayList);
        holder.rvVerification.setAdapter(adapter);


        if(position == response.body().getSections().size()-1) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) context.getResources().getDimension(R.dimen._15sdp), 0, (int) context.getResources().getDimension(R.dimen._20sdp));
            holder.layout.setLayoutParams(layoutParams);
        }else{
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) context.getResources().getDimension(R.dimen._15sdp), 0, 0);
            holder.layout.setLayoutParams(layoutParams);
        }

    }

    @Override
    public int getItemCount() {
        return response.body().getSections().size();
    }


}
