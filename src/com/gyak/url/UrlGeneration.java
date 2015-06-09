package com.gyak.url;

/**
 * url生成器接口
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2015-05-31.
 */
public interface UrlGeneration {

    /**
     * 获取开始编号
     * @return 开始编号
     */
    int getStart();

    /**
     * 获取结束编号
     * @return 结束编号
     */
    int getEnd();

    /**
     * 移向下一个编号
     */
    void next();

    /**
     * 返回一个HasUrl对象
     * @return HasUrl
     */
    HasUrl getCurrentbindObj();
}
