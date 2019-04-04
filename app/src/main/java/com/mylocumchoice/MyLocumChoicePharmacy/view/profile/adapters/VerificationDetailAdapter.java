package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.VerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class VerificationDetailAdapter extends RecyclerView.Adapter<VerificationDetailAdapter.ViewHolder> {
    private Context context;
    private ArrayList<VerificationRes.DetailsRequired> arrayList;
    private Activity mActivity;
    private Utils mUtils;


    public VerificationDetailAdapter(Activity mActivity,
                                     Context context,
                                     ArrayList<VerificationRes.DetailsRequired> arrayList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.arrayList = arrayList;
        this.mActivity = mActivity;
        mUtils = new Utils();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_below)
        LinearLayout llBelow;
        @BindView(R.id.ll_status)
        LinearLayout ll_status;
        @BindView(R.id.tv_cert)
        TextView tvCert;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_upLoadText)
        TextView tv_upLoadText;
        @BindView(R.id.shadow2)
        View shadow;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.layout)
        LinearLayout llLayout;

        /**
         * ButterKnife Code
         **/

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
        View view = inflater.inflate(R.layout.adapter_verify_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //if (arrayList.get(position).getStatus() != null) {

            holder.tvNum.setText(String.valueOf(position+1)+".");
            if (arrayList.get(position).getTitle() != null) {
                holder.tvCert.setText(arrayList.get(position).getTitle());
            } else {
                holder.llLayout.setVisibility(View.GONE);
            }

        if (arrayList.get(position).getStatus() != null) {
            if (arrayList.get(position).getStatus().getName() != null) {
                holder.tv_upLoadText.setText(arrayList.get(position).getStatus().getName().toString());
                if (arrayList.get(position).getStatus().getColour() != null) {
                    holder.tv_upLoadText.setTextColor(Color.parseColor(arrayList.get(position).getStatus().getColour()));
                }
            }
        } else {
            holder.ll_status.setVisibility(View.GONE);
        }

            if (arrayList.get(position).getIssueRaised() != null) {
                if (arrayList.get(position).getStatus().getName() != null) {
                    holder.tv_upLoadText.setText(underLineText(arrayList.get(position).getStatus().getName().toString()));
                }

            }

        /*} else {
            holder.llBelow.setVisibility(View.GONE);
        }*/

        if (position == arrayList.size() - 1) {
            holder.shadow.setVisibility(View.VISIBLE);
        }


        holder.tv_upLoadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (arrayList.get(position).getIssueRaised()!=null &&!arrayList.get(position).getIssueRaised().toString().equalsIgnoreCase("")) {
                        //mUtils = new Utils();
                        mUtils.showInfoDialog(mActivity, arrayList.get(position).getIssueRaised().toString(), GlobalConstants.PendingTextShow);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public SpannableString underLineText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        return content;
    }
}

