package com.gyak.gworm;

import com.gyak.json.JSONStringer;
import org.jsoup.select.Elements;

/**
 * 爬取配置接口
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-03-09.
 */
public interface GwormJsonable {

	/**
	 * 设置id
	 * @param id
	 */
	void setId(String id);

	/**
	 * 设置rule规则
	 * @param rule
	 */
	void setRule(String rule);

	/**
	 * 设置get方法
	 * @param get
	 */
	void setGet(String get);

	/**
	 * 添加下一层的爬取配置接口
	 * @param gj
	 */
	void addGwormJson(GwormJsonable gj);

	/**
	 * 判断id是否相等
	 * @param id 比较的id
	 * @return 是否相等
	 */
	boolean equalId(String id);

	/**
	 * 添加JSON
	 * @param str JSONString
	 * @param elements html标签块
	 */
	void getJson(JSONStringer str, Elements elements);

	/**
	 * 通过指定爬取接口ide添加JSON
	 * @param str JSONString
	 * @param elements html标签块
	 * @param id 指定爬取接口id
	 */
	void getJson(JSONStringer str, Elements elements, String id);
	
}
