package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailAppliedFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailInviteFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailUpcmingFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.ListFragment;


public class ShiftDetailPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount=0;
    private String[] tabTitles = new String[]{"Upcoming", "Applied", "Invitations"};

    //Constructor to the class
    public ShiftDetailPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                ShiftDetailUpcmingFragment tab1 = new ShiftDetailUpcmingFragment();
                return tab1;
            case 1:
                ShiftDetailAppliedFragment tab2 = new ShiftDetailAppliedFragment();
                return tab2;
            case 2:
                ShiftDetailInviteFragment tab3 = new ShiftDetailInviteFragment();
                return tab3;
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
