package com.hanboard.teacherhd.android.adapter;

import android.content.Context;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.TextBook;
import com.hanboard.teacherhd.lib.common.adapter.CommonAdapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonViewHolder;

import java.util.List;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/5 0005 11:39
 */
public class TextBookGridViewAdapter extends CommonAdapter<TextBook> {
    /**
     * @param context         Context
     * @param itemLayoutResId
     * @param dataSource      数据源
     */
    public TextBookGridViewAdapter(Context context, int itemLayoutResId, List<TextBook> dataSource) {
        super(context, itemLayoutResId, dataSource);
    }
    @Override
    protected void fillItemData(CommonViewHolder viewHolder, int position, TextBook item) {
        viewHolder.setTextForTextView(R.id.textbook_version,item.versionName);
        viewHolder.setTextForTextView(R.id.textbook_date,item.publishDate);
    }
}
