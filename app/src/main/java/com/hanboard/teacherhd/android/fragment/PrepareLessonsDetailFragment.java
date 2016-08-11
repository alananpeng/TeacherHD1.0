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
import com.hanboard.teacherhd.android.entity.Chapter;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.Elements;
import com.hanboard.teacherhd.android.entity.PrepareChapter;
import com.hanboard.teacherhd.android.eventBusBean.BookAndChapterId;
import com.hanboard.teacherhd.android.fragment.teachingplan.TestFragment;
import com.hanboard.teacherhd.android.model.IPrepareLessonsModel;
import com.hanboard.teacherhd.android.model.impl.PrepareLessonsModelImpl;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.callback.IDataCallback;
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
public class PrepareLessonsDetailFragment extends BaseFragment implements IDataCallback<Domine> {
    @BindView(R.id.gd_prepare_lessons)
    GridView mGdPrepareLessons;
    private Elements<PrepareChapter> mElements;
    private IPrepareLessonsModel iPrepareLessonsModel;
    public static  String CONTENTID="contentId";
    private PrepareLessonsDetailGirdViewAdapter mAdapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        iPrepareLessonsModel = new PrepareLessonsModelImpl();
        return inflater.inflate(R.layout.fragment_prepare_lessons_detail, container, false);
    }
    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doResultInt(BookAndChapterId ids) {
            initDatas(ids);
            onStart();
    }
    private void initDatas(BookAndChapterId id) {
        showProgress("正在加载中....");
        iPrepareLessonsModel.getChapterDetials((String)SharedPreferencesUtils.getParam(context,"id","null"),id.getChapterId(),id.getTextBookId(),"1",this);
        mAdapter = new PrepareLessonsDetailGirdViewAdapter(context, R.layout.new_lessons_item, mElements.elements);
        mGdPrepareLessons.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mGdPrepareLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, ClassActivity.class);
                String contentId = mElements.elements.get(i).getContentId();
                intent.putExtra(CONTENTID,contentId);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onSuccess(Domine data) {
         disProgress();
        if (data instanceof Elements) {
            mElements = (Elements<PrepareChapter>) data;
        }
    }
    @Override
    public void onError(String msg, int code) {
        disProgress();
        ToastUtils.showShort(context,msg+code);
    }
}
