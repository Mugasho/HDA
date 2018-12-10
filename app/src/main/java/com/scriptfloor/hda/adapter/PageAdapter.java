package com.scriptfloor.hda.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.scriptfloor.hda.fragment.DrugsFragment;
import com.scriptfloor.hda.fragment.FacilityFragment;
import com.scriptfloor.hda.fragment.VerifyFragment;

/**
 * Created by Lincoln on 8/3/2018.
 */

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DrugsFragment drugsFragment=new DrugsFragment();
                return drugsFragment;
            case 1:
                VerifyFragment verifyFragment =new VerifyFragment();
                return verifyFragment;
            case 2:
                FacilityFragment facilityFragment=new FacilityFragment();
                return facilityFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
