package com.gyak.gworm;

/**
 * 爬取参数坐标类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2015-05-31.
 */
public class GwormCoordinate {

    private String name;
    private String urlId;
    private String jsonId;

    /**
     * GwormCoordinate的构造函数
     * @param name {@link com.gyak.gworm.GwormBox GwormBox}在保存{@link com.gyak.gworm.Gworm Gworm}的key
     * @param urlId 配置文件中url标签对应的id
     */
    public GwormCoordinate(String name, String urlId) {
        this.name = name;
        this.urlId = urlId;
    }

    /**
     * GwormCoordinate的构造函数
     * @param name {@link com.gyak.gworm.GwormBox GwormBox}在保存{@link com.gyak.gworm.Gworm Gworm}的key
     * @param urlId 配置文件中url标签对应的id
     * @param jsonId 配置文件中value标签对应的id
     */
    public GwormCoordinate(String name, String urlId, String jsonId) {
        this(name, urlId);
        this.jsonId = jsonId;
    }

    /**
     * 获取name
     * @return {@link com.gyak.gworm.GwormBox GwormBox}在保存{@link com.gyak.gworm.Gworm Gworm}的key
     */
    public String getName() {
        return name;
    }

    /**
     * 获取urlId
     * @return 配置文件中url标签对应的id
     */
    public String getUrlId() {
        return urlId;
    }

    /**
     * 获取jsonId
     * @return 配置文件中value标签对应的id
     */
    public String getJsonId() {
        return jsonId;
    }
}
