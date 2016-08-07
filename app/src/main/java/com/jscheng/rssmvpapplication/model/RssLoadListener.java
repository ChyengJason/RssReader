package com.jscheng.rssmvpapplication.model;

import java.util.List;

/**
 * Created by cheng on 16-7-29.
 */
public interface RssLoadListener {
    void onLoadSuccess(List<RssInfo> rssInfoList);
    void onLoadFailed();
    void onNetWorkFailed();
    void onBeginLoad();
}
