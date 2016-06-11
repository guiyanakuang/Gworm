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


/**
 * 用以提取指定url里的内容，并以JSON格式返回
 * 不能直接初始化，需要使用{@link com.gyak.gworm.GwormFactory#getInstance GwormFactory}来生成
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2015-06-09.
 */
public class Gworm implements GwormJsonable{

	private static final String ATTR = "attr";

	private static final String TEXT = "text";

	private static final String HTML = "html";

	private static final String BODY = "body";

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
			Elements e = elements.select(rule);
			String get = this.get.trim();
			String value;
			if(get.startsWith(ATTR)){
				value = e.attr(get.split(" ")[1]);
			}
			else if(get.equals(TEXT)){
				value = e.text();
			}
			else if(get.equals(HTML)){
				value = e.html();
			}
			else{
				value = null;
			}
			return value;
		}

	}


}
