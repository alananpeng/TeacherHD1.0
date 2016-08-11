package com.hanboard.teacherhd.android.model;

import android.content.Context;

import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.common.callback.IDataCallback;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface IClassShowCouseModel {
  /**
   * 根据章节的id获取具体的备课信息
   * @param contentId
   * @param context
   * @param iDataCallback
     */
  void  getAllCouseWareInfo(String contentId,Context context, IDataCallback<Domine> iDataCallback);
 // void  downloadFile(String );
}
