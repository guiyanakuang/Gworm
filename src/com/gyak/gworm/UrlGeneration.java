package com.gyak.gworm;

/**
 * Created by Guiyanakuang
 * on 2015-05-31.
 */
public interface UrlGeneration {

    public int getStart();
    public int getEnd();
    public void next();
    public String getCurrentUrl();
}
