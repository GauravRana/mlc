package com.mylocumchoice.MyLocumChoicePharmacy.view.notification.adapters;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventList;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventListResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.notification.NotificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.EventListPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.EventListPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.notification.NotificationPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.notification.NotificationVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.EventsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.EventsDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.MapActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.ShowEventsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.EventListView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.AccreditationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PharmaComplianceDetails;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PreferenceActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.References;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SkillActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.VerificationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.fragment.ProfileFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.ShiftDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class NotificationDetailAdapter extends RecyclerView.Adapter<NotificationDetailAdapter.ViewHolder>
        implements NotificationVP.View, EventListView {
    private Activity context;
    private String from;
    private ArrayList<NotificationRes.Notification> arrayListNotify;
    private NotificationPresenter presenter;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    private SignOutAlert signOutAlert;

    List<EventList> lv_events = new ArrayList<>();

    EventListPresenter mPresenter;
    private int event_Id;
    ViewHolder viewHolder;

    public int NOTIFICATION_REQUEST_CODE=1000;


    public NotificationDetailAdapter(Activity context,
                                     ArrayList<NotificationRes.Notification> arrayListNotify) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.arrayListNotify = arrayListNotify;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;

        /** ButterKnife Code **/
        @BindView(R.id.delete_layout)
        LinearLayout deleteLayout;
        @BindView(R.id.front_layout)
        FrameLayout frontLayout;
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.tv_Text)
        TextView tvText;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.swipeRefreshLayout)
        SwipeRevealLayout swipeRefreshLayout;
        @BindView(R.id.iv_delete)
        TextView iv_delete;


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
        View view = inflater.inflate(R.layout.adapter_detail_notify, parent, false);
        viewHolder = new ViewHolder(view);
        mPresenter = new EventListPresenterImpl(this, context);
        presenter = new NotificationPresenter(context, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        binderHelper.bind(holder.swipeRefreshLayout, String.valueOf(arrayListNotify.get(position).getId()));
        if(arrayListNotify.get(position).getRead()){
            holder.layout.setBackgroundResource(R.drawable.drawable_shadow_down);
        }else {
            holder.layout.setBackgroundResource(R.drawable.notify_drawable);
        }

        if(arrayListNotify.get(position).getMessage() != null){
            //holder.tvText.setText("sbdsbdbsdnbbbnnnnnnnsbdsbdnsdsnbdnsbdbsndbnbnbnnsndbsdsdhjhjnndgshgdhsgdgsgdgsgdgshgdhgsgdgsgdhsgdhgsdghsgdhsgdhsgdghsgdhgshgdhgshgdhgnjdjshhdjhdjsdjhsjhdjhsjdhsjhdjshdjhsdhjshdjhjshdjhsjhdhsjdhjshdhsjhdjshdhshdhshdshdhshd");
            holder.tvText.setText(arrayListNotify.get(position).getMessage());
        }

        if(arrayListNotify.get(position).getTime() != null)
            holder.tvTime.setText(arrayListNotify.get(position).getTime());


//        if(arrayListNotify.get(position).getSeen()){
//            holder.layout.setBackgroundResource(R.drawable.drawable_shadow_up_down);
//        }else {
//            holder.layout.setBackgroundResource(R.drawable.notify_drawable);
//        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // holder.layout.setBackgroundResource(R.drawable.drawable_shadow_up_down);
                try{
                    readNotification(arrayListNotify.get(position).getId());
                        if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("shift_invitation")||arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("open_shift")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent intent = new Intent(context, ShiftDetailActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("isFrom", "notificationAdapter");
                                intent.putExtra("id", arrayListNotify.get(position).getEntityId());
                                intent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                intent.putExtra("key", (String)arrayListNotify.get(position).getKey());
                                context.startActivity(intent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if(arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("account")) {
                            try{
                                /*LandingActivity.isNotificationClicked=true;
                                Intent intent = new Intent(context, LandingActivity.class);
                                intent.putExtra("notification_id",arrayListNotify.get(position).getId());
                                intent.putExtra("key",arrayListNotify.get(position).getKey().toString());
                                context.startActivity(intent);*/
                                //LandingActivity.openFragmentPosition = 5;

                                LandingActivity myActivity = (LandingActivity)context;
                                Bundle bundle = new Bundle();
                                bundle.putInt("notification_id", arrayListNotify.get(position).getId());
                                ProfileFragment profileFragment=new ProfileFragment();
                                profileFragment.setArguments(bundle);
                                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                                myActivity.menuPlaying(profileFragment);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if(arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("verification_steps")) {
                            try{
                                /*LandingActivity.isNotificationClicked=true;
                                Intent intent = new Intent(context, LandingActivity.class);
                                intent.putExtra("notification_id",arrayListNotify.get(position).getId());
                                intent.putExtra("key",arrayListNotify.get(position).getKey().toString());
                                context.startActivity(intent);*/
                               // LandingActivity.openFragmentPosition = 5;

                                Intent mIntent = new Intent(context, VerificationActivity.class);
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                mIntent.putExtra("from", "notificationAdapter");
                                context.startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("cancelled_booking")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent=new Intent(context,BookingDetailsActivity.class);
                                mIntent.putExtra("isFrom","notificationAdapter");
                                mIntent.putExtra("key","cancelled");
                                mIntent.putExtra("id",arrayListNotify.get(position).getEntityId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("upcoming_booking")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent = new Intent(context, MapActivity.class);
                                double d = (double)arrayListNotify.get(position).getEntityId();
                                int i = (int)d;
                                mIntent.putExtra("id", i);
                                mIntent.putExtra("from", "notificationAdapter");
                                mIntent.putExtra("key", (String)arrayListNotify.get(position).getKey());
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("event")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                event_Id = arrayListNotify.get(position).getEntityId();
                                mPresenter.onGettingEventListNotification(arrayListNotify.get(position).getId());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("basic_details")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent=new Intent(context,BasicDetailsActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mIntent.putExtra("from", "notificationAdapter");
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("preferences")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent=new Intent(context,PreferenceActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mIntent.putExtra("from", "notificationAdapter");
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("right_to_work")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent=new Intent(context,RightToWorkActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mIntent.putExtra("from", "notificationAdapter");
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("skills")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent=new Intent(context,SkillActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mIntent.putExtra("from", "notificationAdapter");
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("accreditations")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent=new Intent(context,AccreditationActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mIntent.putExtra("from", "notificationAdapter");
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("references")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent=new Intent(context,References.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mIntent.putExtra("from", "notificationAdapter");
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("pharmacy_compliance")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", arrayListNotify.get(position).getEntityId());
                                Intent intent = new Intent(context, PharmaComplianceDetails.class);
                                intent.putExtra("from", "notificationAdapter");
                                intent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                intent.putExtras(bundle);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                        else if (arrayListNotify.get(position).getKey().toString().equalsIgnoreCase("application_accepted")) {
                            try{
                                LandingActivity.isNotificationClicked=false;
                                Intent mIntent = new Intent(context, MapActivity.class);
                                double d = (double)arrayListNotify.get(position).getEntityId();
                                int i = (int)d;
                                mIntent.putExtra("id", i);
                                mIntent.putExtra("from", "notificationAdapter");
                                mIntent.putExtra("key", (String)arrayListNotify.get(position).getKey());
                                mIntent.putExtra("notification_id", arrayListNotify.get(position).getId());
                                context.startActivity(mIntent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


       holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    presenter.deleteSingleNotification(context, arrayListNotify.get(position).getId());
                    arrayListNotify.remove(position);
                    notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                    /*try {
                        presenter.deleteSingleNotification(context, arrayListNotify.get(position + 1).getId());
                    }catch (Exception e1){
                        e1.printStackTrace();

                    }*/
                }
            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    presenter.deleteSingleNotification(context, arrayListNotify.get(position).getId());

                    arrayListNotify.remove(position);
                    notifyDataSetChanged();
                    //notifyItemRemoved(position);
                }catch (Exception e){
                    e.printStackTrace();
                    /*try {
                        presenter.deleteSingleNotification(context, arrayListNotify.get(position + 1).getId());
                    }catch (Exception e1){
                        e1.printStackTrace();

                    }*/
                }
            }
        });


        /*holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    presenter.deleteSingleNotification(context, arrayListNotify.get(position).getId());
                    arrayListNotify.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });*/


        if(position == arrayListNotify.size() - 1){
            holder.layout.setBackgroundResource(R.drawable.drawable_shadow_normal);
        }
    }

    @Override
    public int getItemCount() {
        return arrayListNotify.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void readNotification(int id){
        presenter.readNotification(id);

    }

    @Override
    public void onNotificationSuccess(Response<NotificationRes> response) {

    }

    @Override
    public void onNotificationfailed() {

    }

    @Override
    public void onReadNotification(Response<Void> response) {
        if(response.code() == 200){

        }

    }

    @Override
    public void onDeleteNotification(Response<Void> response) {
        notifyDataSetChanged();
    }

    @Override
    public void onDeleteSingleNotification(Response<Void> response) {
            notifyDataSetChanged();
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


    @Override
    public void onEventListFetched(EventListResponse eventListResponse) {
        try{
            if(eventListResponse.getEvents() != null) {
                lv_events.addAll(eventListResponse.getEvents());
                for(int i = 0; i < lv_events.size(); i++){
                    if(event_Id == lv_events.get(i).getId()){
                        EventBus.getDefault().postSticky(lv_events.get(i));
                        Intent mIntent=new Intent(context,EventsDetailsActivity.class);
                        mIntent.putExtra("from", "notification");
                        context.startActivity(mIntent);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onGettingError() {

    }

}



