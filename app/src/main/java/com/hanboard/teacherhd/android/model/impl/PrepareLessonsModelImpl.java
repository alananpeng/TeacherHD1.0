package com.hanboard.teacherhd.android.model.impl;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Chapter;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.Elements;
import com.hanboard.teacherhd.android.entity.JiaocaiVersion;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.android.entity.Status;
import com.hanboard.teacherhd.android.entity.listentity.ListChapter;
import com.hanboard.teacherhd.android.model.IPrepareLessonsModel;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.tools.JsonGenericsSerializator;
import com.hanboard.teacherhd.config.BaseMap;
import com.hanboard.teacherhd.config.CodeInfo;
import com.hanboard.teacherhd.config.CoderConfig;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.config.Urls;
import com.hanboard.teacherhd.lib.common.exception.DataException;
import com.hanboard.teacherhd.lib.common.http.okhttp.OkHttpUtils;
import com.hanboard.teacherhd.lib.common.http.okhttp.callback.GenericsCallback;
import com.hanboard.teacherhd.lib.common.utils.DESCoder;
import com.hanboard.teacherhd.lib.common.utils.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/26 0026 14:22
 */
public class PrepareLessonsModelImpl implements IPrepareLessonsModel {

    @Override
    public void getChapterList(String tid, String vid, String sid, String key, final IDataCallback<Domine> iDataCallback) {
        try {
            Map<String,String> param = new BaseMap().initMap();
            param.put("tid",tid);
//            param.put("vid",new DESCoder(CoderConfig.CODER_CODE).encrypt(vid));
//            param.put("sid",new DESCoder(CoderConfig.CODER_CODE).encrypt(sid));
//            param.put("key",new DESCoder(CoderConfig.CODER_CODE).encrypt(key));
            OkHttpUtils.get().url(Urls.URL_GETAllCURSOR$CHAPTER).params(param).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator())
            {
                @Override
                public void onError(Call call, Exception e, int id)
                {
                    iDataCallback.onError(e.getMessage(),500);
                }
                @Override
                public void onResponse(String response, int id)
                {
                    if(null!=response||!response.equals("")){
                        MData<List<Chapter>> res = JsonUtil.fromJson(response,new TypeToken<MData<List<Chapter>>>(){}.getType());
                        ListChapter chapterList = new ListChapter();
                        if(res.code.equals(Constants.CODE_SUCCESS)){
                            chapterList.chapters = res.result;
                            iDataCallback.onSuccess(chapterList);
                        }else{
                            iDataCallback.onError(CodeInfo.REQUEST_FAILDE,Integer.valueOf(res.code));
                        }
                    }else {
                        iDataCallback.onError(CodeInfo.REQUEST_EMPTY,0);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
