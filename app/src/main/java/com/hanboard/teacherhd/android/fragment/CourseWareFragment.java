package com.hanboard.teacherhd.android.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.activity.JiecaoPlayer;
import com.hanboard.teacherhd.android.adapter.CursorGridViewAdapter;
import com.hanboard.teacherhd.android.entity.CourseWare;
import com.hanboard.teacherhd.android.model.impl.WpsModel;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.view.DowloadDialog;
import com.hanboard.teacherhd.config.Constants;
import com.hanboard.teacherhd.lib.common.utils.SDCardHelper;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import name.quanke.app.libs.emptylayout.EmptyLayout;

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
    private EmptyLayout e;
    private List<CourseWare> curseType;
    private CursorGridViewAdapter adapter;
    private DowloadDialog mDialog;
     private Handler handler=new Handler(new Handler.Callback() {
         @Override
         public boolean handleMessage(Message message) {
             //disProgress();
             mDialog.setPercent(message.what);
             if (message.what==100){
                 String path=SDCardHelper.getSDCardPath()+File.separator+"我的老师.ppt";
                 openFile(path);
                 mDialog.dismiss();
                 mDialog=null;
             }

            /* File file = new File(path);
             if (file.exists()){
                 ToastUtils.showShort(context,"打开WPS");
             }*/
             return false;
         }
     });
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.fragment_courseware, container, false);
    }

    @Override
    protected void initData() {
        curseType = new ArrayList<>();
        curseType.add(new CourseWare("1.mp3","http://www.baidu.com",".mp3","1345134"));
        curseType.add(new CourseWare("2.mp4","http://www.baidu.com",".mp4","1345134"));
        curseType.add(new CourseWare("3.doc","http://www.baidu.com",".doc","1345134"));
        curseType.add(new CourseWare("4.xls","http://www.baidu.com",".xls","1345134"));
        curseType.add(new CourseWare("5.ppt","http://www.baidu.com",".ppt","1345134"));
        curseType.add(new CourseWare("6.pdf","http://www.baidu.com",".pdf","1345134"));
        adapter = new CursorGridViewAdapter(context, R.layout.item_cursorfragment,curseType);
        couserwareGvCursor.setAdapter(adapter);
        couserwareGvCursor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private CourseWare item;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "点击了"+i, Toast.LENGTH_SHORT).show();
                item = (CourseWare)(adapterView.getAdapter().getItem(i));
                if (item.courseWareType.equals(Constants.TYPE_MP3 )||item.courseWareType.equals(Constants.TYPE_MP4 )){
                    Intent intent=new Intent(context, JiecaoPlayer.class);
                    intent.putExtra("","");
                    startActivity(intent);
                }else {
                    ToastUtils.showShort(context,"打开WPS");
                  //  showProgress("正在加载中....");
                     mDialog=new DowloadDialog(context,"正在下載中...");
                     new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                int J=0;
                                for (int i = 0; i <= 10; i++) {

                                    Message message=new Message();
                                    message.what=J;
                                    handler.sendMessage(message);
                                      J+=10;
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }


                                // File file = new File(getSDCardPath() + File.separator + dir);

                            }
                        }.start();
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
