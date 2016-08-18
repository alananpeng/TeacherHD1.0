package com.hanboard.teacherhd.android.model;

import com.hanboard.teacherhd.android.entity.Account;
import com.hanboard.teacherhd.android.entity.Domine;
import com.hanboard.teacherhd.android.entity.MData;
import com.hanboard.teacherhd.android.entity.Status;
import com.hanboard.teacherhd.common.callback.IDataCallback;

/**
 * 项目名称：TeacherHD1.0
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/18 0018 18:04
 */
public interface IUserModel {
    /**获取用户信息*/
    void getUserIno(String accountId,IDataCallback<Domine> iDataCallback);
    /*验证原密码*/
    void checkOldPwd(String accountId,String userName,String oldPwd,IDataCallback<Domine> iDataCallback);
    /*修改密码*/
    void updatePwd(String accountId,String pwd,IDataCallback<Domine> iDataCallback);
    /*修改电话*/
    void updateTel(String accountId,String tel,IDataCallback<Domine> iDataCallback);
    /*修改邮箱*/
    void updateEmail(String accountId,String email,IDataCallback<Domine> iDataCallback);
    /**修改姓名*/
    void updateName(String accountId,String name,IDataCallback<Domine> iDataCallback);
    /*修改昵称*/
    void updataNickName(String accountId,String nickName,IDataCallback<Domine> iDataCallback);
}
