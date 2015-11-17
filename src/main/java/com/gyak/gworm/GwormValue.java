package com.gyak.gworm;

import com.gyak.json.JSONStringer;
import org.jsoup.select.Elements;

/**
 * 爬取指定值的配置类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-03-09.
 */
public class GwormValue implements GwormJsonable {

	private String id;
	private String rule;
	private String get;

	/**
	 * 设置配置类id
	 * @param id 配置文件中value标签的id
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置爬取规则
	 * @param rule 爬取规则
	 */
	@Override
	public void setRule(String rule) {
		this.rule = rule;
	}

	/**
	 * 设置获取value的方法
	 * @param get 获取方法
	 */
	@Override
	public void setGet(String get) {
		this.get = get;
	}

	/**
	 * GwormValue已为最底层，故调用此方法不会做任何事
	 * @param gj
	 */
	@Override
	public void addGwormJson(GwormJsonable gj) {}

	/**
	 * 判断配置id是否为_id
	 * @param _id 用以比较的id
	 * @return 是否相等
	 */
	@Override
	public boolean equalId(String _id) {
		return _id.equals(this.id);
	}

	/**
	 * 从elements中提取数据插入到str中
	 * @param str JSONString
	 * @param elements html中的一块标签
	 */
	@Override
	public void getJson(JSONStringer str, Elements elements) {
		
		Elements es = elements.select(rule);
		str.key(id);
		String value;
		if(get.substring(0, 4).equals("attr")){
			value = es.attr(get.split(" ")[1]);
		}
		else if(get.equals("text")){
			value = es.text();
		}
		else if(get.equals("html")){
			value = es.html();
		}
		else{
			value = null;
		}
		str.value(value);
	}

	/**
	 * 使用id为_id的配置接口从elements中提取数据插入到str中
	 * @param str JSONString
	 * @param elements html中的一块标签
	 * @param _id 配置接口id
	 */
	@Override
	public void getJson(JSONStringer str, Elements elements, String _id) {
		if(equalId(_id)){
			getJson(str, elements);
		}
	}

}
