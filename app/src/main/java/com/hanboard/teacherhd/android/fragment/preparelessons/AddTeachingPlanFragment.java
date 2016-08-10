package com.hanboard.teacherhd.android.fragment.preparelessons;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.common.base.BaseFragment;

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

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_add_lessons_teachingplan, container, false);
    }

    @Override
    protected void initData() {


    }


    @OnClick({R.id.add_lessons_select_kexin, R.id.add_lessons_enterkeshi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_lessons_select_kexin:
                selectKexin();
                break;
            case R.id.add_lessons_enterkeshi:
                break;
        }
    }
    /*选择课型*/
    private void selectKexin() {
        new AlertDialog.Builder(context)
                .setTitle("请选择课型")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(new String[] {"选项1","选项2","选项3","选项4"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("取消", null)
                .show();
    }
}
