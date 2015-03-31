package org.kitdroid.restapigenerator.builder;

import org.kitdroid.restapigenerator.generator.BaseGenerator;
import org.kitdroid.restapigenerator.model.Parameter;

import java.util.List;


/**
 * Created by 远航 on 2015/3/11.
 */
public class AsyncHttpBuilder extends ApiBuilder {

    public String generate(String requestType, String docComment, String methodName, String url, List<Parameter> parameters) {
        StringBuilder builder = new StringBuilder();

        builder.append(docComment);

        builder.append("\n");
        builder.append("public void ");
        builder.append(methodName);
        builder.append("( ");
        // parameters
        for (Parameter parameter : parameters){
            builder.append(parameter.getValue());
            builder.append(", ");
        }
        builder.append("AsyncHttpResponseHandler handler ) {\n");
        // url
        builder.append("    String url = ");
        builder.append(url);
        builder.append(";\n\n");

        builder.append("    RequestParams params = new RequestParams();\n");

        // set parameters
        for (Parameter parameter : parameters){
            builder.append("    params.put(\"");
            String name = parameter.getName();
            builder.append(name);
            builder.append("\", ");
            builder.append(name);
            builder.append(" );\n");
        }
        // request
        builder.append("\n    ");
        builder.append(requestType.toLowerCase());

        builder.append("(url, params, handler);");
        builder.append("\n}\n");
        return builder.toString();
    }

    @Override
    public String build() {
        return null;
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
