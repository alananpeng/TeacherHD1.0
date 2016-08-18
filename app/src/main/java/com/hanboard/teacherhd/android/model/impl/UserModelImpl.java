package com.hanboard.teacherhd.android.model.impl;

import com.google.gson.reflect.TypeToken;
import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.android.entity.Status;
import com.hanboard.teacherhd.android.model.IUserModel;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.config.BaseMap;
import com.hanboard.teacherhd.config.CodeInfo;
import com.hanboard.teacherhd.config.CoderConfig;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.config.Urls;
import com.hanboard.teacherhd.lib.common.http.okhttp.OkHttpUtils;
import com.hanboard.teacherhd.lib.common.http.okhttp.callback.StringCallback;
import com.hanboard.teacherhd.lib.common.utils.DESCoder;
import com.hanboard.teacherhd.lib.common.utils.JsonUtil;

import java.util.Map;

import okhttp3.Call;

/**
 * 项目名称：TeacherHD1.0
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/18 0018 18:07
 */
public class UserModelImpl implements IUserModel {
    @Override
    public void getUserIno(String accountId, final IDataCallback<Domine> iDataCallback) {
        try {
            Map<String,String> param = new BaseMap().initMap();
            param.put("accountId",new DESCoder(CoderConfig.CODER_CODE).encrypt(accountId));
            OkHttpUtils.get().params(param).url(Urls.URL_GETUSERINFO).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    iDataCallback.onError(e.getMessage(),id);
                }
                @Override
                public void onResponse(String response, int id) {
                    if(null!=response||!response.equals("")){
                        MData<Account> res = JsonUtil.fromJson(response,new TypeToken<MData<Account>>(){}.getType());
                        if(res.code.equals(Constants.CODE_SUCCESS)){
                            iDataCallback.onSuccess(res.result);
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
    @Override
    public void checkOldPwd(String accountId,String userName,String oldPwd,final IDataCallback<Domine> iDataCallback) {
        try {
            Map<String,String> param = new BaseMap().initMap();
            param.put("accountId",new DESCoder(CoderConfig.CODER_CODE).encrypt(accountId));
            param.put("oldPwd",new DESCoder(CoderConfig.CODER_CODE).encrypt(oldPwd));
            param.put("userName",new DESCoder(CoderConfig.CODER_CODE).encrypt(userName));
            OkHttpUtils.get().params(param).url(Urls.URL_GETUSERINFO).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    iDataCallback.onError(e.getMessage(),id);
                }
                @Override
                public void onResponse(String response, int id) {
                    if(null!=response||!response.equals("")){
                        Status res = JsonUtil.fromJson(response,Status.class);
                        if(res.code.equals(Constants.CODE_SUCCESS)){
                            iDataCallback.onSuccess(res);
                        }else{
                            iDataCallback.onError("旧密码验证失败",Integer.valueOf(res.code));
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
    @Override
    public void updatePwd(String accountId, String pwd, final IDataCallback<Domine> iDataCallback) {
        try {
            Map<String,String> param = new BaseMap().initMap();
            param.put("accountId",new DESCoder(CoderConfig.CODER_CODE).encrypt(accountId));
            param.put("pwd",new DESCoder(CoderConfig.CODER_CODE).encrypt(pwd));
            OkHttpUtils.get().params(param).url(Urls.URL_GETUSERINFO).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    iDataCallback.onError(e.getMessage(),id);
                }
                @Override
                public void onResponse(String response, int id) {
                    if(null!=response||!response.equals("")){
                        Status res = JsonUtil.fromJson(response,Status.class);
                        if(res.code.equals(Constants.CODE_SUCCESS)){
                            iDataCallback.onSuccess(res);
                        }else{
                            iDataCallback.onError("密码修改失败",Integer.valueOf(res.code));
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

    @Override
    public void updateTel(String accountId, String tel, final IDataCallback<Domine> iDataCallback) {
        try {
            Map<String,String> param = new BaseMap().initMap();
            param.put("id",new DESCoder(CoderConfig.CODER_CODE).encrypt(accountId));
            param.put("phone",new DESCoder(CoderConfig.CODER_CODE).encrypt(tel));
            OkHttpUtils.get().params(param).url(Urls.URL_GETUSERINFO).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    iDataCallback.onError(e.getMessage(),id);
                }
                @Override
                public void onResponse(String response, int id) {
                    if(null!=response||!response.equals("")){
                        Status res = JsonUtil.fromJson(response,Status.class);
                        if(res.code.equals(Constants.CODE_SUCCESS)){
                            iDataCallback.onSuccess(res);
                        }else{
                            iDataCallback.onError("电话号码修改失败",Integer.valueOf(res.code));
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

    @Override
    public void updateEmail(String accountId, String email, final IDataCallback<Domine> iDataCallback) {
        try {
            Map<String,String> param = new BaseMap().initMap();
            param.put("id",new DESCoder(CoderConfig.CODER_CODE).encrypt(accountId));
            param.put("email",new DESCoder(CoderConfig.CODER_CODE).encrypt(email));
            OkHttpUtils.get().params(param).url(Urls.URL_GETUSERINFO).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    iDataCallback.onError(e.getMessage(),id);
                }
                @Override
                public void onResponse(String response, int id) {
                    if(null!=response||!response.equals("")){
                        Status res = JsonUtil.fromJson(response,Status.class);
                        if(res.code.equals(Constants.CODE_SUCCESS)){
                            iDataCallback.onSuccess(res);
                        }else{
                            iDataCallback.onError("电子邮箱修改失败",Integer.valueOf(res.code));
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

    @Override
    public void updateName(String accountId, String name, final IDataCallback<Domine> iDataCallback) {
        try {
            Map<String,String> param = new BaseMap().initMap();
            param.put("id",new DESCoder(CoderConfig.CODER_CODE).encrypt(accountId));
            param.put("trueName",new DESCoder(CoderConfig.CODER_CODE).encrypt(name));
            OkHttpUtils.get().params(param).url(Urls.URL_GETUSERINFO).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    iDataCallback.onError(e.getMessage(),id);
                }
                @Override
                public void onResponse(String response, int id) {
                    if(null!=response||!response.equals("")){
                        Status res = JsonUtil.fromJson(response,Status.class);
                        if(res.code.equals(Constants.CODE_SUCCESS)){
                            iDataCallback.onSuccess(res);
                        }else{
                            iDataCallback.onError("姓名修改失败",Integer.valueOf(res.code));
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

    @Override
    public void updataNickName(String accountId, String nickName, final IDataCallback<Domine> iDataCallback) {
        try {
            Map<String,String> param = new BaseMap().initMap();
            param.put("id",new DESCoder(CoderConfig.CODER_CODE).encrypt(accountId));
            param.put("nickName",new DESCoder(CoderConfig.CODER_CODE).encrypt(nickName));
            OkHttpUtils.get().params(param).url(Urls.URL_GETUSERINFO).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    iDataCallback.onError(e.getMessage(),id);
                }
                @Override
                public void onResponse(String response, int id) {
                    if(null!=response||!response.equals("")){
                        Status res = JsonUtil.fromJson(response,Status.class);
                        if(res.code.equals(Constants.CODE_SUCCESS)){
                            iDataCallback.onSuccess(res);
                        }else{
                            iDataCallback.onError("昵称修改失败",Integer.valueOf(res.code));
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
