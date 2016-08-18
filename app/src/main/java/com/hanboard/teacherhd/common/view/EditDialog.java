package com.hanboard.teacherhd.common.view;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hanboard.teacherhd.R;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/8 0008 11:43
 */
public class EditDialog extends Dialog{

    public EditDialog(Context context) {
        super(context);
    }

    public EditDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder  implements View.OnClickListener{
        private LeaveDialogListener leaveDialogListener;
        private Context context;
        private TextView mTitle;
        private EditText mEdit;
        private Button mOk;
        private String title;
        private String hint;
        public Builder(Context context,String title,String hint,LeaveDialogListener leaveDialogListener) {
            this.context = context;
            this.leaveDialogListener = leaveDialogListener;
            this.title = title;
            this.hint  = hint;
        }
        public EditDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final EditDialog dialog = new EditDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.edit_dialog, null);
            dialog.addContentView(layout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.setContentView(layout);
            mTitle = (TextView)layout.findViewById(R.id.edit_dialog_title);
            mEdit = (EditText)layout.findViewById(R.id.edit_dialog_edittext);
            mOk = (Button)layout.findViewById(R.id.edit_dialog_ok);
            mTitle.setText(title);
            mEdit.setHint(hint);
            mOk.setOnClickListener(this);
            return dialog;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_dialog_ok:
                    leaveDialogListener.ok(v,mEdit.getText().toString());
                    break;
            }
        }

        public interface LeaveDialogListener{
            void ok(View v,String res);
        }
    }
}
