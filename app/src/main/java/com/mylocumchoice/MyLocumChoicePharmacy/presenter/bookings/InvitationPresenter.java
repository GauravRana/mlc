package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import com.google.gson.JsonObject;

public interface InvitationPresenter {

    void onGetInvitations(JsonObject openShiftReq,int pageNum);

    void getInvitationsDetail(int id);
}
