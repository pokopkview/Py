package com.yuqi.admin.py.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Liang on 2017/3/3.
 */

public abstract class HttpHandle extends Handler{
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case HttpUtil.HTTP_SUCCESE:
                onSuccese(msg.obj + "");
                break;
            case HttpUtil.HTTP_FAILD:
                onFaild(msg.obj + "");
                break;
            case HttpUtil.HTTP_START:
                onStart();
                break;
            case HttpUtil.HTTP_ERRO:
                onErro((Exception) msg.obj,msg.obj + "");
                break;
            case HttpUtil.HTTP_LOADING:
                onLoading(msg.obj + "");
                break;
        }
    }

    public abstract void onStart();

    public abstract void onSuccese(String str);

    public abstract void onFaild( String str);

    public void onErro(Exception e,String erro) {
    }
    public void onLoading(String progress) {
    }
}
