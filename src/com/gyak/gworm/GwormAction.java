package com.gyak.gworm;

import com.gyak.url.UrlGeneration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Guiyanakuang
 * on 2015-05-31.
 */
public abstract class GwormAction {

    private int taskNum;
    private UrlGeneration urlGeneration;
    private GwormCoordinate coordinate;

    public GwormAction(int taskNum, UrlGeneration urlGeneration, GwormCoordinate coordinate) {
        this.taskNum = taskNum;
        this.urlGeneration = urlGeneration;
        this.coordinate = coordinate;
    }

    public void work() {
        ExecutorService exec = Executors.newFixedThreadPool(taskNum);
        for(int i=urlGeneration.getStart();i<=urlGeneration.getEnd();i++) {
            final Object bind = urlGeneration.getCurrentbindObj();
            exec.execute(new GwormCallBack(coordinate, bind) {
                @Override
                void callBack(String json, Object bind) {
                    action(json, bind);
                }

            });
            urlGeneration.next();
        }
        exec.shutdown();
    }

    public abstract void action(String json, Object obj);

}
