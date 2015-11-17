package com.gyak.http;

import com.gyak.proterty.NotInitRequestProperties;

import java.io.IOException;

/**
 * @author <a href="http://guiyanakuang.com">geek'喵</a>
 *         on 2015-06-13.
 */
public class DefaultGetHtml implements Htmlable {

    public static DefaultGetHtml getHtmlable() {
        return new DefaultGetHtml();
    }


    @Override
    /**
     * 通过url获取链接文件内容
     * @param url 链接
     * @return 文件内容
     * @throws NotInitRequestProperties 没有初始化http请求参数
     */
    public String getHtml(String url) throws NotInitRequestProperties {
        Gurl gurl = new Gurl(url);
        try {
            gurl.openUrl();
            return InputStreamUtils.getHttp(gurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
