package com.yuqi.admin.py.utils;

import android.content.Context;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/12/29.
 */
public class TestUTF {
      public static String test( String str){
            try {
                String strGBK = URLEncoder.encode(str, "GBK");
                System.out.println(strGBK);
                String strUTF8 = URLDecoder.decode(str, "UTF-8");
                System.out.println(strUTF8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
          return str;
      }
}