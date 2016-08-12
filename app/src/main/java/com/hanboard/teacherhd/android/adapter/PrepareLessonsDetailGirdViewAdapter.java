package com.hanboard.teacherhd.android.adapter;

import android.content.Context;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.android.entity.Elements;
import com.hanboard.teacherhd.android.entity.JiaocaiVersion;
import com.hanboard.teacherhd.android.entity.PrepareChapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonAdapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonViewHolder;
import com.hanboard.teacherhd.lib.common.utils.TimeUtils;

import java.util.List;
/**
 * 项目名称：TeacherHD
 * 类描述：备课详情Adapter
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/25 0025 11:30
 */
public class PrepareLessonsDetailGirdViewAdapter extends CommonAdapter<PrepareChapter> {


    /**
     * @param context         Context
     * @param itemLayoutResId
     * @param dataSource      数据源
     */
    public PrepareLessonsDetailGirdViewAdapter(Context context, int itemLayoutResId, List<PrepareChapter> dataSource) {
        super(context, itemLayoutResId, dataSource);
    }

    @Override
    protected void fillItemData(CommonViewHolder viewHolder, int position, PrepareChapter item) {

            viewHolder.setTextForTextView(R.id.new_lessons_subject,item.content.getContentObject());
            viewHolder.setTextForTextView(R.id.new_lessons_title,item.getTitle());
            viewHolder.setTextForTextView(R.id.new_lessons_suitobject,item.content.getContentObject());
            viewHolder.setTextForTextView(R.id.new_lessons_courseHour,item.content.getCourseHour()+"");
            viewHolder.setTextForTextView(R.id.new_lessons_createtime, TimeUtils.getTime(item.getCreateTime(),TimeUtils.DATE_FORMAT_DATE)+"         最后修改:"+item.content.getUpdateTime());
            viewHolder.setTextForTextView(R.id.new_lessons_title,item.getTitle());

    }
}
