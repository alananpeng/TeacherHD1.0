package com.hanboard.teacherhd.android.adapter;

import android.content.Context;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.LoadRes;
import com.hanboard.teacherhd.lib.common.adapter.CommonAdapter;
import com.hanboard.teacherhd.lib.common.adapter.CommonViewHolder;

import java.util.List;

/**
 * 项目名称：TeacherHD1.0
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/10 0010 16:55
 */
public class ResAddGdAdapter extends CommonAdapter<LoadRes> {
    /**
     * @param context         Context
     * @param itemLayoutResId
     * @param dataSource      数据源
     */
    public ResAddGdAdapter(Context context, int itemLayoutResId, List<LoadRes> dataSource) {
        super(context, itemLayoutResId, dataSource);
    }
    @Override
    protected void fillItemData(CommonViewHolder viewHolder, int position, LoadRes item) {
        if(item.format.equals("PPT")){
            viewHolder.setImageForView(R.id.res_load_img,R.mipmap.log_ppt);
        }else if(item.format.equals("WORD")){
            viewHolder.setImageForView(R.id.res_load_img,R.mipmap.log_doc);
        }else if(item.format.equals("EXCEL")){
            viewHolder.setImageForView(R.id.res_load_img,R.mipmap.log_xls);
        }else if(item.format.equals("VIDEO")){
            viewHolder.setImageForView(R.id.res_load_img,R.mipmap.log_mp4);
        }else if(item.format.equals("AUDIO")){
            viewHolder.setImageForView(R.id.res_load_img,R.mipmap.log_mp3);
        }else if(item.format.equals("PDF")){
            viewHolder.setImageForView(R.id.res_load_img,R.mipmap.log_pdf);
        }else if(item.format.equals("ADD")){
            viewHolder.setImageForView(R.id.res_load_img,R.mipmap.log_add);
        }
        viewHolder.setTextForTextView(R.id.res_load_name,item.name);
    }
}
