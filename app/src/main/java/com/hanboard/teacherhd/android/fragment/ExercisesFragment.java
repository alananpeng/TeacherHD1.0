package com.hanboard.teacherhd.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.adapter.CursorGridViewAdapter;
import com.hanboard.teacherhd.android.entity.CourseWare;
import com.hanboard.teacherhd.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/30 0030 13:00
 */
public class ExercisesFragment extends BaseFragment {

    @BindView(R.id.exercise_gv_cursor)
    GridView exerciseGvCursor;
    private List<CourseWare> curseType;
    private CursorGridViewAdapter adapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    protected void initData() {
        curseType = new ArrayList<CourseWare>();
        curseType.add(new CourseWare("asdffda1.mp3","",".mp3",""));
        curseType.add(new CourseWare("fadsasdf2.mp4","",".mp4",""));
        curseType.add(new CourseWare("asdfasdf3.doc","",".doc",""));
        curseType.add(new CourseWare("adsfadsf4.xls","",".xls",""));
        curseType.add(new CourseWare("adfdgsdfggfh5.ppt","","ppt",""));
        curseType.add(new CourseWare("dfghdfghh6.pdf","","pdf",""));
        adapter=new CursorGridViewAdapter(getContext(), R.layout.item_cursorfragment,curseType);
        exerciseGvCursor.setAdapter(adapter);
        exerciseGvCursor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "点击了"+i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
