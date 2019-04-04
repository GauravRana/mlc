package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mylocumchoice.MyLocumChoicePharmacy.utils.SmartFragmentStatePagerAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.BookFragOne;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.BookFragThree;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.BookFragTwo;


public class ImageViewPagerAdapter extends SmartFragmentStatePagerAdapter {
    private Context _context;
    public static int totalPage = 3;

    public ImageViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BookFragOne f1 = new BookFragOne();
                return f1;
            case 1:
                BookFragTwo f2 = new BookFragTwo();
                return f2;
            case 2:
                BookFragThree f3 = new BookFragThree();
                return f3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalPage;
    }
}