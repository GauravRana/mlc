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
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ApiResponseListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.OnIntentReceived;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickPositionListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class PreferenceAdapterSections extends RecyclerView.Adapter<PreferenceAdapterSections.ViewHolder>
                    implements UploadDocVP.View,ApiResponseListener{
    private Context context;
    private Response<PrefernceRes> response;
    private RecyclerView.ViewHolder viewHolder;
    private int groupSize = 0;
    private ArrayList<PrefernceRes.Group> arrayList = new ArrayList<>();
    private String from;
    private Activity mActivity;
    private OnIntentReceived listener;
    private RecyclerViewClickPositionListener itemListener;
    private RecyclerViewClickPositionListener mItemListener;
    private PreferenceAdapterGroups adapter;
    int viewPosition;
    ApiResponseListener apiResponseListener;


    public PreferenceAdapterSections(Activity mActivity,
                                     String from,
                                     Context context,
                                     Response<PrefernceRes> response, OnIntentReceived listener,RecyclerViewClickPositionListener mItemListener,ApiResponseListener apiResponseListener ) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.from = from;
        this.mActivity = mActivity;
        this.listener = listener;
        this.itemListener = itemListener;
        this.mItemListener = mItemListener;
        this.apiResponseListener = apiResponseListener;
    }

    @Override
    public void onGetResponse(Response<Void> response) {
        apiResponseListener.onGetResponse(response);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;

        /** ButterKnife Code **/
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.shadow)
        View shadow;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.shadow21)
        View shadow21;
        @BindView(R.id.recyclerView)
        android.support.v7.widget.RecyclerView recyclerView;
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
        View view = inflater.inflate(R.layout.rv_prefs_section, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(response.body().getSections().get(position).getName() != null) {
            if (!response.body().getSections().get(position).getName().equalsIgnoreCase("")) {
                holder.tvTitle.setText(response.body().getSections().get(position).getName());
            } else {
                holder.llTitle.setVisibility(View.GONE);
            }
        }
        arrayList = response.body().getSections().get(position).getGroups();
        holder.tvTitle.setText(response.body().getSections().get(position).getName());


        if(response.body().getSections().get(position).getName().equalsIgnoreCase("Mandatory")){
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.warning));
        }else{
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.black));
        }


        if(position == 0){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.llTitle.getLayoutParams();
            params.setMargins(0,(int) context.getResources().getDimension(R.dimen._15sdp), 0, 0);
        }
        else{
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.llTitle.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
        }


        adapter = new PreferenceAdapterGroups(mActivity,from  ,context, response, arrayList, listener, mItemListener,this);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return response.body().getSections().size();
    }



    @Override
    public void onUploadResponse(Context context, Response<Void> response) {
        if(response.code() == 204){
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
}
