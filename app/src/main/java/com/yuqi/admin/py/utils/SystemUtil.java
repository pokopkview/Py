package com.yuqi.admin.py.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

/**
 * Created by Admin on 2017/3/20.
 * 版本更新用到
 */

public class SystemUtil {
    public static WifiManager wifiManager;
    public static boolean isWifiOpened(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();
    }
    public static void showWifiDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Tip");
        builder.setMessage("未打开Wifi,是否进行设置？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                toggleWiFi(true);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
    //获取版本号
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    public static void toggleWiFi(boolean enabled) {
        wifiManager.setWifiEnabled(enabled);
    }
    public static String getSystemInfoIMEI(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Log.i("tm", tm.getDeviceId());
        return tm.getDeviceId() == null ? "" : tm.getDeviceId();
    }

    public static String getPackagePath(Context context){
        List<ApplicationInfo> installList = context.getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for(ApplicationInfo info :installList){
            if (info.packageName.equals(context.getPackageName())){
                return info.sourceDir;
            }
        }
        return  "";
    }
}
