package com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts;

public interface ShiftDetailsPresenter {

    void onGetShiftDetails(int id);
    void onGetAppliedShiftDetails(int id);
    void onGetInviteShiftDetails(int id);

    void onGetShiftDetailsNotification(int id,int notification_id);
    void onGetAppliedShiftDetailsNotification(int id,int notification_id);
    void onGetInviteShiftDetailsNotification(int id,int notification_id);
}
