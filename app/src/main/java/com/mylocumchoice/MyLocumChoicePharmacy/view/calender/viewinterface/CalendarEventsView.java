package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventListResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface CalendarEventsView extends BaseView{
    void onCalendarEvents(CalendarEventsRes calendarEventsRes);
    void onCalendarEventsError();
}
