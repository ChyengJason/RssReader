package com.jscheng.rssmvpapplication.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jscheng.rssmvpapplication.model.RssInfo;
import com.jscheng.rssmvpapplication.model.RssLoadListener;
import com.jscheng.rssmvpapplication.model.RssLoadModel;
import com.jscheng.rssmvpapplication.utils.XMLRequest;
import com.jscheng.rssmvpapplication.view.RssView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 16-7-24.
 */
public class FactPresenter implements MvpPresenter<RssView> {
    private final static String TAG = "FactPresenter";
    private final static String RSS_URL = "http://media.stu.edu.cn/feed";

    private RssView view;
    private Context context;

    public FactPresenter(Context context) {
        this.context = context;
    }

    public void startLoadFacts() {
        if (view == null) {
            Log.w(TAG, "[startLoadFacts] please attach view first.");
            return;
        }

        new RssLoadModel(context,RSS_URL).excute(new RssLoadListener() {

            @Override
            public void onBeginLoad() {
                view.showLoading();
            }

            @Override
            public void onLoadSuccess(List<RssInfo> rssInfoList) {
                Log.d(TAG, "onLoadSuccess: ");
                view.hideLoading();
                view.showResult(rssInfoList);
            }

            @Override
            public void onLoadFailed() {
                view.hideLoading();
                view.showError("更新失败");
            }

            @Override
            public void onNetWorkFailed() {
                view.showError("网络不可用");
                view.hideLoading();
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
