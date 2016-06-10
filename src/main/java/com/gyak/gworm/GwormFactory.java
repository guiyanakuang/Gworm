package com.gyak.gworm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Gworm静态工厂类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-03-09.
 */
public class GwormFactory {

	private static final String GWORM = "gworm";
	private static final String URL = "url";
	private static final String ARRAY = "array";
	private static final String ID = "id";
	private static final String RULE = "rule";
	private static final String OBJECT = "object";
	private static final String VALUE = "value";
	private static final String GET = "get";

	private static final Gson gson = new Gson();
	private static final Class<Gworm> gwormClass = Gworm.class;

	/**
	 * 返回Gworm
	 * @param wormConfigPath 配置文件路径
	 * @return Gworm
	 * @throws FileNotFoundException 没找到文件
	 */
	public static List<Gworm> getInstance(String wormConfigPath) throws FileNotFoundException {
		return getInstance(getInputStream(wormConfigPath));
	}

	/**
	 * 返回Gworm
	 * @param wormConfigIn 配置文件输入流
	 * @return Gworm
	 */
	public static List<Gworm> getInstance(InputStream wormConfigIn) {
		return getGworm(wormConfigIn);
	}

	private static InputStream getInputStream(String wormConfigPath) throws FileNotFoundException {
		return ClassLoader.getSystemResourceAsStream(wormConfigPath);
	}
	
	private static List<Gworm> getGworm(InputStream wormConfigIn) {
		List<Gworm> list = null;

		try {
			String json = inputStream2String(wormConfigIn);
			Type listType = new TypeToken<ArrayList<Gworm>>() {}.getType();
			list = gson.fromJson(json, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	private static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line = in.readLine()) != null){
			buffer.append(line);
		}
		return buffer.toString();
	}
	
}
