package com.hanboard.teacherhd.config;

/**
 * 项目名称：TeacherHD
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/9 0009 16:44
 */
public class Urls {
    /**host*/
    public static final String SERVER_HOST = "http://172.17.1.25";
    /*port*/
    public static final String SERVER_PORT = "8084";
    /**SERVER_ADDRESS*/
    public static final String SERVER_ADDRESS = SERVER_HOST+":"+SERVER_PORT+"/";
    /** 登录接口 */
    public static final String URL_LOGIN = SERVER_ADDRESS+"api/v1/user/login";
    /**获取所有科目*/
    public static final String URL_ALLSUBJECT = SERVER_ADDRESS+"api/v1/subject/queryAllsubject";
    /**获取所有学段*/
    public static final String URL_ALLSECTION = SERVER_ADDRESS+"api/v1/section/querySection";
    /**获取年级*/
    public static final String URL_ALLSUITOBJECT = SERVER_ADDRESS+"api/v1/suitObject/querySuitObject";
    /**获取教材*/
    public static final String URL_ALLTEXTBOOK = SERVER_ADDRESS+"api/v1/teachbook/queryTeachbook";
    /*教师添加教材*/
    public static final String URL_ADDTEXTBOOK = SERVER_ADDRESS+"api/v1/teachbookaccount/addTeachBookAccount";
    /**根据账户获取教材*/
    public static final String URL_GETCHAPTER = SERVER_ADDRESS+"api/v1/teachbookaccount/queryAllBookChapterByAccountId";
    /**通过章节id 账号id 课本id获取该章节下所有备课信息*/
    public static final String URL_GETLESSONS = SERVER_ADDRESS+"api/v1/chaptercontent/findByChapterId";
    /** App更新地址 */
    public static final String UPDATE_URL = "";
    /**
     * 获取授课课本信息
     */
    public static  final  String URL_GETPREPARECOUSER=SERVER_ADDRESS+"api/v1/teachbookaccount/queryAllBookByAccountId";
    /**
     * 获取个人全部教材课本及其章节
     */
    public static  final  String URL_GETAllCURSOR$CHAPTER=SERVER_ADDRESS+"api/v1/chapter/queryChapter";
}
