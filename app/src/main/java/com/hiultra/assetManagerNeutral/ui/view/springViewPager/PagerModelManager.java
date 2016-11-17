package com.hiultra.assetManagerNeutral.ui.view.springViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;


public class PagerModelManager {
    public static final String DATA = "data";
    private List<CharSequence> titleList = new ArrayList();
    private List<Fragment> fragmentList = new ArrayList();
    
    public PagerModelManager() {}
    
    public Fragment getItem(int position) {
        return (Fragment) this.fragmentList.get(position);
    }
    
    public int getFragmentCount() {
        return this.fragmentList.size();
    }
    
    public boolean hasTitles() {
        return this.titleList.size() != 0;
    }
    
    public CharSequence getTitle(int position) {
        return (CharSequence) this.titleList.get(position);
    }
    
    public PagerModelManager addFragment(Fragment fragment, CharSequence title) {
        this.titleList.add(title);
        this.addFragment(fragment);
        return this;
    }
    
    public PagerModelManager addFragment(Fragment fragment) {
        this.fragmentList.add(fragment);
        return this;
    }
    
    public PagerModelManager addCommonFragment(Class<?> c, List<? extends Serializable> list, List<String> titleList) {
        this.titleList.addAll(titleList);
        this.addCommonFragment(c, list);
        return this;
    }
    
    public PagerModelManager addCommonFragment(Class<?> c, List<? extends Serializable> list) {
        try {
            for (int e = 0; e < list.size(); ++e) {
                Fragment fragment = (Fragment) c.newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) list.get(e));
                fragment.setArguments(bundle);
                this.fragmentList.add(fragment);
            }
        } catch (InstantiationException var6) {
            var6.printStackTrace();
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
        }
        
        return this;
    }
    
    public PagerModelManager addCommonFragment(List<? extends Fragment> list) {
        for (Fragment fragment : list) {
            if (!this.fragmentList.contains(fragment)) this.fragmentList.addAll(list);
        }
        return this;
    }
    
    public PagerModelManager addCommonFragment(List<? extends Fragment> list, List<String> titleList) {
        for (String s : titleList) {
            if (!this.titleList.contains(s)) this.titleList.addAll(titleList);
        }
        this.addCommonFragment(list);
        return this;
    }
}