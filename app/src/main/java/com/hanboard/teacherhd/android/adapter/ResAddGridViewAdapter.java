package com.hanboard.teacherhd.android.adapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.LoadRes;


public class ResAddGridViewAdapter extends BaseAdapter{
 private String names[];
 private int icons[];
 private List<LoadRes> myList;
 private Context mContext;
 private TextView name_tv;
 private ImageView img;
 private View deleteView;
 private boolean isShowDelete;//根据这个变量来判断是否显示删除图标，true是显示，false是不显示
    public ResAddGridViewAdapter(Context mContext, List<LoadRes> myList) {
        this.mContext = mContext;
      //  this.names=names;
        this.myList = myList;
    }
    public void setIsShowDelete(boolean isShowDelete){
       this.isShowDelete=isShowDelete;
       notifyDataSetChanged();
    }
    @Override
    public int getCount() {
       return myList.size();
    }
    @Override
    public Object getItem(int position) {
       return myList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       convertView = LayoutInflater.from(mContext).inflate(R.layout.add_res_load_item, null);
       img = (ImageView) convertView.findViewById(R.id.res_load_img);
       name_tv = (TextView) convertView.findViewById(R.id.res_load_name);
       deleteView = convertView.findViewById(R.id.delete_markView);
       if(myList.get(position).format.equals("PPT")){
           img.setImageResource(R.mipmap.log_ppt);
       }else if(myList.get(position).format.equals("WORD")){
           img.setImageResource(R.mipmap.log_doc);
       }else if(myList.get(position).format.equals("EXCEL")){
           img.setImageResource(R.mipmap.log_xls);
       }else if(myList.get(position).format.equals("VIDEO")){
           img.setImageResource(R.mipmap.log_mp4);
       }else if(myList.get(position).format.equals("AUDIO")){
           img.setImageResource(R.mipmap.log_mp3);
       }else if(myList.get(position).format.equals("PDF")){
           img.setImageResource(R.mipmap.log_pdf);
       }
       name_tv.setText(myList.get(position).name);
       return convertView;
    }
}
