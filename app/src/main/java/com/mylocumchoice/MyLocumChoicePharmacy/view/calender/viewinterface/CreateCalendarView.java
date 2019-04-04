package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CreateCalendarRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface CreateCalendarView extends BaseView{
    void onCreateCalendar(Response<Void> response);
    void onCreateCalendarError();
}
