package com.mylocumchoice.MyLocumChoicePharmacy.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;

public class CustomAlertDialog extends BaseDialogFragment<CustomAlertDialog.OnDialogFragmentClickListener> {

    private Context mContext;

    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        public void onOkClicked(CustomAlertDialog dialog);
        public void onCancelClicked(CustomAlertDialog dialog);
    }

    // Create an instance of the Dialog with the input
    public static CustomAlertDialog newInstance(String title, String message) {
        CustomAlertDialog frag = new CustomAlertDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", message);
        frag.setArguments(args);
        return frag;
    }

    // Create a Dialog using default AlertDialog builder , if not inflate custom view in onCreateView
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .create();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_tc, null);
        TextView tv = dialogView.findViewById(R.id.tvText);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvAccept = dialogView.findViewById(R.id.tv_clear);
        tv.setText(getArguments().getString("title"));

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getActivityInstance().onOkClicked(CustomAlertDialog.this);
            }
        });

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getActivityInstance().onCancelClicked(CustomAlertDialog.this);
            }
        });
        dialog.setView(dialogView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}