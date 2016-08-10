package com.hanboard.teacherhd.android.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.fragment.CourseWareFragment;
import com.hanboard.teacherhd.android.fragment.ExercisesFragment;
import com.hanboard.teacherhd.android.fragment.TeachingPlanFragment;
import com.hanboard.teacherhd.android.fragment.preparelessons.AddCoursewareFragment;
import com.hanboard.teacherhd.android.fragment.preparelessons.AddExercisesFragment;
import com.hanboard.teacherhd.android.fragment.preparelessons.AddTeachingPlanFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPrepareLessonsActivity extends FragmentActivity {

    @BindView(R.id.add_lessons_teachingplan)
    TextView addLessonsTeachingplan;
    @BindView(R.id.add_lessons_courseware)
    TextView addLessonsCourseware;
    @BindView(R.id.add_lessons_exercises)
    TextView addLessonsExercises;

    private int textSizeOn = 24;
    private int textSize = 18;

    private AddCoursewareFragment mAddCoursewareFragment;
    private AddExercisesFragment mAddExercisesFragment;
    private AddTeachingPlanFragment mAddTeachingPlanFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prepare_lessons);
        ButterKnife.bind(this);
        initDisplay();
        initTab();
    }

    /*设置默认Fragment*/
    private void initTab() {
        if (mAddTeachingPlanFragment == null) {
            mAddTeachingPlanFragment = new AddTeachingPlanFragment();
        }
        if (!mAddTeachingPlanFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.add_lessons_content,mAddTeachingPlanFragment).commit();
            currentFragment = mAddTeachingPlanFragment;
            reset();
            addLessonsTeachingplan.setTextColor(Color.parseColor("#00bfff"));
            addLessonsTeachingplan.setTextSize(textSizeOn);
        }
    }

    @OnClick({R.id.add_lessons_teachingplan, R.id.add_lessons_courseware, R.id.add_lessons_exercises})
    void onClick(View v){
        reset();
        switch (v.getId()){

            case R.id.add_lessons_teachingplan:
                clickTeachingPlan();
                break;
            case R.id.add_lessons_courseware:
                clickCourseware();
                break;
            case R.id.add_lessons_exercises:
                clickExercises();
                break;
            default:
                break;
        }
    }


    private void clickTeachingPlan() {
        // TODO Auto-generated method stub
        if (mAddTeachingPlanFragment == null) {
            mAddTeachingPlanFragment = new AddTeachingPlanFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mAddTeachingPlanFragment);
        //变化指示器
        addLessonsTeachingplan.setTextColor(Color.parseColor("#00bfff"));
        addLessonsTeachingplan.setTextSize(textSizeOn);
    }

    private void clickExercises() {
        // TODO Auto-generated method stub
        if (mAddExercisesFragment == null) {
            mAddExercisesFragment = new AddExercisesFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mAddExercisesFragment);
        //变化指示器
        addLessonsExercises.setTextColor(Color.parseColor("#00bfff"));
        addLessonsExercises.setTextSize(textSizeOn);
    }

    private void clickCourseware() {
        // TODO Auto-generated method stub
        if (mAddCoursewareFragment == null) {
            mAddCoursewareFragment = new AddCoursewareFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mAddCoursewareFragment);
        //变化指示器
        addLessonsCourseware.setTextColor(Color.parseColor("#00bfff"));
        addLessonsCourseware.setTextSize(textSizeOn);
    }


    private void reset() {
        addLessonsTeachingplan.setTextColor(Color.parseColor("#ffffff"));
        addLessonsTeachingplan.setTextSize(textSize);
        addLessonsCourseware.setTextColor(Color.parseColor("#ffffff"));
        addLessonsCourseware.setTextSize(textSize);
        addLessonsExercises.setTextColor(Color.parseColor("#ffffff"));
        addLessonsExercises.setTextSize(textSize);
    }

    /*初始化屏幕*/
    private void initDisplay() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.9);
        p.width = (int) (d.getWidth() * 0.9);
        getWindow().setAttributes(p);
    }

    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.add_lessons_content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }
}
