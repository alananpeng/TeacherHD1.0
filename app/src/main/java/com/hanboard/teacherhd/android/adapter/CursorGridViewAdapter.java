package com.hanboard.teacherhd.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Content;
import com.hanboard.teacherhd.android.entity.CourseWare;
import com.hanboard.teacherhd.lib.common.adapter.CommonAdapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CursorGridViewAdapter extends CommonAdapter<CourseWare> {
    /**
     * @param context         Context
     * @param itemLayoutResId
     * @param dataSource      数据源
     */
    public CursorGridViewAdapter(Context context, int itemLayoutResId, List<CourseWare> dataSource) {
        super(context, itemLayoutResId, dataSource);
    }

    @Override
    protected void fillItemData(CommonViewHolder viewHolder, int position, CourseWare item) {
        viewHolder.setTextForTextView(R.id.cursor_tv_item,item.courseWareTitle);
        setLog(viewHolder,position);
        //setOnclickListener(viewHolder,position);

    }

    private void setOnclickListener(CommonViewHolder viewHolder, final int position) {
          viewHolder.getContentView().setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Toast.makeText(mContext.getApplicationContext(), "点击了"+position, Toast.LENGTH_SHORT).show();
              }
          });
    }

    private void setLog(CommonViewHolder viewHolder,int position) {
        if (position==0)

            viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_mp4);
        else if (position==1)
            viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_mp3);
        else if (position==2)

            viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_pdf);
        else if (position==3)

            viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_doc);
        else if (position==4)

            viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_xls);
        else if (position==5)
            viewHolder.setImageForView(R.id.cursor_iamge_item,R.mipmap.log_ppt);
    }
}
