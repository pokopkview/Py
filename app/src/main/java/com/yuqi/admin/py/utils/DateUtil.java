package com.yuqi.admin.py.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.NumberPicker;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Admin on 2017/12/1
 */

public class DateUtil {
    //通过格式 设置当前时间
    public static String getDate(String type) {
        return new SimpleDateFormat(type).format(new Date());
    }

    public static int getMouthDay(int mouth, int year) {
        switch (mouth) {
            case 1:
                return 31;
            case 2:
                if (year % 4 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        return 0;
    }

    public static int getThisYear() {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy");
        return Integer.parseInt(simple.format(new Date()));
    }

    public static int getThisMouth() {
        SimpleDateFormat simple = new SimpleDateFormat("M");
        return Integer.parseInt(simple.format(new Date()));
    }

    public static int getThisDay() {
        SimpleDateFormat simple = new SimpleDateFormat("d");
        return Integer.parseInt(simple.format(new Date()));
    }

    public static String getThisWeek() {
        SimpleDateFormat simple = new SimpleDateFormat("E");
        return simple.format(new Date());
    }

    public static String getWeek(String time) {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String s = new SimpleDateFormat("E").format(simple.parse(time));
            return s.substring(s.length() - 1);
        } catch (ParseException e) {
            return "";
        }
    }

    public static String getLeftMonth(int year, int month) {
        if (month - 1 == 0) return (year - 1) + "年12月";
        return year + "年" + (month - 1) + "月";
    }

    public static String getNextMonth(int year, int month) {
        if (month + 1 > 12) return (year + 1) + "年1月";
        return year + "年" + (month + 1) + "月";
    }

    public static String timeToString(String time, boolean isYear) {
        String result = "";
        if (isYear) result += time.substring(0, 4) + "-";
        result += time.substring(4, 6) + "-";
        result += time.substring(6, 8) + " ";
        if (!isYear) {
            result += time.substring(8, 10) + ":";
            result += time.substring(10, 12);
        }
        return result;
    }

    public static String year, mouth, day;



    private static void initNumpick(NumberPicker numberPicker, String[] value, int index) {
        numberPicker.setDisplayedValues(value);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(value.length - 1);
        numberPicker.setValue(index);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerDividerColor(numberPicker, Color.parseColor("#FF9419"));

    }

    public static String[] getValue(int start, int end) {
        int count = end - start + 1;
        String[] value = new String[count];
        for (int i = 0; i < count; i++) {
            String s = (start + i) + "";
            s = s.length() == 1 ? "0" + s : s;
            value[i] = s;
        }
        return value;
    }

    public static void setNumberPickerDividerColor(NumberPicker numberPicker, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field SelectionDividerField : pickerFields) {
            if (SelectionDividerField.getName().equals("mSelectionDivider")) {
                SelectionDividerField.setAccessible(true);
                try {
                    SelectionDividerField.set(numberPicker, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    public interface OnGetOtherDateListener {
        public void afterGetDate(String str);

        public void cancle();
    }

    //时间戳  转  时间类型
    public static String tampToDate(String s, String type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        long lt = new Long(s);
        Date date = new Date(lt);
        return simpleDateFormat.format(date);
    }
}
