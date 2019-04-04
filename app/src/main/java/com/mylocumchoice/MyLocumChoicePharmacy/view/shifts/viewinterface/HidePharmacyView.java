package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.HidePharmacyResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface HidePharmacyView extends BaseView{

    void onSuccessfulHidden(HidePharmacyResponse hidePharmacyResponse);

    void onHideFailure();
}
