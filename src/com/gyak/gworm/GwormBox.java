package com.gyak.gworm;

import java.io.FileNotFoundException;
import java.util.HashMap;

import com.gyak.proterty.NotInitRequestProperties;

public class GwormBox {

	private static class GwormBoxHondle {
		private final static GwormBox instance = new GwormBox();
	}
	
	public static final GwormBox getInstance() {
		return GwormBoxHondle.instance;
	}
	
	private HashMap<String, String> gwormConfigMap = new HashMap<String, String>();
	
	private HashMap<String, Gworm> gwormMap = new HashMap<String, Gworm>();
	
	public void addWormConfigPath(String key, String path) {
		this.gwormConfigMap.put(key, path);
	}
	
	public String getJson(String wormConfigKey, String url, String urlId) throws NotFindGwormConfigException, NotInitRequestProperties {
		String key = gwormConfigMap.get(wormConfigKey);
		Gworm gworm = addGworm(key);
		if(gworm == null) {
			throw new NotFindGwormConfigException();
		}
		else {
			return gworm.getJson(url, urlId);
		}
	}
	
	public String getJson(String wormConfigKey, String url, String urlId, String jsonId) throws NotFindGwormConfigException, NotInitRequestProperties {
		String key = gwormConfigMap.get(wormConfigKey);
		Gworm gworm = addGworm(key);
		if(gworm == null) {
			throw new NotFindGwormConfigException();
		}
		else {
			return gworm.getJson(url, urlId, jsonId);
		}
	}
	
	private Gworm addGworm(String key) {
		if(key != null && !gwormMap.containsKey(key)) {
			try {
				Gworm gworm = new GwormFactory(key).getInstance();
				gwormMap.put(key, gworm);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return gwormMap.get(key);
	}
	
}
