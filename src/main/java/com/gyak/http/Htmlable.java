package com.gyak.http;

import com.gyak.proterty.NotInitRequestProperties;

import java.io.IOException;

/**
 * 返回html接口
 * @author <a href="http://guiyanakuang.com">geek'喵</a>
 *         on 2015-06-12.
 */
public interface Htmlable {
    /**
     * 通过url获取html
     * @param url 链接
     * @return html内容
     */
    String getHtml(String url) throws NotInitRequestProperties, IOException;
}
