package org.kitdroid.restapigenerator.builder;

import org.kitdroid.restapigenerator.model.Parameter;

import java.util.List;


/**
 * Created by 远航 on 2015/3/11.
 */
public class AsyncHttpBuilder extends ApiBuilder {

    @Override
    public String getResult() {
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

    /*
    String url = "http://10.0.2.2:8080/IC_Server/servlet/RegisterServlet1";
    RequestParams params = new RequestParams();
    params.put("photo", photo);

    AsyncHttpClient client = new AsyncHttpClient();
    AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, String content){ }
        @Override
        public void onFailure(Throwable e, String data){ }
    };
    client.post(url, params, handler);
    */

}
