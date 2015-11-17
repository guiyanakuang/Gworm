package com.gyak.http;


import com.gyak.proterty.NotInitRequestProperties;
import com.gyak.proterty.RequestProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * http连接类，封装HttpURLConnection、CookieManager
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2014-10-11.
 */
public class Gurl {

	private final String GBK = "gbk";
	private final String ISO_8859_1 = "iso-8859-1";
	private final String GB2312 = "gb2312";
	private final String UTF_8 = "utf-8";

	private HttpURLConnection  http;
	private CookieManager cookieManager;
	
	
	public Gurl(String url) {
		try {
			this.http = (HttpURLConnection) new URL(url).openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Gurl(URL url) {
		try {
			this.http = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getContentType(){
		String type = this.http.getContentType();
		if(type == null)
			return UTF_8;
		else{
			type = type.toLowerCase();
		}
		
		if(type.contains(GBK))
			return GBK;
		else if(type.contains(ISO_8859_1))
			return ISO_8859_1;
		else if(type.contains(GB2312))
			return GB2312;
		else
			return UTF_8;
	}
	
	public void openUrl() throws IOException, NotInitRequestProperties{
		this.cookieManager = CookieManager.getInstance();
		this.http.setDoInput(true);
		this.http.setDoOutput(true);
		setRequestProperty();
		setCookie();
	}

	/**
	 * 设置http访问数据包的参数
	 * @throws IOException
	 * @throws NotInitRequestProperties
	 */
	private void setRequestProperty() throws IOException, NotInitRequestProperties{
		RequestProperties rp = RequestProperties.getInstance();
		Iterator<Entry<Object, Object>> itr = rp.getProperties().entrySet().iterator();
        while (itr.hasNext()){
            Entry<Object, Object> e = itr.next();
            http.setRequestProperty(e.getKey().toString(), e.getValue().toString());
        }
	}

	/**
	 * 设置cookie
	 */
	private void setCookie(){
		this.http.setRequestProperty(CookieManager.REQUEST_KEY, cookieManager.getCookies());
	}

	/**
	 * 获取输入流
	 * @return 输入流
	 * @throws IOException
	 * @throws NotInitRequestProperties
	 */
	public InputStream getInputStream() throws IOException, NotInitRequestProperties {
        return http.getInputStream();
    }  

	
	public URL getUrl(){
		return http.getURL();
	}
	
	public boolean isGzip(){
		if(this.http.getContentEncoding() == null)
			return false;
		return this.http.getContentEncoding().equals("gzip");
	}

	
}
