package com.mylocumchoice.MyLocumChoicePharmacy.view.signup.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mylocumchoice.MyLocumChoicePharmacy.utils.SmartFragmentStatePagerAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour1;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour2;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour3;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour4;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour5;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour6;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour7;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour8;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.fragment.FragmentTour9;

public class TourAdapter extends SmartFragmentStatePagerAdapter {

    public int totalPage = 9;
    private Context mContext;

    public TourAdapter(Context mContext,FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext=mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentTour1 f1 = new FragmentTour1();
                return f1;
            case 1:
                FragmentTour2 f2 = new FragmentTour2();
                return f2;
            case 2:
                FragmentTour3 f3 = new FragmentTour3();
                return f3;
            case 3:
                FragmentTour4 f4 = new FragmentTour4();
                return f4;
            case 4:
                FragmentTour5 f5 = new FragmentTour5();
                return f5;
            case 5:
                FragmentTour6 f6 = new FragmentTour6();
                return f6;
            case 6:
                FragmentTour7 f7 = new FragmentTour7();
                return f7;
            case 7:
                FragmentTour8 f8 = new FragmentTour8();
                return f8;
            case 8:
                FragmentTour9 f9 = new FragmentTour9();
                return f9;

        }
        return null;
    }

    @Override
    public int getCount() {
        return totalPage;
    }
}
