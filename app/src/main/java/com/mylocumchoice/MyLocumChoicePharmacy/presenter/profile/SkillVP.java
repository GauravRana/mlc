package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public class SkillVP {

    public interface Presenter {
        void getSkills(Context context);
        void getSkillsNotification(Context context,int notification_id);
    }


    public interface View extends BaseView {
        void onGetSkills(Context context, Response<PrefernceRes> response);
    }
}
