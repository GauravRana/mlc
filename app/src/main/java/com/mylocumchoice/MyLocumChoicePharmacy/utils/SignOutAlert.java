package com.mylocumchoice.MyLocumChoicePharmacy.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;

public class SignOutAlert extends BaseDialogFragment<SignOutAlert.OnDialogFragmentClickListener> {

    private Activity activity;
    private Context context;

    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {

    }

    // Create an instance of the Dialog with the input
    public static SignOutAlert newInstance(Context context, String title, String message) {
        SignOutAlert frag = new SignOutAlert();
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
        View dialogView = inflater.inflate(R.layout.activity_alert_sign_out, null);
        TextView tvClear = dialogView.findViewById(R.id.tv_clear);
        TextView tvText = dialogView.findViewById(R.id.tvText);
        tvText.setText(getArguments().get("title").toString());
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                dialog.dismiss();
                SharedPrefManager.getInstance(getActivity()).clearIdPref();
                getActivity().finish();
            }
        });

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setView(dialogView);
        return dialog;
    }

}