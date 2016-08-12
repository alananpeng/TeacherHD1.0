package com.hanboard.teacherhd.android.activity;

import android.os.Bundle;
import android.os.Message;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.broadCast.NetBrodeCaset;
import com.hanboard.teacherhd.android.model.InetEventHandler;
import com.hanboard.teacherhd.common.base.BaseActivity;
import com.hanboard.teacherhd.lib.common.utils.NetUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import name.quanke.app.libs.emptylayout.EmptyLayout;

public class EmptyClass extends BaseActivity implements InetEventHandler{

    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_class);
        NetBrodeCaset.mListeners.add(this);
        ButterKnife.bind(this);
        int error = getIntent().getIntExtra("error", 1);
        if (error==1){
            emptyLayout.showError();
        }
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {

    }

    @Override
    protected void handler(Message msg) {

    }

    @Override
    public void onWifiNet() {
        if (NetUtil.getNetworkState(this)==NetUtil.NETWORN_WIFI){
            startActivity(MainActivity.class);
        }
    }
}
