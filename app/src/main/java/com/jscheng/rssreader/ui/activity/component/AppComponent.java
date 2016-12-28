package com.jscheng.rssreader.ui.activity.component;


import com.jscheng.rssreader.ui.activity.module.AppModule;
import com.jscheng.rssreader.ui.activity.module.MainActivityModule;
import com.jscheng.rssreader.ui.activity.module.WebActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cheng on 16-12-27.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    MainActivityComponent plus(MainActivityModule module);
    WebActivityComponent plus(WebActivityModule module);
}