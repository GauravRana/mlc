package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.RatingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface PharmacyRatingView extends BaseView{

    void onRatingSuccess(RatingResponse response);
    void onRatingFailure();
}
