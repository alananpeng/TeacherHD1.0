package com.hanboard.teacherhd.android.model.impl;

import com.google.gson.reflect.TypeToken;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.android.entity.Subject;
import com.hanboard.teacherhd.android.entity.Version;
import com.hanboard.teacherhd.android.entity.listentity.SubjectList;
import com.hanboard.teacherhd.android.model.IVersionModel;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.callback.UpdateCallback;
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
import java.util.concurrent.ExecutionException;

import okhttp3.Call;

/**
 * 项目名称：TeacherHD1.0
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/13 0013 14:13
 */
public class VersionModelImpl implements IVersionModel {
    @Override
    public void checkVersion(final UpdateCallback updateCallback) {
            OkHttpUtils.get().url(Urls.UPDATE_URL).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator())
            {
                @Override
                public void onError(Call call, Exception e, int id){

                }
                @Override
                public void onResponse(String response, int id)
                {
                    Version version = JsonUtil.fromJson(response, Version.class);
                    updateCallback.onVersion(version);
                }
            });
    }
}
