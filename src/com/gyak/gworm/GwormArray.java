package com.gyak.gworm;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gyak.json.JSONStringer;

public class GwormArray implements GwormJsonable {

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
		return _id.equals(this.id);
	}
	
	@Override
	public void getJson(JSONStringer str, Elements elements) {
		str.array();
		Elements e = elements.select(rule);
		for(Element element : e){
			for(GwormJsonable gj : list){
				gj.getJson(str, element.getAllElements());
			}
		}
		str.endArray();
	}
	
	@Override
	public void getJson(JSONStringer str, Elements elements, String _id) {
		if(equalId(_id)){
			getJson(str, elements);
		}
		else{
			str.array();
			Elements e = elements.select(rule);
			for(Element element : e){
				for(GwormJsonable gj : list){
					gj.getJson(str, element.getAllElements(), _id);
				}
			}
			str.endArray();
		}
	}

	@Override
	public void setGet(String get) {}
	
}
