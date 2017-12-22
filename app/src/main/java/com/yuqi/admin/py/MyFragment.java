package com.yuqi.admin.py;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuqi.admin.py.utils.ClickUtils;
import com.yuqi.admin.py.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2017/3/1.
 */

public abstract class MyFragment extends Fragment {

    public View main;
    public Map<String,Object> skipinfo = new HashMap<>();
    public abstract int layoutId();
    public TextView title;
    public void onLayoutClick(View v){
        if (ClickUtils.isContinuClick()) return;
        onClick(v);
    };
    public abstract void initView(View v);
    public abstract void onClick(View v);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (main == null){
            main = inflater.inflate(layoutId(),null);
//            DialogUtil.start(main.getContext());
            initView(main);
        }
        return main;
    }
    public boolean showBack(){
        return true;
    }
    public String title_text(){
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!showBack())main.findViewById(R.id.back).setVisibility(View.INVISIBLE);
        title = (TextView)main.findViewById(R.id.title);
        if (!StringUtil.isEmpty(title_text())&&title!=null){
            title.setText(title_text());
        }
    }

    @Override
    public void onDestroy() {
//        if (main !=null) ((ViewGroup) main.getParent()).removeView(main);
        super.onDestroy();
    }

}
