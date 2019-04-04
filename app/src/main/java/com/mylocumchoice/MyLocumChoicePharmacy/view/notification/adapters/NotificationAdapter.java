package com.mylocumchoice.MyLocumChoicePharmacy.view.notification.adapters;

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
import com.mylocumchoice.MyLocumChoicePharmacy.model.notification.NotificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.notification.NotificationPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.OnIntentReceived;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickPositionListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>
{
    private Activity context;
    private Response<NotificationRes> response;
    private ArrayList<NotificationRes.Notification> arrayListNotify = new ArrayList<>();
    private NotificationDetailAdapter notificationDetailsAdapter;

    private RecyclerViewClickPositionListener mItemListener;
    private String from;
    int viewPosition;


    public NotificationAdapter(String from,
                                     Activity context,
                                     Response<NotificationRes> response) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.from = from;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/

        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        @BindView(R.id.tv_header)
        TextView tvHeader;

        private LinearLayoutManager linearLayoutManager;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        if(response.body().getFinalNotifications() != null){
            if(response.body().getFinalNotifications().get(position).getDate() != null) {
                holder.tvHeader.setText(response.body().getFinalNotifications().get(position).getDate().toString());
            }
        }
        arrayListNotify = response.body().getFinalNotifications().get(position).getNotifications();
        notificationDetailsAdapter = new NotificationDetailAdapter(context, arrayListNotify);
        holder.recyclerView.setAdapter(notificationDetailsAdapter);


    }

    @Override
    public int getItemCount() {
        return response.body().getFinalNotifications().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
