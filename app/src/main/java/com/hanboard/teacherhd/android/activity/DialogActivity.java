package com.hanboard.teacherhd.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.adapter.DialogRecyleAdapter;
import com.hanboard.teacherhd.android.entity.Elements;
import com.hanboard.teacherhd.android.entity.PrepareSelectCourse;
import com.hanboard.teacherhd.android.model.impl.GetPrepareCourse;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.view.LoadingDialog;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogActivity extends Activity {
    public static final String TEXTBOOK_ID = "vid";
    @BindView(R.id.ActivityDialog_ryc_head)
    RecyclerView ActivityDialogRycHead;
    private String mBookId = null;
    private List<String> mTitles=new ArrayList<>();
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            mBookId= (String) message.obj;
            Intent intent=new Intent();
            intent.putExtra(TEXTBOOK_ID,mBookId);
            setResult(200,intent);
            finish();
            return false;
        }
    });
    private DialogRecyleAdapter mDialogRecyleAdapter;
    private LoadingDialog mLoadingDialog;

    /**
     * 选择科目的弹出窗口
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        WindowManager m = getWindowManager();
        Display display = m.getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (int) (display.getHeight() * 0.4);
        params.width = (int) (display.getWidth() * 0.5);
        /*params.alpha = 1.0f;
        params.dimAmount = 0.0f;*/
        getWindow().setAttributes(params);

        initData();


    }

    private void initData() {
        mLoadingDialog = new LoadingDialog(this,"获取课本中..");
        GetPrepareCourse getPrepareCourse = new GetPrepareCourse();
        String accountId= (String) SharedPreferencesUtils.getParam(getApplication(),"id","null");
        getPrepareCourse.getPrepareCourse(accountId, "1", new IDataCallback<Elements<PrepareSelectCourse>>() {
            @Override
            public void onSuccess(Elements<PrepareSelectCourse> data) {
                List<PrepareSelectCourse> elements = data.elements;
                mDialogRecyleAdapter = new DialogRecyleAdapter(elements,getApplication(),handler);
                ActivityDialogRycHead.setAdapter(mDialogRecyleAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ActivityDialogRycHead.setLayoutManager(linearLayoutManager);
                mDialogRecyleAdapter.notifyDataSetChanged();
                mLoadingDialog.dismiss();
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.showShort(getApplication(),msg+code);
            }
        });

    }
    public void cancal(View view){
        finish();
    }



}
