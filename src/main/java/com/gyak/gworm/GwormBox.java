package com.gyak.gworm;

import com.google.gson.JsonObject;
import com.gyak.gworm.exception.NotFindGwormConfigException;
import com.gyak.http.Htmlable;
import com.gyak.proterty.NotInitRequestProperties;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


/**
 * 爬虫盒子，用以管理多个爬虫类，屏蔽底层细节
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-03-09.
 */
public class GwormBox {



	private static class GwormBoxHondle {
		private final static GwormBox instance = new GwormBox();
	}

	/**
	 * 返回GwormBox单例
	 * @return GwormBox
	 */
	public static final GwormBox getInstance() {
		return GwormBoxHondle.instance;
	}
	
	private HashMap<String, Gworm> gwormMap = new HashMap<String, Gworm>();


	/**
	 * 添加Gworm配置
	 * @param path 配置文件路径
	 */
	public void addGworm(String path, String name) {
		try {
			List<Gworm> list = GwormFactory.getInstance(path);
			put(list, name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void put(List<Gworm> list, String name) {
		for (Gworm gworm : list) {
			gwormMap.put(name + "." + gworm.getId(), gworm);
		}
	}

	public void addGworm(String path, String name, Htmlable htmlable) {
		try {
			List<Gworm> list = GwormFactory.getInstance(path, htmlable);
			put(list, name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加Gworm配置
	 * @param in 配置文件输入流
	 */
	public void addGworm(InputStream in, String name) {
		List<Gworm> list = GwormFactory.getInstance(in);
		put(list, name);
	}


	public JsonObject getJson(GwormCoordinate coordinate, String url) throws NotInitRequestProperties, NotFindGwormConfigException {
		if (coordinate.getJsonId() == null) {
			return getJson(coordinate.getName(), url,
					coordinate.getUrlId());
		}

		return null;
	}

	public String getJsonString(GwormCoordinate coordinate, String url) throws NotInitRequestProperties, NotFindGwormConfigException {
		JsonObject jsonObject = getJson(coordinate, url);
		return jsonObject != null ? jsonObject.getAsString() : null;
	}


	public JsonObject getJson(String name, String url, String urlId) throws NotFindGwormConfigException, NotInitRequestProperties {
		Gworm gworm = gwormMap.get(name + "." + urlId);
		if(gworm == null) {
			throw new NotFindGwormConfigException();
		}
		else {
			return gworm.getJson(url);
		}
	}
	
}
