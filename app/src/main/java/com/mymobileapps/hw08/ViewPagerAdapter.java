/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - ViewPagerAdapter.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */

package com.mymobileapps.hw08;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstFragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return lstFragment.get(i);
    }

    @Override
    public int getCount() {

        return lstFragmentTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstFragmentTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String stitle) {
        lstFragment.add(fragment);
        lstFragmentTitles.add(stitle);
    }
}
