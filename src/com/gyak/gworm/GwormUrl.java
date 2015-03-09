package com.gyak.gworm;

import java.util.ArrayList;

import org.jsoup.select.Elements;

import com.gyak.json.JSONStringer;

public class GwormUrl implements GwormJsonable{

	private String id;

	private ArrayList<GwormJsonable> list = new ArrayList<GwormJsonable>();
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void addGwormJson(GwormJsonable gj){
		list.add(gj);
	}
	
	@Override
	public boolean equalId(String _id) {
		return _id.equals(this.id);
	}
	
	@Override
	public void getJson(JSONStringer jsonStringer, Elements elements) {
		for(GwormJsonable gj : list){
			gj.getJson(jsonStringer, elements);
		}
	}
	
	@Override
	public void getJson(JSONStringer jsonStringer, Elements elements, String id) {
		for(GwormJsonable gj : list){
			gj.getJson(jsonStringer, elements, id);
		}
	}

	@Override
	public void setRule(String rule) {}

	@Override
	public void setGet(String get) {}

}
