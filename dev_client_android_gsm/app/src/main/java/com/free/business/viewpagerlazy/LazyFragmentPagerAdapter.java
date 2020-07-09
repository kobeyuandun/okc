package com.free.business.viewpagerlazy;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author yuandunbin
 * @date 2020/7/8
 */
class LazyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public LazyFragmentPagerAdapter(@NonNull FragmentManager fm,List<Fragment> flist) {
        super(fm);
        fragmentList = flist;
    }

    public LazyFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior,List<Fragment> flist) {
        super(fm, behavior);
        fragmentList = flist;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
