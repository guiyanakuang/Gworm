package com.gyak.gworm;

import org.jsoup.select.Elements;

import com.gyak.json.JSONStringer;

public class GwormValue implements GwormJsonable {

	private String id;
	private String rule;
	private String get;
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setRule(String rule) {
		this.rule = rule;
	}

	@Override
	public void setGet(String get) {
		this.get = get;
	}
	
	@Override
	public void addGwormJson(GwormJsonable gj) {}
	
	@Override
	public boolean equalId(String _id) {
		return _id.equals(this.id);
	}

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

	@Override
	public void getJson(JSONStringer str, Elements elements, String _id) {
		if(equalId(_id)){
			getJson(str, elements);
		}
	}

}
