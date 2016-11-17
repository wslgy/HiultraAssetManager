package com.hiultra.assetManagerNeutral.ui.view.springViewPager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

public class ModelPagerAdapter extends FragmentStatePagerAdapter {
    protected PagerModelManager pagerModelManager;

    public ModelPagerAdapter(FragmentManager fm, PagerModelManager pagerModelManager) {
        super(fm);
        this.pagerModelManager = pagerModelManager;
    }

    public Fragment getItem(int position) {
        return this.pagerModelManager.getItem(position);
    }

    public int getCount() {
        return this.pagerModelManager.getFragmentCount();
    }

    public CharSequence getPageTitle(int position) {
        return this.pagerModelManager.hasTitles()?this.pagerModelManager.getTitle(position):super.getPageTitle(position);
    }
}
