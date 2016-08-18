package com.hanboard.teacherhd.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.activity.ClassActivity;
import com.hanboard.teacherhd.android.entity.LessonPlan;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import name.quanke.app.libs.emptylayout.EmptyLayout;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/30 0030 12:59
 */
public class TeachingPlanFragment extends BaseFragment implements OnTabSelectListener {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = SimpleCardFragment.COURSETITLES;
    private MyPagerAdapter mAdapter;
    private LessonPlan mPlans;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        // EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_teaching_plan, container, false);
        return view;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mPlans = (LessonPlan) bundle.getSerializable(ClassActivity.TEACHINGPLAN);
        if (mPlans != null) {
            for (String title : mTitles) {
                mFragments.add(SimpleCardFragment.getInstance(title, mPlans));
            }
            ViewPager vp = (ViewPager) getRootView().findViewById(R.id.vp);
            mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
            vp.setAdapter(mAdapter);
            /** tab固定宽度 */
            SlidingTabLayout tabLayout_2 = (SlidingTabLayout) getRootView().findViewById(R.id.tl_2);
            tabLayout_2.setViewPager(vp);
        } else{
            ToastUtils.showShort(context, "没有备课");
        }

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
        public Fragment getItem(int position) {
            return mFragments.get(position);

        }
    }
}
