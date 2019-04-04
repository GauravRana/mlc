package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEvents;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CreateCalendarRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.BasicDetailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.CreateCalendarImp;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.CreateCalendarPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RangeTimePickerDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.fragment.CalFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.CreateCalendarView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.RegisterActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MarkAsBusyActivity extends AppActivity implements CreateCalendarView, CalFragment.OnFragmentInteractionListener {

    /**
     * ButterKnife Code
     **/
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.shadow5)
    View shadow5;
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
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.ll_Date)
    LinearLayout llDate;
    @BindView(R.id.tv_Date)
    TextView tvDate;
    @BindView(R.id.ll_StartTime)
    LinearLayout llStartTime;
    @BindView(R.id.tv_startTime)
    TextView tvStartTime;
    @BindView(R.id.ll_EndTime)
    LinearLayout llEndTime;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    private Dialog dialog;
    private Utils mUtils;
    public static TextView tvStartDate;
    private Calendar calendar;
    private CalendarEvents events;
    private RangeTimePickerDialog timepickerdialog;

    private int CalendarHour, CalendarMinute;
    private String format;

    private HashMap<Integer, String> repeat = new HashMap<Integer, String>();
    private CreateCalendarPresenter presenter;
    private int recurring_id;
    private int currentDate;

    private String strDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_as_busy);
        ButterKnife.bind(this);
        tvStartDate = findViewById(R.id.tv_Date);
        mUtils = new Utils();
        iniIntent();

        init();
        for (int i = 0; i < events.getRecurringTypes().size(); i++) {
            repeat.put(events.getRecurringTypes().get(i).getId(), events.getRecurringTypes().get(i).getName());
        }
        showRepeatList(repeat);
        setHeader();
    }


    public void setHeader() {
        tvHeader.setText("Mark As Busy");
        tvDots.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.ll_Date)
    public void onDateClick() {
        mUtils.fn_showDatePicker(tvStartDate.getText().toString(),"Start", MarkAsBusyActivity.this);
    }


    @OnClick(R.id.ll_StartTime)
    public void onStartDate() {
        onTimeShow("start","",tvStartTime);
    }


    @OnClick(R.id.ll_EndTime)
    public void onEnd() {
        onTimeShow("end",tvStartTime.getText().toString(),tvEndTime);
    }

    @OnClick(R.id.ll_Repeat)
    public void onRep() {
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_accept)
    public void btnAccept() {
        if (tvStartDate.getText().toString().equalsIgnoreCase("")) {
            showWarning(this, "", "Please select start date.", "error");
        } else if (tvStartTime.getText().toString().equalsIgnoreCase("")) {
            showWarning(this, "", "Please select start time.", "error");
        } else if (tvEndTime.getText().toString().equalsIgnoreCase("")) {
            showWarning(this, "", "Please select end time.", "error");
        } else if (tvRepeat.getText().toString().equalsIgnoreCase("")) {
            showWarning(this, "", "Please select Repeat.", "error");
        } else {

            
            addModel(tvStartDate.getText().toString()
                    , tvStartTime.getText().toString()
                    , tvEndTime.getText().toString()
                    , recurring_id);


//            Intent intent = new Intent(MarkAsBusyActivity.this, LandingActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            LandingActivity.openFragmentPosition = 3;


        }
    }

    public void showRepeatList(HashMap<Integer, String> repeat) {
        dialog = new Dialog(this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom_single_button, null);
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        TextView tv = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_cancel = convertView.findViewById(R.id.tv_cancel);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(convertView);
        tv.setText("Select Repeat");
        ListAdapter adapter = new ListAdapter(repeat);
        lv.setAdapter(adapter);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void init() {
        events = EventBus.getDefault().getStickyEvent(CalendarEvents.class);
    }

    @Override
    public void onCreateCalendar(Response<Void> calendarEventsRes) {

        if(calendarEventsRes.code()==201){
            Intent intent = new Intent();
            intent.putExtra("select_date", tvStartDate.getText().toString());
            intent.putExtra("select_date_other", changeDateFormat(strDate));
            intent.putExtra("isFromBusy", true);
            setResult(RESULT_OK, intent);
            overridePendingTransitionExit();
            finish();

        } else if(calendarEventsRes.code() == 422){
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(calendarEventsRes.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(MarkAsBusyActivity.this, "", errorString, "error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateCalendarError() {

    }

    @Override
    public void onFragmentInteraction(int position) {

    }

    public void iniIntent() {
        if (getIntent().getExtras() != null) {
            strDate = getIntent().getExtras().get("currentDate").toString();
            tvStartDate.setText(mUtils.changeDateFormat(strDate));
            tvStartTime.setText("00:00");
            tvEndTime.setText("23:59");
            tvRepeat.setText("Never");
        }
    }

    public class ListAdapter extends BaseAdapter {
        private final ArrayList mData;

        public ListAdapter(HashMap<Integer, String> map) {
            mData = new ArrayList();
            mData.addAll(map.entrySet());
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public HashMap.Entry<Integer, String> getItem(int position) {
            return (HashMap.Entry) mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO implement you own logic with ID
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view;
            HashMap.Entry<Integer, String> item = getItem(position);
            TextView tv;
            if (convertView == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_list, parent, false);
                tv = view.findViewById(R.id.tv);
                tv.setText(item.getValue());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvRepeat.setText(item.getValue());
                        recurring_id = item.getKey();
                        dialog.dismiss();
                    }
                });

            } else {
                view = convertView;
            }
            // TODO replace findViewById by ViewHolder
            return view;
        }
    }


    public void onTimeShow(String from,String text,TextView textView) {

        timepickerdialog=new RangeTimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String min;
                if (String.valueOf(minute).equalsIgnoreCase("0")) {
                    min = "00";
                } else if (String.valueOf(minute).equalsIgnoreCase("5")) {
                    min = "05";
                } else {
                    min = String.valueOf(minute);
                }

                if(from.equalsIgnoreCase("start")){
                    tvEndTime.setText("");
                }
                textView.setText(String.valueOf(hourOfDay) + ":" + min);
            }
        },CalendarHour,CalendarMinute,true);



        if(text.equals("")){

        }else {
            String strHour = text.substring(0, text.indexOf(":"));
            String strMin = text.substring(text.indexOf(":") + 1);

            int intHour = Integer.parseInt(strHour);
            int intMin = Integer.parseInt(strMin);

            timepickerdialog.setMin(intHour,intMin);
        }

        timepickerdialog.show();
    }


    public void addModel(String date
            , String start_time, String end_time, int recurring_type_id) {
        presenter = new CreateCalendarImp(this, this);
        CalendarEventReq calendarEventReq = new CalendarEventReq();
        presenter.oncreateCalendarList(calendarEventReq.add(date, start_time, end_time, recurring_type_id));
    }



    public String changeDateFormat(String time) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "yyyy-M-d";
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


}


