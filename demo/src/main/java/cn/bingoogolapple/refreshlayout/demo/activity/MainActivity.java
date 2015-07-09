package cn.bingoogolapple.refreshlayout.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.bingoogolapple.refreshlayout.demo.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/28 10:23
 * 描述:
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int LOADING_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeToGridViewDemo(View v) {
        startActivity(new Intent(this, GridViewDemoActivity.class));
    }

    public void changeToNormalListViewDemo(View v) {
        startActivity(new Intent(this, NormalListViewDemoActivity.class));
    }

    public void changeToNormalRecyclerViewDemo(View v) {
        startActivity(new Intent(this, NormalRecyclerViewDemoActivity.class));
    }

    public void changeToStaggeredGridRecyclerViewDemo(View v) {
        startActivity(new Intent(this, StaggeredGridRecyclerViewDemoActivity.class));
    }

    public void changeToSwipeListViewDemo(View v) {
        startActivity(new Intent(this, SwipeListViewDemoActivity.class));
    }

    public void changeToSwipeRecyclerViewDemo(View v) {
        startActivity(new Intent(this, SwipeRecyclerViewDemoActivity.class));
    }

    public void changeToScrollViewDemo(View v) {
        startActivity(new Intent(this, ScrollViewDemoActivity.class));
    }

    public void changeToNormalViewDemo(View v) {
        startActivity(new Intent(this, NormalViewDemoActivity.class));
    }

}