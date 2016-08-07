package com.jscheng.rssmvpapplication.view;


import com.jscheng.rssmvpapplication.model.RssInfo;

/**
 * Created by cheng on 16-7-24.
 */
public interface MyWebView extends MvpView {
    /**
     * show loading view
     */
    void showLoading();

    /**
     * hide loading view when finish load or exception
     */
    void hideLoading();

    /**
     * show error message
     * @param msg
     */
    void showError(String msg);

    /**
     * show list item
     * @param rssInfo
     */
    void showResult(RssInfo rssInfo);

    /**
     * update action bar title
     * @param title
     */
    void showTitle(String title);


    boolean isNetworkAvailable();
}
