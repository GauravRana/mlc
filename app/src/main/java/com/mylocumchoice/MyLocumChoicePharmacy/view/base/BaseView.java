package com.mylocumchoice.MyLocumChoicePharmacy.view.base;

import android.app.Activity;

public interface BaseView {
    void showProgress();
    void hideProgress();
    void showWarning(Activity activity, String title, String msg, String error);
}
