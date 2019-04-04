package com.mylocumchoice.MyLocumChoicePharmacy.view.calender.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEvents;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventModel;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.CalendarEventsImp;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.CalendarEventsPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.EventListPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.EventsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.MarkAsBusyActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.MyListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.ShowEventsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.adapters.CalendarEventAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.adapters.CalendarListEventsAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.customviews.CustomDayView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.customviews.ThemeDayView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.CalendarEventsView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SingleSelectActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalFragment extends BaseFragment /*implements CalendarPickerController*/ implements CalendarEventsView, MyListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.show_month_view)
    TextView showMonthView;
    @BindView(R.id.show_year_view)
    TextView showYearView;
    @BindView(R.id.choose_date_view)
    LinearLayout chooseDateView;
    @BindView(R.id.show_month_year)
    TextView showMonthYear;
    @BindView(R.id.back_today_button)
    TextView backTodayButton;
    @BindView(R.id.scroll_switch)
    TextView scrollSwitch;
    @BindView(R.id.theme_switch)
    TextView themeSwitch;
    @BindView(R.id.next_month)
    TextView nextMonth;
    @BindView(R.id.last_month)
    TextView lastMonth;
    @BindView(R.id.calendar_view)
    MonthPager calendarView;
    @BindView(R.id.list)
    RecyclerView rvToDoList;
    @BindView(R.id.content)
    CoordinatorLayout content;
    @BindView(R.id.tv_noData)
    TextView tvNoData;
    @BindView(R.id.ll_calendarView)
    LinearLayout ll_calendarView;

    private int RESULT_SHOW_EVENTS = 101;


    private CalendarEventsPresenter mPresenter;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context context;
    private CalendarDate currentDate, selectedDate;
    private boolean initiated = false;
    private String dateParse;

    private String dateFromBusy;
    private boolean isInAdapter;

    int visibleMonth=-1;

    private List<CalendarEventsRes.Event> listEvent;
    private List<CalendarEventsRes.CalendarEvent> listcalenderEvent = new ArrayList<CalendarEventsRes.CalendarEvent>();

    private boolean isShowEvents = false;

    private HashMap<String, String> markData = new HashMap<>();

    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    Utils mUtils;

    com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils utils;

    private boolean isMinSet = false;
    private boolean isMaxSet = false;


    private CalendarEventAdapter adapter;
    private String dateFromCalListener = "";
    private boolean isFromBusy = false;

    private CalendarEventAdapter calendarEventAdapter;

    boolean isActivityResulted = false;


    boolean isScrolled = false;
    CalendarViewAdapter.OnCalendarTypeChanged calendarTypeChanged;


    int selectedPosition = -1;
    CalendarListEventsAdapter calendarListEventsAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalFragment newInstance(String param1, String param2) {
        CalFragment fragment = new CalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, view);
        init();
        mPresenter = new CalendarEventsImp(this, getActivity());


        LandingActivity.ll_RightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                currentDate = new CalendarDate();
                bundle.putString("currentDate", dateParse);
                Intent intent = new Intent(getActivity(), MarkAsBusyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);
            }
        });


        LandingActivity.ll_LeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EventsActivity.class);
            }
        });
        return view;
    }

    private void init() {
        context = getActivity();
        mUtils = new Utils();
        calendarView.setViewHeight(Utils.dpi2px(context, 270));
        rvToDoList.setHasFixedSize(true);
        rvToDoList.setLayoutManager(new LinearLayoutManager(getActivity()));

        initCurrentDate();
        initCalendarView();
        initToolbarClickListener();
        Log.e("ldf", "OnCreated");

    }


    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        visibleMonth=currentDate.getMonth();
        showYearView.setText(currentDate.getYear() + "");
        showMonthView.setText(currentDate.getMonth() + "");
        showMonthYear.setText(mUtils.getMonthForInt(currentDate.getMonth()) + " " + currentDate.getYear());
