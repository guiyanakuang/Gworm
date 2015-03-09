package com.gyak.http;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 简单的Cookie Manager，按照顶级域名管理Cookie信息
 * @author <a href="mailto:jingyu@taohua.com">惊羽</a>
 *
 */
class CookieManager implements Serializable{
	private static final long serialVersionUID = 292218695837624307L;
	private static CookieManager cookieManager = new CookieManager();
	private Map<String,Map<String,String>> cookies = new ConcurrentHashMap<String, Map<String,String>>();
	
	private CookieManager(){}
	/**
	 * 根据域名获取对应的Cookie
	 * @param domain
	 * @return
	 */
	public String getCookies(String domain){
		Map<String, String> domainCookies = cookies.get(getTopLevelDomain(domain));
		if(domainCookies != null){
			StringBuilder sb = new StringBuilder();
			boolean isFirst = true;
			for(Map.Entry<String, String> cookieEntry : domainCookies.entrySet()){
				if(!isFirst){
					sb.append("; ");
				}else{
					isFirst = false;
				}
				sb.append(cookieEntry.getKey())
			      .append("=")
			      .append(cookieEntry.getValue());
			}
			return sb.toString();
		}
		return "";
	}
	
	/**
	 *
	 * 设置Cookie值
	 * @param domain
	 * @param cookiesString
	 */
	public void setCookies(String domain,String cookiesString){
		Map<String, String> domainCookies = cookies.get(getTopLevelDomain(domain));
		if(domainCookies == null){
			domainCookies = new ConcurrentHashMap<String, String>();
			cookies.put(getTopLevelDomain(domain), domainCookies);
		}
		String[] cookies = cookiesString.split("; ");
		for (String cookie : cookies) {
			if(cookie != null && !cookie.trim().isEmpty()
					&& cookie.indexOf("=") > 0){
				int equalMarkIndex = cookie.indexOf("=");
				String key = cookie.substring(0,equalMarkIndex);
				String value = cookie.substring(equalMarkIndex+1);
				domainCookies.put(key, value);
			}
		}
	}
	/**
	 * 删除域名下所有的Cookie
	 * @param domain
	 */
	public void removeCookies(String domain){
		cookies.remove(getTopLevelDomain(domain));
	}
	
	/**
	 * 获取CookieManager的实例
	 * @return
	 */
	public static CookieManager getInstance(){
		return cookieManager;
	}
	
	/**
	 * 获取域名的顶级域名
	 * @param domain
	 * @return
	 */
	public String getTopLevelDomain(String domain){
		if(domain == null){
			return null;
		}
		if(!domainToTopLevelDomainMap.containsKey(domain)){
			String[] splits = domain.split("\\.");
			domainToTopLevelDomainMap.put(domain, (splits[splits.length-2] + "." + splits[splits.length -1]));
		}
		return domainToTopLevelDomainMap.get(domain);
	}
	/**
	 * 存储域名与其顶级域名之间映射关系，避免重复的计算顶级域名
	 */
	private Map<String,String> domainToTopLevelDomainMap = new ConcurrentHashMap<String, String>();
}