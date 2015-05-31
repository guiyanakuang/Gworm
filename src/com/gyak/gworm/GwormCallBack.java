package com.gyak.gworm;

import com.gyak.proterty.NotInitRequestProperties;

/**
 * Created by Guiyanakuang
 * on 2015-05-31.
 */
public abstract class GwormCallBack implements Runnable{

    private GwormCoordinate coordinate;
    private String url;

    public GwormCallBack(GwormCoordinate coordinate, String currentUrl) {
        this.coordinate = coordinate;
        this.url = currentUrl;
    }

    @Override
    public void run() {
        GwormBox gwormBox = GwormBox.getInstance();
        String json = null;

        try {
            json = gwormBox.getJson(coordinate, url);
        } catch (NotFindGwormConfigException e) {
            e.printStackTrace();
        } catch (NotInitRequestProperties notInitRequestProperties) {
            notInitRequestProperties.printStackTrace();
        }

        if (json != null) {
            callBack(json);
        }
    }

    abstract void callBack(String json);
}
