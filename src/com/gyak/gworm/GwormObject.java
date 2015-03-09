package com.gyak.gworm;

import java.util.ArrayList;

import org.jsoup.select.Elements;

import com.gyak.json.JSONStringer;

public class GwormObject implements GwormJsonable {

	private String id;
	private String rule;
	
	private ArrayList<GwormJsonable> list = new ArrayList<GwormJsonable>();
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	@Override
	public void addGwormJson(GwormJsonable gj) {
		list.add(gj);
	}

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
	
	@Override
	public void getJson(JSONStringer str, Elements elements) {
		str.object();
		elements = getElements(elements);
		for(GwormJsonable gj : list){
			gj.getJson(str, elements);
		}
		str.endObject();
	}

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

	@Override
	public void setGet(String get) {}

}
