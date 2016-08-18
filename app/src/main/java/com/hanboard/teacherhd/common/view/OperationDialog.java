package com.hanboard.teacherhd.common.view;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.hanboard.teacherhd.R;
/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/8 0008 11:43
 */
public class OperationDialog extends Dialog{

    public OperationDialog(Context context) {
        super(context);
    }

    public OperationDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder  implements View.OnClickListener{
        private LeaveDialogListener leaveDialogListener;
        private Context context;
        private TextView mDelete,mEdit;
        public Builder(Context context,LeaveDialogListener leaveDialogListener) {
            this.context = context;
            this.leaveDialogListener = leaveDialogListener;
        }
        public OperationDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final OperationDialog dialog = new OperationDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.operation_dialog, null);
            dialog.addContentView(layout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.setContentView(layout);
            mDelete = (TextView)layout.findViewById(R.id.dialog_delete);
            mEdit = (TextView)layout.findViewById(R.id.dialog_edit);
            mDelete.setOnClickListener(this);
            mEdit.setOnClickListener(this);
            return dialog;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_delete:
                    leaveDialogListener.delete(v);
                    break;
                case R.id.dialog_edit:
                    leaveDialogListener.edit(v);
                    break;
            }
        }

        public interface LeaveDialogListener{
            void delete(View v);
            void edit(View v);
        }
    }
}
