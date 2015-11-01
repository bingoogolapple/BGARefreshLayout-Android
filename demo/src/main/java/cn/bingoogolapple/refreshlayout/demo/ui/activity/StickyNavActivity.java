package cn.bingoogolapple.refreshlayout.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.bingoogolapple.refreshlayout.demo.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/11/1 下午1:58
 * 描述:
 */
public class StickyNavActivity  extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sticky_nav);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    public void normalRecyclerViewDemo(View v) {
        startActivity(new Intent(this, NormalRecyclerViewActivity.class));
    }

    public void normalListViewDemo(View v) {
        startActivity(new Intent(this, NormalListViewActivity.class));
    }

    public void swipeRecyclerViewDemo(View v) {
        startActivity(new Intent(this, SwipeRecyclerViewActivity.class));
    }

    public void swipeListViewDemo(View v) {
        startActivity(new Intent(this, SwipeListViewActivity.class));
    }

    public void scrollViewDemo(View v) {
        startActivity(new Intent(this, ScrollViewActivity.class));
    }

    public void webViewDemo(View v) {
        startActivity(new Intent(this, WebViewActivity.class));
    }

    public void viewPagerDemo(View v) {
        startActivity(new Intent(this, ViewPagerActivity.class));
    }
}