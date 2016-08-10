package com.hanboard.teacherhd.android.adapter;

import android.content.Context;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.android.entity.JiaocaiVersion;
import com.hanboard.teacherhd.lib.common.adapter.CommonAdapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonViewHolder;

import java.util.List;
/**
 * 项目名称：TeacherHD
 * 类描述：备课详情Adapter
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/25 0025 11:30
 */
public class PrepareLessonsDetailGirdViewAdapter extends CommonAdapter<Content> {
    /**
     * @param context         Context
     * @param itemLayoutResId
     * @param dataSource      数据源
     */
    public PrepareLessonsDetailGirdViewAdapter(Context context, int itemLayoutResId, List<Content> dataSource) {
        super(context, itemLayoutResId, dataSource);
    }
    @Override
    protected void fillItemData(CommonViewHolder viewHolder, int position, Content item) {

    }
}
