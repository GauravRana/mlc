package com.mylocumchoice.MyLocumChoicePharmacy.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.TransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.crash.FirebaseCrash;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;
import com.tapadoo.alerter.Alerter;

import pub.devrel.easypermissions.EasyPermissions;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseActivity extends AppCompatActivity implements BaseView {

    private boolean changeStatusIconColor = true;
    private String android_id;

    ProcessDialog processDialog;

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransitionExit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        SharedPrefManager.getInstance(this).setUniqueID(android_id);
        checkInternet();
        //checkInternetConnection(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SanFranciscoText_Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (changeStatusIconColor) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                decor.setSystemUiVisibility(0);
            }
        }

        // processDialog=new ProcessDialog(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);


    }

    /**
     * Start activity
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * Start activity
     *
     * @param cls
     */

    /**
     * Start activity
     *
     * @param cls
     */
    public void startActivityWithAllFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        //finishAffinity();
    }


    public void startActivityWithFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void showWarning(Activity activity, String title, String msg, String error) {
        if (error.equalsIgnoreCase("error")) {
            Alerter.create(activity)
                    .setTitle(title)
                    .setContentGravity(Gravity.CENTER)
                    .setBackgroundColorRes(R.color.warning)
                    .setText(msg)
                    .setIconColorFilter(0)
                    .hideIcon()
                    .show();
        } else {
            Alerter.create(activity)
                    .setTitle(title)
                    .setContentGravity(Gravity.CENTER)
                    .setBackgroundColorRes(R.color.colorPrimaryDark)
                    .setText(msg)
                    .setIconColorFilter(0)
                    .hideIcon()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startActivityWithBundle(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void checkInternet() {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan != null) {
            NetworkInfo mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);//.getState();
            NetworkInfo wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);//.getState();

            if (mobile!=null && mobile.isConnectedOrConnecting()) {
                //mobile
            } else if (wifi!=null && wifi.isConnectedOrConnecting()) {
                //wifi
            } else {
                showWarning(this, "", "No Internet Connection", "error");
            }
        }else {
            showWarning(this, "", "No Internet Connection", "error");
        }
    }

    public static boolean checkInternetConnection(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public void showProgress() {
        if (processDialog != null) {
            processDialog.hide();
        }

        processDialog = new ProcessDialog(BaseActivity.this);
        processDialog.show();
    }

    @Override
    public void hideProgress() {
        if (processDialog != null) {
            processDialog.hide();
        }
    }


    public class HiddenPassTransformationMethod implements TransformationMethod {

        private char DOT = '\u2B24';

        @Override
        public CharSequence getTransformation(final CharSequence charSequence, final View view) {
            return new HiddenPassTransformationMethod.PassCharSequence(charSequence);
        }

        @Override
        public void onFocusChanged(final View view, final CharSequence charSequence, final boolean b, final int i,
                                   final Rect rect) {
            //nothing to do here
        }

        private class PassCharSequence implements CharSequence {

            private final CharSequence charSequence;

            public PassCharSequence(final CharSequence charSequence) {
                this.charSequence = charSequence;
            }

            @Override
            public char charAt(final int index) {
                return DOT;
            }

            @Override
            public int length() {
                return charSequence.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return new HiddenPassTransformationMethod.PassCharSequence(charSequence.subSequence(start, end));
            }
        }
    }

}
