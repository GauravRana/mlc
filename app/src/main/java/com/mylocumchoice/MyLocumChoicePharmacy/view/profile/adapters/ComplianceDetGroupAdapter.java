package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.app.Activity;
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
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.OnIntentReceived;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickPositionListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class ComplianceDetGroupAdapter extends RecyclerView.Adapter<ComplianceDetGroupAdapter.ViewHolder>
        implements UploadDocVP.View {
    private Context context;
    private Response<ComplianceDetailsRes> response;
    private RecyclerView.ViewHolder viewHolder;
    private ArrayList<ComplianceDetailsRes.Group> arrayList;
    private ArrayList<ComplianceDetailsRes.Field> arrayListField = new ArrayList<>();
    private String from;
    private Activity mActivity;
    private OnIntentReceived listener;
    private RecyclerViewClickPositionListener itemListener;
    private ComplianceDetFieldAdapter adapter;
    private int[] scrollXState = new int[20];


    public ComplianceDetGroupAdapter(Activity mActivity,
                                     String from,
                                     Context context,
                                     Response<ComplianceDetailsRes> response,
                                     ArrayList<ComplianceDetailsRes.Group> arrayList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayList = arrayList;
        this.from = from;
        this.mActivity = mActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_mainGroups)
        LinearLayout llMainGroups;
        @BindView(R.id.ll_text)
        LinearLayout llText;
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.recyclerView)
        android.support.v7.widget.RecyclerView recyclerView;

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
        View view = inflater.inflate(R.layout.rv_prefs_groups, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.recyclerView.setNestedScrollingEnabled(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (arrayList.get(position).getHelpText() != null) {
            holder.tvText.setText(arrayList.get(position).getHelpText().toString());
        } else {
            holder.llText.setVisibility(View.GONE);
        }

        arrayListField = arrayList.get(position).getFields();
        adapter = new ComplianceDetFieldAdapter(mActivity, from, context, response, arrayListField);
        holder.recyclerView.setAdapter(adapter);

//        holder.recyclerView.post(() ->
//                holder.recyclerView.setScrollX(scrollXState[holder.getAdapterPosition()]));
    }

    @Override
    public int getItemCount() {
        try {
            return arrayList.size();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void onUploadResponse(Context context, Response<Void> response) {
        if (response.code() == 204) {
            try {
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUploadComplianceResponse(Context context, Response<Void> response) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showWarning(Activity activity, String title, String msg, String error) {

    }


//    @Override
//    public void onViewRecycled(@NonNull ViewHolder holder) {
//        super.onViewRecycled(holder);
//        scrollXState[holder.getAdapterPosition()] = holder.recyclerView.getScrollX();
//
//    }
}