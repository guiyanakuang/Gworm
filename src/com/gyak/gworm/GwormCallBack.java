package com.gyak.gworm;

import com.gyak.proterty.NotInitRequestProperties;
import com.gyak.url.HasUrl;

/**
 * Created by Guiyanakuang
 * on 2015-05-31.
 */
public abstract class GwormCallBack implements Runnable{

    private GwormCoordinate coordinate;
    private Object obj;

    public GwormCallBack(GwormCoordinate coordinate, Object obj) {
        this.coordinate = coordinate;
        this.obj = obj;
    }

    @Override
    public void run() {
        GwormBox gwormBox = GwormBox.getInstance();
        String json = null;

        try {
            json = gwormBox.getJson(coordinate, getUrlFromObject(obj));
        } catch (NotFindGwormConfigException e) {
            e.printStackTrace();
        } catch (NotInitRequestProperties notInitRequestProperties) {
            notInitRequestProperties.printStackTrace();
        }

        if (json != null) {
            callBack(json, obj);
        }
    }

    abstract void callBack(String json, Object obj);

    private String getUrlFromObject(Object obj){
        return ((HasUrl)obj).getUrl();
    }
}
