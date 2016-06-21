package com.gyak.gworm;

import org.jsoup.select.Elements;

/**
 * 爬取规则接口
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2016-06-20.
 */
public interface GwormRule {

    String getValue(Elements elements);
}
