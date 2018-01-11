//package com.yuqi.admin.py;
//
//import android.app.Activity;
//import android.app.LocalActivityManager;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioGroup;
//
//import com.yuqi.admin.py.activity.AnliActivity;
//import com.yuqi.admin.py.activity.ShouyActivity;
//import com.yuqi.admin.py.activity.WomActivity;
//import com.yuqi.admin.py.activity.YuqActivity;
//import com.yuqi.admin.py.utils.ToastUtil;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//public class MainActivity extends Activity{
//    List<Intent> intentList;
//    LocalActivityManager activityManager;
//
//    String[] tabIds={"home","task","record","profile"};
//
//    private RadioGroup radioGroup;
//    private ViewPager viewpager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //activityҳ
//        activityManager=new LocalActivityManager(this,true);
//        activityManager.dispatchCreate(savedInstanceState);
//
//        intentList=new LinkedList<Intent>();
//        Intent homeIntent=new Intent(this,ShouyActivity.class);
//        intentList.add(homeIntent);
//
//        Intent taskIntent=new Intent(this,YuqActivity.class);
//        intentList.add(taskIntent);
//
//        Intent recordIntent=new Intent(this,AnliActivity.class);
//        intentList.add(recordIntent);
//
//        Intent profileIntent=new Intent(this,WomActivity.class);
//        intentList.add(profileIntent);
//
//
//
//        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
//
//            @Override
//            public void onCheckedChanged(RadioGroup arg0, int checkedId) {
//                switch (checkedId) {
//                    case R.id.btn_home:
//                        viewpager.setCurrentItem(0);
//                        break;
//                    case R.id.btn_classify:
//                        viewpager.setCurrentItem(1);
//                        break;
//                    case R.id.btn_discover:
//                        viewpager.setCurrentItem(2);
//                        break;
//                    case R.id.btn_me:
//                        viewpager.setCurrentItem(3);
//                        break;
//                }
//            }
//
//        });
//
//        //����viewpager
//        viewpager=(ViewPager)findViewById(R.id.viewpager);
//        viewpager.setAdapter(new MyPageAdapter());
//        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
//
//            @Override
//            public void onPageScrollStateChanged(int position) {
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch(position){
//                    case 0:
//                        radioGroup.check(R.id.btn_home);
//                        break;
//                    case 1:
//                        radioGroup.check(R.id.btn_classify);
//                        break;
//                    case 2:
//                        radioGroup.check(R.id.btn_discover);
//                        break;
//                    case 3:
//                        radioGroup.check(R.id.btn_me);
//                        break;
//                }
//            }
//
//        });
//
//    }
//
//    protected void onResume(){
//        super.onResume();
//        activityManager.dispatchResume();
//    }
//
//    protected void onPause(){
//        super.onPause();
//        activityManager.dispatchPause(isFinishing());
//    }
//
//    protected void onStop(){
//        super.onStop();
//        activityManager.dispatchStop();
//    }
//
//    protected void onDestory(){
//        super.onDestroy();
//        activityManager.dispatchDestroy(isFinishing());
//    }
//
//    private class MyPageAdapter extends PagerAdapter {
//
//        HashMap<String,View> idViewMap;
//
//        public MyPageAdapter(){
//            idViewMap=new HashMap<String,View>();
//        }
//
//        public int getItemPosition(Object object) {
//            // TODO Auto-generated method stub
//            return POSITION_NONE;
//        }
//
//        @Override
//        public void destroyItem(View view, int position, Object arg2) {
//            ViewPager viewPager = ((ViewPager) view);
//            View tabView=idViewMap.get(tabIds[position]);
//            viewPager.removeView(tabView);
//
//            activityManager.destroyActivity(tabIds[position], true);
//            idViewMap.remove(tabIds[position]);
//        }
//
//        @Override
//        public void finishUpdate(View arg0) {
//        }
//
//        @Override
//        public int getCount() {
//            return intentList.size();
//        }
//
//        @Override
//        public Object instantiateItem(View view, int position) {
//            ViewPager viewPager = ((ViewPager) view);
//
//            View tabView=idViewMap.get(tabIds[position]);
//            if(tabView==null){
//                tabView=activityManager.startActivity(tabIds[position], intentList.get(position)).getDecorView();
//                idViewMap.put(tabIds[position], tabView);
//            }else{
//                ViewGroup tabViewParent = (ViewGroup) tabView.getParent();
//                if (tabViewParent != null) {
//                    tabViewParent.removeAllViewsInLayout();
//                }
//            }
//            viewPager.addView(tabView);
//
//            return tabView;
//        }
//
//        @Override
//        public boolean isViewFromObject(View arg0, Object arg1) {
//            return arg0 == arg1;
//        }
//
//        @Override
//        public void restoreState(Parcelable arg0, ClassLoader arg1) {
//        }
//
//        @Override
//        public Parcelable saveState() {
//            return null;
//        }
//
//        @Override
//        public void startUpdate(View arg0) {
//        }
//    }
//}
