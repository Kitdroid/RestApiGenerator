package org.kitdroid.restapigenerator.generator;

import org.kitdroid.restapigenerator.Style;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 远航 on 2015/3/11.
 */
public class GeneratorFactory {
    private static final Map<Style,Generator> styles;
    static {
        styles = new HashMap<Style, Generator>();
        styles.put(Style.AsyncHttp, new AsyncHttpGenerator());
        styles.put(Style.Retrofit, new RetrofitGenerator());
        styles.put(Style.OKHttp, new OKHttpGenerator());
    }
    public static Generator create(Style style){
        if(!styles.containsKey(style)){
            throw new IllegalStateException();
        }
        return styles.get(style);
    }
}
