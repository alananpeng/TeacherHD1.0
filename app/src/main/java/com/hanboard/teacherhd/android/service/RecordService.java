package com.hanboard.teacherhd.android.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.IBinder;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RecordService extends Service {

    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private VirtualDisplay virtualDisplay;
    private boolean running;
    private int width;
    private int height;
    private int dpi;


    public class RecordBinder extends Binder {
        public RecordService getRecordService() {
                return RecordService.this;
            }
    }

    @Override
    public IBinder onBind(Intent intent) {
            return new RecordBinder();
        }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            return START_STICKY;
        }
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread serviceThread = new HandlerThread("service_thread",android.os.Process.THREAD_PRIORITY_BACKGROUND);
        serviceThread.start();
        running = false;
        mediaRecorder = new MediaRecorder();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setMediaProject(MediaProjection project) {
          mediaProjection = project;
      }

    public boolean isRunning() {
          return running;
      }

    public void setConfig(int width, int height, int dpi) {
        this.width = width;
        this.height = height;
        this.dpi = dpi;
    }

    public boolean startRecord() {
        if (mediaProjection == null || running) {
            return false;
        }
        initRecorder();
        createVirtualDisplay();
        mediaRecorder.start();
        running = true;
        return true;
    }

    public boolean stopRecord() {
        if (!running) {
            return false;
        }
        running = false;
        mediaRecorder.stop();
        mediaRecorder.reset();
        virtualDisplay.release();
        mediaProjection.stop();
        return true;
    }

    private void createVirtualDisplay() {
        virtualDisplay = mediaProjection.createVirtualDisplay("MainScreen", width, height, dpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
    }

    private void initRecorder() {

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String path = sp.getString("path", Environment.getExternalStorageDirectory().getPath());
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        String nName="Hanboard"+df.format(day)+".mp4";
//        File file = new File(path,
//                df.format(day) + ".mp4");
//        file.getAbsolutePath();

        mediaRecorder.setVideoSize(width, height);

        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setVideoEncodingBitRate(6000000);
        mediaRecorder.setVideoFrameRate(30);
        mediaRecorder.setOutputFile(path+"/"+nName);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}