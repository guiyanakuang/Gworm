package com.gyak.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="mailto:guiyanakuang@gmail.com">ÕÂÒÝÈº</a>
 *
 */
public class CookieManager {

	public static final String REQUEST_KEY = "Cookie";

	private static volatile CookieManager instance;

	private Map<String, CookieGeneration> map = new HashMap<>();


	public static CookieManager getInstance() {
		if (instance == null) {
			synchronized (CookieManager.class) {
				if (instance == null) {
					instance = new CookieManager();
				}
			}
		}
		return instance;
	}

	public void put(String key, CookieGeneration cg) {
		map.put(key, cg);
	}

	public void remove(String key) {
		map.remove(key);
	}

	public String getCookies() {
		StringBuilder s = new StringBuilder();
		Iterator<Map.Entry<String, CookieGeneration>> itr = map.entrySet().iterator();
		while (itr.hasNext()){
			Map.Entry<String, CookieGeneration> e = itr.next();
			s.append(e.getKey() + "=" + e.getValue().getCookie() + ";");
		}
		return s.toString();
	}

}