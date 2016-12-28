package com.jscheng.rssreader.presenter;


import com.jscheng.rssreader.view.MvpView;

/**
 * Created by cheng on 16-7-24.
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     * Bind presenter with MvpView
     * @param view
     */
    public void attachView(V view);

    /**
     * @param retainInstance
     * unBind
     */
    public void detachView(boolean retainInstance);
}
