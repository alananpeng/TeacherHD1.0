package com.hanboard.teacherhd.android.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.activity.ClassActivity;
import com.hanboard.teacherhd.android.adapter.PrepareLessonsDetailGirdViewAdapter;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;
import com.hanboard.teacherhd.manager.AppContext;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/22 0022 10:45
 */
public class PrepareLessonsDetailFragment extends BaseFragment {
    @BindView(R.id.gd_prepare_lessons)
    GridView mGdPrepareLessons;
    private String mCoursorId=null;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_prepare_lessons_detail, container, false);
    }
    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        List<Content> list = new ArrayList<>();
        Content c = new Content();

        list.add(c);
        list.add(c);
        list.add(c);
        list.add(c);
        list.add(c);
        list.add(c);
        list.add(c);
        list.add(c);
        list.add(c);
        mGdPrepareLessons.setAdapter(new PrepareLessonsDetailGirdViewAdapter(context, R.layout.prepare_lessons_detail_item, list));
        mGdPrepareLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(context, ClassActivity.class);
                intent.putExtra(Constants.USER_ID,(String) SharedPreferencesUtils.getParam(AppContext.getInstance(),"id","null"));
                startActivity(intent);
            }
        });

    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void doResult(String mCoursorId){
        ToastUtils.showShort(context,mCoursorId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
