package com.hanboard.teacherhd.android.activity;

import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.common.base.BaseActivity;
import butterknife.BindView;

public class TestActivity extends BaseActivity {

    @BindView(R.id.list_item)
    ListView listItem;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);


    }

    @Override
    protected void handler(Message msg) {

    }
}
