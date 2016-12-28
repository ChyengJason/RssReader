package com.jscheng.rssreader.ui.activity.module;


import com.jscheng.rssreader.presenter.RssPresenter;
import com.jscheng.rssreader.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cheng on 16-12-27.
 */
@Module
public class MainActivityModule {
    private MainActivity activity;

    public MainActivityModule(MainActivity activity){
        this.activity = activity;
    }

    @Provides
    MainActivity provideActivity(){
        return activity;
    }

    @Provides
    RssPresenter providePresenter(){
        return new RssPresenter(this.activity);
    }
}
