package com.hanboard.teacherhd.android.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.common.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_iv)
    ImageView mSplashIv;
    @BindView(R.id.counter)
    TextView counter;
    private int recLen = 6;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        timer = new Timer();
        timer.schedule(task, 1000, 1000);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.5f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.5f);
        ObjectAnimator.ofPropertyValuesHolder(mSplashIv, pvhX, pvhY, pvhZ).setDuration(20000).start();
    }
    @Override
    protected void initContentView(Bundle savedInstanceState) {

    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    counter.setVisibility(View.VISIBLE);
                    counter.setText("跳过:" + recLen + "s");
                    if (recLen < 1) {
                        startActivity(LoginActivity.class);
                        finish();
                        timer.cancel();
                    }
                }
            });
        }
    };

    @Override
    protected void handler(Message msg) {

    }

    @OnClick(R.id.counter)
    public void onClick() {
        startActivity(LoginActivity.class);
        finish();
        timer.cancel();

    }
}
