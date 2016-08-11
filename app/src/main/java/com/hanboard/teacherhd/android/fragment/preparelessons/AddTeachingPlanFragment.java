package com.hanboard.teacherhd.android.fragment.preparelessons;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称：TeacherHD1.0
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/10 0010 14:13
 */
public class AddTeachingPlanFragment extends BaseFragment {

    @BindView(R.id.add_lessons_select_kexin)
    Button mAddLessonsSelectKexin;
    @BindView(R.id.add_lessons_enterkeshi)
    Button mAddLessonsEnterkeshi;
    @BindView(R.id.add_lessons_teachingplan_mubiao)
    EditText addLessonsTeachingplanMubiao;
    @BindView(R.id.add_lessons_teachingplan_zhongdian)
    EditText addLessonsTeachingplanZhongdian;
    @BindView(R.id.add_lessons_teachingplan_zhunbei)
    EditText addLessonsTeachingplanZhunbei;
    @BindView(R.id.add_lessons_teachingplan_guocheng)
    EditText addLessonsTeachingplanGuocheng;
    @BindView(R.id.add_lessons_teachingplan_zuoyebuzhi)
    EditText addLessonsTeachingplanZuoyebuzhi;
    @BindView(R.id.add_lessons_teachingplan_save)
    Button addLessonsTeachingplanSave;
    @BindView(R.id.add_lessons_title)
    EditText addLessonsTitle;
    private String mubiao;
    private String zhongdian;
    private String zhunbei;
    private String guocheng;
    private String zuoyebuzhi;
    private String kexing;
    private String keshi;
    private String title;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_add_lessons_teachingplan, container, false);
    }

    @Override
    protected void initData() {
        addLessonsTeachingplanMubiao.setText("这是教学目标");
        addLessonsTeachingplanZhongdian.setText("这是教学重点");
        addLessonsTeachingplanZhunbei.setText("这是教学准备");
        addLessonsTeachingplanGuocheng.setText("这是教学过程");
        addLessonsTeachingplanZuoyebuzhi.setText("这是作业布置");
    }


    @OnClick({R.id.add_lessons_select_kexin, R.id.add_lessons_enterkeshi, R.id.add_lessons_teachingplan_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_lessons_select_kexin:
                selectKexin();
                break;
            case R.id.add_lessons_enterkeshi:
                enterKeshi();
                break;
            case R.id.add_lessons_teachingplan_save:
                mubiao = addLessonsTeachingplanMubiao.getText().toString();
                zhongdian = addLessonsTeachingplanZhongdian.getText().toString();
                zhunbei = addLessonsTeachingplanZhunbei.getText().toString();
                guocheng = addLessonsTeachingplanGuocheng.getText().toString();
                zuoyebuzhi = addLessonsTeachingplanZuoyebuzhi.getText().toString();
                //
                kexing = mAddLessonsSelectKexin.getText().toString();
                keshi = mAddLessonsEnterkeshi.getText().toString();
                title = addLessonsTitle.getText().toString();
                saveData();
                break;
        }
    }
    /*输入课时*/
    private void enterKeshi() {
        final EditText e = new EditText(context);
        e.setHint("只能是数字...");
        e.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog
                .Builder(context)
                .setTitle("请输入您的课时")
                .setView(e)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAddLessonsEnterkeshi.setText(e.getText().toString());
                    }
                })
                .setNegativeButton("取消", null).show();
    }
    private void saveData() {
        SharedPreferencesUtils.setParam(context, "mubiao", mubiao);
        SharedPreferencesUtils.setParam(context, "guocheng", guocheng);
        SharedPreferencesUtils.setParam(context, "zhongdian", zhongdian);
        SharedPreferencesUtils.setParam(context, "zhunbei", zhunbei);
        SharedPreferencesUtils.setParam(context, "zuoyebuzhi", zuoyebuzhi);
        SharedPreferencesUtils.setParam(context, "kexing", kexing);
        SharedPreferencesUtils.setParam(context, "keshi", keshi);
        SharedPreferencesUtils.setParam(context, "title", title);
        ToastUtils.showShort(context, "教案临时保存成功");
    }

    /*选择课型*/
    private void selectKexin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("请选择课型");
        //    指定下拉列表的显示数据
        final String[] cities = {"新授课", "练习课", "复习课", "讲评课", "实验课"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                mAddLessonsSelectKexin.setText(cities[which]);
            }
        });
        builder.show();
    }

}
