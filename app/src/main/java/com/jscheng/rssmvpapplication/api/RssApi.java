package com.jscheng.rssmvpapplication.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by cheng on 16-12-27.
 */
public interface RssApi {

//    @GET("feed")
//    Call<ResponseBody> getRssHtml();

//    @GET("feed")
//    Observable<retrofit2.Response<ResponseBody>> getRssHtml();

    @GET("feed")
    Observable<String> getRssHtml();
}
