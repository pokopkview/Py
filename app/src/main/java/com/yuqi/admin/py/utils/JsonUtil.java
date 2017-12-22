package com.yuqi.admin.py.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {
public static HashMap getJsonObj(String json){
	try {
	HashMap map =new HashMap();
	JSONObject obj = new JSONObject(json);
	Iterator it = obj.keys();
	while(it.hasNext()){
		String key = it.next()+"";
	if (obj.opt(key) instanceof JSONArray) {
		map.put(key, getJsonArray((JSONArray)obj.opt(key)));
	}else if(obj.opt(key) instanceof JSONObject){
		map.put(key, getJsonObj((JSONObject)obj.opt(key)));
	}else {
		map.put(key, obj.opt(key+""));
	}
	}
	return map;
	} catch (Exception e) {
		Log.e("--Exception-----", e+"");
    return null;
	}
}
public static HashMap getJsonObj(JSONObject obj){
	HashMap map =new HashMap();
	Iterator it = obj.keys();
	while(it.hasNext()){
		String key = it.next()+"";
		if (obj.opt(key) instanceof JSONArray) {
			map.put(key, getJsonArray((JSONArray)obj.opt(key)));
		}else if(obj.opt(key) instanceof JSONObject){
			map.put(key, getJsonObj((JSONObject)obj.opt(key)));
		}else {
			map.put(key, obj.opt(key+""));
		}
	}
	return map;
}
public static List getJsonArray(String json){
	try {
	List list = new ArrayList();
	JSONArray array = new JSONArray(json);
	for (int i = 0; i < array.length(); i++) {
		if (array.opt(i) instanceof JSONArray) {
			list.add(getJsonArray((JSONArray)array.opt(i)));
		}else if (array.opt(i) instanceof JSONObject) {
			list.add(getJsonObj((JSONObject)(array.opt(i))));
		}else {
			list.add(array.opt(i));
		}
	}
	return list;
	} catch (Exception e) {
		return null;
	}
}
public static List getJsonArray(JSONArray array){
	List list = new ArrayList();
	for (int i = 0; i < array.length(); i++) {
		if (array.opt(i) instanceof JSONArray) {
			list.add(getJsonArray((JSONArray)array.opt(i)));
		}else if (array.opt(i) instanceof JSONObject) {
			list.add(getJsonObj((JSONObject)(array.opt(i))));
		}else {
			list.add(array.opt(i));
		}
	}
	return list;
}
public static String toJsonObj(Map obj){
	try {
	Iterator iter = obj.entrySet().iterator();
	String json="";
	while (iter.hasNext()) {
		HashMap.Entry entry = (HashMap.Entry) iter.next();
		if (obj.get(entry.getKey()+"") instanceof List) {
			json += ",\""+entry.getKey()+"\"";
			json += ":";
			json += toJsonArray((List) obj.get(entry.getKey()+""));
		}else if(obj.get(entry.getKey()+"") instanceof HashMap){
			json += ",\""+entry.getKey()+"\"";
			json += ":";
			json += "\""+toJsonObj((HashMap) obj.get(entry.getKey()))+"\"";
		}else if(obj.get(entry.getKey()+"") instanceof String){
			json += ",\""+entry.getKey()+"\"";
			json += ":";
			json += "\""+entry.getValue()+"\"";
		}else if (obj.get(entry.getKey()+"")==null){
			json += ",\""+entry.getKey()+"\"";
			json += ":";
			json += "\"\"";
		}else {
			json += ",\""+entry.getKey()+"\"";
			json += ":";
			json += entry.getValue();
		}
	}
	   json = json.replaceFirst(",", "");
	   return "{"+json+"}";
	} catch (Exception e) {
		return "";
	}
}
public static String toJsonArray(List data){
	String json = "";
	for (int i = 0; i < data.size(); i++) {
		json += ",";
		if (data.get(i) instanceof HashMap) {
			json += toJsonObj((HashMap) data.get(i));
		}else if(data.get(i) instanceof List){
            json += toJsonArray((List) data.get(i));
		}else{
			if (data.get(i)!=null)
			json += "\""+data.get(i)+"\"";
		}
	}
	json = json.replaceFirst(",", "");
	return "["+json+"]";
}

}
