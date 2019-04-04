package com.mylocumchoice.MyLocumChoicePharmacy.view.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.TransformationMethod;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.crash.FirebaseCrash;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ExceptionDisplay;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;
import com.tapadoo.alerter.Alerter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class AppActivity extends AppCompatActivity implements BaseView {

    private boolean changeStatusIconColor = true;
    private String android_id;
    private ProcessDialog processDialog;
    private SignOutAlert signOutAlert;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        try{
            super.startActivity(intent);
            overridePendingTransitionEnter();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Subscribe
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setStatusBarGradiant(AppActivity.this);
        EventBus.getDefault().register(this);
        // Window window = this.getWindow();
        android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        SharedPrefManager.getInstance(this).setUniqueID(android_id);
        checkInternet();
        //checkInternetConnection(this);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SanFranciscoText_Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (changeStatusIconColor) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                decor.setSystemUiVisibility(0);
            }
        }

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

    @Subscribe
    public void onSelectionEvent(Selection selectionEvent) {

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



    public void startActivityClearTop(Class<?> cls) {
        Intent mIntent = new Intent(this, cls);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mIntent.putExtra("from", "notification");
        startActivity(mIntent);
    }

    /**
     * Start activity
     *
     * @param cls
     */

    public void startActivityWithBundle(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void startActivityWithFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

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

    /**
     * Fragment add and replace
     *
     * @param fragment
     */
    @SuppressWarnings("ResourceType")
    public Fragment replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.container, fragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
        fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        return fragment;
    }


    @SuppressWarnings("ResourceType")
    public void replaceFragmentNoBackstack(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.container, fragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        finish();
    }


    public SpannableString underLineText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        return content;
    }


    @Override
    public void showProgress() {
        try {
            if (processDialog != null) {
                //processDialog.hide();
                processDialog.cancel();

            } else {
                processDialog = new ProcessDialog(this);
                processDialog.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void hideProgress() {
        try{
            if (processDialog != null) {
                //processDialog.hide();
                processDialog.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

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
                    .hideIcon()
                    .setIconColorFilter(0)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.bg1);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(background);
        }
    }
    
    
    public void handleAPIErrors(Activity activity, Response<?> response){
          if (response.code() == 401) {
            try {
                signOutAlert = SignOutAlert.newInstance(this, getResources().getString(R.string.text_sign_out), "");
                signOutAlert.setCancelable(false);
                signOutAlert.show(getSupportFragmentManager(), "dialog");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (response.code() == 422) {
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(this, "", errorString, "error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try{
                showWarning(activity, "", getResources().getString(R.string.handle_strange_error), "error");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
