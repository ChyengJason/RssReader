package com.jscheng.rssreader.presenter;

import android.content.Context;

import com.jscheng.rssreader.api.RssService;
import com.jscheng.rssreader.model.RssInfo;
import com.jscheng.rssreader.model.RssParser;
import com.jscheng.rssreader.utils.NetworkUtils;
import com.jscheng.rssreader.view.RssView;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cheng on 16-7-24.
 */
public class RssPresenter implements MvpPresenter<RssView> {
    private final static String TAG = "RssPresenter";

    private RssView view;
    private Context context;

    public RssPresenter(Context context) {
        this.context = context;
    }

    public void startLoadTask() {
        if (view == null) {
            Logger.w(TAG, "please attach view first.");
            return;
        }

//        Observable<retrofit2.Response<ResponseBody>> observer = RssService.getRssApi().getRssHtml();
//        observer.doOnNext(new Action1<retrofit2.Response<ResponseBody>>() {
//                    @Override
//                    public void call(retrofit2.Response<ResponseBody> s) {
//
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<retrofit2.Response<ResponseBody>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(retrofit2.Response<ResponseBody> s) {
//                        try {
//                            Logger.e(s.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

//        Call<ResponseBody> call = RssService.getRssApi().getRssHtml();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//
//                if(response.isSuccessful())
//                    try {
//                        ResponseBody body = response.body();
//                        String result = body.string();
//                        Logger.e(result);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

        Observable<String> observer = RssService.getRssApi().getRssHtml();
        observer.doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<String, List<RssInfo>>() {
                    @Override
                    public List<RssInfo> call(String s) {
                        return RssParser.parserXML(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RssInfo>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        view.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        if (!NetworkUtils.isNetworkAvailable(context))
                            view.showError("网络不可用");
                        else
                            view.showError("更新失败");
                    }

                    @Override
                    public void onNext(List<RssInfo> rssInfos) {
                        view.showResult(rssInfos);
                    }
                });

    }

    @Override
    public void attachView(RssView view) {
        this.view = view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        // TODO

    }
}
