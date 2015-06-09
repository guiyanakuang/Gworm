package com.gyak.gworm;

import java.util.ArrayList;

import org.jsoup.select.Elements;

import com.gyak.json.JSONStringer;

/**
 * 爬取JSONObject的配置类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-03-09.
 */
public class GwormObject implements GwormJsonable {

	private String id;
	private String rule;
	
	private ArrayList<GwormJsonable> list = new ArrayList<GwormJsonable>();

	/**
	 * 设置配置类id
	 * @param id 配置文件中object标签的id
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
	 * 添加下一层的配置接口
	 * @param gj
	 */
	@Override
	public void addGwormJson(GwormJsonable gj) {
		list.add(gj);
	}

	/**
	 * 判断配置id是否为_id
	 * @param _id 用以比较的id
	 * @return 是否相等
	 */
	@Override
	public boolean equalId(String _id) {
		if(_id == null)
			return false;
		return _id.equals(this.id);
	}

	private Elements getElements(Elements elements){
		if(rule != null && !rule.equals(""))
			return elements.select(rule);
		return elements;
	}

	/**
	 * 使用下一层配置接口从elements中提取数据插入到str中
	 * @param str JSONString
	 * @param elements html中的一块标签
	 */
	@Override
	public void getJson(JSONStringer str, Elements elements) {
		str.object();
		elements = getElements(elements);
		for(GwormJsonable gj : list){
			gj.getJson(str, elements);
		}
		str.endObject();
	}

	/**
	 * 使用id为_id的配置接口从elements中提取数据插入到str中
	 * @param str JSONString
	 * @param elements html中的一块标签
	 * @param _id 配置接口id
	 */
	@Override
	public void getJson(JSONStringer str, Elements elements, String _id) {
		if(equals(_id)){
			getJson(str, elements);
		}
		else{
			str.object();
			elements = getElements(elements);
			for(GwormJsonable gj : list){
				gj.getJson(str, elements, _id);
			}
			str.endObject();
		}
	}

	/**
	 * GwormObject并不需要实现GwormJsonable中的setGet方法，
	 * 故调用此方法不会做任何事
	 * @param get
	 */
	@Override
	public void setGet(String get) {}

}
