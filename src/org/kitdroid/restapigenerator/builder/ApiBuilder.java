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
 * Created by 远航 on 2015/3/29.
 */
public abstract class ApiBuilder {

    private static final Map<Style,ApiBuilder> styles;
    static {
        styles = new HashMap<Style, ApiBuilder>();
        styles.put(Style.AsyncHttp, new AsyncHttpBuilder());
        styles.put(Style.Retrofit, new RetrofitBuilder());
        styles.put(Style.OKHttp, new OKHttpBuilder());
    }

    private RequestType requestType;
    private String docComment;
    private String url;
    private String host;
    private String path;
    private String methodName;
    private List<Parameter> parameters;

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
        requestType = type;
    }

    public void setComment(String comment) {
        docComment = formatDocComment(comment);
    }

    public void setUrl(String host,String path){
        this.host = host;
        this.path = path;
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
        return requestType;
    }

    public String getDocComment() {
        return docComment;
    }

    public String getUrl() {
        return url;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    //TODO 这里有问题
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
            if(position == sb.length()){
                sb.replace(position -1,position,"");
                continue;
            }
            String substring = sb.substring(position, position + 1);
            char c = substring.toCharArray()[0];
            if((c >='a' && c<='z') || (c >='A' && c<='Z')  ){
                sb.replace(position-1,position+1,substring.toUpperCase());
            }
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
        methodName = splitLineToCamel(methodName, "\\{");
        methodName = splitLineToCamel(methodName, "\\}");
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
