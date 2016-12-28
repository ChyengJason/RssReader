package com.jscheng.rssmvpapplication.api;

import com.jscheng.rssmvpapplication.utils.Constants;
import com.jscheng.rssmvpapplication.utils.StringConverterFactory;
import com.jscheng.rssmvpapplication.utils.ToStringConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by cheng on 16-12-27.
 */
public class RssService {
    private static RssApi rssApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
//    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static ToStringConverterFactory stringConverterFactory =new ToStringConverterFactory();

    public static RssApi getRssApi(){
        if(rssApi==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.RSS_BASE_URL)
                    .addConverterFactory(stringConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            rssApi = retrofit.create(RssApi.class);
        }
        return rssApi;
    }
}
