package com.mylocumchoice.MyLocumChoicePharmacy.view.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.tapadoo.alerter.Alerter;

import org.json.JSONObject;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

public class BaseFragment extends Fragment implements BaseView {

    private ProcessDialog processDialog;
    private SignOutAlert signOutAlert;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }


    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_from_right, R.anim.slide_to_left).toBundle();
        startActivity(intent, bundle);
    }

    public SpannableString underLineText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        return content;
    }


    public void startActivityOn(Activity activity, Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_from_right, R.anim.slide_to_left).toBundle();
        startActivity(intent, bundle);
    }


    public void startActivityNormal(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * Start activity
     *
     * @param cls
     */
    public void startActivityWithFinish(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * Start activity
     *
     * @param cls
     */
    public void startActivityWithAllFinish(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
        //finishAffinity();
    }


       @SuppressWarnings("ResourceType")
    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
           ft.setCustomAnimations(R.animator.slide_frag_left,
                   R.animator.slide_frag_right, 0, 0);
        ft.replace(R.id.container, fragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commitAllowingStateLoss();

    }

    /**
     * Fragment add and replace with no backstack
     *
     * @param fragment
     */  @SuppressWarnings("ResourceType")
    public void replaceFragmentNoBackstack(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.container, fragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commitAllowingStateLoss();

    }

    public void startActivityWithBundle(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    @Override
    public void showProgress(){
         try{
             processDialog=new ProcessDialog(getActivity());
             processDialog.show();
             if(processDialog!=null){
                 processDialog.cancel();
             }

             processDialog=new ProcessDialog(getActivity());
             processDialog.show();
         }catch (Exception e){
             e.printStackTrace();
         }

    }

    @Override
    public void hideProgress() {
         try{
             processDialog.hide();
             if(processDialog!=null){
                 processDialog.cancel();
             }
         }catch (Exception e){
             e.printStackTrace();
         }

    }

    public void showWarning(Activity activity, String title, String msg, String error) {
        if(error.equalsIgnoreCase("error")) {
            Alerter.create(activity)
                    .setTitle(title)
                    .setContentGravity(Gravity.CENTER)
                    .setBackgroundColorRes(R.color.warning)
                    .setText(msg)
                    .setIconColorFilter(0)
                    .hideIcon()
                    .show();
        }else {
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public void handleAPIErrors(Activity activity, Response<?> response){
        if (response.code() == 401) {
            try {
                signOutAlert = SignOutAlert.newInstance(getActivity(), getResources().getString(R.string.text_sign_out), "");
                signOutAlert.setCancelable(false);
                signOutAlert.show(getChildFragmentManager(), "dialog");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (response.code() == 422) {
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(getActivity(), "", errorString, "error");
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
