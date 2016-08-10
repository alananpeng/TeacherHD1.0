package com.hanboard.teacherhd.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.adapter.NewLessonsGridViewAdapter;
import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.Lessons;
import com.hanboard.teacherhd.android.entity.listentity.LessonsList;
import com.hanboard.teacherhd.android.entity.params.GetLessons;
import com.hanboard.teacherhd.android.model.ISelectTextBookModel;
import com.hanboard.teacherhd.android.model.impl.SelectTextBookModelImpl;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
class NewLessonsDetailFragment extends BaseFragment implements IDataCallback<Domine>{
    @BindView(R.id.gd_new_lessons_list)
    GridView mGdNewLessonsList;
    private ISelectTextBookModel iSelectTextBookModel;
    private String mSubjectName;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        EventBus.getDefault().register(this);
        iSelectTextBookModel = new SelectTextBookModelImpl();
        return inflater.inflate(R.layout.fragment_new_lessons, container, false);
    }

    @Override
    protected void initData() {

    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    void getData(GetLessons getLessons){
        int pageNum = 1;
        String accountId = (String) SharedPreferencesUtils.getParam(context,"id","");
        mSubjectName = getLessons.subject;
        iSelectTextBookModel.getPrepareLessons(getLessons.chapterId,accountId,getLessons.textBookId,String.valueOf(pageNum),this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onSuccess(Domine data) {
        if(data instanceof LessonsList){
            List<Lessons> res = ((LessonsList) data).elements;
            mGdNewLessonsList.setAdapter(new NewLessonsGridViewAdapter(context,R.layout.new_lessons_item,res,mSubjectName));
        }
    }
    @Override
    public void onError(String msg, int code) {
        ToastUtils.showShort(context,msg+"错误码"+code);
    }
}
