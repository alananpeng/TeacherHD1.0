package com.hanboard.teacherhd.android.fragment;

import android.os.Bundle;
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
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private String[] mTitles=SimpleCardFragment.COURSETITLES;

    private MyPagerAdapter mAdapter;
    private  LessonPlan mPlans;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
       // EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_teaching_plan, container, false);
    }
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mPlans= (LessonPlan) bundle.getSerializable(ClassActivity.TEACHINGPLAN);
        if (mPlans != null) {
            ToastUtils.successful(context);
        }
        for (String title :mTitles ) {
            mFragments.add(SimpleCardFragment.getInstance(title,mPlans));
        }
        View decorView = getActivity().getWindow().getDecorView();
        ViewPager vp = (ViewPager) getRootView().findViewById(R.id.vp);
        mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /** tab固定宽度 */
        SlidingTabLayout tabLayout_2= (SlidingTabLayout)getRootView().findViewById(R.id.tl_2);
        tabLayout_2.setViewPager(vp);
    }
    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void doResult(LessonPlan plans){
        mPlans=plans;
        ToastUtils.showShort(context,"获取数据成功");
        ToastUtils.successful(context);
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
       // EventBus.getDefault().unregister(this);
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
