package com.jscheng.rssreader.view;


import com.jscheng.rssreader.model.RssInfo;

import java.util.List;

/**
 * Created by cheng on 16-7-24.
 */
public interface RssView extends MvpView {
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
     * @param rssInfos
     */
    void showResult(List<RssInfo> rssInfos);

    /**
     * update action bar title
     * @param title
     */
    void showTitle(String title);


    boolean isNetworkAvailable();
}
