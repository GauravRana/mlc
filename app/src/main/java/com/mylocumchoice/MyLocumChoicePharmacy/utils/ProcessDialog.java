package com.mylocumchoice.MyLocumChoicePharmacy.utils;

import android.app.Dialog;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;


public class ProcessDialog extends Dialog {
    public Context mContext;
    public ProcessDialog dialog;

    public ProcessDialog(Context context) {
        super(context, R.style.alert_process_dialog);
        this.setContentView(R.layout.progress_dialog);
    }

}
