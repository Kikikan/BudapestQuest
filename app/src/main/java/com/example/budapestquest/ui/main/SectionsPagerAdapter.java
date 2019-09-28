package com.example.budapestquest.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;
import com.example.budapestquest.TabStats;
import com.example.budapestquest.TabInventory;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static final int TAB_STATS = 0;
    public static final int TAB_INVENTORY = 1;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    private final TabStats tabStats;
    private final TabInventory tabInventory;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        GameController.tabStats = tabStats = new TabStats();
        GameController.tabInventory = tabInventory = new TabInventory();
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        Fragment fragment = null;
        switch (position) {
            case TAB_STATS:
                fragment = tabStats;
                break;
            case TAB_INVENTORY:
                fragment = tabInventory;
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}