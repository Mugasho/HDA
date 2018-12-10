package com.scriptfloor.hda.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.scriptfloor.hda.fragment.AboutFragment;
import com.scriptfloor.hda.fragment.HWFragment;
import com.scriptfloor.hda.fragment.RegistrationFragment;

/**
 * Created by LINCOLN on 10/7/2018.
 */

public class FacilityPageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public FacilityPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RegistrationFragment registrationFragment=new RegistrationFragment();
                return registrationFragment;
            case 1:
                AboutFragment aboutFragment=new AboutFragment();
                return aboutFragment;
            case 2:
                HWFragment hwFragment=new HWFragment();
                return hwFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
