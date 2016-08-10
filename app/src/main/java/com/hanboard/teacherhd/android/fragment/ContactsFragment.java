package com.hanboard.teacherhd.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.adapter.PrepareLessonsDetailGirdViewAdapter;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.lib.ui.romainpiel.titanic.library.Titanic;
import com.hanboard.teacherhd.lib.ui.romainpiel.titanic.library.TitanicTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/27 0027 12:56
 */
public class ContactsFragment extends BaseFragment {
    @BindView(R.id.my_text_view)
    TitanicTextView myTextView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    protected void initData() {
//      EmptyLayout f = (EmptyLayout) getRootView().findViewById(R.id.emptyLayout);
//      f.showEmpty();
        new Titanic().start(myTextView);
    }
}
