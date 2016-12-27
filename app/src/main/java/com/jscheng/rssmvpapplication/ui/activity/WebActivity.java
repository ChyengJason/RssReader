package com.jscheng.rssmvpapplication.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.jscheng.rssmvpapplication.R;
import com.jscheng.rssmvpapplication.RssApplication;
import com.jscheng.rssmvpapplication.model.RssInfo;
import com.jscheng.rssmvpapplication.presenter.WebPresenter;
import com.jscheng.rssmvpapplication.ui.activity.module.WebActivityModule;
import com.jscheng.rssmvpapplication.utils.NetworkUtils;
import com.jscheng.rssmvpapplication.view.MyWebView;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cheng on 16-7-24.
 */
public class WebActivity extends BaseActivty implements MyWebView {

    @Inject
    WebPresenter webPresenter;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.webview_act_WebView)
    WebView myWebView;
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_act);
        ButterKnife.bind(this);
        // load & parse json progress view
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置工具栏标题
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings mSetting = myWebView.getSettings();
        mSetting.setJavaScriptEnabled(true); //设置js权限，比如js弹出窗
        mSetting.setSupportMultipleWindows(false);
        mSetting.setBlockNetworkImage(false);//不阻止图片显示
        mSetting.setAllowFileAccess(true);

        //下面是缩放控制
        mSetting.setSupportZoom(false);
        mSetting.setBuiltInZoomControls(false);
        mSetting.setUseWideViewPort(false);
//		mSetting.setCacheMode(WebSettings.LOAD_DEFAULT);//开启缓存
        myWebView.setWebChromeClient(getWebChromeClient());
        myWebView.setWebViewClient(getWebViewClient());

        webPresenter.attachView(this);
        webPresenter.startLoading(getIntent());
    }

    @Override
    public void setupActivityComponent() {
        RssApplication.getInstance().getAppComponent().plus(new WebActivityModule()).inject(this);
    }

    @Override
    public void showLoading() {
        progressWheel.setVisibility(View.VISIBLE);
        ValueAnimator progressFadeInAnim = ObjectAnimator.ofFloat(progressWheel, "alpha", 0, 1, 1);
        progressFadeInAnim.start();
    }

    @Override
    public void hideLoading() {
        progressWheel.setVisibility(View.GONE);
        ValueAnimator progressFadeInAnim = ObjectAnimator.ofFloat(progressWheel, "alpha", 1, 0, 0);
        progressFadeInAnim.start();
    }

    @Override
    public void showError(String msg) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(msg)
                .show();
    }

    @Override
    public void showResult(RssInfo rssInfo) {
        if(rssInfo.getImg()==null || rssInfo.getImg().equals("")) {
//            backdrop.setVisibility(View.GONE);
            ViewGroup.LayoutParams para = backdrop.getLayoutParams();
            para.height = (int)getResources().getDimension(R.dimen.actionBarSize);
            backdrop.setLayoutParams(para);
        }
        else
            Picasso.with(this).load(rssInfo.getImg()).fit().centerCrop().into(backdrop);
        myWebView.loadUrl(rssInfo.getLink());
    }

    @Override
    public void showTitle(String title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public boolean isNetworkAvailable() {
        if(NetworkUtils.isNetworkAvailable(this))
            return true;
        else
            return false;
    }

    /**生成WebChromeClient对象
     * @return
     */
    public WebChromeClient getWebChromeClient(){
        return new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if(100 == newProgress){
                    hideLoading();
                }
            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                // TODO Auto-generated method stub
                super.onReceivedTitle(view, title);
                if (title.contains("404状态页面")) {
                    showError("状态页面丢失");
                    hideLoading();
                }else if(title.contains("找不到网页")){
                    showError("找不到网页");
                    hideLoading();
                }
            }
        };
    }

    /**生成WebViewClient对象
     * @return
     */
    public WebViewClient getWebViewClient(){
        return new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                showLoading();
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                //自定义出错界面
                if(errorCode == -2){//断网
                    showError("网络异常");
                    hideLoading();
                }else{

                }
                String errorHtml = "<html><body></body></html>";
                view.loadData(errorHtml, "text/html", "UTF-8");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        };
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
