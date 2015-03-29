package org.kitdroid.restapigenerator;

/**
 * Created by 远航 on 2015/3/12.
 */
public enum  RequestType {
    GET,POST,PUT,DELETE,HEAD,TRACE,OPTIONS,CONNECT;

    public static String[] names(){
        RequestType[] values = values();
        String[] names = new String[values.length];
        for(int i = 0; i < values.length; i++){
            names[i] = values[i].name();
        }
        return names;
    }
}
