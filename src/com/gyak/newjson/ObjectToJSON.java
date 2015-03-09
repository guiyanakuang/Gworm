package com.gyak.newjson;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.gyak.json.JSONException;
import com.gyak.json.JSONStringer;

public class ObjectToJSON {

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
