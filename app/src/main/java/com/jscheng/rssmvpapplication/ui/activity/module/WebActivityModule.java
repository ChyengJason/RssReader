package com.jscheng.rssmvpapplication.ui.activity.module;

import com.jscheng.rssmvpapplication.presenter.WebPresenter;

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
