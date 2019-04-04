package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface ShiftDetailView extends BaseView{

    void onDetailsFetched(ShiftDetailsRes response);
    void onGettingError();
}
