package com.gyak.newjson;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.gyak.json.JSONArray;
import com.gyak.json.JSONException;
import com.gyak.json.JSONObject;
import com.gyak.json.JSONTokener;

/**
 * 将JSON数据反射为java对象
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-10-11.
 */
public class JSONToObject {

	/**
	 * 将JSONObject反射为java对象
	 * @param jo JSONObject
	 * @param c 反射类class
	 * @param <T> 反射类
	 * @return 反射生成类
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws JSONException
	 */
	public static <T> T getObject(JSONObject jo , Class<T> c) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, JSONException{
		T obj = c.newInstance();
		Field[] declaredFields = c.getDeclaredFields();
		for(Field f :declaredFields){
			f.setAccessible(true);
			String name = f.getName();
			String value = jo.getString(name);
			if(value == null || value.equals(""))
				continue;
			Method method = c.getMethod(getFuncationName(name), String.class);
			method.invoke(obj, value);
		}
		return obj;
	}

	/**
	 * 将JSON数组反射为java中的ArrayList数组
	 * @param json JSON数组
	 * @param c 反射类class
	 * @param <T> 反射类
	 * @return ArrayList
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws JSONException
	 */
	public static <T> ArrayList<T> getArrayList(String[] json, Class<T> c) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, JSONException {
		JSONArray[] array = new JSONArray[json.length];
		for(int i=0; i<json.length; i++) {
			array[i] = new JSONArray(new JSONTokener(json[i]));
		}
		return getArrayList(array, c);
	}

	/**
	 * 将JSONArray数组反射为java中的ArrayList数组
	 * @param array JSONArray数组
	 * @param c 反射类class
	 * @param <T> 反射类
	 * @return ArrayList
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws JSONException
	 */
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
