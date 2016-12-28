package com.jscheng.rssreader.ui.activity.component;


import com.jscheng.rssreader.ui.activity.WebActivity;
import com.jscheng.rssreader.ui.activity.module.WebActivityModule;

import dagger.Subcomponent;

/**
 * Created by cheng on 16-12-27.
 */
@Subcomponent(modules = WebActivityModule.class)
public interface WebActivityComponent {
    WebActivity inject(WebActivity webActivity);
}
