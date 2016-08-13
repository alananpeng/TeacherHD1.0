package com.hanboard.teacherhd.android.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.Version;
import com.hanboard.teacherhd.android.model.ILoginModel;
import com.hanboard.teacherhd.android.model.impl.LoginModelImpl;
import com.hanboard.teacherhd.common.base.BaseActivity;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.view.ClearEditText;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements IDataCallback<Domine>{
    @BindView(R.id.edt_loging_username)
    ClearEditText mEdtLogingUsername;
    @BindView(R.id.edt_loging_pwd)
    EditText mEdtLogingPwd;
    private static final String TAG = "LoginActivity";
    private ILoginModel loginModel;
    private SharedPreferences sharedPreferences;
    String accountName,password;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initEditText();
    }
    /**
     * 从SharePreference里面获取用户信息
     */
    private void initEditText() {
        accountName = (String) SharedPreferencesUtils.getParam(me,"accountName", "null");
        password = (String) SharedPreferencesUtils.getParam(me,"password", "null");
        loginModel = new LoginModelImpl();
        if (!accountName.equals("null")||!password.equals("null")){
            mEdtLogingUsername.setText(accountName);
            mEdtLogingPwd.setText(password);
        }
    }


    @Override
    protected void handler(Message msg) {

    }

    @OnClick({R.id.btn_login})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                doLogin();
//
                break;
        }
    }

    private void doLogin() {
        showProgress("正在登录");
        Account account = new Account();
        account.accountName = accountName;
        account.password  = password;
        if (accountName.equals("null")||password.equals("null")){
            account.accountName = mEdtLogingUsername.getText().toString().trim();
            account.password  = mEdtLogingPwd.getText().toString().trim();
        }
        loginModel.doLogin(account,this,this);
    }

    @Override
    public void onSuccess(Domine data){
        disProgress();
        if (data instanceof Account){
            SharedPreferencesUtils.setParam(me,"accountName",mEdtLogingUsername.getText().toString().trim());
            SharedPreferencesUtils.setParam(me,"password",mEdtLogingPwd.getText().toString().trim());
            SharedPreferencesUtils.setParam(me,"id",((Account) data).id);
            SharedPreferencesUtils.setParam(me,"userImg",((Account) data).avatarUrl);
            startActivity(MainActivity.class);
        }
        finish();

    }

    @Override
    public void onError(String msg, int code) {
        disProgress();
        ToastUtils.showShort(this,msg+"错误码"+code);
    }
}