//        mUtils.setMinDate(currentDate.getMonth());
//        mUtils.setMaxDate(currentDate.getMonth());
        try {
            currentCalendars.get(calendarView.getCurrentPosition() % currentCalendars.size()).setSeedDate(currentDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        dateParse = currentDate.getDay() + "" + "/" + currentDate.getMonth() + "" + "/" + currentDate.getYear() + "";
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(context,
                onSelectDateListener,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);

        calendarTypeChanged = new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {

                try {

                    /*if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.MONTH) {
                    if (selectedDate != null) {
                        if (visibleMonth == selectedDate.getMonth())
                            currentCalendars.get(selectedPosition % currentCalendars.size()).setSeedDate(selectedDate);
                        else {
                            CalendarDate calDate = new CalendarDate();
                            if (visibleMonth == calDate.getMonth()) {
                                currentCalendars.get(calendarView.getCurrentPosition() % currentCalendars.size()).setSeedDate(calDate);
                            }
                        }
                    } else if (selectedDate == null) {
                        CalendarDate calDate = new CalendarDate();
                        if (visibleMonth == calDate.getMonth()) {
                            currentCalendars.get(selectedPosition % currentCalendars.size()).setSeedDate(calDate);
                        }
                    }

                    }*/

                    currentCalendars = calendarAdapter.getPagers();
                    if (currentCalendars.get(selectedPosition % currentCalendars.size()) != null) {
                        try {
                            CalendarDate date = currentCalendars.get(selectedPosition % currentCalendars.size()).getSeedDate();
                            currentDate = date;
                            showYearView.setText(currentDate.getYear() + "");
                            showMonthView.setText(currentDate.getMonth() + "");
                            showMonthYear.setText(mUtils.getMonthForInt(currentDate.getMonth()) + " " + currentDate.getYear());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    java.util.Calendar currentCal = java.util.Calendar.getInstance();
                    currentCal.set(java.util.Calendar.MONTH, currentDate.month - 1);
                    currentCal.set(java.util.Calendar.DAY_OF_MONTH, currentDate.day);
                    currentCal.set(java.util.Calendar.YEAR, currentDate.year);
                    Date currentDate = currentCal.getTime();

                    Log.e("Current Date Format", currentDate + "");
                    String strCurrentDate = changeDateToString(currentDate);
                    currentDate = changeStringToDate(strCurrentDate);

                    java.util.Calendar futureCal = java.util.Calendar.getInstance();
                    if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK)
                        futureCal.set(java.util.Calendar.MONTH, (futureCal.get(java.util.Calendar.MONTH) + 6));
                    else
                        futureCal.set(java.util.Calendar.MONTH, (futureCal.get(java.util.Calendar.MONTH) + 6));
                    Date futureDate = futureCal.getTime();

                    String strfutureDate = changeDateToString(futureDate);
                    futureDate = changeStringToDate(strfutureDate);

                    java.util.Calendar pastCal = java.util.Calendar.getInstance();
                    pastCal.set(java.util.Calendar.MONTH, (pastCal.get(java.util.Calendar.MONTH) - 3));
                    Date pastDate = pastCal.getTime();

                    String strPastDate = changeDateToString(pastDate);
                    pastDate = changeStringToDate(strPastDate);

                    if (type == CalendarAttr.CalendarType.WEEK) {
                        try {
                            if (currentDate.before(pastDate) || strCurrentDate.equals(strPastDate)) {
                                calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.right);
                            } else if (currentDate.after(futureDate) || strCurrentDate.equals(strfutureDate)) {
                                calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.left);
                            } else if (currentDate.compareTo(pastDate) >= 0 && currentDate.compareTo(futureDate) <= 0) {
                                calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.all);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        int currentMonth = currentDate.getMonth() - 1;
                        if (isScrolled) {
                            currentMonth = currentDate.getMonth();
                        }

                        int futureMonth = 0;
                        java.util.Calendar futureCalander = java.util.Calendar.getInstance();
                        if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK)
                            futureMonth = futureCalander.get(java.util.Calendar.MONTH) + 6;
                        else
                            futureMonth = futureCalander.get(java.util.Calendar.MONTH) + 6;


                        if (futureMonth > 12) {
                            futureMonth = futureMonth % 12 == 0 ? 12 : futureMonth % 12;
                        }

                        java.util.Calendar pastCalander = java.util.Calendar.getInstance();
                        int pastMonth = pastCalander.get(java.util.Calendar.MONTH) - 3;
                        if (pastMonth < 0) {
                            pastCalander.set(java.util.Calendar.YEAR, pastCalander.get(java.util.Calendar.YEAR) + pastMonth / 12 - 1);
                            int month = 12 - Math.abs(pastMonth) % 12;
                            pastMonth = month == 0 ? 12 : month;
                        }

                        if (currentDate.before(pastDate) || currentMonth == pastMonth) {
                            calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.right);
                            isScrolled = true;
                        } else if (currentDate.after(futureDate) || currentMonth == futureMonth) {
                            calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.left);
                            isScrolled = false;
                        } else if (currentDate.compareTo(pastDate) >= 0 && currentDate.compareTo(futureDate) <= 0) {
                            calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.all);
                            isScrolled = false;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        calendarAdapter.setOnCalendarTypeChangedListener(calendarTypeChanged);
        initMonthPager();
    }


    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                //refreshClickDate(date);
                rvToDoList.setVisibility(View.VISIBLE);

                selectedDate = date;

                /*if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {
                    calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.all);
                }*/

                java.util.Calendar currentCal = java.util.Calendar.getInstance();
                currentCal.set(java.util.Calendar.MONTH, date.month - 1);
                currentCal.set(java.util.Calendar.DAY_OF_MONTH, date.day);
                currentCal.set(java.util.Calendar.YEAR, date.year);
                Date currentDate = currentCal.getTime();

                java.util.Calendar futureCal = java.util.Calendar.getInstance();
                futureCal.set(java.util.Calendar.MONTH, (futureCal.get(java.util.Calendar.MONTH) + 6));
                Date futureDate = futureCal.getTime();

                java.util.Calendar pastCal = java.util.Calendar.getInstance();
                pastCal.set(java.util.Calendar.MONTH, (pastCal.get(java.util.Calendar.MONTH) - 3));
                Date pastDate = pastCal.getTime();


                if (currentDate.before(pastDate) || currentDate.after(futureDate)) {
                } else {

                    try {
                        dateFromCalListener = String.valueOf(date.year) + "-" + String.valueOf(date.month) + "-" + String.valueOf(date.day);
                        dateParse = String.valueOf(date.day) + "/" + String.valueOf(date.month) + "/" + String.valueOf(date.year);
                        isInAdapter = false;
                        if (listcalenderEvent != null) {
                            if (listcalenderEvent.size() != 0) {
                                for (int i = 0; i < listcalenderEvent.size(); i++) {
                                    if (listcalenderEvent.get(i).getEvents().size() != 0) {
                                        try {
                                            if (listcalenderEvent.get(i).getDate().equalsIgnoreCase(modifyDateLayout(dateFromCalListener))) {
                                                isInAdapter = true;
                                                tvNoData.setVisibility(View.GONE);
                                                rvToDoList.setAdapter(null);
                                                rvToDoList.setVisibility(View.VISIBLE);
                                                calendarListEventsAdapter = new CalendarListEventsAdapter(getActivity(), getActivity(), listcalenderEvent.get(i).getEvents(), listcalenderEvent, dateFromCalListener, CalFragment.this);
                                                rvToDoList.setAdapter(calendarListEventsAdapter);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        isInAdapter = false;
                                        rvToDoList.setAdapter(null);
                                        calendarListEventsAdapter = new CalendarListEventsAdapter(getActivity(), getActivity(), dateFromCalListener, CalFragment.this);
                                        rvToDoList.setAdapter(calendarListEventsAdapter);
                                    }
                                }
                                if (!isInAdapter) {
                                    rvToDoList.setAdapter(null);
                                    calendarListEventsAdapter = new CalendarListEventsAdapter(getActivity(), getActivity(), dateFromCalListener, CalFragment.this);
                                    rvToDoList.setAdapter(calendarListEventsAdapter);
                                }
                            }
                        }
                        //initMarkData(0);
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                //calendarView.setCurrentPosition(calendarView.getCurrentPosition());
                calendarView.selectOtherMonth(offset);

            }
        };
    }

    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        showYearView.setText(currentDate.getYear() + "");
        showMonthView.setText(currentDate.getMonth() + "");
        showMonthYear.setText(mUtils.getMonthForInt(currentDate.getMonth()) + " " + currentDate.getYear());
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        calendarView.setAdapter(calendarAdapter);
        calendarView.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        calendarView.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });


        mCurrentPage = calendarView.getCurrentPosition();
        calendarView.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if(position == 1000 - 3){
//                    isMinSet = true;
//                }else if(position == 1000 + 9){
//                    isMinSet = true;
//                } else {
//                    isMinSet = false;
//                    isMaxSet = false;
//                }

                Toast.makeText(getActivity(), "position--", Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(),"position--" + position,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPageSelected(int position) {
                calendarAdapter.setOnCalendarTypeChangedListener(null);
                selectedPosition = position;
                //calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.all);
                //calendarView.setScrollable(true);
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    try {
                        CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                        currentDate = date;
                        showYearView.setText(currentDate.getYear() + "");
                        showMonthView.setText(currentDate.getMonth() + "");
                        showMonthYear.setText(mUtils.getMonthForInt(currentDate.getMonth()) + " " + currentDate.getYear());

                        visibleMonth=currentDate.getMonth();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                java.util.Calendar futureCal = java.util.Calendar.getInstance();
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK)
                    futureCal.set(java.util.Calendar.MONTH, (futureCal.get(java.util.Calendar.MONTH) + 6));
                else
                    futureCal.set(java.util.Calendar.MONTH, (futureCal.get(java.util.Calendar.MONTH) + 5));
                Date futureDate = futureCal.getTime();

                String strfutureDate = changeDateToString(futureDate);
                futureDate = changeStringToDate(strfutureDate);

                java.util.Calendar pastCal = java.util.Calendar.getInstance();
                pastCal.set(java.util.Calendar.MONTH, (pastCal.get(java.util.Calendar.MONTH) - 3));
                Date pastDate = pastCal.getTime();

                String strPastDate = changeDateToString(pastDate);
                pastDate = changeStringToDate(strPastDate);


                if ((selectedDate == null) || (selectedDate != null && calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK)) {
                    java.util.Calendar currentCal = java.util.Calendar.getInstance();
                    currentCal.set(java.util.Calendar.MONTH, currentDate.month - 1);
                    currentCal.set(java.util.Calendar.DAY_OF_MONTH, currentDate.day);
                    currentCal.set(java.util.Calendar.YEAR, currentDate.year);
                    Date currentDate = currentCal.getTime();

                    Log.e("Current Date Format", currentDate + "");
                    String strCurrentDate = changeDateToString(currentDate);
                    currentDate = changeStringToDate(strCurrentDate);


                    if (currentDate.before(pastDate) || strCurrentDate.equals(strPastDate)) {
                        calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.right);
                    } else if (currentDate.after(futureDate) || strCurrentDate.equals(strfutureDate)) {
                        calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.left);
                    } else if (currentDate.compareTo(pastDate) >= 0 && currentDate.compareTo(futureDate) <= 0) {
                        calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.all);
                    }


                } else if (selectedDate != null && calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.MONTH) {

                    java.util.Calendar currentCal = java.util.Calendar.getInstance();
                    currentCal.set(java.util.Calendar.MONTH, currentDate.month - 1);
                    currentCal.set(java.util.Calendar.DAY_OF_MONTH, currentDate.day);
                    currentCal.set(java.util.Calendar.YEAR, currentDate.year);

                    int currentMonth = currentDate.getMonth() - 1;
                    if (isScrolled) {
                        currentMonth = currentDate.getMonth();
                    }

                    Date currentDate = currentCal.getTime();


                    Log.e("Current Date Format", currentDate + "");
                    String strCurrentDate = changeDateToString(currentDate);
                    currentDate = changeStringToDate(strCurrentDate);

                    int futureMonth = 0;
                    java.util.Calendar futureCalander = java.util.Calendar.getInstance();
                    if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK)
                        futureMonth = futureCalander.get(java.util.Calendar.MONTH) + 6;
                    else
                        futureMonth = futureCalander.get(java.util.Calendar.MONTH) + 6;


                    if (futureMonth > 12) {
                        futureMonth = futureMonth % 12 == 0 ? 12 : futureMonth % 12;
                    }

                    java.util.Calendar pastCalander = java.util.Calendar.getInstance();
                    int pastMonth = pastCalander.get(java.util.Calendar.MONTH) - 3;
                    if (pastMonth < 0) {
                        pastCalander.set(java.util.Calendar.YEAR, pastCalander.get(java.util.Calendar.YEAR) + pastMonth / 12 - 1);
                        int month = 12 - Math.abs(pastMonth) % 12;
                        pastMonth = month == 0 ? 12 : month;
                    }

                    if (currentDate.before(pastDate) || currentMonth == pastMonth) {
                        calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.right);
                        isScrolled = true;
                    } else if (currentDate.after(futureDate) || currentMonth == futureMonth) {
                        calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.left);
                        isScrolled = false;
                    } else if (currentDate.compareTo(pastDate) >= 0 && currentDate.compareTo(futureDate) <= 0) {
                        calendarView.setAllowedSwipeDirection(Utils.SwipeDirection.all);
                        isScrolled = false;
                    }

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                calendarAdapter.setOnCalendarTypeChangedListener(calendarTypeChanged);
            }
        });

    }

    /**
     * 初始化对应功能的listener
     *
     * @return void
     */
    private void initToolbarClickListener() {
        backTodayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackToDayBtn();
            }
        });
        scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {
//                    Utils.scrollTo(content, rvToDoList, calendarView.getViewHeight(), 200);
//                    calendarAdapter.switchToMonth();
//                } else {
//                    Utils.scrollTo(content, rvToDoList, calendarView.getCellHeight(), 200);
//                    calendarAdapter.switchToWeek(calendarView.getRowIndex());
//                }
            }
        });

        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //refreshSelectBackground();
            }
        });
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setCurrentItem(calendarView.getCurrentPosition() + 1);
            }
        });
        lastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarView.getCurrentPosition() == 0) {

                } else {
                    calendarView.setCurrentItem(calendarView.getCurrentPosition() - 1);
                }
            }
        });
    }


    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        showYearView.setText(currentDate.getYear() + "");
        showMonthView.setText(currentDate.getMonth() + "");
        showMonthYear.setText(mUtils.getMonthForInt(currentDate.getMonth()) + " " + currentDate.getYear());
    }

    private void refreshSelectBackground() {
        ThemeDayView themeDayView = new ThemeDayView(context, R.layout.custom_day_focus);
        calendarAdapter.setCustomDayRenderer(themeDayView);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter.notifyDataChanged(new CalendarDate());
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */
    private void initMarkData(int topple) {
        markData = new HashMap<>();
        if (listcalenderEvent != null) {
            for (int i = 0; i < listcalenderEvent.size(); i++) {
                if (listcalenderEvent.get(i).getHasBooking()) {
                    //green dot
                    try {
                        markData.put(modifyDateLayoutMarker(listcalenderEvent.get(i).getDate().toString()), "0");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    //red dot
                    try {
                        markData.put(modifyDateLayoutMarker(listcalenderEvent.get(i).getDate().toString()), "1");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        calendarAdapter.setMarkData(markData, topple);
        //Utils.loadMarkData();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onResume() {
        super.onResume();

        //initMarkData();
        /*try{
            hideProgress();
        }catch (Exception e){
            e.printStackTrace();
        }*/
        if (isActivityResulted) {
            isActivityResulted = false;
            //init();
        } else {
            mPresenter.onCalendarEventsList(getActivity());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }

    @Override
    public void onCalendarEvents(CalendarEventsRes calendarEventsRes) {
        CalendarEvents events = new CalendarEvents();
        events.setRecurringTypes(calendarEventsRes.getRecurringTypes());
        EventBus.getDefault().postSticky(events);
        listcalenderEvent = calendarEventsRes.getCalendarEvents();

        if (calendarEventsRes.getCalendarEvents() != null) {
            for (int i = 0; i < calendarEventsRes.getCalendarEvents().size(); i++) {
                listEvent = calendarEventsRes.getCalendarEvents().get(i).getEvents();
            }
        }
        if (dateFromCalListener.equalsIgnoreCase("")) {
            dateFromCalListener = String.valueOf(currentDate.year) + "-" + String.valueOf(currentDate.month) + "-" + String.valueOf(currentDate.day);
        }

        rvToDoList.setVisibility(View.VISIBLE);
        isInAdapter = false;
        if (listcalenderEvent != null) {
            if (listcalenderEvent.size() != 0) {
                for (int i = 0; i < listcalenderEvent.size(); i++) {
                    if (listcalenderEvent.get(i).getEvents().size() != 0) {
                        try {
                            if (listcalenderEvent.get(i).getDate().equalsIgnoreCase(modifyDateLayout(dateFromCalListener))) {
                                isInAdapter = true;
                                tvNoData.setVisibility(View.GONE);
                                rvToDoList.setAdapter(null);
                                rvToDoList.setVisibility(View.VISIBLE);
                                calendarListEventsAdapter = new CalendarListEventsAdapter(getActivity(), getActivity(), listcalenderEvent.get(i).getEvents(), listcalenderEvent, dateFromCalListener, CalFragment.this);
                                rvToDoList.setAdapter(calendarListEventsAdapter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        isInAdapter = false;
                        rvToDoList.setAdapter(null);
//                        tvNoData.setVisibility(View.VISIBLE);
                        calendarListEventsAdapter = new CalendarListEventsAdapter(getActivity(), getActivity(), dateFromCalListener, CalFragment.this);
                        rvToDoList.setAdapter(calendarListEventsAdapter);
                    }
                }
            }
        }
        if (!isInAdapter) {
            rvToDoList.setAdapter(null);
//            tvNoData.setVisibility(View.VISIBLE);
            calendarListEventsAdapter = new CalendarListEventsAdapter(getActivity(), getActivity(), dateFromCalListener, CalFragment.this);
            rvToDoList.setAdapter(calendarListEventsAdapter);
        }

        if (calendarEventsRes.isUnseen_notifications()) {
            LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.notify_dot));
        } else {
            LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.menu_bell_off));
        }


        try {
            SharedPrefManager.getInstance(getActivity()).setNotifyDot(calendarEventsRes.isUnseen_notifications());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initMarkData(1);
        //dateFromCalListener


        try {
            String[] splitDate = dateFromCalListener.split("-");
            String selectYear = splitDate[0];
            String selectMonth = splitDate[1];
            String selectDay = splitDate[2];
            calendarAdapter.notifyDataChanged(new CalendarDate(Integer.parseInt(selectYear), Integer.parseInt(selectMonth), Integer.parseInt(selectDay)));
            showMonthYear.setText(MONTHS[Integer.parseInt(selectMonth) - 1] + " " + selectYear);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        if(!isFromBusy) {
//            initMarkData();
//            isFromBusy = false;
//        }

    }

    @Override
    public void onCalendarEventsError() {

    }

    private String modifyDateLayoutMarker(String inputDate) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-d");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-d");
        Date date = format1.parse(inputDate);
        return format2.format(date);
    }


    private String modifyDateLayout(String inputDate) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-d");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format1.parse(inputDate);
        return format2.format(date);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        isActivityResulted = true;
        if (requestCode == 100) {
            try {

                dateFromBusy = data.getExtras().get("select_date").toString();
                isFromBusy = data.getExtras().getBoolean("isFromBusy");
                String dateForDot = data.getExtras().get("select_date_other").toString();
                // put red on selected date for a day
                markData.put(dateForDot, "1");
                utils = new com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils();
                dateFromCalListener = utils.changeDateFormatToNormal(dateFromBusy);

                try {
                    String[] splitDate = dateFromCalListener.split("-");
                    String selectYear = splitDate[0];
                    String selectMonth = splitDate[1];
                    String selectDay = splitDate[2];
                    calendarAdapter.notifyDataChanged(new CalendarDate(Integer.parseInt(selectYear), Integer.parseInt(selectMonth), Integer.parseInt(selectDay)));
                    showMonthYear.setText(MONTHS[Integer.parseInt(selectMonth) - 1] + " " + selectYear);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.onCalendarEventsList(getActivity());
                    }
                }, 200);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == 101 && resultCode == RESULT_SHOW_EVENTS) {
            try {
                isFromBusy = data.getExtras().getBoolean("isFromBusy");
                String dateForDot = data.getExtras().get("select_date_other").toString();
                markData.values().remove(dateForDot);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.onCalendarEventsList(getActivity());
                    }
                }, 200);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //calendarView.setCurrentItem(calendarView.getCurrentPosition() + 1);
    }

    @Override
    public void callback() {
        Intent intent = new Intent(getActivity(), ShowEventsActivity.class);
        startActivityForResult(intent, 101);
    }


    public Date changeStringToDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat formatterOut = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(str);
            Log.e("returned date--", date + "");
            Log.e("returned string--", formatterOut.format(date) + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public String changeDateToString(Date date) {
        String dateTime = "";

        /*E, dd MMM yyyy HH:mm:ss z
        Tue Jan 01 18:50:05 GMT+05:30 2019*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        dateFormat.setTimeZone(TimeZone.getDefault());
        try {
            dateTime = dateFormat.format(date);
            System.out.println("Current Date Time : " + dateTime);
            Log.e("Current Date Time", dateTime);
        } catch (android.net.ParseException e) {
            e.printStackTrace();
        }

        dateTime = changeDateFormatToNormal(dateTime);

        return dateTime;
    }

    public String changeDateFormatToNormal(String time) {
        String outputPattern = "dd-MM-yyyy";
        String inputPattern = "E MMM dd HH:mm:ss z yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e("dateformat---", str);
        return str;
    }


    public Date addDaysToDate(Date date, java.util.Calendar c, String str) {
        String strDate = date.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //java.util.Calendar c = java.util.Calendar.getInstance();
        try {
            c.setTime(sdf.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (str.equalsIgnoreCase("add")) {
            c.add(java.util.Calendar.DATE, 7);
        } else {
            c.add(java.util.Calendar.DATE, -7);
        }// number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        String output = sdf1.format(c.getTime());

        Date extDate = changeStringToDate(output);
        return extDate;
    }


}