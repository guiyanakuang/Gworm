package com.gyak.gworm;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gyak.http.Gurl;
import com.gyak.http.InputStreamUtils;
import com.gyak.json.JSONStringer;
import com.gyak.proterty.NotInitRequestProperties;

public class Gworm {
	
	private HashMap<String, GwormUrl> gwormUrlMap = new HashMap<String, GwormUrl>();
  
	public void putGwormUrl(String key, GwormUrl url){
		gwormUrlMap.put(key, url);
	}
	
	public GwormUrl getGwormUrl(String key){
		return gwormUrlMap.get(key);
	}
	
	public String getJson(String url, String urlId) throws NotInitRequestProperties {
		JSONStringer str = new JSONStringer();
		getGwormUrl(urlId).getJson(str, htmlToElements(getHtml(url)));
		return str.toString();
	}

	public String getJson(String url, String urlId, String jsonId) throws NotInitRequestProperties {
		JSONStringer str = new JSONStringer();
		getGwormUrl(urlId).getJson(str, htmlToElements(getHtml(url)), jsonId);
		return str.toString();
	}
	
	private String getHtml(String url) throws NotInitRequestProperties {
		Gurl gurl = new Gurl(url);
		try {
			gurl.openUrl();
			return InputStreamUtils.getHttp(gurl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private Elements htmlToElements(String html) {
		Document doc = Jsoup.parse(html);
		return doc.getElementsByTag("body");
	}

}
