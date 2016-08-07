package com.jscheng.rssmvpapplication.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.jscheng.rssmvpapplication.model.RssInfo;
import com.jscheng.rssmvpapplication.view.MyWebView;

/**
 * Created by cheng on 16-7-24.
 */
public class WebPresenter implements MvpPresenter<MyWebView> {
    private final static String TAG = "WebPresenter";
    private MyWebView view;
    private RssInfo rssInfo;

    @Override
    public void attachView(MyWebView view) {
        this.view = view;
    }

    @Override
    public void detachView(boolean retainInstance) {

    }

    public void startLoading(Intent intent) {
        Bundle bundle = intent.getExtras();
        rssInfo = (RssInfo) bundle.get("RssInfo");
        startLoading();
    }

    public void startLoading(){
        view.showLoading();
        view.showTitle("Loading... ");
        if (rssInfo == null || rssInfo.getLink() == null || rssInfo.getLink().equals("")) {
            view.hideLoading();
            view.showError("该网页不存在");
        }
        else if (!view.isNetworkAvailable()) {
            view.hideLoading();
            view.showError("网络异常");
        }
        else {
            view.showResult(rssInfo);
        }
        view.showTitle("     ");
    }
}
