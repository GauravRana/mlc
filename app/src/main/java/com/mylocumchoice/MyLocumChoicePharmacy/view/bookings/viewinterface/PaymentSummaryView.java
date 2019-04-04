package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.EmailSummaryResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface PaymentSummaryView extends BaseView{

    void onSummarySuccess(EmailSummaryResponse response);
    void onSummaryFailure();
}
