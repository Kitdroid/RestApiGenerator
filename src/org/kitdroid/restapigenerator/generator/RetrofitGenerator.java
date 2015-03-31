package org.kitdroid.restapigenerator.generator;

import org.kitdroid.restapigenerator.model.Parameter;

import java.util.List;

/**
 * Created by 远航 on 2015/3/11.
 */
public class RetrofitGenerator extends BaseGenerator {
    @Override
    public String generate(String requestType, String comment, String methodName, String url, List<Parameter> parameters) {
        return null;
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
}
