package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.HistoryFilterFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.HistorySortFragment;

public class HistoryPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public HistoryPager(FragmentManager fm, int tabCount) {
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
                HistoryFilterFragment tab1 = new HistoryFilterFragment();
                return tab1;
            case 1:
                HistorySortFragment tab2 = new HistorySortFragment();
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
}
