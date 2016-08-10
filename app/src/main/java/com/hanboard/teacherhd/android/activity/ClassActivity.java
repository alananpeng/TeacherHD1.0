package com.hanboard.teacherhd.android.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.fragment.CourseWareFragment;
import com.hanboard.teacherhd.android.fragment.ExercisesFragment;
import com.hanboard.teacherhd.android.fragment.TeachingPlanFragment;
import com.hanboard.teacherhd.common.base.BaseActivity;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassActivity extends BaseActivity {
    @BindView(R.id.img_class_courseware)
    ImageView mImgClassCourseware;
    @BindView(R.id.img_class_grammar)
    ImageView mImgClassGrammar;
    @BindView(R.id.img_class_exercises)
    ImageView mImgClassExercises;

    @BindView(R.id.txt_class_courseware)
    TextView mTextClassCourseware;
    @BindView(R.id.txt_class_exercises)
    TextView mTextClassExercises;
    @BindView(R.id.txt_class_grammar)
    TextView mTextClassGrammar;

    @BindView(R.id.view_class_grammar)
    View mViewClassGrammar;
    @BindView(R.id.view_class_courseware)
    View mViewClassCourseware;
    @BindView(R.id.view_class_exercises)
    View mViewClassExercises;

    @BindColor(R.color.theme_color) int mThemeColoc;
    @BindColor(R.color.white) int mWhiteColoc;


    private TeachingPlanFragment mTeachingPlanFragment;
    private ExercisesFragment mExercisesFragment;
    private CourseWareFragment mCourseWareFragment;
    private Fragment currentFragment;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_class);
        ButterKnife.bind(this);
        initTab();
    }

    /*设置默认Fragment*/
    private void initTab() {
        if (mTeachingPlanFragment == null) {
            mTeachingPlanFragment = new TeachingPlanFragment();
        }

        if (!mTeachingPlanFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.lnl_class_content,mTeachingPlanFragment).commit();
            currentFragment = mTeachingPlanFragment;
            reset();
            //变化指示器
            mViewClassGrammar.setBackgroundResource(R.mipmap.left_on_black_02);
            mTextClassGrammar.setTextColor(mThemeColoc);
            mImgClassGrammar.setBackgroundResource(R.mipmap.home_on);


        }
    }

    @Override
    protected void handler(Message msg) {

    }

    @OnClick({R.id.class_grammar, R.id.class_course, R.id.class_exercises})
    void onClick(View v){
        reset();
        switch (v.getId()){

            case R.id.class_grammar:
                clickTeachingPlanLayout();
                break;
            case R.id.class_course:
                clickCoursewareLayout();
                break;
            case R.id.class_exercises:
                clickExercisesLayout();
                break;
            default:
                break;
        }
    }

    private void reset() {

        mViewClassGrammar.setBackgroundResource(R.mipmap.left_02);
        mTextClassGrammar.setTextColor(mWhiteColoc);
        mImgClassGrammar.setBackgroundResource(R.mipmap.home);

        mViewClassCourseware.setBackgroundResource(R.mipmap.left_02);
        mTextClassCourseware.setTextColor(mWhiteColoc);
        mImgClassCourseware.setBackgroundResource(R.mipmap.kejian_06);

        mViewClassExercises.setBackgroundResource(R.mipmap.left_02);
        mTextClassExercises.setTextColor(mWhiteColoc);
        mImgClassExercises.setBackgroundResource(R.mipmap.xiti_06);

    }

    private void clickTeachingPlanLayout() {
        // TODO Auto-generated method stub
        if (mTeachingPlanFragment == null) {
            mTeachingPlanFragment = new TeachingPlanFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mTeachingPlanFragment);
        //变化指示器
        mViewClassGrammar.setBackgroundResource(R.mipmap.left_on_black_02);
        mTextClassGrammar.setTextColor(mThemeColoc);
        mImgClassGrammar.setBackgroundResource(R.mipmap.home_on);
    }

    private void clickCoursewareLayout() {
        // TODO Auto-generated method stub
        if (mCourseWareFragment == null) {
            mCourseWareFragment = new CourseWareFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mCourseWareFragment);
        mViewClassCourseware.setBackgroundResource(R.mipmap.left_on_black_02);
        mTextClassCourseware.setTextColor(mThemeColoc);
        mImgClassCourseware.setBackgroundResource(R.mipmap.kejian_on_06);
    }

    private void clickExercisesLayout() {
        // TODO Auto-generated method stub
        if (mExercisesFragment == null) {
            mExercisesFragment = new ExercisesFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mExercisesFragment);
        mViewClassExercises.setBackgroundResource(R.mipmap.left_on_black_02);
        mTextClassExercises.setTextColor(mThemeColoc);
        mImgClassExercises.setBackgroundResource(R.mipmap.xiti_on_06);
    }



    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.lnl_class_content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }
    public void back(View view){
        finish();
    }
}
