package com.example.tosik.live_information_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;

public class CustomPageAdapter extends FragmentPagerAdapter {
    private final SparseArray<Fragment> cachedFragments;
    private FragmentManager m;

    public CustomPageAdapter(FragmentManager m){
        super(m);
        this.m =  m;
        cachedFragments = new SparseArray<Fragment>();
    }


    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new BackgroundFragment();
                Log.d("CustomPageAdapter","Fragmentセット："+position);
                cachedFragments.put(position, fragment);
                break;
            default:
                fragment = new TextColorFragment();
                Log.d("CustomPageAdapter","Fragmentセット："+position);
                cachedFragments.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount(){
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "背景設定";
            case 1:
                return "文字色設定";
        }
        return null;
    }

    public Fragment getCachedFragmentAt(int position) {
        return cachedFragments.get(position);
    }

    public FragmentManager getManager(){
        return m;
    }
}
