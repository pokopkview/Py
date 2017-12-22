package com.yuqi.admin.py;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.yuqi.admin.py.utils.ClickUtils;
import com.yuqi.admin.py.utils.StringUtil;

/**
 * Created by Administrator on 2017/11/23.
 */
public abstract class BaseMainActivity  extends Activity {
    public abstract void onClick(View v);
    public void onLayoutClick(View v){
        onClick(v);
    }
}
