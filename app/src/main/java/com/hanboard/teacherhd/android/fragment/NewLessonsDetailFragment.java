package com.hanboard.teacherhd.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.activity.AddPrepareLessonsActivity;
import com.hanboard.teacherhd.android.adapter.NewLessonsGridViewAdapter;
import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.Lessons;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.android.entity.listentity.LessonsList;
import com.hanboard.teacherhd.android.entity.params.GetLessons;
import com.hanboard.teacherhd.android.model.ISelectTextBookModel;
import com.hanboard.teacherhd.android.model.impl.SelectTextBookModelImpl;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.view.LoadingDialog;
import com.hanboard.teacherhd.common.view.OperationDialog;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/9 0009 10:41
 */
public class NewLessonsDetailFragment extends BaseFragment implements IDataCallback<Domine>{
    @BindView(R.id.gd_new_lessons_list)
    GridView mGdNewLessonsList;
    private ISelectTextBookModel iSelectTextBookModel;
    private String mSubjectName;
    private List<Lessons> res = new ArrayList<>();
    private LoadingDialog loading;
    private String textbookId;
    private String chapterId;
    private String accountId;
    private OperationDialog o;
    private NewLessonsGridViewAdapter mAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        EventBus.getDefault().register(this);
        iSelectTextBookModel = new SelectTextBookModelImpl();
        return inflater.inflate(R.layout.fragment_new_lessons, container, false);
    }

    @Override
    protected void initData() {
        mGdNewLessonsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lessons ls = (Lessons) parent.getAdapter().getItem(position);
                if(ls.content.contentTitle.equals("添加课程")){
                    startActivityForResult(new Intent(context, AddPrepareLessonsActivity.class),300);
                }
            }
        });
        mGdNewLessonsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Lessons l = (Lessons)parent.getAdapter().getItem(position);
                o = new OperationDialog.Builder(context, new OperationDialog.Builder.LeaveDialogListener() {
                    @Override
                    public void delete(View v) {
                        iSelectTextBookModel.deleteLessons(l.contentId, new IDataCallback<MData<String>>() {
                            @Override
                            public void onSuccess(MData<String> data) {
                                ToastUtils.showShort(context,"删除成功");
                                o.dismiss();
                            }
                            @Override
                            public void onError(String msg, int code) {
                                ToastUtils.showShort(context,msg+code);
                            }
                        });
                    }
                    @Override
                    public void edit(View v) {

                    }
                }).create();
                o.show();
                return false;
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    void getData(GetLessons getLessons){
        textbookId = getLessons.textBookId;
        chapterId = getLessons.chapterId;
        loading = new LoadingDialog(context,"玩命加载中...");
        int pageNum = 1;
        accountId = (String) SharedPreferencesUtils.getParam(context,"id","");
        mSubjectName = getLessons.subject;
        iSelectTextBookModel.getPrepareLessons(chapterId,accountId,textbookId,String.valueOf(pageNum),this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onSuccess(Domine data) {
        loading.dismiss();
        res.clear();
        if(data instanceof LessonsList){
            res = ((LessonsList) data).elements;
            //添加课程
            Lessons l = new Lessons();
            Lessons.ContentBean c = new Lessons.ContentBean();
            c.contentTitle="添加课程";
            l.content = c;
            res.add(0,l);
            mAdapter = new NewLessonsGridViewAdapter(context,R.layout.new_lessons_item,res,mSubjectName);
            mGdNewLessonsList.setAdapter(mAdapter);
        }
    }
    @Override
    public void onError(String msg, int code) {
        loading.dismiss();
        if(code == 30010) {
            res.clear();
            //添加课程
            Lessons l = new Lessons();
            Lessons.ContentBean c = new Lessons.ContentBean();
            c.contentTitle = "添加课程";
            l.content = c;
            res.add(0, l);
            mGdNewLessonsList.setAdapter(new NewLessonsGridViewAdapter(context, R.layout.new_lessons_item, res, mSubjectName));
        }else {
            ToastUtils.showShort(context, msg + ",错误码:" + code);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            iSelectTextBookModel.getPrepareLessons(chapterId,accountId,textbookId,String.valueOf(1),this);
        }
    }
}
