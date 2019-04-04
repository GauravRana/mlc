package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface OpenShiftView extends BaseView{

    void setOpenShiftData(OpenShiftResponse openShiftResponse);
    void onResponseFailure();
}
