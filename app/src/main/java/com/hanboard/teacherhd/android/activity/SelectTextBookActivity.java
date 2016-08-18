package com.hanboard.teacherhd.android.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.os.Bundle;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.adapter.TextBookGridViewAdapter;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.Pickers;
import com.hanboard.teacherhd.android.entity.Status;
import com.hanboard.teacherhd.android.entity.Subject;
import com.hanboard.teacherhd.android.entity.SuitObject;
import com.hanboard.teacherhd.android.entity.listentity.SubjectList;
import com.hanboard.teacherhd.android.entity.TextBook;
import com.hanboard.teacherhd.android.entity.listentity.SuitObjectList;
import com.hanboard.teacherhd.android.entity.listentity.TextBookList;
import com.hanboard.teacherhd.android.fragment.TeachingPreparationFragment;
import com.hanboard.teacherhd.android.model.ISelectTextBookModel;
import com.hanboard.teacherhd.android.model.impl.SelectTextBookModelImpl;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.view.CustomDialog;
import com.hanboard.teacherhd.common.view.LoadingDialog;
import com.hanboard.teacherhd.common.view.WheelView;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;
import com.hanboard.teacherhd.lib.handle.UIHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectTextBookActivity extends Activity implements IDataCallback<Domine>{
    @BindView(R.id.wlw_select_textbook_subject)
    WheelView mWlwSelectTextbookSubject;
    @BindView(R.id.wlw_select_textbook_grade)
    WheelView mWlwSelectTextbookGrade;
    @BindView(R.id.gd_select_textbook)
    GridView mGdSelectTextbook;
    private List<Pickers> mAllSubject = new ArrayList<>();
    private List<Pickers> mAllSuitObject = new ArrayList<>();

    private LoadingDialog loadingDialog;
    private ISelectTextBookModel iSubjectModel;
    protected static UIHandler handler = new UIHandler(Looper.getMainLooper());
    private List<TextBook> textbooks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_text_book);
        ButterKnife.bind(this);
        setHandler();
        iSubjectModel = new SelectTextBookModelImpl();
        initDisplay();
        initData();
    }
    private void initData() {
        iSubjectModel.getAllSubject(this);
        iSubjectModel.getAllSuitObject(this);
        mGdSelectTextbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public CustomDialog b;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextBook t = (TextBook) parent.getAdapter().getItem(position);
                CustomDialog.Builder s = new CustomDialog.Builder(SelectTextBookActivity.this, "提示信息！", "您是否需要添加"+t.subjectName+t.sectionName+t.versionName+"到你的课本?", new CustomDialog.Builder.LeaveDialogListener() {
                    @Override
                    public void cancel(View v) {
                        b.dismiss();
                    }
                    @Override
                    public void confirm(View v) {
                       String accountId = (String) SharedPreferencesUtils.getParam(SelectTextBookActivity.this,"id","");
                        iSubjectModel.addTextbook(accountId,t,SelectTextBookActivity.this);
                    }
                });
                b = s.create();
                b.show();
            }
        });
    }
    /*初始化屏幕*/
    private void initDisplay() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.55);
        p.width = (int)(d.getWidth()*0.4);
        getWindow().setAttributes(p);
    }
    @Override
    public void onSuccess(Domine data) {
        if (data instanceof SubjectList)
        {
            List<Subject> allSubject = ((SubjectList) data).subjects;
            for (Subject subject:allSubject){
                mAllSubject.add(new Pickers(subject.title,subject.id));
            }
            handler.sendEmptyMessage(0);
        }else if (data instanceof SuitObjectList){
            List<SuitObject> allSuitObjects = ((SuitObjectList) data).suitObjects;
            for (SuitObject suitObject:allSuitObjects){
                mAllSuitObject.add(new Pickers(suitObject.title,suitObject.id));
            }
            handler.sendEmptyMessage(1);
        }else if(data instanceof TextBookList){
            textbooks.clear();
            textbooks = ((TextBookList) data).textBooks;
            handler.sendEmptyMessage(2);
        }else if(data instanceof Status){
            handler.sendEmptyMessage(3);
        }
    }
    @Override
    public void onError(String msg, int code) {
        ToastUtils.showShort(this,msg);
    }

    private void setHandler() {
        handler.setHandler(new UIHandler.IHandler() {
            public void handleMessage(Message msg) {
                handler(msg);
            }
        });
    }
    private void handler(Message msg) {
        switch (msg.what){
            case 0:
                mWlwSelectTextbookSubject.setOffset(1);
                mWlwSelectTextbookSubject.setItems(mAllSubject);
                break;
            case 1:
                mWlwSelectTextbookGrade.setOffset(1);
                mWlwSelectTextbookGrade.setItems(mAllSuitObject);
                break;
            case 2:
                loadingDialog.dismiss();
                mGdSelectTextbook.setAdapter(new TextBookGridViewAdapter(SelectTextBookActivity.this,R.layout.select_textbook_item,textbooks));
                break;
            case 3:
                Intent intent=new Intent();
                setResult(TeachingPreparationFragment.REQUSET, intent);
                finish();
                break;
        }
    }
    @OnClick(R.id.txt_select_textbook_search)
    void onClick(View v){
        loadingDialog = new LoadingDialog(this,"正在查询...");
        iSubjectModel.getTextbookBySubIdAndSuitID( mWlwSelectTextbookSubject.getSeletedItem().getShowId(),mWlwSelectTextbookGrade.getSeletedItem().getShowId(),this);
    }
}
