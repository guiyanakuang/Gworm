package com.gyak.gworm;

import com.gyak.http.DefaultGetHtml;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Gworm静态工厂类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-03-09.
 */
public class GwormFactory {
	
	private static final String GWORM = "gworm";
	private static final String URL = "url";
	private static final String ARRAY = "array";
	private static final String ID = "id";
	private static final String RULE = "rule";
	private static final String OBJECT = "object";
	private static final String VALUE = "value";
	private static final String GET = "get";

	/**
	 * 返回Gworm
	 * @param wormConfigPath 配置文件路径
	 * @return Gworm
	 * @throws FileNotFoundException 没找到文件
	 */
	public static Gworm getInstance(String wormConfigPath) throws FileNotFoundException {
		return getInstance(getInputStream(wormConfigPath));
	}

	/**
	 * 返回Gworm
	 * @param wormConfigIn 配置文件输入流
	 * @return Gworm
	 */
	public static Gworm getInstance(InputStream wormConfigIn) {
		return getGworm(wormConfigIn);
	}

	private static InputStream getInputStream(String wormConfigPath) throws FileNotFoundException {
		return ClassLoader.getSystemResourceAsStream(wormConfigPath);
	}
	
	private static Gworm getGworm(InputStream wormConfigIn) {
		Gworm gworm = null;
		try {
			Class<?> c = Gworm.class;
			Constructor constructor = c.getDeclaredConstructor();
			constructor.setAccessible(true);
			gworm = (Gworm) constructor.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
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
		gworm.setHtmlable(DefaultGetHtml.getHtmlable());
		return gworm;
	}

	private static void initGwormUrl(List<Element> elements, Gworm gworm) {
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
	
	private static void initGwormJsonable(List<Element> elements, GwormJsonable gj) {
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
	
	private static String trim(String str) {
		if(str != null)
			return str.trim();
		return null;
	}
	
}
