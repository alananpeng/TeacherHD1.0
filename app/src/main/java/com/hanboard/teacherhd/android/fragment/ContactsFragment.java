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
import name.quanke.app.libs.emptylayout.EmptyLayout;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/27 0027 12:56
 */
public class ContactsFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        EmptyLayout e = new EmptyLayout(context);
        e.showError(R.mipmap.wawa_03,"此功能待开放");

        return e;
    }
    @Override
    protected void initData() {

    }
}
