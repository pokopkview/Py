package com.yuqi.admin.py.view;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtil {

    private static ProgressDialog pd;
    public static void start(Context context){
        pd = new ProgressDialog(context);
        pd.setMessage("请求数据中，请稍后。。");
        pd.show();;
    }
    public static void finish(){
        pd.cancel();
    }
}
