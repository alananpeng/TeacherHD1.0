package com.hanboard.teacherhd.android.model.impl;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Banner;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.android.model.IAppModel;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.tools.JsonGenericsSerializator;
import com.hanboard.teacherhd.config.BaseMap;
import com.hanboard.teacherhd.config.CodeInfo;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.config.Urls;
import com.hanboard.teacherhd.lib.common.http.okhttp.OkHttpUtils;
import com.hanboard.teacherhd.lib.common.http.okhttp.callback.GenericsCallback;
import com.hanboard.teacherhd.lib.common.utils.JsonUtil;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 项目名称：TeacherHD1.0
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/12 0012 11:26
 */
public class AppModelImpl implements IAppModel {
    @Override
    public void getBanner(final IDataCallback<List<Banner>> iDataCallback) {
        try {
            Map<String, String> params = new BaseMap().initMap();
            OkHttpUtils.post().url(Urls.URL_GETBANNER).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator())
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
                    MData<List<Banner>> res = JsonUtil.fromJson(response, new TypeToken<MData<List<Banner>>>() {}.getType());
                    if(res.code.equals(Constants.CODE_SUCCESS))
                        iDataCallback.onSuccess(res.result);
                    else
                        iDataCallback.onError(CodeInfo.REQUEST_EMPTY,Integer.valueOf(res.code));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
