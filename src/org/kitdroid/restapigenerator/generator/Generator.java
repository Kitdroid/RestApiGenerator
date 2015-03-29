package org.kitdroid.restapigenerator.generator;

import org.kitdroid.restapigenerator.RequestType;

import java.util.List;

/**
 * Created by 远航 on 2015/3/11.
 */
public interface Generator {
    public String generate(RequestType requestType, String comment, String host, String path, String dataTypeLines, String parameterLines);
}
