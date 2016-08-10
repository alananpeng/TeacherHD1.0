package com.hanboard.teacherhd.android.model;

import android.content.Context;

import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.common.callback.IDataCallback;

/**
 * Created by Administrator on 2016/8/3.
 */
public interface ILoginModel {
    /*用户登录*/
    void doLogin(Account account, IDataCallback<Domine> iDataCallback, Context context);
    void doTest(IDataCallback<Domine> iDataCallback);
}
