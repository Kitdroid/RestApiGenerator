package org.kitdroid.restapigenerator.builder;

import org.kitdroid.restapigenerator.generator.BaseGenerator;
import org.kitdroid.restapigenerator.model.Parameter;

import java.util.List;

/**
 * Created by 远航 on 2015/3/11.
 */
public class RetrofitBuilder extends ApiBuilder {
    @Override
    public String build() {
        // TODO
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
