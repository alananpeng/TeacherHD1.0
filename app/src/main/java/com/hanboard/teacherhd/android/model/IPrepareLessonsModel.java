package com.hanboard.teacherhd.android.model;

import com.hanboard.teacherhd.android.entity.Chapter;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.lib.common.exception.DataException;

import java.util.List;
import java.util.Map;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/26 0026 14:21
 */
public interface IPrepareLessonsModel {
    /**
     * 获取课程版本列表
     */
    void getChapterList(String tid,String vid,String sid,String key,IDataCallback<Domine> iDataCallback);
}
