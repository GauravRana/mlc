package com.mylocumchoice.MyLocumChoicePharmacy.view.base;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.utils.DialogClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.R;

import java.util.List;

public class CustomDialog extends Dialog implements View.OnClickListener{

    public Activity c;
    public Dialog d;
    public TextView top, bottom, third;
    public View view_top, view_bottom;
    public DialogClickListener listener;
    public int type;
    private View v1, v2;
    List<String> lv_options;


    public CustomDialog(Activity a, int type, List<String> lv_options, DialogClickListener listener) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.listener = listener;
        this.lv_options = lv_options;
        this.type = type;
    }

    @Override
    public void onClick(View v) {
        if(type == 1) {
            switch (v.getId()) {
                case R.id.tv_top:
                    listener.onMapClick(lv_options.get(0));
                    dismiss();
                    break;
                case R.id.tv_bottom:
                    listener.onOpenMapClick(lv_options.get(1));
                    dismiss();
                    break;
                case R.id.tv_third:
                    listener.onThirdClick(lv_options.get(2));
                    dismiss();
                    break;
                default:
                    break;
            }
        }else if(type == 2)  {
            switch (v.getId()) {
                case R.id.tv_top:
                    listener.onMapClick(lv_options.get(0));
                    dismiss();
                    break;
                case R.id.tv_bottom:
                    listener.onOpenMapClick(lv_options.get(1));
                    dismiss();
                    break;
                default:
                    break;
            }
        }else if(type == 3)  {
            switch (v.getId()) {
                case R.id.tv_top:
                    listener.onMapClick(lv_options.get(0));
                    dismiss();
                    break;
                default:
                    break;
            }
        }
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_custom_dialog);

        top = (TextView) findViewById(R.id.tv_top);
        bottom = (TextView) findViewById(R.id.tv_bottom);
        third  = (TextView) findViewById(R.id.tv_third);
        view_top  = (View) findViewById(R.id.view_top);
        view_bottom  = (View) findViewById(R.id.view_bottom);
//        v1 = findViewById(R.id.view_up);
//        v2 = findViewById(R.id.view_down);
        if (type == 1) {
            //view_top.setVisibility(View.GONE);
            //view_bottom.setVisibility(View.GONE);
            //third.setVisibility(View.GONE);
            //bottom.setVisibility(View.GONE);
//            v1.setVisibility(View.GONE);
//            v2.setVisibility(View.GONE);
        }
        if (type == 2) {
            view_top.setVisibility(View.VISIBLE);
            view_bottom.setVisibility(View.GONE);
            top.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            third.setVisibility(View.GONE);
        }
        if (type == 3) {
            view_top.setVisibility(View.GONE);
            view_bottom.setVisibility(View.GONE);
            top.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
//            v2.setVisibility(View.GONE);
            third.setVisibility(View.GONE);
        }



        if(type==1){
            top.setText(lv_options.get(0));
            bottom.setText(lv_options.get(1));
            third.setText(lv_options.get(2));
        }else if(type==2){
            top.setText(lv_options.get(0));
            bottom.setText(lv_options.get(1));
        }else if(type==3){
            top.setText(lv_options.get(0));
        }

        top.setOnClickListener(this);
        bottom.setOnClickListener(this);
        third.setOnClickListener(this);

    }
}
