package com.gyak.proterty;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * <p><B>功能:</B>获取配置参数
 * @author 作者 E-mail:guiyanakuang@gmail.com
 * @version 创建时间：2014年8月17日 下午9:13:21
 *
 */

public class RequestProperties {
	
	private static class PropertiesHondle{
		private final static RequestProperties instance = new RequestProperties();
	}
	
	public final static RequestProperties getInstance(){
		return PropertiesHondle.instance;
	}
	
	private Properties properties;

	/**
	 * 实例化MyProperties
	 */
	private RequestProperties(){}
	
	public void initProperties(InputStream is){
		properties = new Properties();
        try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
