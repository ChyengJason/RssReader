package com.jscheng.rssmvpapplication.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jscheng.rssmvpapplication.R;
import com.jscheng.rssmvpapplication.adapter.RssAdapter;
import com.jscheng.rssmvpapplication.model.RssInfo;
import com.jscheng.rssmvpapplication.presenter.RssPresenter;
import com.jscheng.rssmvpapplication.utils.NetworkUtils;
import com.jscheng.rssmvpapplication.view.RssView;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivty implements RssView, SwipeRefreshLayout.OnRefreshListener  {
    private RssPresenter presenter;
    private ProgressWheel progressWheel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView listView;
    private RssAdapter rssAdapter;
    private long exitTime;
    private RelativeLayout toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        setToolbar();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        listView = (RecyclerView)findViewById(R.id.recyclerView);
        rssAdapter = new RssAdapter(MainActivity.this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(rssAdapter);
        // MVP: presenter
        presenter = new RssPresenter(MainActivity.this);
        presenter.attachView(this); // important, must attachView before use presenter
        presenter.startLoadFacts();
    }

    @Override
    public void showLoading() {
        progressWheel.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
        // swipeRefreshLayout.setRefreshing(true);
        // use objectAnimator let progress from InVisible to Visible
        ValueAnimator progressFadeInAnim = ObjectAnimator.ofFloat(progressWheel, "alpha", 0, 1, 1);
        progressFadeInAnim.start();
    }

    @Override
    public void hideLoading() {
        progressWheel.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false); // close refresh animator
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
    public void showResult(List<RssInfo> rssInfos) {
        rssAdapter.setRssInfos(rssInfos);
    }

    @Override
    public void showTitle(String title) {
        ((TextView)findViewById(R.id.toolbar_title)).setText(title);
    }

    @Override
    public boolean isNetworkAvailable() {
        if(NetworkUtils.isNetworkAvailable(MainActivity.this))
            return true;
        else
            return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView(false);
    }

    @Override
    public void onRefresh() {
        presenter.startLoadFacts();
    }

    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis()-exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else {
            exitTime = 0;
            finish();
        }
    }

    private void setToolbar() {
        toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        final ImageButton right_btn=  (ImageButton)findViewById(R.id.right_btn);
        right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(right_btn);
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((System.currentTimeMillis()-exitTime) > 2000) {
                    exitTime = System.currentTimeMillis();
                }
                else {
                    exitTime = 0;
                    listView.smoothScrollToPosition(0);
                }
            }
        });
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.main_activity_actions, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_settings:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_share:
                        showShare();
                        break;
                }
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) { }
        });
        popupMenu.show();
    }
}
