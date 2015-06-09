package com.gyak.url;

/**
 * Created by Guiyanakuang
 * on 2015-05-31.
 */
public interface UrlGeneration {

    int getStart();
    int getEnd();
    void next();
    HasUrl getCurrentbindObj();
}
