package org.kitdroid.restapigenerator.builder;

import org.kitdroid.restapigenerator.RequestType;
import org.kitdroid.restapigenerator.generator.BaseGenerator;
import org.kitdroid.restapigenerator.model.Parameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 远航 on 2015/3/11.
 */
public class RetrofitBuilder extends ApiBuilder {

    Pattern PATH_PARAMETER_PATTERN = Pattern.compile("\\{[a-zA-Z]+[a-zA-Z0-9]*\\}");

    @Override
    public String getResult() {
        StringBuilder builder = new StringBuilder();

        builder.append(getDocComment());
        builder.append("\n");

        RequestType requestType = getRequestType();
        if(requestType == RequestType.POST){
            builder.append("@FormUrlEncoded");
            builder.append("\n");
        }
        builder.append("@");
        builder.append(requestType.name());
        builder.append("(\"");
        String path = getPath();
        builder.append(path);
        builder.append("\")\n");

        builder.append("public void ");
        builder.append(getMethodName());
        builder.append("( ");

        // parameters
        List<String> pathParameters = getPathParameters(path);
        for (String name : pathParameters) {
            builder.append("@Path(\"");
            builder.append(name);
            builder.append("\") String ");
            builder.append(name);
            builder.append(", ");
        }
        List<Parameter> parameters = getParameters();
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            if(pathParameters.contains(name)){
                continue;
            }
            builder.append("@Field(\"");
            builder.append(name);
            builder.append("\") ");
            builder.append(parameter.getValue());
            builder.append(", ");
        }

        builder.append("Callback<?> callback );\n\n");
        return builder.toString();
    }


        public List<String> getPathParameters(String path) {
            Matcher m = PATH_PARAMETER_PATTERN.matcher(path);
            List<String> strings = new ArrayList<String>();
            while(m.find()) {
                String group = m.group(0);
                strings.add(group.substring(1,group.length()-1));
            }
            return strings;
        }



    /*
    @FormUrlEncoded
    @POST("/user/{token}/modOrderContinueStatus.html")
    public void modOrderContinueStatus(@Path("token") String token,
                                       @Field("pid") String pid,
                                       @Field("continueStatus") String continueStatus,
                                       Callback<BaseResponse> callback);

    @GET("/user/{token}/getMProductMaxAmount.html")
    public void getMProductMaxAmount(@Path("token") String token,
                                     Callback<MProductMaxAmountResp> callback);
    */
    public String getResult(int i) {
        StringBuilder builder = new StringBuilder();

        builder.append(getDocComment());

        builder.append("\n");
        builder.append("public void ");
        builder.append(getMethodName());
        builder.append("( ");
        // parameters
        List<Parameter> parameters = getParameters();
        for (Parameter parameter : parameters) {
            builder.append(parameter.getValue());
            builder.append(", ");
        }
        builder.append("AsyncHttpResponseHandler handler ) {\n");
        // url
        builder.append("    String url = ");
        builder.append(getUrl());
        builder.append(";\n\n");

        builder.append("    RequestParams params = new RequestParams();\n");

        // set parameters
        for (Parameter parameter : parameters) {
            builder.append("    params.put(\"");
            String name = parameter.getName();
            builder.append(name);
            builder.append("\", ");
            builder.append(name);
            builder.append(" );\n");
        }
        // request
        builder.append("\n    ");
        builder.append("send");
        builder.append(getRequestType().name());

        builder.append("(url, params, handler);");
        builder.append("\n}\n");
        return builder.toString();
    }

}
