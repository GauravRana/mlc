package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface InvitationView extends BaseView {

    void onInvitesFetch(OpenShiftResponse openShiftResponse);

    void onInviteError();


    void onInvitesDetailFetch(OpenShiftResponse openShiftResponse);

    void onInviteDetailError();

}
