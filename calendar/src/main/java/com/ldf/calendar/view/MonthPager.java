package com.ldf.calendar.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ldf.calendar.Utils;
import com.ldf.calendar.behavior.MonthPagerBehavior;
import com.ldf.calendar.component.CalendarViewAdapter;

@CoordinatorLayout.DefaultBehavior(MonthPagerBehavior.class)
public class MonthPager extends Utils.CustomViewPager {
    public static int CURRENT_DAY_INDEX = 1000;


    public int currentPosition = CURRENT_DAY_INDEX;
    private int cellHeight;
    private int viewHeight;
    private int rowIndex = 6;

    private OnPageChangeListener monthPageChangeListener;
    private boolean pageChangeByGesture = false;
    private boolean hasPageChangeListener = false;
    private boolean scrollable = true;
    private int pageScrollState = ViewPager.SCROLL_STATE_IDLE;
    private static final float thresholdOffset = 0.5f;

    private boolean isRunFirstTime = false;
    private int selectedPostion = 0;

    private boolean isMinSet = false;
    private boolean isMaxSet = false;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    private float downX, downY, upX, upY;

    private GestureDetector mDetector;

    public MonthPager(Context context) {
        this(context, null);
    }

    public MonthPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //setCurrentPosition(1000);
        ViewPager.OnPageChangeListener viewPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (monthPageChangeListener != null) {
//                    if(position == 997){
//                        setAllowedSwipeDirection(Utils.SwipeDirection.values()[2]);    //1000 - 3 == 997  3 months before.
//                    }else if(position == 1009){
//                        setAllowedSwipeDirection(Utils.SwipeDirection.values()[1]);   //1000 + 9 == 1009  9 months ahead.
//                    }else {
//                        setAllowedSwipeDirection(Utils.SwipeDirection.values()[0]);
//                        monthPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                    }

                }
            }

            @Override
            public void onPageSelected(int position) {
//                currentPosition = position;
//                CURRENT_DAY_INDEX=position;
//                setCurrentPosition(1000);
//                if (pageChangeByGesture) {
//                    if (monthPageChangeListener != null) {
//                        if(position == 997){
//                            setAllowedSwipeDirection(Utils.SwipeDirection.values()[2]);
//                            monthPageChangeListener.onPageSelected(position);//1000 - 3 == 997  3 months before.
//                        }else if(position == 1006){
//                            setAllowedSwipeDirection(Utils.SwipeDirection.values()[1]);
//                            monthPageChangeListener.onPageSelected(position);//1000 + 6 == 1006  6 months ahead.
//                        }else {
//                            setAllowedSwipeDirection(Utils.SwipeDirection.values()[0]);
//                            monthPageChangeListener.onPageSelected(position);
//                        }
//                    }
//                    pageChangeByGesture = false;
//                }

                currentPosition = position;
                if (pageChangeByGesture) {
                    if (monthPageChangeListener != null) {
                        monthPageChangeListener.onPageSelected(position);
                    }
                    pageChangeByGesture = false;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                pageScrollState = state;
                if (monthPageChangeListener != null) {
                    monthPageChangeListener.onPageScrollStateChanged(state);
                }
                pageChangeByGesture = true;
            }
        };
        addOnPageChangeListener(viewPageChangeListener);
        hasPageChangeListener = true;



        ViewPager.OnTouchListener viewOnTouchListener = new OnTouchListener() {
            float prevX = -1;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (prevX != -1) {
                    if (motionEvent.getX() > prevX) {
                        //Toast.makeText(getContext(), "left2right swipe", Toast.LENGTH_SHORT).show();

                    } else if (prevX > motionEvent.getX()) {
                        //Toast.makeText(getContext(), "right2left swipe", Toast.LENGTH_SHORT).show();

                    }
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    prevX = motionEvent.getX();
                } else {
                    prevX = -1;
                }
                return false;
            }
        };
        setOnTouchListener(viewOnTouchListener);


    }

    @Override
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (hasPageChangeListener) {
            Log.e("ldf", "MonthPager Just Can Use Own OnPageChangeListener");
        } else {
            super.addOnPageChangeListener(listener);
        }
    }

    public void addOnPageChangeListener(OnPageChangeListener listener) {
        this.monthPageChangeListener = listener;
        Log.e("ldf", "MonthPager Just Can Use Own OnPageChangeListener");
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
//        if (!scrollable)
//            return false;
//        else
//            return super.onTouchEvent(me);

//        switch(me.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                downX = me.getX();
//                downY = me.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                upX = me.getX();
//                upY = me.getY();
//
//                float deltaX = downX - upX;
//                float deltaY = downY - upY;
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    if (Math.abs(deltaX) > MIN_DISTANCE) {
//                        if (deltaX < 0) {
//                            Toast.makeText(getContext(), "left2right swipe", Toast.LENGTH_SHORT).show();
//                            if (isMinSet) {
//                                setPagingEnabled(false);
//                            }
//                            return true;
//                        }
//
//                        if (deltaX > 0) {
//                            Toast.makeText(getContext(), "right2left swipe", Toast.LENGTH_SHORT).show();
//                            return true;
//                        }
//
//
//                    } else {
//                        // consider as something else - a screen tap for example
//                    }
//                }
//                break;
//        }
        return super.onTouchEvent(me);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent me) {
        if (!scrollable)
            return false;
        else
            return super.onInterceptTouchEvent(me);
    }



    public void selectOtherMonth(int offset) {
        setCurrentItem(currentPosition + offset);
        CalendarViewAdapter calendarViewAdapter = (CalendarViewAdapter) getAdapter();
        calendarViewAdapter.notifyDataChanged(CalendarViewAdapter.loadSelectedDate());
    }

    public int getPageScrollState() {
        return pageScrollState;
    }

    public interface OnPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    public int getTopMovableDistance() {
        CalendarViewAdapter calendarViewAdapter = (CalendarViewAdapter) getAdapter();
        if(calendarViewAdapter == null) {
            return cellHeight;
        }
        rowIndex = calendarViewAdapter.getPagers().get(currentPosition % 3).getSelectedRowIndex();
        return cellHeight * rowIndex;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setViewHeight(int viewHeight) {
        cellHeight = viewHeight / 6;
        this.viewHeight = viewHeight;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getRowIndex() {
        CalendarViewAdapter calendarViewAdapter = (CalendarViewAdapter) getAdapter();
        rowIndex = calendarViewAdapter.getPagers().get(currentPosition % 3).getSelectedRowIndex();
        Log.e("ldf", "getRowIndex = " + rowIndex);
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void closeLeftScroll(){
        setAllowedSwipeDirection(Utils.SwipeDirection.right);
    }

    public void closeRightScroll(){
        setAllowedSwipeDirection(Utils.SwipeDirection.left);
    }

    public void openScrolls(){
        setAllowedSwipeDirection(Utils.SwipeDirection.all);
    }

}