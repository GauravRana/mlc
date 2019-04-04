package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventListResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface EventListView extends BaseView {

    void onEventListFetched(EventListResponse eventListResponse);

    void onGettingError();
}
