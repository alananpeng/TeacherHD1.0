package com.hanboard.teacherhd.android.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.activity.ClassActivity;
import com.hanboard.teacherhd.android.activity.JiecaoPlayer;
import com.hanboard.teacherhd.android.adapter.CursorGridViewAdapter;
import com.hanboard.teacherhd.android.entity.CourseWare;
import com.hanboard.teacherhd.android.model.impl.WpsModel;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.view.DowloadDialog;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.config.Urls;
import com.hanboard.teacherhd.lib.common.utils.JsonUtil;
import com.hanboard.teacherhd.lib.common.utils.SDCardHelper;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/27 0027 12:56
 */
public class CourseWareFragment extends BaseFragment {
    @BindView(R.id.couserware_gv_cursor)
    GridView couserwareGvCursor;
    private DowloadDialog dowload;
    private List<CourseWare> mCurseType=new ArrayList<>();
    private CursorGridViewAdapter adapter;
    public static  String COURSEWAREURL ="courseWareUrl";
    public static  String COURSEWARETITLE ="courseWareTitle";
     private Handler handler=new Handler(new Handler.Callback() {
         @Override
         public boolean handleMessage(Message message) {
             if (message.obj!=null){
                 openFile((String) message.obj);
             }
             return false;
         }
     });


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.fragment_courseware, container, false);
    }

    @Override
    protected void initData() {
        String couserWareJson = getArguments().getString(ClassActivity.COURSEWARES, "");
        Log.i("CourseWare", "initData: "+couserWareJson);
        mCurseType = JsonUtil.fromJson(couserWareJson, new TypeToken<List<CourseWare>>() {
        }.getType());
        adapter = new CursorGridViewAdapter(context, R.layout.item_cursorfragment,mCurseType);
        couserwareGvCursor.setAdapter(adapter);
        couserwareGvCursor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private CourseWare item;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = (CourseWare)(adapterView.getAdapter().getItem(i));
                if (item.courseWareType.equals("5")||item.courseWareType.equals("6")){
                    Intent intent=new Intent(context, JiecaoPlayer.class);
                    intent.putExtra(COURSEWAREURL,item.courseWareUrl);
                    intent.putExtra(COURSEWARETITLE,item.courseWareTitle);
                    startActivity(intent);
                }else {
                    File file = new File(SDCardHelper.getSDCardPath() + File.separator + "temp"+File.separator+"Hanboard"+File.separator+item.courseWareTitle);
                    if(file.exists()){
                        ToastUtils.showShort(context,file.getName()+"直接打开...");
                        Message msg = new Message();
                        msg.obj = file.getAbsolutePath();
                        handler.sendMessage(msg);
                    }else {
                        dowload = new DowloadDialog(context,"正在下载中,请稍等...");
                        OkHttpUtils.get(item.courseWareUrl)//
                                .tag(this)//
                                .execute(new FileCallback("/sdcard/temp/Hanboard/CourseWare/", item.courseWareTitle) {  //文件下载时，需要指定下载的文件目录和文件名
                                    @Override
                                    public void onResponse(boolean isFromCache, File file, Request request, @Nullable Response response) {
                                        // file 即为文件数据，文件保存在指定目录
                                        dowload.dismiss();
                                        dowload = null;
                                        ToastUtils.showShort(context,file.getName()+"下载成功");
                                        Message msg = new Message();
                                        msg.obj = file.getAbsolutePath();
                                        handler.sendMessage(msg);
                                    }
                                    @Override
                                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                                        String downloadLength = Formatter.formatFileSize(context, currentSize);
                                        String totalLength = Formatter.formatFileSize(context, totalSize);
                                        String netSpeed = Formatter.formatFileSize(context, networkSpeed);
                                        dowload.setPercent(Math.round(Math.round(progress * 10000) * 1.0f / 100));
                                        dowload.setDownloadLength(downloadLength+"/");
                                        dowload.setTotalLength(totalLength);
                                        dowload.setnNtSpeed(netSpeed+"/s");
                                    }

                                    @Override
                                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                        super.onError(isFromCache, call, response, e);
                                        ToastUtils.showShort(context,"下载失败...");
                                        dowload.dismiss();
                                        dowload = null;
                                    }
                                });
                    }

                }
            }
        });
    }

    /**
     * 用wps打开相应文件
     * @param path
     * @return
     */
   private boolean openFile(String path) {
       Intent intent = new Intent();
       Bundle bundle = new Bundle();
       bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
       bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
       bundle.putString(WpsModel.THIRD_PACKAGE, Constants.NORMAL); // 第三方应用的包名，用于对改应用合法性的验证
       bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
       // bundle.putBoolean(CLEAR_FILE, true); //关闭后删除打开文件
       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       intent.setAction(Intent.ACTION_VIEW);
       intent.setClassName(WpsModel.PackageName.NORMAL, WpsModel.ClassName.NORMAL);
       File file = new File(path);
       if (file == null || !file.exists()) {
           ToastUtils.showShort(context, "找不到該文件");
           return false;
       }
       Uri uri = Uri.fromFile(file);
       intent.setData(uri);
       intent.putExtras(bundle);
       try {
           startActivity(intent);
       } catch (ActivityNotFoundException e) {
           System.out.println("打开wps异常：" + e.toString());
           e.printStackTrace();
           return false;
       }
       return true;
   }

}
