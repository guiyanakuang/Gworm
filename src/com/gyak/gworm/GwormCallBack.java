package com.gyak.gworm;

import com.gyak.proterty.NotInitRequestProperties;
import com.gyak.url.HasUrl;

/**
 * 并发模型类
 * @author  <a href="http://guiyanakuang.com">geek'喵</a>
 * on 2015-05-31.
 */
public abstract class GwormCallBack implements Runnable{

    private GwormCoordinate coordinate;
    private Object bind;

    /**
     * GwormCallBack的构造函数
     * @param coordinate 爬取参数坐标
     * @param bind 对应本次爬取过程绑定的参数
     */
    public GwormCallBack(GwormCoordinate coordinate, Object bind) {
        this.coordinate = coordinate;
        this.bind = bind;
    }

    /**
     * 爬取过程
     */
    @Override
    public void run() {
        GwormBox gwormBox = GwormBox.getInstance();
        String json = null;

        try {
            json = gwormBox.getJson(coordinate, getUrlFromObject(bind));
        } catch (NotFindGwormConfigException e) {
            e.printStackTrace();
        } catch (NotInitRequestProperties notInitRequestProperties) {
            notInitRequestProperties.printStackTrace();
        }

        if (json != null) {
            callBack(json, bind);
        }
    }

    abstract void callBack(String json, Object bind);

    private String getUrlFromObject(Object bind){
        return ((HasUrl)bind).getUrl();
    }
}
