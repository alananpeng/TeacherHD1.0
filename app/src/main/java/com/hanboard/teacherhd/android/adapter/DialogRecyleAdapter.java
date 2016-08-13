package com.hanboard.teacherhd.android.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.PrepareSelectCourse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class DialogRecyleAdapter extends RecyclerView.Adapter<DialogRecyleAdapter.MyViewhoder>{
    private List<PrepareSelectCourse> prepareSelectCourses;
    private Context context;
    private Handler handler=new Handler();

    public DialogRecyleAdapter(List<PrepareSelectCourse> prepareSelectCourses, Context context, Handler handler) {
        this.prepareSelectCourses = prepareSelectCourses;
        this.context = context;
        this.handler=handler;
    }

    @Override
    public MyViewhoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewhoder(LayoutInflater.from(context).inflate(R.layout.item_title,parent,false));
    }
    @Override
    public void onBindViewHolder(final MyViewhoder holder, final int position) {
            holder.textView.setText(prepareSelectCourses.get(position).getSubjectName());
            holder.sectionName.setText(prepareSelectCourses.get(position).getSectionName());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message message=new Message();
                    message.obj = prepareSelectCourses.get(position);
                    handler.sendMessage(message);
                }
            });
    }
    @Override
    public int getItemCount() {
        return prepareSelectCourses.size();
    }

    static  class MyViewhoder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout ;
        TextView textView,sectionName;
        public MyViewhoder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.item_text);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.item_layout);
            sectionName= (TextView) itemView.findViewById(R.id.item_tv_cousersuitObject);
        }
  }
}
