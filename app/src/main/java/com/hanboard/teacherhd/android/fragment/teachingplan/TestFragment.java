package com.hanboard.teacherhd.android.fragment.teachingplan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.adapter.SearchListAdapter;
import com.hanboard.teacherhd.android.adapter.TextBookAllChapterAdapter;
import com.hanboard.teacherhd.android.adapter.TreeAdapter;
import com.hanboard.teacherhd.android.adapter.TreeListViewAdapter;
import com.hanboard.teacherhd.android.entity.Chapter;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.Elements;
import com.hanboard.teacherhd.android.entity.listentity.LessonsList;
import com.hanboard.teacherhd.android.entity.listentity.ListChapter;
import com.hanboard.teacherhd.android.model.IPrepareLessonsModel;
import com.hanboard.teacherhd.android.model.impl.PrepareLessonsModelImpl;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.view.ClearEditText;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/6.
 */
public class TestFragment extends BaseFragment implements TextWatcher,IDataCallback<Domine> {
    private String textBookId;
    @BindView(R.id.fragment_prepare_lv_chapterList)
    ListView mLvChapterList;
    @BindView(R.id.edt_loging_username)
    ClearEditText mEdtLogingUsername;
    @BindView(R.id.prepare_lessons_seachlist)
    ListView mPrepareLessonsSeachlist;
    private IPrepareLessonsModel iPrepareLessonsModel;
    private List<Chapter> mDatas = new ArrayList<Chapter>();
    private TreeListViewAdapter mAdapter;
    private TextBookAllChapterAdapter mTreeAdapter;
    private SearchListAdapter mSearchListAdapter;
    private static int START_SEARCH = 2;
    private static int CLOSE_SEARCH = 1;
    private static int OPEN_SEARCH = 0;
    private int flag = 0;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            mSearchListAdapter.notifyDataSetChanged();
            return false;
        }
    });



    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        EventBus.getDefault().register(this);
        iPrepareLessonsModel = new PrepareLessonsModelImpl();
        return inflater.inflate(R.layout.fragment_prepare_lessons, container, false);
    }

    @Override
    protected void initData() {
        iPrepareLessonsModel.getChapterList("2","087ff757ca46407e9e01062d3233f8b4","","",this);
        initSearchListView();
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void getData(String textBookId){
      this.textBookId = textBookId;
        ToastUtils.showShort(context,textBookId);
    }

    private void initSearchListView() {
        List<String> content = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            content.add("搜索内容" + j);
        }
        mSearchListAdapter = new SearchListAdapter(context, R.layout.item_seacher, content);
        mPrepareLessonsSeachlist.setAdapter(mSearchListAdapter);
    }
    @OnClick(R.id.fragment_prepare_search)
    public void onClick() {
        if (flag == 0) {
            mEdtLogingUsername.setVisibility(View.VISIBLE);
            mPrepareLessonsSeachlist.setVisibility(View.VISIBLE);
            flag = 1;
        } else if (flag == 1) {
            mEdtLogingUsername.setVisibility(View.VISIBLE);
            mEdtLogingUsername.setText("");
            ToastUtils.showShort(getContext(), "开始搜索内容!");
            flag = 2;
        } else if (flag == 2) {
            mEdtLogingUsername.setVisibility(View.GONE);

            mPrepareLessonsSeachlist.setVisibility(View.GONE);
            flag = 0;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onSuccess(Domine data) {
        if (data instanceof ListChapter){
            try {
                mAdapter = new TextBookAllChapterAdapter<Chapter>(mLvChapterList,context,((ListChapter) data).chapters, 0);
                mLvChapterList.setAdapter(mAdapter);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String msg, int code) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
