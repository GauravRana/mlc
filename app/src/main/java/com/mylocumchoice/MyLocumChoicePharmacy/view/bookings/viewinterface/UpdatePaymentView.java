package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpdatePaymentResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface UpdatePaymentView extends BaseView{

    void onPaymentUpdated(UpdatePaymentResponse response);
    void onUpdateFailure();
}
