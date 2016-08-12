package com.hanboard.teacherhd.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.hanboard.teacherhd.android.activity.EmptyClass;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.lib.common.http.okhttp.OkHttpUtils;
import com.hanboard.teacherhd.lib.common.http.okhttp.log.LoggerInterceptor;
import com.hanboard.teacherhd.lib.common.utils.NetUtil;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class AppContext extends Application {

    private static final String TAG = "InitApplication";
    private static boolean isLogged = false;
    private static AppContext instance;
    public static int mNetWorkState;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initOkHttp();
        initData();
        com.lzy.okhttputils.OkHttpUtils.init(this);
    }
    /**初始化okhttp*/
   public void initOkHttp(){
       OkHttpClient okHttpClient = new OkHttpClient.Builder()
               .addInterceptor(new LoggerInterceptor("TAG"))
               .connectTimeout(10000L, TimeUnit.MILLISECONDS)
               .readTimeout(10000L, TimeUnit.MILLISECONDS)
               .build();
       OkHttpUtils.initClient(okHttpClient);
   }
    public static AppContext getInstance()
    {
        return instance;
    }

    public void initData() {
        mNetWorkState = NetUtil.getNetworkState(this);


    }

}
