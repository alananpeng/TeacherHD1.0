package com.hanboard.teacherhd.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.LoadRes;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.android.entity.RequestInfo;
import com.hanboard.teacherhd.android.fragment.CourseWareFragment;
import com.hanboard.teacherhd.android.fragment.ExercisesFragment;
import com.hanboard.teacherhd.android.fragment.TeachingPlanFragment;
import com.hanboard.teacherhd.android.fragment.TeachingPreparationFragment;
import com.hanboard.teacherhd.android.fragment.preparelessons.AddCoursewareFragment;
import com.hanboard.teacherhd.android.fragment.preparelessons.AddExercisesFragment;
import com.hanboard.teacherhd.android.fragment.preparelessons.AddTeachingPlanFragment;
import com.hanboard.teacherhd.common.callback.JsonCallback;
import com.hanboard.teacherhd.common.view.DowloadDialog;
import com.hanboard.teacherhd.config.BaseMap;
import com.hanboard.teacherhd.config.CoderConfig;
import com.hanboard.teacherhd.config.Urls;
import com.hanboard.teacherhd.lib.common.utils.DESCoder;
import com.hanboard.teacherhd.lib.common.utils.JsonUtil;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.StringUtil;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.BaseRequest;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class AddPrepareLessonsActivity extends FragmentActivity {

    @BindView(R.id.add_lessons_teachingplan)
    TextView addLessonsTeachingplan;
    @BindView(R.id.add_lessons_courseware)
    TextView addLessonsCourseware;
    @BindView(R.id.add_lessons_exercises)
    TextView addLessonsExercises;

    private int textSizeOn = 24;
    private int textSize = 18;

    private AddCoursewareFragment mAddCoursewareFragment;
    private AddExercisesFragment mAddExercisesFragment;
    private AddTeachingPlanFragment mAddTeachingPlanFragment;
    private Fragment currentFragment;
    private DowloadDialog mProgress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prepare_lessons);
        ButterKnife.bind(this);
        initDisplay();
        initTab();
    }

    /*设置默认Fragment*/
    private void initTab() {
        if (mAddTeachingPlanFragment == null) {
            mAddTeachingPlanFragment = new AddTeachingPlanFragment();
        }
        if (!mAddTeachingPlanFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.add_lessons_content,mAddTeachingPlanFragment).commit();
            currentFragment = mAddTeachingPlanFragment;
            reset();
            addLessonsTeachingplan.setTextColor(Color.parseColor("#00bfff"));
            addLessonsTeachingplan.setTextSize(textSizeOn);
        }
    }

    @OnClick({R.id.add_lessons_teachingplan, R.id.add_lessons_courseware, R.id.add_lessons_exercises,R.id.add_lessons_allsave})
    void onClick(View v){
        reset();
        switch (v.getId()){

            case R.id.add_lessons_teachingplan:
                clickTeachingPlan();
                break;
            case R.id.add_lessons_courseware:
                clickCourseware();
                break;
            case R.id.add_lessons_exercises:
                clickExercises();
                break;
            case R.id.add_lessons_allsave:
                fileUpLoad();
                break;
            default:
                break;
        }
    }


    private void clickTeachingPlan() {
        // TODO Auto-generated method stub
        if (mAddTeachingPlanFragment == null) {
            mAddTeachingPlanFragment = new AddTeachingPlanFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mAddTeachingPlanFragment);
        //变化指示器
        addLessonsTeachingplan.setTextColor(Color.parseColor("#00bfff"));
        addLessonsTeachingplan.setTextSize(textSizeOn);
    }

    private void clickExercises() {
        // TODO Auto-generated method stub
        if (mAddExercisesFragment == null) {
            mAddExercisesFragment = new AddExercisesFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mAddExercisesFragment);
        //变化指示器
        addLessonsExercises.setTextColor(Color.parseColor("#00bfff"));
        addLessonsExercises.setTextSize(textSizeOn);
    }

    private void clickCourseware() {
        // TODO Auto-generated method stub
        if (mAddCoursewareFragment == null) {
            mAddCoursewareFragment = new AddCoursewareFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mAddCoursewareFragment);
        //变化指示器
        addLessonsCourseware.setTextColor(Color.parseColor("#00bfff"));
        addLessonsCourseware.setTextSize(textSizeOn);
    }


    private void reset() {
        addLessonsTeachingplan.setTextColor(Color.parseColor("#ffffff"));
        addLessonsTeachingplan.setTextSize(textSize);
        addLessonsCourseware.setTextColor(Color.parseColor("#ffffff"));
        addLessonsCourseware.setTextSize(textSize);
        addLessonsExercises.setTextColor(Color.parseColor("#ffffff"));
        addLessonsExercises.setTextSize(textSize);
    }
    /*初始化屏幕*/
    private void initDisplay() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.8);
        p.width = (int) (d.getWidth() * 0.8);
        getWindow().setAttributes(p);
    }
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.add_lessons_content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }
    private void fileUpLoad(){
        /**章节ID*/
        String chapterId = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"chapterId","");
        /**课本ID*/
        String textbookId = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"textBookId","");
        /*账户ID*/
        String accountId = (String)SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"id","");
        /*教案*/
        String mubiao = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"mubiao","");
        String guocheng = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"guocheng","");
        String zhongdian = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"zhongdian","");
        String zhunbei = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"zhunbei","");
        String zuoyebuzhi = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"zuoyebuzhi","");
        String kexing = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"kexing","");
        String keshi = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"keshi","");
        String title = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"title","");
        /*习题*/
        String xitisJson = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"xiti","");
        /*课件*/
        String kejiansJson = (String) SharedPreferencesUtils.getParam(AddPrepareLessonsActivity.this,"kejian","");
        List<LoadRes> xitis = JsonUtil.fromJson(xitisJson, new TypeToken<List<LoadRes>>() {}.getType());
        List<LoadRes> kejians = JsonUtil.fromJson(kejiansJson, new TypeToken<List<LoadRes>>() {}.getType());
        Map<String, String> params = null;
        List<File> xitiFiles = new ArrayList<>();
        if (xitis != null && xitis.size() > 0) {
            for (int i = 0; i < xitis.size(); i++) {
                xitiFiles.add(new File(xitis.get(i).path));
                Log.e("TAG", xitis.get(i).path);
            }
        }
        List<File> kejianFiles = new ArrayList<>();
        if (kejians != null && kejians.size() > 0) {
            for (int i = 0; i < kejians.size(); i++) {
                kejianFiles.add(new File(kejians.get(i).path));
                Log.e("TAG", kejians.get(i).path);
            }
        }
        try {
            params = new BaseMap().initMap();
            params.put("lessonPlanGoal",new DESCoder(CoderConfig.CODER_CODE).encrypt(mubiao));
            params.put("lessonPlanKeyPoint",new DESCoder(CoderConfig.CODER_CODE).encrypt(zhongdian));
            params.put("lessonPlanPrepare",new DESCoder(CoderConfig.CODER_CODE).encrypt(zhunbei));
            params.put("lessonPlanProcess",new DESCoder(CoderConfig.CODER_CODE).encrypt(guocheng));
            params.put("lessonPlanWord",new DESCoder(CoderConfig.CODER_CODE).encrypt(zuoyebuzhi));
            params.put("accountId",new DESCoder(CoderConfig.CODER_CODE).encrypt(accountId));
            params.put("contentObject",new DESCoder(CoderConfig.CODER_CODE).encrypt(kexing));
            params.put("chapterId",new DESCoder(CoderConfig.CODER_CODE).encrypt(chapterId));
            params.put("teachBookId",new DESCoder(CoderConfig.CODER_CODE).encrypt(textbookId));
            params.put("contentTitle",new DESCoder(CoderConfig.CODER_CODE).encrypt(title));
            params.put("courseHour",keshi);
//
            OkHttpUtils.post(Urls.URL_ADDLESSONSINFO)
                    .tag(this)
                    .params(params)
                    .addFileParams("file", kejianFiles)
                    .addFileParams("files", xitiFiles)
                    .execute(new ProgressUpCallBack<>(this, RequestInfo.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class ProgressUpCallBack<T> extends JsonCallback<T>{
        public ProgressUpCallBack(Activity activity, Class<T> clazz) {
            super(clazz);
        }
        @Override
        public void onResponse(boolean isFromCache, T t, Request request, @Nullable Response response) {
            //上传完成
            ToastUtils.showShort(AddPrepareLessonsActivity.this,response.message());
            mProgress.dismiss();
            clearSharedPreferences();
            Intent intent=new Intent();
            setResult(300, intent);
            finish();
        }
        private void clearSharedPreferences() {
            /*教案*/
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"mubiao","");
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"guocheng","");
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"zhongdian","");
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"zhunbei","");
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"zuoyebuzhi","");
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"kexing","");
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"keshi","");
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"title","");
            /*习题*/
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"xiti","");
            /*课件*/
            SharedPreferencesUtils.setParam(AddPrepareLessonsActivity.this,"kejian","");
        }

        @Override
        public void onBefore(BaseRequest request) {
            super.onBefore(request);
            mProgress = new DowloadDialog(AddPrepareLessonsActivity.this, "正在上传课件，请稍等...");
        }
        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
            mProgress.dismiss();
            //上传错误
            ToastUtils.showShort(AddPrepareLessonsActivity.this,e.getMessage());
        }
        @Override
        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            super.upProgress(currentSize, totalSize, progress, networkSpeed);
//          String downloadLength = Formatter.formatFileSize(AddPrepareLessonsActivity.this, currentSize);
//          String totalLength = Formatter.formatFileSize(AddPrepareLessonsActivity.this, totalSize);
//          String netSpeed = Formatter.formatFileSize(AddPrepareLessonsActivity.this, networkSpeed);
//          ToastUtils.showShort(AddPrepareLessonsActivity.this,"currentSize"+currentSize+"totalSize"+totalSize+"networkSpeed"+networkSpeed);
            mProgress.setPercent(Math.round(Math.round(progress * 10000) * 1.0f / 100));
        }
    }
}
