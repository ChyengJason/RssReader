package com.jscheng.rssmvpapplication.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jscheng.rssmvpapplication.R;
import com.jscheng.rssmvpapplication.utils.Constants;
import com.jscheng.rssmvpapplication.utils.SharedPreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2016/8/5.
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_act_point_linear)
    LinearLayout point_linear;

    @BindView(R.id.splash_act_viewpager)
    ViewPager viewPager;

    private ArrayList<ImageView> pointViews ;
    private Integer[] imageArr = {R.mipmap.b1,R.mipmap.b2,R.mipmap.b1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIsFirstStart();
        setContentView(R.layout.splash_act);
        ButterKnife.bind(this);
        initPoint();
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        viewPager.setAdapter(new SamplePagerAdapter());
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < pointViews.size(); i++) {
                    if (arg0 == i) {
                        pointViews.get(i).setBackgroundResource(R.mipmap.home_circle_press_image);
                    } else {
                        pointViews.get(i).setBackgroundResource(R.mipmap.home_circle_normal_image);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
    }

    private void checkIsFirstStart() {
        boolean isFristStart =(boolean)SharedPreferencesUtils.getParam(this, Constants.FRIST_STRART, true);
        if(isFristStart){
            SharedPreferencesUtils.setParam(this, Constants.FRIST_STRART, false);
        }else{
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageArr.length;
        }
        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(SplashActivity.this)
                    .inflate(R.layout.splash_viewpager_item, null,false);
            ImageView iv = (ImageView) view.findViewById(R.id.splash_viewpager_item_iv);
            iv.setBackgroundResource(imageArr[position]);
            Button btn = (Button) view.findViewById(R.id.splash_viewpager_item_btn_start);
            if(imageArr.length-1 == position){
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(getOnClickListener());
            }else{
                btn.setVisibility(View.GONE);
            }
            container.addView(view, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            return view;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public View.OnClickListener getOnClickListener() {
            return new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    /**
     * 初始小圆点游标
     */
    private void initPoint() {
        pointViews = new ArrayList<ImageView>();
        ImageView imageView;
        for (int i = 0; i < imageArr.length; i++) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(R.mipmap.home_circle_normal_image);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 20;//图片大小
            layoutParams.height = 20;
            point_linear.addView(imageView, layoutParams);
            if (i == 0) {
                imageView.setBackgroundResource(R.mipmap.home_circle_press_image);
            }
            pointViews.add(imageView);
        }
    }
}
