package com.hanboard.teacherhd.android.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.Status;
import com.hanboard.teacherhd.android.model.IUserModel;
import com.hanboard.teacherhd.android.model.impl.UserModelImpl;
import com.hanboard.teacherhd.common.base.BaseActivity;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.view.EditDialog;
import com.hanboard.teacherhd.common.view.UpDatePwdDialog;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.picasso.Picasso;

public class MineActivity extends BaseActivity implements IDataCallback<Domine> {
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.user_img)
    ImageView userImg;
    private EditDialog editNickName;
    private UpDatePwdDialog pwdDialog;
    private String originalPassword;
    private String updatePassword;
    private String verifyPassword;
    private IUserModel iUserModel;
    private String accountId;
    private String accountName;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);
        iUserModel = new UserModelImpl();
        loadData();
        accountId = (String) SharedPreferencesUtils.getParam(me, "id", "");
    }

    private void loadData() {
        iUserModel.getUserIno(accountId, this);
    }

    @Override
    protected void handler(Message msg) {

    }
    @OnClick({R.id.nick_name, R.id.name, R.id.tel, R.id.email, R.id.password, R.id.login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nick_name:
                upDateNickName();
                break;
            case R.id.name:
                upDateName();
                break;
            case R.id.tel:
                upDateTel();
                break;
            case R.id.email:
                upDateEmail();
                break;
            case R.id.password:
                upDatePwd();
                break;
            case R.id.login_out:
                startActivity(LoginActivity.class);
                break;
        }
    }
    /*修改密码*/
    private void upDatePwd() {
        pwdDialog = new UpDatePwdDialog.Builder(me, new UpDatePwdDialog.Builder.LeaveDialogListener() {
            @Override
            public void ok(View v, String original_password, String update_password, String verify_password) {
                originalPassword = original_password;
                updatePassword = update_password;
                verifyPassword = verify_password;
                if(updatePassword.equals(verifyPassword)){
                    iUserModel.checkOldPwd(accountId, accountName, originalPassword, new IDataCallback<Domine>() {
                        @Override
                        public void onSuccess(Domine data) {
                            if (data instanceof Status){
                                iUserModel.updatePwd(accountId,verifyPassword,this);
                            }
                        }
                        @Override
                        public void onError(String msg, int code) {
                            Toast.makeText(me,"原密码输入错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(me,"请保持两次密码输入一致",Toast.LENGTH_SHORT).show();
                }
            }
        }).create();
        pwdDialog.show();
    }

    /*修改邮箱*/
    private void upDateEmail() {
        editNickName = new EditDialog.Builder(me, "修改电子邮箱", "请输入您的电子邮箱", new EditDialog.Builder.LeaveDialogListener() {
            @Override
            public void ok(View v, String res) {
               iUserModel.updateEmail(accountId,res,MineActivity.this);
            }
        }).create();
        editNickName.show();
    }

    /*修改手机*/
    private void upDateTel() {
        editNickName = new EditDialog.Builder(me, "修改电话号码", "请输入您的电话号码", new EditDialog.Builder.LeaveDialogListener() {
            @Override
            public void ok(View v, String res) {
                iUserModel.updateTel(accountId,res,MineActivity.this);
            }
        }).create();
        editNickName.show();
    }

    /*修改姓名*/
    private void upDateName() {
        editNickName = new EditDialog.Builder(me, "修改姓名", "请输入您的姓名", new EditDialog.Builder.LeaveDialogListener() {
            @Override
            public void ok(View v, String res) {
                iUserModel.updateName(accountId,res,MineActivity.this);
            }
        }).create();
        editNickName.show();
    }
    /**
     * 修改昵称
     */
    private void upDateNickName() {
        editNickName = new EditDialog.Builder(me, "修改昵称", "请输入您的昵称", new EditDialog.Builder.LeaveDialogListener() {
            @Override
            public void ok(View v, String res) {
                iUserModel.updataNickName(accountId,res,MineActivity.this);
            }
        }).create();
        editNickName.show();
    }
    @Override
    public void onSuccess(Domine data) {
        if (data instanceof Account) {
            Picasso.with(me).load(((Account) data).avatarUrl).into(userImg);
            nickName.setText(((Account) data).nickName);
            name.setText(((Account) data).trueName);
            tel.setText(((Account) data).phone);
            email.setText(((Account) data).email);
            accountName = ((Account) data).accountName;
        }else if(data instanceof Status){
            Toast.makeText(me,"修改成功",Toast.LENGTH_SHORT).show();
            editNickName.dismiss();
            pwdDialog.dismiss();
        }
    }

    @Override
    public void onError(String msg, int code) {
        Toast.makeText(me,msg,Toast.LENGTH_SHORT).show();
    }
}
