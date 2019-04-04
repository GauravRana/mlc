package com.mylocumchoice.MyLocumChoicePharmacy.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.snapshot.Index;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.HidePharmacyPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.MapActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.HistoryFilterFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.MarkAsBusyActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.PreferenceAdapterFields;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.SortActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.SortRightFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.RegisterActivity;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    Context context;

    public Utils() {
    }

    public Utils(Context context) {
        this.context = context;
    }


    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    int selectedYear = -1, selectedMonth = -1, selectedDay = -1;


    public void fn_showDatePicker(String startDate, String from, Activity activity) {
        Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.setContentView(R.layout.layout_date_picker);
        dialog.setCancelable(false);

        Log.e("startDate---", startDate);

        int year = -1, month = -1, day = -1;


        if (activity.getClass().getSimpleName().equalsIgnoreCase("RightToWorkActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("AccreditationActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("SkillActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("PreferenceActivity")
                ||from.equalsIgnoreCase("M")){
            if(startDate.equals("")){
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }else{
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                    Date date = sdf.parse(startDate);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            }


            selectedYear=year;
            selectedMonth=month;
            selectedDay=day;
        }else {
            if (from.equalsIgnoreCase("Start") && startDate.equals("")) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                selectedYear=year;
                selectedMonth=month;
                selectedDay=day;
            } else if (from.equalsIgnoreCase("Start") && !startDate.equals("")) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                    Date date = sdf.parse(startDate);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);

                    selectedYear=year;
                    selectedMonth=month;
                    selectedDay=day;
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }


            } else if (from.equalsIgnoreCase("End") && startDate.equals("")) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            } else if (from.equalsIgnoreCase("End") && !startDate.equals("")) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                    Date date = sdf.parse(startDate);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            }
        }

        TextView tv_clear, tv_cancel;
        tv_clear = dialog.findViewById(R.id.tv_clear);
        tv_cancel = dialog.findViewById(R.id.tv_cancel);

        if (activity.getClass().getSimpleName().equalsIgnoreCase("RightToWorkActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("AccreditationActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("SkillActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("PreferenceActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("MarkAsBusyActivity")){
            tv_clear.setText("Ok");
        }
        DatePicker datePicker = dialog.findViewById(R.id.datePicker);
        if (activity.getClass().getSimpleName().equalsIgnoreCase("RightToWorkActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("AccreditationActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("SkillActivity")
                ||activity.getClass().getSimpleName().equalsIgnoreCase("PreferenceActivity")
                ||from.equalsIgnoreCase("M")){
            Calendar c = Calendar.getInstance();
            c.set(c.get(Calendar.YEAR)-100, 0, 1);
            datePicker.setMinDate(c.getTimeInMillis());

            Calendar c1 = Calendar.getInstance();
            c1.set(c1.get(Calendar.YEAR)+100, 11, 31);
            datePicker.setMaxDate(c1.getTimeInMillis());
        }else {
            if (startDate.equals("")) {
                datePicker.setMinDate(System.currentTimeMillis() - 1000);
            } else if (startDate.equals("") && from.equalsIgnoreCase("End")) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                    Date date = sdf.parse(startDate);
                    long millis = date.getTime();
                    datePicker.setMinDate(millis);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!startDate.equals("") && from.equalsIgnoreCase("Start")) {
                datePicker.setMinDate(System.currentTimeMillis() - 1000);
            } else if (!startDate.equals("") && from.equalsIgnoreCase("End")) {


                if (activity.getClass().getSimpleName().equalsIgnoreCase("SortActivity")) {
                    if (SortRightFragment.tvStartDate.getText().toString().equals("")) {

                    } else {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                            Date date = sdf.parse(SortRightFragment.tvStartDate.getText().toString());
                            long millis = date.getTime();
                            datePicker.setMinDate(millis);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (activity.getClass().getSimpleName().equalsIgnoreCase("BookingHistorySortActivity")) {
                    if (HistoryFilterFragment.tvStartDate.getText().toString().equals("")) {

                    } else {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                            Date date = sdf.parse(HistoryFilterFragment.tvStartDate.getText().toString());
                            long millis = date.getTime();
                            datePicker.setMinDate(millis);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    String strDate = changeDateFormat(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    if (activity.getClass().getSimpleName().equalsIgnoreCase("RegisterActivity")) {
                        RegisterActivity.etExp.setText(strDate);

                        dialog.cancel();
                    }

                    if (activity.getClass().getSimpleName().equalsIgnoreCase("BookingHistorySortActivity")) {
                        if (from.equalsIgnoreCase("Start")) {
                            HistoryFilterFragment.tvStartDate.setText(strDate);
                        }
                        if (from.equalsIgnoreCase("End")) {
                            HistoryFilterFragment.tvEndDate.setText(strDate);
                        }

                        dialog.cancel();
                    }
                    if (activity.getClass().getSimpleName().equalsIgnoreCase("SortActivity")) {
                        if (from.equalsIgnoreCase("Start")) {
                            SortRightFragment.tvStartDate.setText(strDate);
                            SortActivity.openShift.setStartDate(strDate);
                        }
                        if (from.equalsIgnoreCase("End")) {
                            SortRightFragment.tvEndDate.setText(strDate);
                            SortActivity.openShift.setEndDate(strDate);
                        }

                        dialog.cancel();
                    }

                    selectedYear = year;
                    selectedMonth = monthOfYear;
                    selectedDay = dayOfMonth;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (activity.getClass().getSimpleName().equalsIgnoreCase("RegisterActivity")) {
                        RegisterActivity.etExp.setText("");
                    } else if (activity.getClass().getSimpleName().equalsIgnoreCase("MarkAsBusyActivity")) {
                        if (from.equalsIgnoreCase("Start")) {
                            MarkAsBusyActivity.tvStartDate.setText(selectedDay + " " + MONTHS[(selectedMonth)] + " " + selectedYear);
                        }
                        dialog.cancel();
                    } else if (activity.getClass().getSimpleName().equalsIgnoreCase("BookingHistorySortActivity")) {
                        if (from.equalsIgnoreCase("Start")) {
                            HistoryFilterFragment.tvStartDate.setText("");
                        }
                        if (from.equalsIgnoreCase("End")) {
                            HistoryFilterFragment.tvEndDate.setText("");
                        }
                    }else  if (activity.getClass().getSimpleName().equalsIgnoreCase("RightToWorkActivity")
                            ||activity.getClass().getSimpleName().equalsIgnoreCase("AccreditationActivity")
                            ||activity.getClass().getSimpleName().equalsIgnoreCase("SkillActivity")
                            ||activity.getClass().getSimpleName().equalsIgnoreCase("PreferenceActivity")){
                        if (from.equalsIgnoreCase("M")) {
                            PreferenceAdapterFields.ViewHolderDate.tvDebNum.setText(selectedDay + " " + MONTHS[(selectedMonth)] + " " + selectedYear);
                        }
                        if (from.equalsIgnoreCase("MY")) {
                            PreferenceAdapterFields.ViewHolderMonthYear.tvDebNum.setText(MONTHS[(selectedMonth)] + " " + selectedYear);
                        }
                        if (from.equalsIgnoreCase("Y")) {
                            PreferenceAdapterFields.ViewHolderYear.tvDebNum.setText("" + selectedYear);
                        }

                    } else {
                        if (from.equalsIgnoreCase("Start")) {
                            SortRightFragment.tvStartDate.setText("");
                            SortActivity.openShift.setStartDate("");
                        }
                        if (from.equalsIgnoreCase("End")) {
                            SortRightFragment.tvEndDate.setText("");
                            SortActivity.openShift.setEndDate("");
                        }
                        if (from.equalsIgnoreCase("M")) {
                            PreferenceAdapterFields.ViewHolderDate.tvDebNum.setText("");
                        }
                    }

                    selectedYear = -1;
                    selectedMonth = -1;
                    selectedDay = -1;

                    dialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    public void showDialog(Activity activity, String title, String msg, int requestMode, ApiListener mListener) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.alert_tc, null);
        TextView tv = dialogView.findViewById(R.id.tvText);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvAccept = dialogView.findViewById(R.id.tv_clear);
        tv.setText(msg);
        tvAccept.setText("Cancel");

        if (requestMode == GlobalConstants.ConfirmCancel) {
            tvCancel.setText("Confirm");
        } else {
            tvCancel.setText("Ok");
        }

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prompt the user once explanation has been shown
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prompt the user once explanation has been shown
                if (requestMode == 1) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            MapActivity.MY_PERMISSIONS_REQUEST_LOCATION);
                } else if (requestMode == 2) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    activity.startActivity(myIntent);
                } else if (requestMode == GlobalConstants.ConfirmHidePharmacy) {
                    mListener.onHitApi(true);
                } else if (requestMode == GlobalConstants.ConfirmCancel) {
                    mListener.onHitApi(false);
                }
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setView(dialogView);
        dialog.show();
    }

    public void showInfoDialog(Activity activity, String msg, int requestMode) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView;
        if (msg.length() > 40 && msg.length() < 100) {
            dialogView = inflater.inflate(R.layout.dialog_info, null);
        } else if (msg.length() > 100) {
            dialogView = inflater.inflate(R.layout.dialog_info2, null);
        } else {
            dialogView = inflater.inflate(R.layout.activity_alert_sign_out, null);
        }
        TextView tv = dialogView.findViewById(R.id.tvText);
        TextView tvAccept = dialogView.findViewById(R.id.tv_clear);

        tv.setText(msg);
        tvAccept.setText("Ok");

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prompt the user once explanation has been shown
                if (requestMode == GlobalConstants.AcceptAlert) {
                    activity.finish();
                }
                if (requestMode == GlobalConstants.PendingTextShow) {
                    //activity.finish();
                }

                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setView(dialogView);
        dialog.show();
    }

    public void fn_openUrl(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }


    public void showKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    public void fn_showMonthYearPicker(String from, Activity activity, Context context) {
        final Calendar c = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context,
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        if (from.equalsIgnoreCase("MY")) {
                            PreferenceAdapterFields.ViewHolderMonthYear.tvDebNum.setText(MONTHS[(selectedMonth)] + " " + selectedYear);
                        } else if (from.equalsIgnoreCase("Y")) {
                            PreferenceAdapterFields.ViewHolderYear.tvDebNum.setText("" + selectedYear);
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH));


        if (from.equalsIgnoreCase("M")) {

            if(PreferenceAdapterFields.ViewHolderMonthYear.tvDebNum.getText().toString()!=null
                    &&!PreferenceAdapterFields.ViewHolderMonthYear.tvDebNum.getText().toString().equals("")){

                String selectedText=PreferenceAdapterFields.ViewHolderMonthYear.tvDebNum.getText().toString();
                String selectedMonth=selectedText.substring(0, selectedText.indexOf(" "));
                Log.e("selectedMonth--",selectedMonth);

                String selectedYear=selectedText.substring(selectedText.indexOf(" ")+1);

                try {
                    Date date = new SimpleDateFormat("MMM").parse(selectedMonth);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    //println(cal.get(Calendar.MONTH));

                    Log.e("selectedMonth--",cal.get(Calendar.MONTH)+1+"");

                    Date date1=new SimpleDateFormat("yyyy").parse(selectedYear);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date1);
                    //println(cal.get(Calendar.MONTH));
                    Log.e("selectedYear--",cal1.get(Calendar.YEAR)+1+"");


                    builder.setActivatedMonth(cal.get(Calendar.MONTH))
                            .setActivatedYear(cal1.get(Calendar.YEAR)+1)
                            .setMonthAndYearRange(0, 11, c.get(Calendar.YEAR)-100, c.get(Calendar.YEAR)+100)
                            .setTitle("Select Month & Year")
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();

                }catch (ParseException pe){
                    pe.printStackTrace();
                }
            }else {
                builder.setActivatedMonth(c.get(Calendar.MONTH))
                        .setActivatedYear(c.get(Calendar.YEAR))
                        .setMonthAndYearRange(0, 11, c.get(Calendar.YEAR) - 100, c.get(Calendar.YEAR) + 100)
                        .setMonthRange(0, 11)
                        .setTitle("Select Month & Year")
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {

                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {

                            }
                        })
                        .build()
                        .show();
            }
        }
        if (from.equalsIgnoreCase("MY")) {
            if(PreferenceAdapterFields.ViewHolderMonthYear.tvDebNum.getText().toString()!=null
                    &&!PreferenceAdapterFields.ViewHolderMonthYear.tvDebNum.getText().toString().equals("")){

                String selectedText=PreferenceAdapterFields.ViewHolderMonthYear.tvDebNum.getText().toString();
                String selectedMonth=selectedText.substring(0, selectedText.indexOf(" "));
                String selectedYear=selectedText.substring(selectedText.indexOf(" ")+1);
                int sYear=Integer.parseInt(selectedYear);
                try {
                    Date date = new SimpleDateFormat("MMM").parse(selectedMonth);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    Date date1=new SimpleDateFormat("yyyy").parse(selectedYear);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date1);


                    int cYear=cal1.get(Calendar.YEAR);

                    if(cal1.get(Calendar.YEAR)==sYear){
                        cYear=cal1.get(Calendar.YEAR);
                    }else if(cal1.get(Calendar.YEAR)>sYear){
                        cYear=cal1.get(Calendar.YEAR)-1;
                    }else if(cal1.get(Calendar.YEAR)<sYear){
                        cYear=cal1.get(Calendar.YEAR)+1;
                    }


                builder.setActivatedMonth(cal.get(Calendar.MONTH))
                        .setActivatedYear(cYear)
                        .setMonthAndYearRange(0, 11, c.get(Calendar.YEAR)-100, c.get(Calendar.YEAR)+100)
                        .setTitle("Select Month & Year")
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {

                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {

                            }
                        })
                        .build()
                        .show();

                }catch (ParseException pe){
                    pe.printStackTrace();
                }
            }else {
                try {
                    builder.setActivatedMonth(c.get(Calendar.MONTH))
                            .setActivatedYear(c.get(Calendar.YEAR))
                            .setMonthAndYearRange(0, 11, c.get(Calendar.YEAR) - 100, c.get(Calendar.YEAR) + 100)
                            .setTitle("Select Month & Year")
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } else if (from.equalsIgnoreCase("Y")) {
            if(PreferenceAdapterFields.ViewHolderYear.tvDebNum.getText().toString()!=null
                    &&!PreferenceAdapterFields.ViewHolderYear.tvDebNum.getText().toString().equals("")){

                try {
                    String selectedYear = PreferenceAdapterFields.ViewHolderYear.tvDebNum.getText().toString();
                    int sYear = Integer.parseInt(selectedYear);

                    Date date1=new SimpleDateFormat("yyyy").parse(selectedYear);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date1);
                    int cYear=cal1.get(Calendar.YEAR);

                    if(cal1.get(Calendar.YEAR)==sYear){
                        cYear=cal1.get(Calendar.YEAR);
                    }else if(cal1.get(Calendar.YEAR)>sYear){
                        cYear=cal1.get(Calendar.YEAR)-1;
                    }else if(cal1.get(Calendar.YEAR)<sYear){
                        cYear=cal1.get(Calendar.YEAR)+1;
                    }
                    /*if((cal1.get(Calendar.YEAR)==c.get(Calendar.YEAR) + 100)||(cal1.get(Calendar.YEAR)==c.get(Calendar.YEAR) - 100)){
                        cYear=cal1.get(Calendar.YEAR);
                    }else{
                        cYear=cal1.get(Calendar.YEAR);
                    }*/
                    builder.setActivatedYear(cYear)
                            .setMinYear(c.get(Calendar.YEAR) - 100)
                            .setMaxYear(c.get(Calendar.YEAR) + 100)
                            .setTitle("Select Year")
                            .showYearOnly()
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {

                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {

                                }
                            })
                            .build()
                            .show();

                }catch (ParseException pe){
                    pe.printStackTrace();
                }
            }else {
                builder.setActivatedYear(c.get(Calendar.YEAR))
                        .setMinYear(c.get(Calendar.YEAR) - 100)
                        .setMaxYear(c.get(Calendar.YEAR) + 100)
                        .setTitle("Select Year")
                        .showYearOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {

                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {

                            }
                        })
                        .build()
                        .show();
            }
        } else {
            builder.setActivatedYear(c.get(Calendar.YEAR))
                    .setActivatedMonth(c.get(Calendar.MONTH))
                    .setMinYear(c.get(Calendar.YEAR)-100)
                    .setMaxYear(c.get(Calendar.YEAR)+100)
                    .setTitle("Select Year")
                    .showYearOnly()
                    .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                        @Override
                        public void onMonthChanged(int selectedMonth) {

                        }
                    })
                    .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                        @Override
                        public void onYearChanged(int selectedYear) {

                        }
                    })
                    .build()
                    .show();
        }

    }


    public void fn_showDatePickerAll(String from, Activity activity) {
        Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.setContentView(R.layout.layout_date_picker);
        dialog.setCancelable(false);


        int year, month, day;
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        TextView tv_clear, tv_cancel;
        tv_clear = dialog.findViewById(R.id.tv_clear);
        tv_cancel = dialog.findViewById(R.id.tv_cancel);
        DatePicker datePicker = dialog.findViewById(R.id.datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });

        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceAdapterFields.ViewHolderDate.tvDebNum.setText("");
                dialog.cancel();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    public String changeDateFormat(String time) {
        String inputPattern = "dd/MM/yyyy";
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

    /*public String modifyDateLayout(String inputDate) {
        SimpleDateFormat format1=null;
        SimpleDateFormat format2=null;
        Date date=null;
        try {
            format1 = new SimpleDateFormat("dd/MM/yyyy");
            format2 = new SimpleDateFormat("dd-MM-yyyy");
            date = format1.parse(inputDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return format2.format(date);
    }*/


    public void showDialog(Activity activity, String title, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_alert_sign_out, null);
        TextView tvClear = dialogView.findViewById(R.id.tv_clear);
        TextView tvText = dialogView.findViewById(R.id.tvText);
        tvText.setText(msg);

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(i);
                dialog.dismiss();
                SharedPrefManager.getInstance(activity).clearIdPref();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setView(dialogView);
        dialog.show();
    }


    public void showPermDialog(Activity activity, String title, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();
        dialog.setCancelable(false);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_alert_sign_out, null);
        TextView tvClear = dialogView.findViewById(R.id.tv_clear);
        TextView tvText = dialogView.findViewById(R.id.tvText);
        tvText.setText(msg);

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    activity.startActivity(intent);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }


    public String changeDateFormatToNormal(String time) {
        String outputPattern = "yyyy-MM-dd";
        String inputPattern = "dd MMM yyyy";
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

    public String getAddress(Activity activity,double lat, double lng) {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        String add="";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            /*add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();*/

            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return add;
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime){
        SharedPreferences sharedPreference = context.getSharedPreferences("SharedPref", MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }
    public static boolean isFirstTimeAskingPermission(Context context, String permission){
        return context.getSharedPreferences("SharedPref", MODE_PRIVATE).getBoolean(permission, true);
    }



}
