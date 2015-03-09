package com.gyak.gworm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GwormFactory {
	
	private final String GWORM = "gworm";
	private final String URL = "url";
	private final String ARRAY = "array";
	private final String ID = "id";
	private final String RULE = "rule";
	private final String OBJECT = "object";
	private final String VALUE = "value";
	private final String GET = "get";
	
	private Gworm gworm;

	public GwormFactory(String wormConfigPath) throws FileNotFoundException {
		this(new FileInputStream(new File(wormConfigPath)));
	}
	
	public Gworm getInstance(){
		return gworm;
	}
	
	public GwormFactory(InputStream wormConfigIn) {
		gworm = new Gworm(); 
		try {
        	SAXReader saxReader = new SAXReader(); 
			Document document = saxReader.read(wormConfigIn);
			Element elementGworm = document.getRootElement();
			if(elementGworm.getName().equals(GWORM)) {
				List<Element> elements = elementGworm.elements();
				initGwormUrl(elements, gworm);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void initGwormUrl(List<Element> elements, Gworm gworm) {
		for(Element element : elements) {
			if(element.getName().equals(URL)) {
				List<Element> elementss = element.elements();
				GwormUrl gu = new GwormUrl();
				String id = element.attributeValue(ID);
				gu.setId(id);
				initGwormJsonable(elementss, gu);
				gworm.putGwormUrl(id, gu);
			}
		}
	}
	
	private void initGwormJsonable(List<Element> elements, GwormJsonable gj) {
		for(Element element : elements) {
			
			String name = element.getName();
			String id = element.attributeValue(ID);
			String rule = element.attributeValue(RULE);
			String get = element.attributeValue(GET);
			GwormJsonable gjable = null;
			
			if(name.equals(ARRAY)) {
				gjable = new GwormArray();
			}
			else if(name.equals(OBJECT)) {
				gjable = new GwormObject();
			}
			else if(name.equals(VALUE)) {
				gjable = new GwormValue();
			}
			
			gjable.setId(trim(id));
			gjable.setRule(trim(rule));
			if(get != null){
				gjable.setGet(trim(get));
			}
			
			if(name.equals(ARRAY) || name.equals(OBJECT)){
				List<Element> elementss = element.elements();
				initGwormJsonable(elementss, gjable);
			}
			gj.addGwormJson(gjable);
		}
	}
	
	private String trim(String str) {
		if(str != null)
			return str.trim();
		return null;
	}
	
}
