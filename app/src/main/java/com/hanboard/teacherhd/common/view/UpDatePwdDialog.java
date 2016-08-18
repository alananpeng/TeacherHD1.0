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
public class UpDatePwdDialog extends Dialog{

    public UpDatePwdDialog(Context context) {
        super(context);
    }

    public UpDatePwdDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder  implements View.OnClickListener{
        private LeaveDialogListener leaveDialogListener;
        private Context context;
        private EditText original_password,update_password,verify_password;
        private Button mOk;
        public Builder(Context context,LeaveDialogListener leaveDialogListener) {
            this.context = context;
            this.leaveDialogListener = leaveDialogListener;
        }
        public UpDatePwdDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final UpDatePwdDialog dialog = new UpDatePwdDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.update_password_dialog, null);
            dialog.addContentView(layout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.setContentView(layout);
            original_password = (EditText)layout.findViewById(R.id.original_password);
            update_password = (EditText)layout.findViewById(R.id.update_password);
            verify_password = (EditText)layout.findViewById(R.id.verify_password);
            mOk = (Button)layout.findViewById(R.id.uppwd_dialog_ok);
            mOk.setOnClickListener(this);
            return dialog;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.uppwd_dialog_ok:
                    leaveDialogListener.ok(v,original_password.getText().toString(),update_password.getText().toString().toString(),verify_password.getText().toString());
                    break;
            }
        }
        public interface LeaveDialogListener{
            void ok(View v, String original_password,String update_password,String verify_password);
        }
    }
}
