package com.hanboard.teacherhd.android.adapter;

import android.content.Context;
import android.view.View;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.lib.common.adapter.CommonAdapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonViewHolder;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class SearchListAdapter extends CommonAdapter<String> {
    /**
     * @param context         Context
     * @param itemLayoutResId
     * @param dataSource      数据源
     */
    public SearchListAdapter(Context context, int itemLayoutResId, List<String> dataSource) {
        super(context, itemLayoutResId, dataSource);
    }

    @Override
    protected void fillItemData(CommonViewHolder viewHolder, final int position, final String item) {
        viewHolder.setTextForTextView(R.id.item_tv_search,item);
        viewHolder.setOnClickListener(R.id.item_tv_search, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(mContext,item+position);
            }
        });
    }
}
