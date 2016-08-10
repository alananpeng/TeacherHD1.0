package com.hanboard.teacherhd.android.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import it.sephiroth.android.library.picasso.Picasso;

public class JiecaoPlayer extends BaseActivity {

    @BindView(R.id.jiecaoplayer_jc)
    JCVideoPlayer jiecaoplayerJc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiecao_player);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {

    }

    @Override
    protected void handler(Message msg) {

    }

    private void initData() {
        jiecaoplayerJc.setUp("http://flv.bn.netease.com/videolib3/1601/13/RzAQP8148/HD/RzAQP8148-mobile.mp4","测试视频");
        Picasso.with(this).load(Uri.parse("http://h.hiphotos.baidu.com/baike/whfpf%3D180%2C140%2C50/sign=b7e5b07e4934970a4726436ff3f7e0f0/0df431adcbef76095400e1ce26dda3cc7cd99e53.jpg")).into(jiecaoplayerJc.ivThumb);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();

    }
}
