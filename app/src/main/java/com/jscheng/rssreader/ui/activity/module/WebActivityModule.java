package com.jscheng.rssreader.ui.activity.module;


import com.jscheng.rssreader.presenter.WebPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cheng on 16-12-27.
 */
@Module
public class WebActivityModule {
    @Provides
    WebPresenter providePresenter(){
        return new WebPresenter();
    }
}
