package com.gyak.gworm;

import org.jsoup.select.Elements;

import com.gyak.json.JSONStringer;

public interface GwormJsonable {
	
	void setId(String id);
	
	void setRule(String rule);
	
	void setGet(String get);
	
	void addGwormJson(GwormJsonable gj);
	
	boolean equalId(String id);

	void getJson(JSONStringer str, Elements elements);
	
	void getJson(JSONStringer str, Elements elements, String id);
	
}
