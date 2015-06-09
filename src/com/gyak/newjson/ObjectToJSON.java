package com.gyak.newjson;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.gyak.json.JSONException;
import com.gyak.json.JSONStringer;

/**
 * 将java对象转化为JSON数据
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-10-11.
 */
public class ObjectToJSON {

	/**
	 * 将ArrayList内对象转化为JSON数据
	 * @param list ArrayList
	 * @param <T> 数组内保存的内
	 * @return JSON数据
	 * @throws JSONException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> String getJson(ArrayList<T> list) throws JSONException, IllegalArgumentException, IllegalAccessException {
		if (list.size() > 0) {
			JSONStringer str = new JSONStringer();
			Class<? extends Object> c = list.get(0).getClass();
			Field[] declaredFields = c.getDeclaredFields();
			str.array();
			for(T t : list){
				str.object();
				for(Field f :declaredFields) {
					f.setAccessible(true);
					str.key(f.getName()).value(f.get(t));
				}
				str.endObject();
			}
			str.endArray();
			return str.toString();
		}
		else{
			return "";
		}
	}
}
