package com.hanboard.teacherhd.android.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.activity.ClassActivity;
import com.hanboard.teacherhd.android.activity.JiecaoPlayer;
import com.hanboard.teacherhd.android.adapter.CursorGridViewAdapter;
import com.hanboard.teacherhd.android.entity.CourseWare;
import com.hanboard.teacherhd.android.entity.Exercises;
import com.hanboard.teacherhd.android.model.impl.WpsModel;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.view.DowloadDialog;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.lib.common.utils.JsonUtil;
import com.hanboard.teacherhd.lib.common.utils.SDCardHelper;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import name.quanke.app.libs.emptylayout.EmptyLayout;

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
    @BindView(R.id.emptLayout)
    EmptyLayout emptLayout;
    private DowloadDialog mDialog;
    private CursorGridViewAdapter adapter;
    private static final String TAG = "ExercisesFragment";
    private List<Exercises> mExercises = new ArrayList<>();
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            mDialog.setPercent(message.what);
            if (message.what == 100) {
                String path = SDCardHelper.getSDCardPath() + File.separator + "我的老师.ppt";
                openFile(path);
                mDialog.dismiss();
                mDialog = null;
            }

            return false;
        }
    });

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    protected void initData() {
        initDatas();
    }

    public void initDatas() {
        String exerciseJsonString = getArguments().getString(ClassActivity.EXERCISES, "");
        mExercises = JsonUtil.fromJson(exerciseJsonString, new TypeToken<List<Exercises>>() {
        }.getType());
        adapter = new CursorGridViewAdapter(getContext(), R.layout.item_cursorfragment, mExercises);
        exerciseGvCursor.setAdapter(adapter);
        exerciseGvCursor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private CourseWare item;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "点击了" + i, Toast.LENGTH_SHORT).show();
                item = (CourseWare) (adapterView.getAdapter().getItem(i));
                if (item.courseWareType.equals("5") || item.courseWareType.equals("6")) {
                    Intent intent = new Intent(context, JiecaoPlayer.class);
                    intent.putExtra("", "");
                    startActivity(intent);
                } else {
                    ToastUtils.showShort(context, "打开WPS");
                    //  showProgress("正在加载中....");
                    mDialog = new DowloadDialog(context, "正在下載中...");
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            int J = 0;
                            for (int i = 0; i <= 10; i++) {

                                Message message = new Message();
                                message.what = J;
                                handler.sendMessage(message);
                                J += 10;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }


                            // File file = new File(getSDCardPath() + File.separator + dir);

                        }
                    }.start();
                }
            }
        });
    }

    /**
     * 用wps打开相应文件
     *
     * @param path
     * @return
     */
    private boolean openFile(String path) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
        bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
        bundle.putString(WpsModel.THIRD_PACKAGE, Constants.NORMAL); // 第三方应用的包名，用于对改应用合法性的验证
        bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
        // bundle.putBoolean(CLEAR_FILE, true); //关闭后删除打开文件
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName(WpsModel.PackageName.NORMAL, WpsModel.ClassName.NORMAL);
        File file = new File(path);
        if (file == null || !file.exists()) {
            ToastUtils.showShort(context, "找不到該文件");
            return false;
        }
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        intent.putExtras(bundle);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            System.out.println("打开wps异常：" + e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
