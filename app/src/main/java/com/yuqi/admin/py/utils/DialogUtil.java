package com.yuqi.admin.py.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuqi.admin.py.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Admin on 2017/12/1.
 */

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


//    public static void checkUpdate(final Context context, final String url, final HashMap map){
//        final Dialog dialog = new Dialog(context);
//        dialog.setCancelable(false);
//        final View view = View.inflate(context,R.layout.dialog_update,null);
//        final TextView progress = (TextView)view.findViewById(R.id.progress);
//        final ProgressBar bar = (ProgressBar)view.findViewById(R.id.progressbar);
//        StringUtil.fuzhi(view,R.id.content,map.get("version_content")+"");
//        StringUtil.fuzhi(view,R.id.content,"发布时间："+map.get("version_time")+"");
//        StringUtil.fuzhi(view,R.id.content,"版本大小："+map.get("version_time")+"MB");
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                switch (v.getId()){
//                    case R.id.submit:
//                        view.findViewById(R.id.version_content).setVisibility(View.GONE);
//                        bar.setVisibility(View.VISIBLE);
//                        v.setVisibility(View.GONE);
//                        HttpUtil.down(url, "/sdcard/xfj/", new HttpHandle() {
//                            @Override
//                            public void onStart() {
//
//                            }
//
//                            @Override
//                            public void onLoading(String str) {
//                                super.onLoading(str);
//                                progress.setText(str+"%");
//                                bar.setProgress(Integer.parseInt(str));
//                            }
//
//                            @Override
//                            public void onSuccese(String str) {
//                                final Handler handler = new Handler(){
//                                    @Override
//                                    public void handleMessage(Message msg) {
//                                        super.handleMessage(msg);
//                                        switch (msg.what){
//                                            case 0:
//                                                FileUtil.openFile(new File("/sdcard/xfj/xfj.apk"),context);
//                                                break;
//                                            case 1:
//                                                ToastUtil.show(context,"更新失败，请重新下载补丁或下载完整APK");
//                                                v.setVisibility(View.VISIBLE);
//                                                dialog.cancel();
//                                                break;
//                                            default:
//                                                ToastUtil.show(context,"更新失败，请重新下载补丁或下载完整APK");
//                                                dialog.cancel();
//                                                v.setVisibility(View.VISIBLE);
//                                                break;
//                                        }
//                                    }
//                                };
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        int result = PatchUtils.getInstance().patch(SystemUtil.getPackagePath(context),"/sdcard/xfj/xfj.apk","/sdcard/xfj/xfj.patch");
//                                        handler.obtainMessage(result).sendToTarget();
//                                    }
//                                }).start();
//                            }
//
//                            @Override
//                            public void onFaild(String str) {
//                                ToastUtil.show(context,"更新失败,请检查网络");
//                                v.setVisibility(View.VISIBLE);
//                                dialog.cancel();
//                            }
//                        });
//                        break;
//                    case R.id.cancle:
//                        dialog.cancel();
//                        break;
//                }
//            }
//        };
//        view.findViewById(R.id.submit).setOnClickListener(listener);
//        view.findViewById(R.id.cancle).setOnClickListener(listener);
//        dialog.setContentView(view);
//        dialog.show();
//    }

}
