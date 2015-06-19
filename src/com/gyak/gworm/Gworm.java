package com.gyak.gworm;

import java.io.IOException;
import java.util.HashMap;

import com.gyak.http.Htmlable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gyak.http.Gurl;
import com.gyak.http.InputStreamUtils;
import com.gyak.json.JSONStringer;
import com.gyak.proterty.NotInitRequestProperties;

/**
 * 用以提取指定url里的内容，并以JSON格式返回
 * 不能直接初始化，需要使用{@link com.gyak.gworm.GwormFactory#getInstance GwormFactory}来生成
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2015-06-09.
 */
public class Gworm {
	
	private HashMap<String, GwormUrl> gwormUrlMap = new HashMap<String, GwormUrl>();

	private Htmlable htmlable;

	private Gworm(){}

	/**
	 * 设置Htmlable
	 * @param htmlable
	 */
	public void setHtmlable(Htmlable htmlable) {
		this.htmlable = htmlable;
	}

	/**
	 * 保存GwormUrl
	 * @param urlId 保存的键（配置文件url标签对应的id）
	 * @param url {@link com.gyak.gworm.GwormUrl GwormUrl}
	 */
	public void putGwormUrl(String urlId, GwormUrl url){
		gwormUrlMap.put(urlId, url);
	}

	/**
	 * 通过key获取GwormUrl
	 * @param urlId 保存的键(配置文件url标签对应的id)
	 * @return {@link com.gyak.gworm.GwormUrl GwormUrl}
	 */
	public GwormUrl getGwormUrl(String urlId){
		return gwormUrlMap.get(urlId);
	}

	/**
	 * 获取JSON，通过指定的url与{@link com.gyak.gworm.GwormUrl GwormUrl}对应的键urlId
	 * @param url 爬取的链接
	 * @param urlId {@link com.gyak.gworm.GwormUrl GwormUrl}对应的键
	 * @return 爬取的JSON
	 * @throws NotInitRequestProperties 没有初始化http请求参数
	 */
	public String getJson(String url, String urlId) throws NotInitRequestProperties {
		JSONStringer str = new JSONStringer();
		getGwormUrl(urlId).getJson(str, htmlToElements(htmlable.getHtml(url)));
		return str.toString();
	}

	/**
	 * 获取JSON，通过指定的url与{@link com.gyak.gworm.GwormUrl GwormUrl}对应的键urlId以及单条jsonId
	 * @param url 爬取的链接
	 * @param urlId 配置文件url标签对应的id
	 * @param jsonId 配置文件value标签的id
	 * @return 爬取的JSON
	 * @throws NotInitRequestProperties 没有初始化http请求参数
	 */
	public String getJson(String url, String urlId, String jsonId) throws NotInitRequestProperties {
		JSONStringer str = new JSONStringer();
		getGwormUrl(urlId).getJson(str, htmlToElements(htmlable.getHtml(url)), jsonId);
		return str.toString();
	}


	
	private Elements htmlToElements(String html) {
		Document doc = Jsoup.parse(html);
		return doc.getElementsByTag("body");
	}

}
