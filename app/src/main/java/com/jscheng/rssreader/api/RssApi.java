package com.jscheng.rssreader.api;


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
