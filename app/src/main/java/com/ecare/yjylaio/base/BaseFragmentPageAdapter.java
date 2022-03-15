package com.ecare.yjylaio.base;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.base
 * ClassName: BaseFragmentPageAdapter
 * Description:
 * Author:
 * CreateDate: 2021/6/18 10:42
 * Version: 1.0
 */
public class BaseFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private CharSequence[] mTitles;

    public BaseFragmentPageAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.mList = list;
    }

    public BaseFragmentPageAdapter(FragmentManager fragmentManager, List<Fragment> list, CharSequence[] titles) {
        super(fragmentManager);
        this.mList = list;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && position < mTitles.length) {
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
