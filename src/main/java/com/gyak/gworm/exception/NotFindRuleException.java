package com.gyak.gworm.exception;

/**
 * Created by guiyanakuang@gmail.com on 2016/6/7.
 */
public class NotFindRuleException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4234234243434431118L;

    private String id;

    public NotFindRuleException(String id) {
        this.id = id;
    }

    @Override
    public void printStackTrace() {
        System.err.println("Gworm[id=" + id +"] is not contain rule");
    }
}
