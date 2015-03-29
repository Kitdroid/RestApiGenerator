package org.kitdroid.restapigenerator;

import java.util.Arrays;

/**
 * Created by 远航 on 2015/3/11.
 */
public enum Style {
    AsyncHttp, Retrofit, OKHttp;

    public static String[] names(){
        Style[] values = values();
        String[] names = new String[values.length];
        for(int i = 0; i < values.length; i++){
            names[i] = values[i].name();
        }
        return names;
    }
}
