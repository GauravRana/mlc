package com.mylocumchoice.MyLocumChoicePharmacy.presenter.notification;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.SignInRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.notification.NotificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface NotificationVP {

    public interface Presenter {
        void onNotification();
        void readNotification(int id);
        void deleteNotification();
        void deleteSingleNotification(Context context, int id);
    }

    public interface View extends BaseView {
        void onNotificationSuccess(Response<NotificationRes> response);
        void onNotificationfailed();
        void onReadNotification(Response<Void> response);
        void onDeleteNotification(Response<Void> response);
        void onDeleteSingleNotification(Response<Void> response);
    }
}
