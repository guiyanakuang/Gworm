package com.gyak.gworm;

import com.google.gson.JsonArray;

import com.google.gson.JsonObject;
import com.gyak.proterty.NotInitRequestProperties;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * 爬取JSONArray的配置类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-03-09.
 */
public class GwormArray implements GwormJsonable {

	private String id;
	private String rule;

	private List<Gworm> list;

	private GwormArray() {}

	public String getId() {
		return id;
	}

	@Override
	public JsonArray getJsonFromElements(Elements elements) throws NotInitRequestProperties {
		JsonArray jsonArray = new JsonArray();
		Elements es = elements.select(rule);
		for (Element element : es) {
			JsonObject jsonObject = new JsonObject();
			for (Gworm gworm : list) {
				gworm.getJsonFromElements(jsonObject, element.getAllElements());
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
}
