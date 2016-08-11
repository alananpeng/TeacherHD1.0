package com.hanboard.teacherhd.android.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.LessonPlan;
import com.hanboard.teacherhd.config.Constants;


@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private String mTitle;
    private TextView mTextView;
    private LessonPlan lessonPlan;
    public static final  String  TEACHING_T="教学目标";
    public static final  String  TEACHING_I="教学重点";
    public static final  String TEACHING_P="教学准备";
    public static final  String TEACHING_G="教学过程";
    public static final  String TEACHING_H="作业布置";
    public static  final String[] COURSETITLES = {TEACHING_T,TEACHING_I,TEACHING_P,TEACHING_G,TEACHING_H };
    public static SimpleCardFragment getInstance(String title, LessonPlan plans) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        sf.lessonPlan=plans;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        mTextView = (TextView) v.findViewById(R.id.card_title_tv);
        if (mTitle.equals(TEACHING_T))
            mTextView.setText(lessonPlan.lessonPlanGoal);
        else if (mTitle.equals(TEACHING_I))
            mTextView.setText(lessonPlan.lessonPlanKeyPoint);
        else if (mTitle.equals(TEACHING_P))
            mTextView.setText(lessonPlan.lessonPlanPrepare);
        else if (mTitle.equals(TEACHING_G))
            mTextView.setText(lessonPlan.lessonPlanProcess);
        else if (mTitle.equals(TEACHING_H))
            mTextView.setText(lessonPlan.lessonPlanWord);
        return v;
    }


}