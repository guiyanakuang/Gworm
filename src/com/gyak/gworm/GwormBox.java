package com.gyak.gworm;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import com.gyak.proterty.NotInitRequestProperties;


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
	 * @param name 配置名
	 */
	public void addGworm(String path, String name) {
		try {
			Gworm gworm = GwormFactory.getInstance(path);
			gwormMap.put(name, gworm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加Gworm配置
	 * @param in 配置文件输入流
	 * @param name 配置名
	 */
	public void addGworm(InputStream in, String name) {
		Gworm gworm = GwormFactory.getInstance(in);
		gwormMap.put(name, gworm);
	}

	/**
	 * 通过爬取参数坐标coordinate,爬取url,返回JSON数据
	 * @param coordinate 爬取参数坐标
	 * @param url 爬取链接
	 * @return JSON
	 * @throws NotInitRequestProperties 没有初始化http请求参数
	 * @throws NotFindGwormConfigException 没有找到爬虫配置文件
	 */
	public String getJson(GwormCoordinate coordinate, String url) throws NotInitRequestProperties, NotFindGwormConfigException {
		if (coordinate.getJsonId() == null) {
			return getJson(coordinate.getName(), url,
					coordinate.getUrlId());
		}
		else
			return getJson(coordinate.getName(), url,
					coordinate.getUrlId(),
					coordinate.getJsonId());
	}

	/**
	 * 通过由name指定的Gworm使用urlId对应的规则爬取链接url
	 * @param name 保存在GwormBox中的key
	 * @param url 爬取链接
	 * @param urlId 配置文件中url标签对应的id
	 * @return JSON
	 * @throws NotFindGwormConfigException 没有初始化http请求参数
	 * @throws NotInitRequestProperties 没有找到爬虫配置文件
	 */
	public String getJson(String name, String url, String urlId) throws NotFindGwormConfigException, NotInitRequestProperties {
		Gworm gworm = gwormMap.get(name);
		if(gworm == null) {
			throw new NotFindGwormConfigException();
		}
		else {
			return gworm.getJson(url, urlId);
		}
	}

	/**
	 * 通过由name指定的Gworm使用urlId、jsonId对应的规则爬取链接url
	 * @param name 保存在GwormBox中的key
	 * @param url 爬取链接
	 * @param urlId 配置文件中url标签对应的id
	 * @param jsonId 配置文件中value标签对应的id
	 * @return JSON
	 * @throws NotFindGwormConfigException 没有初始化http请求参数
	 * @throws NotInitRequestProperties 没有找到爬虫配置文件
	 */
	public String getJson(String name, String url, String urlId, String jsonId) throws NotFindGwormConfigException, NotInitRequestProperties {
		Gworm gworm = gwormMap.get(name);
		if(gworm == null) {
			throw new NotFindGwormConfigException();
		}
		else {
			return gworm.getJson(url, urlId, jsonId);
		}
	}
	
	
}
