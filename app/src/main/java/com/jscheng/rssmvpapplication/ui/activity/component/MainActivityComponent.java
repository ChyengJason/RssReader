package com.jscheng.rssmvpapplication.ui.activity.component;

import com.jscheng.rssmvpapplication.presenter.RssPresenter;
import com.jscheng.rssmvpapplication.ui.activity.MainActivity;
import com.jscheng.rssmvpapplication.ui.activity.module.MainActivityModule;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by cheng on 16-12-27.
 */
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent {
    MainActivity inject(MainActivity mainActivity);
}
