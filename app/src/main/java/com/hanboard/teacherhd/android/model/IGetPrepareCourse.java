package com.hanboard.teacherhd.android.model;

import com.hanboard.teacherhd.android.entity.Elements;
import com.hanboard.teacherhd.android.entity.PrepareSelectCourse;
import com.hanboard.teacherhd.common.callback.IDataCallback;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface IGetPrepareCourse {
   void  getPrepareCourse(String accountId, String pageNum, IDataCallback<Elements<PrepareSelectCourse>> elementsIDataCallback);
}
