package com.jscheng.rssmvpapplication;

import android.app.Application;

import com.jscheng.rssmvpapplication.ui.activity.component.AppComponent;
import com.jscheng.rssmvpapplication.ui.activity.component.DaggerAppComponent;
import com.jscheng.rssmvpapplication.ui.activity.module.AppModule;

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
}
