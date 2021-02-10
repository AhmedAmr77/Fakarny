package com.FakarnyAppForTripReminder.Fakarny.Demo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            case 3:
                return new FragmentFive();
            case 4:
                return new FragmentSix();
            case 5:
                return new FragmentSeven();
            case 6:
                return new FragmentEight();
            case 7:
                return new FragmentNine();

            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 8;
    }
}
