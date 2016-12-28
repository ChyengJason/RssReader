package com.jscheng.rssreader;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.jscheng.rssreader.ui.activity.component.AppComponent;
import com.jscheng.rssreader.ui.activity.component.DaggerAppComponent;
import com.jscheng.rssreader.ui.activity.module.AppModule;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by cheng on 16-12-27.
 */
public class RssApplication extends Application {
    private static RssApplication instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initApplicationComponent();
    }

    private void initApplicationComponent() {
        appComponent =  DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static RssApplication getInstance(){
        return instance;
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }
}
