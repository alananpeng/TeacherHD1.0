package com.hanboard.teacherhd.android.adapter;

import android.content.Context;
import android.view.View;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Lessons;
import com.hanboard.teacherhd.lib.common.adapter.CommonAdapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonViewHolder;
import com.hanboard.teacherhd.lib.common.utils.StringUtil;
import com.hanboard.teacherhd.lib.common.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/9 0009 14:13
 */
public class NewLessonsGridViewAdapter extends CommonAdapter<Lessons> {

    private String subjectName;
    /**
     * @param context         Context
     * @param itemLayoutResId
     * @param dataSource      数据源
     */
    public NewLessonsGridViewAdapter(Context context, int itemLayoutResId, List<Lessons> dataSource,String subjectName) {
        super(context, itemLayoutResId, dataSource);
        this.subjectName = subjectName;
    }
    @Override
    protected void fillItemData(CommonViewHolder viewHolder, int position, Lessons item) {
        if(item.content.contentTitle.equals("添加课程")){
            viewHolder.setVisibility(R.id.new_item_add, View.VISIBLE);
            viewHolder.setVisibility(R.id.new_item_content,View.INVISIBLE);
        }
        viewHolder.setTextForTextView(R.id.new_lessons_title,item.content.contentTitle);
        viewHolder.setTextForTextView(R.id.new_lessons_subject,subjectName);
        viewHolder.setTextForTextView(R.id.new_lessons_suitobject,item.content.contentObject);
        viewHolder.setTextForTextView(R.id.new_lessons_courseHour,item.content.courseHour+"");
        viewHolder.setTextForTextView(R.id.new_lessons_createtime, TimeUtils.getTime(item.content.createTime,new SimpleDateFormat("yyyy-MM-dd")));
    }


}
