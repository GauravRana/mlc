package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mylocumchoice.MyLocumChoicePharmacy.utils.SmartFragmentStatePagerAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.CancelledBookHistoryFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.CompleteBookHistoryFragment;


public class BookingHistoryPager extends SmartFragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    private String[] tabTitles = new String[]{"Completed", "Cancelled"};

    //Constructor to the class
    public BookingHistoryPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                CompleteBookHistoryFragment tab1 = new CompleteBookHistoryFragment();
                return tab1;
            case 1:
                CancelledBookHistoryFragment tab2 = new CancelledBookHistoryFragment();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}