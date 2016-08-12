package com.hanboard.teacherhd.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.broadCast.NetBrodeCaset;
import com.hanboard.teacherhd.android.model.InetEventHandler;
import com.hanboard.teacherhd.android.fragment.ContactsFragment;
import com.hanboard.teacherhd.android.fragment.CourseWareFragment;
import com.hanboard.teacherhd.android.fragment.HomeFragment;
import com.hanboard.teacherhd.common.base.BaseActivity;
import com.hanboard.teacherhd.lib.common.utils.NetUtil;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.picasso.Picasso;
import name.quanke.app.libs.emptylayout.EmptyLayout;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/25 0025 15:22
 */
public class MainActivity extends BaseActivity  implements InetEventHandler{
    @BindView(R.id.view_main_home)
    View mViewMainHome;
    @BindView(R.id.view_main_contacts)
    View mViewMainContacts;
    @BindView(R.id.view_main_courseware)
    View mViewMainCourseWare;
    @BindView(R.id.img_main_home)
    ImageView mImgMainHome;
    @BindView(R.id.txt_main_home)
    TextView mTxtMainHome;
    @BindView(R.id.img_main_contacts)
    ImageView mImgMainContacts;
    @BindView(R.id.txt_main_contacts)
    TextView mTxtMainContacts;
    @BindView(R.id.img_main_courseware)
    ImageView mImgMainCourseware;
    @BindView(R.id.txt_main_courseware)
    TextView mTxtMainCourseware;

    @BindColor(R.color.theme_color)
    int mThemeColoc;
    @BindColor(R.color.white)
    int mWhiteColoc;
    @BindView(R.id.imageView3)
    ImageView userIcon;
    @BindView(R.id.lnl_main_contacts)
    LinearLayout lnlMainContacts;
    @BindView(R.id.lnl_main_courseware)
    LinearLayout lnlMainCourseware;
    @BindView(R.id.lnl_main_content)
    LinearLayout lnlMainContent;

    private HomeFragment mHomeFragment;
    private ContactsFragment mContactsFragment;
    private CourseWareFragment mCourseWareFragment;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setDefaultFragment();
        initData();
    }

    private void initData() {
        Picasso.with(this).load((String) SharedPreferencesUtils.getParam(this, "avatarUrl", "")).into(userIcon);
        //initBroadCast();
        Log.i("MainActivity", "========================initData: "+NetUtil.getNetworkState(this));
        NetBrodeCaset.mListeners.add(this);

    }

    /*设置默认Fragment*/
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mHomeFragment = new HomeFragment();
        transaction.replace(R.id.lnl_main_content, mHomeFragment);
        //变化指示器
        mViewMainHome.setBackgroundResource(R.mipmap.left_on_black_02);
        mTxtMainHome.setTextColor(mThemeColoc);
        mImgMainHome.setBackgroundResource(R.mipmap.home_on);
        transaction.commit();
    }

    @OnClick({R.id.lnl_main_home, R.id.lnl_main_contacts, R.id.lnl_main_courseware})
    void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        //重置
        reset();
        switch (v.getId()) {
            case R.id.lnl_main_home:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.lnl_main_content, mHomeFragment);
                //变化指示器
                mViewMainHome.setBackgroundResource(R.mipmap.left_on_black_02);
                mTxtMainHome.setTextColor(mThemeColoc);
                mImgMainHome.setBackgroundResource(R.mipmap.home_on);
                break;
            case R.id.lnl_main_contacts:
                if (mContactsFragment == null) {
                    mContactsFragment = new ContactsFragment();
                }
                transaction.replace(R.id.lnl_main_content, mContactsFragment);
                //变化指示器
                mViewMainContacts.setBackgroundResource(R.mipmap.left_on_black_02);
                mTxtMainContacts.setTextColor(mThemeColoc);
                mImgMainContacts.setBackgroundResource(R.mipmap.contacts_on);
                break;
            case R.id.lnl_main_courseware:
                if (mCourseWareFragment == null) {
                    mCourseWareFragment = new CourseWareFragment();
                }
                transaction.replace(R.id.lnl_main_content, mCourseWareFragment);
                //变化指示器
                mViewMainCourseWare.setBackgroundResource(R.mipmap.left_on_black_02);
                mTxtMainCourseware.setTextColor(mThemeColoc);
                mImgMainCourseware.setBackgroundResource(R.mipmap.courseware_on);
                break;

        }
        // transaction.addToBackStack();
        // 事务提交
        transaction.commit();
    }

    /*重置选中项*/
    private void reset() {
        mViewMainHome.setBackgroundResource(R.mipmap.left_02);
        mTxtMainHome.setTextColor(mWhiteColoc);
        mImgMainHome.setBackgroundResource(R.mipmap.home);
        /**/
        mViewMainContacts.setBackgroundResource(R.mipmap.left_02);
        mTxtMainContacts.setTextColor(mWhiteColoc);
        mImgMainContacts.setBackgroundResource(R.mipmap.contacts);
        /**/
        mViewMainCourseWare.setBackgroundResource(R.mipmap.left_02);
        mTxtMainCourseware.setTextColor(mWhiteColoc);
        mImgMainCourseware.setBackgroundResource(R.mipmap.courseware);
    }

    @Override
    protected void handler(Message msg) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public void onWifiNet() {
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {
             ToastUtils.showShort(this,"网络中断");
          /* Intent in=new Intent(this,EmptyClass.class);
            in.putExtra("error",1);
            startActivity(in);
            EmptyLayout emptyLayout = new EmptyLayout(me);
            emptyLayout.showError(R.mipmap.add_06,"网络中断");
            setContentView(emptyLayout);*/
        }else {
            ToastUtils.showShort(this,"网络已连接");

        }
    }
}
