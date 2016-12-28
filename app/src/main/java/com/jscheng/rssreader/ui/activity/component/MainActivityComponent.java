package com.jscheng.rssreader.ui.activity.component;


import com.jscheng.rssreader.ui.activity.MainActivity;
import com.jscheng.rssreader.ui.activity.module.MainActivityModule;

import dagger.Subcomponent;

/**
 * Created by cheng on 16-12-27.
 */
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent {
    MainActivity inject(MainActivity mainActivity);
}
