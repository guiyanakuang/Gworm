package com.gyak.gworm;

import org.jsoup.select.Elements;

import com.gyak.json.JSONStringer;

public interface GwormJsonable {
	
	public void setId(String id);
	
	public void setRule(String rule);
	
	public void setGet(String get);
	
	public void addGwormJson(GwormJsonable gj);
	
	public boolean equalId(String id);

	public void getJson(JSONStringer str, Elements elements);
	
	public void getJson(JSONStringer str, Elements elements, String id);
	
}
