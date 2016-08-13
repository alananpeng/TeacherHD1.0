package com.hanboard.teacherhd.android.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.activity.DialogActivity;
import com.hanboard.teacherhd.android.fragment.teachingplan.TestFragment;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.view.Rotate3DCircleLayout;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称：TeacherHD
 * 类描述：首页
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/27 0027 12:47
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.rotatelayout)
    Rotate3DCircleLayout rotatelayout;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    protected void initData() {
        rotatelayout.setOnItemClickListener(new Rotate3DCircleLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

            }
        });
    }
    @OnClick({R.id.txt_home_word, R.id.txt_home_teaching_preparation, R.id.home_tv_startClass})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_home_word:
                startWord();
                break;
            case R.id.txt_home_teaching_preparation:
                startTeachingPreparation();
                break;
            case R.id.home_tv_startClass:
                Intent intent=new Intent(context, DialogActivity.class);
                startActivityForResult(intent,100);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==200){
           // switchToFragment(R.id.lnl_main_content,new TestFragment(),true);
            TestFragment testFragment = new TestFragment();
            Bundle  bundle=new Bundle();
            bundle.putString(DialogActivity.TEXTBOOK_ID,data.getStringExtra(DialogActivity.TEXTBOOK_ID));
            bundle.putString(DialogActivity.TEXTBOOK_TITLE,data.getStringExtra(DialogActivity.TEXTBOOK_TITLE));
            testFragment.setArguments(bundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.zoomin, R.anim.zoomout);
            ft.replace(R.id.lnl_main_content, testFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
    /*打开备课*/
    private void startTeachingPreparation() {
        switchToFragment(R.id.lnl_main_content, new TeachingPreparationFragment(), true);
    }

    /*打开单词通*/
    private void startWord() {
        if (isAvilible(context, "com.gohighinfo.wordstonehd.app")) {
            Intent mIntent = new Intent();
            ComponentName comp = new ComponentName("com.gohighinfo.wordstonehd.app", "com.gohighinfo.wordstonehd.app.activity.WelComeActivity");
            mIntent.putExtra("username", "admin");
            mIntent.putExtra("pwd", "123456");
            mIntent.setComponent(comp);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mIntent);
        } else {
            ToastUtils.showLong(context, getString(R.string.show_setup_prompt));
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     */
    private boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

    /**/
    public void switchToFragment(int containerViewId, Fragment dfragment, boolean isAddedStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.zoomin, R.anim.zoomout);
        ft.replace(containerViewId, dfragment);
        if (isAddedStack)
            ft.addToBackStack(null);
        ft.commit();
    }

}
