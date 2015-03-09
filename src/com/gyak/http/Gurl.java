package com.gyak.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.gyak.proterty.NotInitRequestProperties;
import com.gyak.proterty.RequestProperties;
/**
 * 
 * <p><B>功能:</B> 封装的HttpURLConnection
 * @author 作者 E-mail: guiyanakuang@gmail.com
 * @version 创建时间：2014年10月21日 下午7:07:11 
 *
 */

public class Gurl {
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
			return "UTF-8";
		else{
			type = type.toLowerCase();
		}
		
		if(type.contains("gbk"))
			return "GBK";
		else if(type.contains("iso-8859-1"))
			return "ISO-8859-1";
		else if(type.contains("gb2312"))
			return "GB2312";
		else
			return "UTF-8";
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
            Entry<Object, Object> e = (Entry<Object, Object>)itr.next();
            this.http.setRequestProperty(e.getKey().toString(), e.getValue().toString());
        }
	}
	
	/**
	 * 设置cookie
	 */
	private void setCookie(){
		this.http.setRequestProperty("Cookie", cookieManager.getCookies(http.getURL().getHost()));
	}
	
	/**
	 * 获取下载流
	 * @return
	 * @throws IOException
	 * @throws NotInitRequestProperties 
	 */
	public InputStream getInputStream() throws IOException, NotInitRequestProperties {  
        InputStream is = http.getInputStream();
        //取到输入流中后处理Cookie信息  
        resolveCookies();  
        int responseCode = http.getResponseCode();  
        if(responseCode != 200 && responseCode != 404 ){  
            //清除cookie并重新发请求  
            CookieManager.getInstance().removeCookies(http.getURL().getHost());  
            try{  
                http.disconnect();  
                is.close();
            }catch (Exception e) {  
            }  
            http = (HttpURLConnection) http.getURL().openConnection();  
            this.setRequestProperty();
            this.setCookie();  
            is = http.getInputStream();  
        }  
        return is;  
    }  
	
	/**
	 * 清空cookies
	 */
	private void resolveCookies(){  
        List<String> setCookies = http.getHeaderFields().get("Set-Cookie");  
        if(setCookies != null && !setCookies.isEmpty()){  
            for (String setCookie : setCookies) {  
                cookieManager.setCookies(http.getURL().getHost(), setCookie);  
            }     
        }  
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
