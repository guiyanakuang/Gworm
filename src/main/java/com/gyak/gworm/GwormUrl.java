package com.gyak.gworm;

import com.gyak.json.JSONStringer;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 爬取相应url的配置类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-03-09.
 */
public class GwormUrl implements GwormJsonable{

	private String id;

	private ArrayList<GwormJsonable> list = new ArrayList<GwormJsonable>();

	/**
	 * 设置配置类id
	 * @param id 配置文件中url标签的id
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 添加下一层的配置接口
	 * @param gj
	 */
	@Override
	public void addGwormJson(GwormJsonable gj){
		list.add(gj);
	}

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
	 * 使用下一层配置接口从elements中提取数据插入到str中
	 * @param jsonStringer JSONString
	 * @param elements html中的一块标签
	 */
	@Override
	public void getJson(JSONStringer jsonStringer, Elements elements) {
		for(GwormJsonable gj : list){
			gj.getJson(jsonStringer, elements);
		}
	}

	/**
	 * 使用id为_id的配置接口从elements中提取数据插入到str中
	 * @param jsonStringer JSONString
	 * @param elements html中的一块标签
	 * @param id 下一层配置接口id
	 */
	@Override
	public void getJson(JSONStringer jsonStringer, Elements elements, String id) {
		for(GwormJsonable gj : list){
			gj.getJson(jsonStringer, elements, id);
		}
	}

	/**
	 * GwormUrl并不需要实现GwormJsonable中的setRule方法，
	 * 故不需要做任何事
	 * @param rule
	 */
	@Override
	public void setRule(String rule) {}

	/**
	 * GwormUrl并不需要实现GwormJsonable中的setGet方法，
	 * 故不需要做任何事
	 * @param get
	 */
	@Override
	public void setGet(String get) {}

}
