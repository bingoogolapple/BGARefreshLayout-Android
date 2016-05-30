package cn.bingoogolapple.refreshlayout.demo.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickyNavLayout;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.adapter.StickWithChangeAdapter;

/**
 * in BGARefreshLayout-Android.
 * by:chinaume@163.com
 * Created by moo on 16/4/16 12:48.
 */
public class StickWithChangeActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    RecyclerView recy;
    BGARefreshLayout refreshLayout;
    BGAStickyNavLayout stickyNavLayout;
    AppBarLayout appBarLayout;
    View lines;
    TextView textView;
    Toolbar toolbar;

    private List demoData = new ArrayList();
    private StickWithChangeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_with_change_layout);
        initBar();
        initLayout();
    }

    private void initBar() {
        appBarLayout = (AppBarLayout) findViewById(R.id.widget_app_barLayout);
        lines = findViewById(R.id.widget_lines);
        toolbar = (Toolbar) findViewById(R.id.widget_toolBar);
        toolbar.setBackgroundColor(0x00000000);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        beReset();
        textView = (TextView) findViewById(R.id.layout_title);


        showLines(false);

    }

    private void showLines(boolean b) {
        if (Build.VERSION.SDK_INT < 21 && b) {
            lines.setVisibility(View.VISIBLE);
        } else {
            lines.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public float getToolBarSize() {
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimension(tv.data, getResources().getDisplayMetrics());
        }
        return 0;
    }

    private void initLayout() {
        stickyNavLayout = (BGAStickyNavLayout) findViewById(R.id.widget_stickLayout);
        stickyNavLayout.setMarginTopToShow(
                (int) (getToolBarSize()
                        + TypedValue.complexToDimension(
                        lines.getMeasuredHeight(),
                        getResources().getDisplayMetrics())));

        stickyNavLayout.setScrollListener(new BGAStickyNavLayout.OnBGANavScrollListener() {
            @Override
            public void onScroll(int x, int y) {
                int scrollY = Math.min(stickyNavLayout.getHeaderViewHeight(), Math.max(y, 0));
                if (scrollY >= (stickyNavLayout.getHeaderViewHeight() / 2)) {
                    beChange();
                } else {
                    beReset();
                }
            }
        });

        refreshLayout = (BGARefreshLayout) findViewById(R.id.widget_refrelayout);
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, false));

        recy = (RecyclerView) findViewById(R.id.widget_demo_recyclerview);
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setItemAnimator(new DefaultItemAnimator());
        adapter = new StickWithChangeAdapter();
        recy.setAdapter(adapter);
        recy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int last = manager.findLastCompletelyVisibleItemPosition();
                if (last >= manager.getItemCount() - 1) {
                    loadMore();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void beReset() {
        appBarLayout.setBackgroundColor(0x00000000);
        showLines(false);
        setTitle("");
    }

    private void beChange() {
        appBarLayout.setBackgroundColor(0xFF0000FF);
        showLines(true);
        setTitle("demo");
    }

    private void loadMore() {
        loadData();
    }


    @Override
    public void setTitle(CharSequence title) {
        if (textView != null) {
            textView.setText(title);
        } else {
            super.setTitle(title);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        refreshLayout.beginRefreshing();
    }

    private synchronized void RefreshRecyView() {
        adapter.setmData(demoData);
        if (refreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
            refreshLayout.postDelayed(run, 1000l);
        }
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            //FixMe 防止Ondestory 组件被回收
            if (refreshLayout != null) {
                refreshLayout.endRefreshing();
            }
        }
    };

    private void loadData() {
        for (int i = 0; i < 20; i++) {
            demoData.add(i + "");
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        demoData.clear();
        loadData();
        RefreshRecyView();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
