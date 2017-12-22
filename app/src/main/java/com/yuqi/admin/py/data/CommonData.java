package com.yuqi.admin.py.data;

import android.content.Intent;
import android.graphics.Bitmap;

import com.yuqi.admin.py.bean.LoginBean;

/**
 * Created by Administrator on 2017/4/26.
 */

public class CommonData {
    public static int windowWidth = 0;
    public static int windowHeight = 0;

    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxd930ea5d5a258f4f";


    public final static String URL="http://139.224.238.234:8021/pyjh/";
//    public final static String  URL="http://192.168.1.117:7755/pyjh/";

    public static String state;          //登录成功状态

    public static String accounts;            //用户账号
    public static String password;            //用户密码
    public static String token;            //临时id，唯一标识
    public static int user_id;             //用户ID
    public static String portrait;            //用户头像
    public static String nickname;             //昵称
    public static int gender;          //性别
    public static String mailbox;    //邮箱
    public static String phoneNumber;          //手机号
    public static String companyName;         //公司名
    public static double balance;       //余额
    public static String creationTime;          //创建时间
    public static int company_integral;          //积分

    public static final int TYPENUM_CONTENT = 4;
    public static final int TUPIAN = 1;
    public static final int SHIPIAN = 2;
    public static final int LUYIN = 3;

    public static final String LISTVIEW_FIRSTLOAD_NULL = "没有任何记录" ;
    public static final String LISTVIEW_LOADING = "加载中，请稍后" ;
    public static final String LISTVIEW_LOADING_FAILD = "加载失败，点击重新加载" ;
    public static final String LISTVIEW_REFRESHING_TOUCH_DOWN = "下拉加载" ;
    public static final String LISTVIEW_REFRESHING_TOUCH_HALF = "释放刷新" ;
    public static final String LISTVIEW_REFRESHING = "正在刷新，请稍后" ;

}
