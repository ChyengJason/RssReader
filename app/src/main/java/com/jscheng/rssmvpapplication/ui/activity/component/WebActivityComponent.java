package com.jscheng.rssmvpapplication.ui.activity.component;

import com.jscheng.rssmvpapplication.ui.activity.MainActivity;
import com.jscheng.rssmvpapplication.ui.activity.WebActivity;
import com.jscheng.rssmvpapplication.ui.activity.module.MainActivityModule;
import com.jscheng.rssmvpapplication.ui.activity.module.WebActivityModule;

import dagger.Subcomponent;

/**
 * Created by cheng on 16-12-27.
 */
@Subcomponent(modules = WebActivityModule.class)
public interface WebActivityComponent {
    WebActivity inject(WebActivity webActivity);
}
