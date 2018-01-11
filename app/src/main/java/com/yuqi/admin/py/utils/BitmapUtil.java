package com.yuqi.admin.py.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by Administrator on 2017/3/31.
 */

public class BitmapUtil {
    public static Bitmap imageCompressL(Bitmap bitmap, int mByte) {
        double targetwidth = Math.sqrt(mByte * 1000);
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
    public static Bitmap getFileBitmap(String path){
        return imageCompressL(BitmapFactory.decodeFile(path),1);
    }
}
