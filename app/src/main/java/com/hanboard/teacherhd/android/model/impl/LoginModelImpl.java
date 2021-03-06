package com.hanboard.teacherhd.android.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.android.model.ILoginModel;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.tools.JsonGenericsSerializator;
import com.hanboard.teacherhd.config.BaseMap;
import com.hanboard.teacherhd.config.CoderConfig;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.config.Urls;
import com.hanboard.teacherhd.lib.common.http.okhttp.OkHttpUtils;
import com.hanboard.teacherhd.lib.common.http.okhttp.callback.GenericsCallback;
import com.hanboard.teacherhd.lib.common.utils.DESCoder;
import com.hanboard.teacherhd.lib.common.utils.JsonUtil;

import java.util.Map;

import okhttp3.Call;
/**
 * Created by Administrator on 2016/8/3.
 */
public class LoginModelImpl implements ILoginModel {
    @Override
    public void doLogin(Account account, final IDataCallback<Domine> iDataCallback, final Context context){
        try {
            Map<String,String> params = new BaseMap().initMap();
            params.put("accountName",new DESCoder(CoderConfig.CODER_CODE).encrypt(account.accountName));
            params.put("accountPassword",new DESCoder(CoderConfig.CODER_CODE).encrypt(account.password));
            OkHttpUtils.post().url(Urls.URL_LOGIN).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator())
            {
                @Override
                public void onError(Call call, Exception e, int id)
                {
                    iDataCallback.onError(e.getMessage(),id);
                }
                @Override
                public void onResponse(String response, int id)
                {
                    Log.e("TAG",response);
                    MData<Account> res = JsonUtil.fromJson(response, new TypeToken<MData<Account>>() {}.getType());
                    if(res.code.equals(Constants.CODE_SUCCESS))
                        iDataCallback.onSuccess(res.result);
                    else
                        iDataCallback.onError(context.getString(R.string.pwd_error),Integer.valueOf(res.code));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void doTest(IDataCallback<Domine> iDataCallback) {

    }
}
