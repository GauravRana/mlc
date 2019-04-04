package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftAcceptResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface ShiftAcceptView extends BaseView{

    void onShiftAccepted(ShiftAcceptResponse shiftAcceptResponse);
    void onAcceptError();
    void onShiftApplied(ShiftAcceptResponse shiftAcceptResponse);
    void onApplyError();
    void onShiftCanceled(ShiftAcceptResponse shiftAcceptResponse);
    void onCancelError();
}
