package com.hanboard.teacherhd.android.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.CoursewareInfo;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.LessonPlan;
import com.hanboard.teacherhd.android.fragment.CourseWareFragment;
import com.hanboard.teacherhd.android.fragment.ExercisesFragment;
import com.hanboard.teacherhd.android.fragment.PrepareLessonsDetailFragment;
import com.hanboard.teacherhd.android.fragment.TeachingPlanFragment;
import com.hanboard.teacherhd.android.model.IClassShowCouseModel;
import com.hanboard.teacherhd.android.model.impl.ClassShowCouseModelImp;
import com.hanboard.teacherhd.android.service.RecordService;
import com.hanboard.teacherhd.common.base.BaseActivity;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.lib.common.utils.JsonUtil;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassActivity extends BaseActivity implements IDataCallback<Domine> {
    @BindView(R.id.img_class_courseware)
    ImageView mImgClassCourseware;
    @BindView(R.id.img_class_grammar)
    ImageView mImgClassGrammar;
    @BindView(R.id.img_class_exercises)
    ImageView mImgClassExercises;

    @BindView(R.id.txt_class_courseware)
    TextView mTextClassCourseware;
    @BindView(R.id.txt_class_exercises)
    TextView mTextClassExercises;
    @BindView(R.id.txt_class_grammar)
    TextView mTextClassGrammar;

    @BindView(R.id.view_class_grammar)
    View mViewClassGrammar;
    @BindView(R.id.view_class_courseware)
    View mViewClassCourseware;
    @BindView(R.id.view_class_exercises)
    View mViewClassExercises;

    @BindColor(R.color.theme_color)
    int mThemeColoc;
    @BindColor(R.color.white)
    int mWhiteColoc;
    @BindView(R.id.FabPlus)
    FloatingActionButtonPlus FabPlus;


    private TeachingPlanFragment mTeachingPlanFragment;
    private ExercisesFragment mExercisesFragment;
    private CourseWareFragment mCourseWareFragment;
    private Fragment currentFragment;
    private IClassShowCouseModel iClassShowCouseModel;
    private static final String TAG = "ClassActivity";
    private CoursewareInfo mCoursewareInfo;
    public static String TEACHINGPLAN = "teachplan";
    public static String COURSEWARES = "coursewares";
    public static String EXERCISES = "exercise";


    private static final int RECORD_REQUEST_CODE  = 101;
    private static final int STORAGE_REQUEST_CODE = 102;
    private static final int AUDIO_REQUEST_CODE   = 103;

    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private RecordService recordService;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        setContentView(R.layout.activity_class);
        iClassShowCouseModel = new ClassShowCouseModelImp();
        iClassShowCouseModel.getAllCouseWareInfo(getIntent().getStringExtra(PrepareLessonsDetailFragment.CONTENTID), this, this);
        ButterKnife.bind(this);
        showProgress("正在加载中...");
        FabPlus.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                if(position==2){
                    if (recordService.isRunning()) {
                        recordService.stopRecord();
                    } else {
                        Intent captureIntent = projectionManager.createScreenCaptureIntent();
                        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
                        Toast.makeText(me,"开始录屏",Toast.LENGTH_SHORT).show();
                    }
                }else  if(position==0){
                    startActivity(BlockBoardActivity.class);
                }else if(position==1){
                    startActivity(new Intent(Settings.ACTION_CAST_SETTINGS));
                }
            }
        });
        if (ContextCompat.checkSelfPermission(me, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(me, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO}, AUDIO_REQUEST_CODE);
        }
        Intent intent = new Intent(me, RecordService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            recordService.setMediaProject(mediaProjection);
            recordService.startRecord();
            Toast.makeText(me,"停止录屏",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE || requestCode == AUDIO_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
            }
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
            recordService = binder.getRecordService();
            recordService.setConfig(metrics.heightPixels/2, metrics.heightPixels/2, metrics.densityDpi);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {}
    };

    /*设置默认Fragment*/
    private void initTab() {
        if (mTeachingPlanFragment == null) {
            mTeachingPlanFragment = new TeachingPlanFragment();
        }
        if (!mTeachingPlanFragment.isAdded()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            LessonPlan lessonPlan = mCoursewareInfo.lessonPlan;
            Bundle bundle = new Bundle();
            bundle.putSerializable(TEACHINGPLAN, lessonPlan);
            mTeachingPlanFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.lnl_class_content, mTeachingPlanFragment).commit();
            currentFragment = mTeachingPlanFragment;
            reset();
            //变化指示器
            mViewClassGrammar.setBackgroundResource(R.mipmap.left_on_black_02);
            mTextClassGrammar.setTextColor(mThemeColoc);
            mImgClassGrammar.setBackgroundResource(R.mipmap.home_on);
        }
    }

    @Override
    protected void handler(Message msg) {

    }

    @OnClick({R.id.class_grammar, R.id.class_course, R.id.class_exercises})
    void onClick(View v) {
        reset();
        switch (v.getId()) {

            case R.id.class_grammar:
                clickTeachingPlanLayout();
                break;
            case R.id.class_course:
                clickCoursewareLayout();
                break;
            case R.id.class_exercises:
                clickExercisesLayout();
                break;
            default:
                break;
        }
    }

    private void reset() {

        mViewClassGrammar.setBackgroundResource(R.mipmap.left_02);
        mTextClassGrammar.setTextColor(mWhiteColoc);
        mImgClassGrammar.setBackgroundResource(R.mipmap.home);

        mViewClassCourseware.setBackgroundResource(R.mipmap.left_02);
        mTextClassCourseware.setTextColor(mWhiteColoc);
        mImgClassCourseware.setBackgroundResource(R.mipmap.kejian_06);

        mViewClassExercises.setBackgroundResource(R.mipmap.left_02);
        mTextClassExercises.setTextColor(mWhiteColoc);
        mImgClassExercises.setBackgroundResource(R.mipmap.xiti_06);

    }

    private void clickTeachingPlanLayout() {
        if (mTeachingPlanFragment == null) {
            mTeachingPlanFragment = new TeachingPlanFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mTeachingPlanFragment);
        //变化指示器
        mViewClassGrammar.setBackgroundResource(R.mipmap.left_on_black_02);
        mTextClassGrammar.setTextColor(mThemeColoc);
        mImgClassGrammar.setBackgroundResource(R.mipmap.home_on);
    }

    private void clickCoursewareLayout() {
        if (mCourseWareFragment == null) {
            mCourseWareFragment = new CourseWareFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mCourseWareFragment);
        mViewClassCourseware.setBackgroundResource(R.mipmap.left_on_black_02);
        mTextClassCourseware.setTextColor(mThemeColoc);
        mImgClassCourseware.setBackgroundResource(R.mipmap.kejian_on_06);
    }

    private void clickExercisesLayout() {
        if (mExercisesFragment == null) {
            mExercisesFragment = new ExercisesFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mExercisesFragment);
        mViewClassExercises.setBackgroundResource(R.mipmap.left_on_black_02);
        mTextClassExercises.setTextColor(mThemeColoc);
        mImgClassExercises.setBackgroundResource(R.mipmap.xiti_on_06);
    }


    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.lnl_class_content, fragment);
            if (fragment instanceof CourseWareFragment) {
                String courseWareJson = JsonUtil.toJson(mCoursewareInfo.courseWares);
                Bundle bundle = new Bundle();
                bundle.putString(COURSEWARES, courseWareJson);
                fragment.setArguments(bundle);
            } else if (fragment instanceof ExercisesFragment) {
                String courseWareJson = JsonUtil.toJson(mCoursewareInfo.exercises);
                Bundle bundle = new Bundle();
                bundle.putString(EXERCISES, courseWareJson);
                fragment.setArguments(bundle);
            }
            transaction.commit();

        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onSuccess(Domine data) {
        disProgress();
        if (data instanceof CoursewareInfo) {
            mCoursewareInfo = ((CoursewareInfo) data);
            initTab();
        }
    }

    @Override
    public void onError(String msg, int code) {
        disProgress();
        Log.i(TAG, "onError: ============================获取数据失败=====");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
