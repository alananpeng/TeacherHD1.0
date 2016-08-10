package com.hanboard.teacherhd.android.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hanboard.teacherhd.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目名称：TeacherHD
 * 类描述：录制屏幕及外部声音
 * 创建人：kun.ren@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/29
 **/
public class ScreenCastActivity extends AppCompatActivity {

    private static final String TAG = "ScreenCastActivity";
    private static final int PERMISSION_CODE = 1;
    private int mScreenDensity;
    private MediaProjectionManager mProjectionManager;
    private static int DISPLAY_WIDTH;
    private static int DISPLAY_HEIGHT;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionCallback mMediaProjectionCallback;
    private ToggleButton mToggleButton;
    private MediaRecorder mMediaRecorder;
    Toolbar toolbar;
    TextView textView;

    int hasPermissionStorage;
    int hasPermissionAudio;
    private static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 20;
    List<String> permissions = new ArrayList<>();

    //    设置实例状态
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_cast);

//        设置录屏与录音系统权限
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            hasPermissionStorage = ContextCompat.checkSelfPermission(ScreenCastActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            hasPermissionAudio = ContextCompat.checkSelfPermission(ScreenCastActivity.this, Manifest.permission.RECORD_AUDIO);

            if( hasPermissionAudio != PackageManager.PERMISSION_GRANTED ) {
                permissions.add( Manifest.permission.RECORD_AUDIO );
            }

            if( hasPermissionStorage != PackageManager.PERMISSION_GRANTED ) {
                permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE );
            }

            if( !permissions.isEmpty() ) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray( new String[permissions.size()] ), REQUEST_CODE_SOME_FEATURES_PERMISSIONS );
            }
        }


        textView = (TextView) findViewById(R.id.textView);
        textView.setText("");

//      设置视频大小
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        DISPLAY_WIDTH = size.x / 2;
        DISPLAY_HEIGHT = size.y / 2;

        mMediaRecorder = new MediaRecorder();
        //initRecorder();
        //prepareRecorder();

        mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        mToggleButton = (ToggleButton) findViewById(R.id.toggle);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleScreenShare(v);
            }
        });

        mMediaProjectionCallback = new MediaProjectionCallback();

///*        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        String path = sp.getString("path", Environment.getExternalStorageDirectory().getPath());
//        String format = sp.getString("prefOutput", "MPEG_4");
//        String bitrate = sp.getString("prefBitrate", "260");
//        String frameRate = sp.getString("prefFrameRate", "4");
//        String audio = sp.getString("prefAudio", "AAC");
//
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(path + "\n" + format + "\n" + bitrate + "\n" + frameRate + "\n" + audio);*/
    }

    //    销毁录屏
    @Override
    public void onDestroy() {
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
        }
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        super.onDestroy();
    }

    //    录屏开始与结束
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != PERMISSION_CODE) {
            Log.e(TAG, "Unknown request code: " + requestCode);
            return;
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Screen Cast Permission Denied", Toast.LENGTH_SHORT).show();
            mToggleButton.setChecked(false);
            mMediaRecorder.reset();
            textView.setText("");
            return;
        }
        mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
        mMediaProjection.registerCallback(mMediaProjectionCallback, null);
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    //    设置录屏开关
    public void onToggleScreenShare(View view) {
        if (((ToggleButton) view).isChecked()) {
            shareScreen();
        } else {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            Log.v(TAG, "Recording Stopped");
            stopScreenSharing();
            //initRecorder();
            //prepareRecorder();
        }
    }

    //    共享屏幕
    private void shareScreen() {
        initRecorder();
        prepareRecorder();
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), PERMISSION_CODE);
            return;
        }
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

//    停止共享屏幕
    private void stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        //mMediaRecorder.release();
        textView.setText("");
    }

    private VirtualDisplay createVirtualDisplay() {
        return mMediaProjection.createVirtualDisplay("MainActivity",
                DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mMediaRecorder.getSurface(), null /*Callbacks*/, null /*Handler*/);
    }

    //    屏幕投射响应回调
    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (mToggleButton.isChecked()) {
                mToggleButton.setChecked(false);
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                Log.v(TAG, "Recording Stopped");
                initRecorder();
                prepareRecorder();
            }
            mMediaProjection = null;
            stopScreenSharing();
            Log.i(TAG, "MediaProjection Stopped");
        }
    }

    //    开启录音机
    private void prepareRecorder() {
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    //    初始化屏幕记录器
    private void initRecorder() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ScreenCastActivity.this);
        String path = sp.getString("path", Environment.getExternalStorageDirectory().getPath());
        //String format = sp.getString("prefOutput", "MPEG_4");
        String bitrate = sp.getString("prefBitrate", "500");
        String frameRate = sp.getString("prefFrameRate", "30");
        //String audio = sp.getString("prefAudio", "AAC");
        //String ext;

        textView.setText("Current path: " + path);

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);

//        输出视频与音频编码
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);


        //        设置输出文件名
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String nName=df.format(day)+".mp4 ";
        File file = new File(Environment.getExternalStorageDirectory(),
                "录像" +df.format(day)+ ".mp4");
        file.getAbsolutePath();


        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setVideoEncodingBitRate(Integer.parseInt(bitrate) * 100); //low260 high800
        mMediaRecorder.setVideoFrameRate(Integer.parseInt(frameRate));
        mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        mMediaRecorder.setOutputFile(path+"/"+nName);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//
//            case R.id.action_settings:
//                Intent i = new Intent(this, UserSettings.class);
//                startActivity(i);
//                return true;
//            case R.id.action_about:
//                new AlertDialog.Builder(this)
//                        .setTitle("ABOUT")
//                        .setMessage("Hey... Devipriya here!")
//                        .setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                //continue
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_menu_gallery)
//                        .show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

    //    请求权限结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int grantResults[]) {
        switch ( requestCode ) {
            case REQUEST_CODE_SOME_FEATURES_PERMISSIONS: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] == PackageManager.PERMISSION_GRANTED ) {
                        Log.d("Permissions", "Permission Granted: " + permissions[i] );
                    } else if( grantResults[i] == PackageManager.PERMISSION_DENIED ) {
                        Log.d("Permissions", "Permission Denied: " + permissions[i] );
                        finish();
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}