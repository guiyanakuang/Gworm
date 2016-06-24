package com.gyak.gworm;

import com.google.gson.JsonObject;
import com.gyak.gworm.exception.NotFindRuleException;
import com.gyak.http.DefaultGetHtml;
import com.gyak.http.Htmlable;
import com.gyak.proterty.NotInitRequestProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 用以提取指定url里的内容，并以JSON格式返回
 * 不能直接初始化，需要使用{@link com.gyak.gworm.GwormFactory#getInstance GwormFactory}来生成
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2015-06-09.
 */
public class Gworm implements GwormJsonable{

	private static final String TEXT  = "TEXT";

	private static final String HTML  = "HTML";

	private static final String FUNC  = "FUNC";

	private static final String REGEX = "REGEX";

	private static final String BODY = "BODY";

	private String id;

	private String rule;

	private String get;

	private GwormArray gwormArray;

	private Htmlable htmlable = new DefaultGetHtml();

	private Gworm(){}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * 设置Htmlable
	 * @param htmlable
	 */
	public void setHtmlable(Htmlable htmlable) {
		this.htmlable = htmlable;
	}



	public JsonObject getJson(String url) throws NotInitRequestProperties {
		if (id == null) return null;

		Elements elements = null;
		try {
			elements = htmlToElements(htmlable.getHtml(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getJsonFromElements(new JsonObject(), elements);
	}

	@Override
	public JsonObject getJsonFromElements(Elements elements) throws NotInitRequestProperties {
		JsonObject jsonObject = new JsonObject();

		return getJsonFromElements(jsonObject, elements);
	}

	public JsonObject getJsonFromElements(JsonObject jsonObject, Elements elements) throws NotInitRequestProperties {

		if (get != null) {
			jsonObject.addProperty(id, getValue(elements));
		}

		if (gwormArray != null ) {
			jsonObject.add(gwormArray.getId(), gwormArray.getJsonFromElements(elements));
		}

		return jsonObject;
	}
	
	private Elements htmlToElements(String html) {
		Document doc = Jsoup.parse(html);
		return doc.getElementsByTag(BODY);
	}

	private String getValue(Elements elements) {
		if (rule == null) {
			try {
				throw new NotFindRuleException(id);
			} catch (NotFindRuleException e) {
				e.printStackTrace();
			}
			return null;
		}
		else {
			switch (get.trim().toUpperCase()) {
				case TEXT:  return elements.select(rule).text();
				case HTML:  return elements.select(rule).html();
				case FUNC:  return getValueByFunc(elements);
				case REGEX: return getValueByRegex(elements);
				default:    return elements.select(rule).attr(get);
			}
		}

	}

	private String getValueByFunc(Elements elements) {
		String className = rule.trim();
		try {
			Class rule = Class.forName(className);
			GwormRule gwormRule = (GwormRule)rule.newInstance();
			return gwormRule.getValue(elements);
		} catch (ClassNotFoundException |
				InstantiationException  |
				IllegalAccessException e) {
			return null;
		}
	}

	private String getValueByRegex(Elements elements) {
		String html = elements.html();
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(html);
		if (matcher.find())
			return matcher.group(0);
		return null;
	}

}
