package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftAcceptResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface ShiftOfferView extends BaseView{
    void onShiftOfferd(ShiftAcceptResponse shiftAcceptResponse);
    void onOfferError();
}
