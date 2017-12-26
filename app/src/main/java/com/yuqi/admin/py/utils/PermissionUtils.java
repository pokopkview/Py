package com.yuqi.admin.py.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/3/6.
 */

public class PermissionUtils {
    public static void check(Activity context, String permission,int request,ManifestListener listener){
        if (ContextCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context,new String[]{permission},request);
        }else {
               listener.onOpen();
        }
    }
    public static void check1(Activity context, String[] permission,int request,ManifestListener listener){
        List data = new ArrayList();
        for (int i=0;i<permission.length;i++){
            if (ContextCompat.checkSelfPermission(context,permission[i])!= PackageManager.PERMISSION_GRANTED){data.add(permission);}
        }
        String[] str = new String[data.size()];
        for (int i=0;i<data.size();i++){
            str[i] = data.get(i)+"";
        }
        if (data.size()!=0) {
            ActivityCompat.requestPermissions(context,str,request);
        }else {
            listener.onOpen();
        }
    }
    public static void onRequestPermissionsResult(int requestCode,int subRequest, String[] permissions,String checkpermissions, int[] grantResults, Activity context, final ManifestListener listener){
        if (requestCode==subRequest){
            if (permissions[0].equals(checkpermissions)&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                listener.onOpen();
            }else {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setMessage("该应用必须开启授权，不开启将无法正常工作！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onClose();
                            }
                        }).create();
                dialog.show();
            }
        }
    }
    public interface ManifestListener{
        public void onOpen();
        public void onClose();
    }
}
