package com.hanboard.teacherhd.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.android.entity.CourseWare;
import com.hanboard.teacherhd.android.entity.Exercises;
import com.hanboard.teacherhd.lib.common.adapter.CommonAdapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonViewHolder;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CursorGridViewAdapter<T> extends CommonAdapter<T> {
    /**
     * @param context         Context
     * @param itemLayoutResId
     * @param dataSource      数据源
     */
    public CursorGridViewAdapter(Context context, int itemLayoutResId, List<T> dataSource) {
        super(context, itemLayoutResId, dataSource);
    }

    @Override
    protected void fillItemData(CommonViewHolder viewHolder, int position, T item) {
        if (item != null) {
            if (item instanceof Exercises){
                String exercisesTitle = ((Exercises) item).exercisesTitle;
                String exercisesTypeString = ((Exercises) item).exercisesType;
                int exercisesTypeInt=Integer.parseInt(exercisesTypeString);
                viewHolder.setTextForTextView(R.id.cursor_tv_item,exercisesTitle);
                if (exercisesTypeInt==1)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_ppt);
                else if (exercisesTypeInt==2)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_word);
                else if (exercisesTypeInt==4)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_xls);
                else if (exercisesTypeInt==5)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_mp4);
                else if (exercisesTypeInt==6)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_mp3);
                else  if (exercisesTypeInt==8)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_pdf);
                else  viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_pdf);
            }else if (item instanceof  CourseWare){
                String courseWareTitle = ((CourseWare) item).courseWareTitle;
                String courseWareTypeString = ((CourseWare) item).courseWareType;
                int courseWareTypeInt=Integer.parseInt(courseWareTypeString);
                viewHolder.setTextForTextView(R.id.cursor_tv_item,courseWareTitle);
                if (courseWareTypeInt==1)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_ppt);
                else if (courseWareTypeInt==2)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_word);
                else if (courseWareTypeInt==4)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_xls);
                else if (courseWareTypeInt==5)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_mp4);
                else if (courseWareTypeInt==6)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_mp3);
                else  if (courseWareTypeInt==8)
                    viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_pdf);
            }
        }else  viewHolder.setTextForTextView(R.id.cursor_tv_item,"还没有上传资料");

    }



    private void setOnclickListener(CommonViewHolder viewHolder, final int position) {
          viewHolder.getContentView().setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Toast.makeText(mContext.getApplicationContext(), "点击了"+position, Toast.LENGTH_SHORT).show();
              }
          });
    }


}
