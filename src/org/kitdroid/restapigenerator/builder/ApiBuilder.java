package org.kitdroid.restapigenerator.builder;

import com.sun.istack.internal.NotNull;
import org.kitdroid.restapigenerator.RequestType;
import org.kitdroid.restapigenerator.Style;
import org.kitdroid.restapigenerator.common.TextUtils;
import org.kitdroid.restapigenerator.model.Parameter;
import org.kitdroid.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ô¶º½ on 2015/3/29.
 */
public abstract class ApiBuilder {

    private static final Map<Style,ApiBuilder> styles;
    static {
        styles = new HashMap<Style, ApiBuilder>();
        styles.put(Style.AsyncHttp, new AsyncHttpBuilder());
        styles.put(Style.Retrofit, new RetrofitBuilder());
        styles.put(Style.OKHttp, new OKHttpBuilder());
    }


    private RequestType mRequestType;
    private String docComment;
    private String url;
    private List<Parameter> parameters;
    private String methodName;

    private static ApiBuilder create(Style style){
        if(!styles.containsKey(style)){
            throw new IllegalStateException();
        }
        return styles.get(style);
    }


    public static ApiBuilder getBuilder(Style style){
        return create(style);
    }

    public abstract String getResult();

    public void setRequestType(RequestType type){
        mRequestType = type;
    }

    public void setComment(String comment) {
        docComment = formatDocComment(comment);
    }

    public void setUrl(String host,String path){
        url = formatUrl(host, path);
        if(StringUtils.isEmpty(methodName)){
            methodName = formatMethodName(path);
        }
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameters(String dataTypeLines,String parameterLines){
        parameters = formatParameter(dataTypeLines, parameterLines);
    }

    public RequestType getRequestType() {
        return mRequestType;
    }

    public String getDocComment() {
        return docComment;
    }

    public String getUrl() {
        return url;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public String getMethodName() {
        return methodName;
    }
//    public abstract String generate(String requestMethodName, String docComment, String methodName, String url, List<Parameter> parameters);
//
//    public String generate(RequestType requestType,
//                           String comment,
//                           String host,
//                           String path,
//                           String dataTypeLines,
//                           String parameterLines) {
//        String docComment = formatDocComment(comment);
//        String methodName = formatMethodName(path);
//        String url = formatUrl(host, path);
//        List<Parameter> parameters = formatParameter(dataTypeLines, parameterLines);
//
//        return generate(requestType.name().toLowerCase(), docComment, methodName, url, parameters);
//    }


    protected String splitLineToCamel(String param, String taget){
        String paramTrim = param.trim();
        String tagetTrim = taget.trim();
        if (TextUtils.isEmpty(paramTrim)){
            return "";
        }
        if(TextUtils.isEmpty(tagetTrim)){
            return "";
        }
        StringBuilder sb=new StringBuilder(paramTrim);
        Matcher mc= Pattern.compile(taget).matcher(paramTrim);
        int i=0;
        while (mc.find()){
            int position=mc.end()-(i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
        }
        return sb.toString();
    }


    protected List<Parameter> formatParameter(String dataTypesStr, String parameterNamesStr){
        String[] types = dataTypesStr.replace(" ", "\n").split("\n");
        String[] names = parameterNamesStr.replace(" ", "\n").split("\n");
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        int length =  names.length;
        for(int i = 0; i < length; i++){
            String name = names[i];
            if(TextUtils.isEmpty(name)){
                continue;
            }
            Parameter parameter = new Parameter();
            parameter.setName(name);
            String type = i < types.length ? types[i] : "String";
            parameter.setTypeName(TextUtils.isEmpty(type) ? "String" : type);
            parameters.add(parameter);
        }

        return parameters;
    }


    protected String formatMethodName(@NotNull String path){
        String methodName = splitLineToCamel(path, "/");
        methodName = splitLineToCamel(methodName, "=");
        methodName = splitLineToCamel(methodName, "&");
        methodName = splitLineToCamel(methodName, "\\?");
        methodName = splitLineToCamel(methodName, "\\.");
        return methodName.replace(" ", "");
    }

    protected String formatDocComment(String comment){
        String[] commentLines = comment.split("\n");
        StringBuilder builder = new StringBuilder("/**\n");
        for(String line : commentLines){
            builder.append("* ");
            builder.append(line);
            builder.append("\n");
        }
        builder.append("*/");
        return builder.toString();
    }

    protected String formatUrl(@NotNull String host, String path){
        StringBuilder builder = new StringBuilder(host);
        if(!TextUtils.isEmpty(path)){
            builder.append(" + \"");
            builder.append(path.replace(" ", ""));
            builder.append("\"");
        }
        return builder.toString();
    }

}
