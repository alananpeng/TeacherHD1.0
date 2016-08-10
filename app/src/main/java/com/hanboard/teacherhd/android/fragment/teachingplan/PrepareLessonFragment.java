package com.hanboard.teacherhd.android.fragment.teachingplan;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.adapter.SearchListAdapter;
import com.hanboard.teacherhd.android.fragment.PrepareLessonsDetailFragment;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.view.ClearEditText;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/5.
 */
public class PrepareLessonFragment extends BaseFragment implements TextWatcher {
    @BindView(R.id.edt_loging_username)
    ClearEditText edtLogingUsername;
    @BindView(R.id.preppare_frag_left)
    LinearLayout preppareFragLeft;
    @BindView(R.id.preppare_frag_right)
    LinearLayout preppareFragRight;
    @BindView(R.id.prepare_lessons_seachlist)
    ListView mPrepareLessonsSeachlist;
    private FragmentManager mamager;
    private static int START_SEARCH = 2;
    private static int CLOSE_SEARCH = 1;
    private static int OPEN_SEARCH = 0;
    private int flag = 0;
    private SearchListAdapter mSearchListAdapter;
   /* private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            mSearchListAdapter.notifyDataSetChanged();
            return false;
        }
    });*/
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_prepare_lessons, container, false);
    }
    @Override
    protected void initData() {
        /* initLeftAndRightFragment();
        mamager = getChildFragmentManager();
        List<String> content = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            content.add("搜索内容" + j);
        }
        mSearchListAdapter = new SearchListAdapter(context, R.layout.item_seacher, content);
        mPrepareLessonsSeachlist.setAdapter(mSearchListAdapter);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initLeftAndRightFragment() {
       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
        FragmentTransaction fragmentTransaction = mamager.beginTransaction();
       // fragmentTransaction.replace(R.id.preppare_frag_left, new PrepareLessonsListFragment());
        fragmentTransaction.replace(R.id.preppare_frag_right, new PrepareLessonsDetailFragment());
        fragmentTransaction.commit();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //handler.sendEmptyMessage(0);

    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

    @OnClick(R.id.fragment_prepare_search)
    public void onClick() {
        if (flag==0){
            edtLogingUsername.setVisibility(View.VISIBLE);
            mPrepareLessonsSeachlist.setVisibility(View.VISIBLE);
            flag=1;
        }


        else if (flag==1){
            edtLogingUsername.setVisibility(View.VISIBLE);
            edtLogingUsername.setText("");
            ToastUtils.showShort(getContext(),"开始搜索内容!");
            flag=2;
        }
        else if (flag==2){
            edtLogingUsername.setVisibility(View.GONE);

            mPrepareLessonsSeachlist.setVisibility(View.GONE);
            flag=0;
        }

    }
}



