package com.gyak.proterty;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * http请求参数配置类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-08-17.
 */
public class RequestProperties {
	
	private static class PropertiesHondle{
		private final static RequestProperties instance = new RequestProperties();
	}

	/**
	 * 返回配置类单例
	 * @return RequestProperties（http请求参数配置类）
	 */
	public final static RequestProperties getInstance(){
		return PropertiesHondle.instance;
	}
	
	private Properties properties;

	private RequestProperties(){}

	/**
	 * 初始化配置类
	 * @param is 输入流
	 */
	public void initProperties(InputStream is){
		properties = new Properties();
        try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取 properties
	 * @return properties
	 * @throws NotInitRequestProperties 没有初始化http请求参数
	 */
	public Properties getProperties() throws NotInitRequestProperties {
		if(this.properties == null)
			throw new NotInitRequestProperties();
		return this.properties;
	}

	/**
	 * 获取配置参数
	 * @param key 参数名
	 * @return 参数值
	 */
	public String getValue(String key){
		return properties.getProperty(key);
	}
	
	
}
