package com.gyak.newjson;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.gyak.json.JSONArray;
import com.gyak.json.JSONException;
import com.gyak.json.JSONObject;
import com.gyak.json.JSONTokener;

public class JSONToObject {

	public static <T> T getObject(JSONObject jo , Class<T> c) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, JSONException{
		T obj = c.newInstance();
		Field[] declaredFields = c.getDeclaredFields();
		for(Field f :declaredFields){
			f.setAccessible(true);
			String name = f.getName();
			String value = jo.getString(name);
			if(value == null || value.equals(""))
				return null;
			Method method = c.getMethod(getFuncationName(name), String.class);
			method.invoke(obj, value);
		}
		return obj;
	}
	
	public static <T> ArrayList<T> getArrayList(String[] json, Class<T> c) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, JSONException {
		JSONArray[] array = new JSONArray[json.length];
		for(int i=0; i<json.length; i++) {
			array[i] = new JSONArray(new JSONTokener(json[i]));
		}
		return getArrayList(array, c);
	}
	
	public static <T> ArrayList<T> getArrayList(JSONArray[] array, Class<T> c) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, JSONException {
		ArrayList<T> list = new ArrayList<T>();
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[i].length(); j++){
				JSONObject jo = array[i].getJSONObject(j);
				T t = getObject(jo, c);
				if(t != null){
					list.add(t);
				}
			}
		}
		return list;
	}
	
	private static String getFuncationName(String name){
		return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}
