package com.gyak.gworm;

/**
 * Created by Guiyanakuang
 * on 2015-05-31.
 */
public class GwormCoordinate {

    private String name;
    private String urlId;
    private String jsonId;

    public GwormCoordinate(String name, String urlId) {
        this.name = name;
        this.urlId = urlId;
    }

    public GwormCoordinate(String name, String urlId, String jsonId) {
        this(name, urlId);
        this.jsonId = jsonId;
    }

    public String getName() {
        return name;
    }

    public String getUrlId() {
        return urlId;
    }

    public String getJsonId() {
        return jsonId;
    }
}
