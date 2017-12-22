package com.yuqi.admin.py.utils;

import android.content.Intent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ListUtil {
    public static void replace(List start, List end) {
        start.clear();
        if (isNull(start) || isNull(end))
            return;
        for (int i = 0; i < end.size(); i++) {
            start.add(end.get(i));
        }
    }

    public static void addStartFirst(List start, List end) {
        if (isNull(start) || isNull(end))
            return;
        for (int i = 0; i < end.size(); i++) {
            start.add(0, end.get(i));
        }
    }

    public static void toFirst(List data, Object obj) {
        int count = data.indexOf(obj);
        data.remove(count);
        data.add(0, obj);
    }

    public static void toOther(List data, Object obj, int index) {
        int count = data.indexOf(obj);
        data.remove(count);
        data.add(index, obj);
    }

    public static void addList(List thisData, List newData) {
        if (isNull(thisData) || isNull(newData))
            return;
        for (int i = 0; i < newData.size(); i++) {
            thisData.add(newData.get(i));
        }
    }

    public static boolean isNull(List data) {
        if (data == null) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List data) {
        if (data == null || data.size() == 0) {
            return true;
        }
        return false;
    }

    public static void mapToIntent(Intent it, HashMap map) {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            it.putExtra(entry.getKey() + "", entry.getValue() + "");
        }
    }

    public static String addOtherData(String name, List end) {
        if (ListUtil.isEmpty(end)) return name;
        for (Object obj : end) {
            HashMap map = (HashMap) obj;
            List data = (List) map.get("userList");
            for (Object o : data) {
                HashMap m = (HashMap) o;
                if (name.equals(m.get("userId"))) {
                    return m.get("userName") + "";
                }
            }
        }
        return name;
    }

    public static void addAllOtherData(List data, String key, Object value) {
        if (isEmpty(data)) return;
        for (int i = 0; i < data.size(); i++) {
            HashMap map = (HashMap) data.get(i);
            map.put(key, value);
        }
    }
}
