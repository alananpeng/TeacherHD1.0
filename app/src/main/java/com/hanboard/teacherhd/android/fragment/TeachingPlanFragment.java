package com.hanboard.teacherhd.android.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.config.Constants;

import java.util.ArrayList;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/30 0030 12:59
 */
public class TeachingPlanFragment extends BaseFragment implements OnTabSelectListener {

    private ArrayList<android.support.v4.app.Fragment> mFragments = new ArrayList<>();
    private String[] mTitles= Constants.COURSETITLES;

    private MyPagerAdapter mAdapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_teaching_plan, container, false);
    }
    @Override
    protected void initData() {
        for (String title :mTitles ) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }
        View decorView = getActivity().getWindow().getDecorView();
        ViewPager vp = (ViewPager) getRootView().findViewById(R.id.vp);
        mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /** tab固定宽度 */
        SlidingTabLayout tabLayout_2= (SlidingTabLayout)getRootView().findViewById(R.id.tl_2);
        tabLayout_2.setViewPager(vp);
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
