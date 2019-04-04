package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.DeleteAllCalReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.DeleteCalEventReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.ShowCalendarEvents;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.DeleteAllCalendarPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.DeleteAllCalenderImp;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.DeleteCalendarEventImp;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.DeleteCalendarPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.adapters.ShowEventsAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.DeleteAllCalEventsView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.DeleteCalEventsView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowEventsActivity extends AppActivity implements DeleteAllCalEventsView, DeleteCalEventsView{

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    /** ButterKnife Code **/
    @BindView(R.id.shadow5)
    View shadow5;
    @BindView(R.id.ll_Date)
    LinearLayout llDate;
    @BindView(R.id.tv_Date)
    TextView tvDate;
    @BindView(R.id.ll_StartTime)
    LinearLayout llStartTime;
    @BindView(R.id.tv_startTime)
    TextView tvStartTime;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @BindView(R.id.ll_EndTime)
    LinearLayout llEndTime;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    @BindView(R.id.ll_Repeat)
    LinearLayout llRepeat;
    @BindView(R.id.tv_repeat)
    TextView tvRepeat;
    @BindView(R.id.shadow6)
    View shadow6;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Dialog dialog;

    private int RESULT_SHOW_EVENTS = 101;



    private ShowEventsAdapter adapter;
    /** ButterKnife Code **/
    private ShowCalendarEvents showEvents;
    private DeleteCalendarPresenter presenter;
    private DeleteAllCalendarPresenter allPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
        ButterKnife.bind(this);
        setHeader();
        init();
        //showChoice();

        if(showEvents.getDate() != null){

            Log.e("event date--",showEvents.getDate());
            tvTitle.setText(changeDateFormat(showEvents.getDate()));
        }else{
            llTitle.setVisibility(View.GONE);
        }

        if(showEvents.getTitle() != null){
            //tvDate.setText(showEvents.getTitle());
            tvHeader.setText(showEvents.getTitle());
        }else{
            llDate.setVisibility(View.GONE);
        }

        if(showEvents.getStart_time() != null){
            tvStartTime.setText(showEvents.getStart_time());
        }else{
            llStartTime.setVisibility(View.GONE);
        }

        if(showEvents.getEnd_time() != null){
            tvEndTime.setText(showEvents.getEnd_time());
        }else{
            llEndTime.setVisibility(View.GONE);
        }

        if(showEvents.getRecurringType() != null) {
            tvRepeat.setText(showEvents.getRecurringType().toString());
        }else{
            llRepeat.setVisibility(View.GONE);
        }

    }



    public String changeDateFormat(String time){
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e("dateformat---", str);
        return str;

    }
    public void showChoice(){
        ArrayList<String> arrayList = new ArrayList<>();
        String[] lv_arr = {};
        dialog = new Dialog(this);
        //builderSingle = new AlertDialog.Builder(BasicDetailsActivity.this);
        // builderSingle.setTitle("Select Country");

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom_single_button, null);
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        TextView tv = (TextView)  convertView.findViewById(R.id.tv_title);
        TextView tv_cancel = convertView.findViewById(R.id.tv_cancel);
        dialog.setContentView(convertView);
        //builderSingle.setView(dialogView);
        tv.setText("Select Option");
        arrayList.add("Delete This Event Only");
        if(showEvents.isRecurring()) {
            arrayList.add("Delete All Future Events");
        }

        lv_arr = arrayList.toArray(new String[arrayList.size()]);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShowEventsActivity.this, R.layout.alert_list, lv_arr);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strName = arrayAdapter.getItem(position);
                onClickOperation(position);
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("isFromBusy", true);
                intent.putExtra("select_date_other", showEvents.getDate());
                setResult(RESULT_SHOW_EVENTS, intent);
                overridePendingTransitionExit();
                finish();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


    public void showDeleteChoice(){
        ArrayList<String> arrayList = new ArrayList<>();
        String[] lv_arr = {};
        Dialog dialog = new Dialog(this);
        //builderSingle = new AlertDialog.Builder(BasicDetailsActivity.this);
        // builderSingle.setTitle("Select Country");

        LayoutInflater inflater = getLayoutInflater();
        View convertView;
        TextView tv_delete;
        if(showEvents.isRecurring()) {
            convertView = (View) inflater.inflate(R.layout.dialog_delete_event, null);
            LinearLayout ll_repeat = convertView.findViewById(R.id.ll_repeat);
            tv_delete = convertView.findViewById(R.id.tv_delete);
            TextView tv_delete_all = convertView.findViewById(R.id.tv_delete_all);
            tv_delete_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickOperation(1);
                    dialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("isFromBusy", true);
                    intent.putExtra("select_date_other", showEvents.getDate());
                    setResult(RESULT_SHOW_EVENTS, intent);
                    overridePendingTransitionExit();
                    finish();
                }
            });
        }else{
            convertView = (View) inflater.inflate(R.layout.dialog_single_delete_event, null);
            tv_delete = convertView.findViewById(R.id.tv_delete);
            tv_delete.setText("Delete This Event");
        }
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(convertView);
        //builderSingle.setView(dialogView);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);


        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperation(0);
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("isFromBusy", true);
                intent.putExtra("select_date_other", showEvents.getDate());
                setResult(RESULT_SHOW_EVENTS, intent);
                overridePendingTransitionExit();
                finish();
            }
        });



        dialog.show();

        /*lv_arr = arrayList.toArray(new String[arrayList.size()]);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShowEventsActivity.this, R.layout.alert_list, lv_arr);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strName = arrayAdapter.getItem(position);
                onClickOperation(position);
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("isFromBusy", true);
                intent.putExtra("select_date_other", showEvents.getDate());
                setResult(RESULT_SHOW_EVENTS, intent);
                overridePendingTransitionExit();
                finish();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/


    }

    @OnClick(R.id.btn_accept)
    public void buttonClick(){
        try {
            showDeleteChoice();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setHeader(){
        tvHeader.setText("Calendar Events");
        tvDots.setVisibility(View.GONE);

    }

    private void init() {
        showEvents = EventBus.getDefault().getStickyEvent(ShowCalendarEvents.class);
    }


    @Override
    public void onDeleteAllEvents() {

    }

    @Override
    public void onDeleteEventsAllError() {

    }

    @Override
    public void onDeleteEvents() {

    }

    @Override
    public void onDeleteEventsError() {

    }

    public void addModel(String date){

    }

    @OnClick(R.id.ll_back)
    public void onBack(){
        onBackPressed();
    }


    public void onClickOperation(int position){
        if(position == 0){
            presenter = new DeleteCalendarEventImp(this,this);
            DeleteCalEventReq deleteCalReq = new DeleteCalEventReq();
            presenter.onDeleteEvent(showEvents.getId(), deleteCalReq.add(showEvents.getDate()));
        }else {
            allPresenter = new DeleteAllCalenderImp(this,this);
            DeleteAllCalReq deleteAllCalReq = new DeleteAllCalReq();
            allPresenter.onDeleteAllEvent(showEvents.getId(), deleteAllCalReq.add(showEvents.getDate()));
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
