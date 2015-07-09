package cn.bingoogolapple.refreshlayout.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.adapter.StaggeredGridRecyclerViewAdapter;
import cn.bingoogolapple.refreshlayout.demo.engine.DataEngine;
import cn.bingoogolapple.refreshlayout.demo.model.RefreshModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class StaggeredGridRecyclerViewDemoActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {
    private static final String TAG = StaggeredGridRecyclerViewDemoActivity.class.getSimpleName();
    private StaggeredGridRecyclerViewAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private List<RefreshModel> mDatas;
    private RecyclerView mDataRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initRefreshLayout();
        initRecyclerView();
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setCustomHeaderView(DataEngine.getCustomHeaderView(this), true);
        BGAStickinessRefreshViewHolder stickinessRefreshViewHolder = new BGAStickinessRefreshViewHolder(this, true);
        stickinessRefreshViewHolder.setStickinessColor(Color.parseColor("#11cd6e"));
        stickinessRefreshViewHolder.setRotateDrawable(getResources().getDrawable(R.mipmap.custom_stickiness_roate));
        mRefreshLayout.setRefreshViewHolder(stickinessRefreshViewHolder);
    }

    private void initRecyclerView() {
        mDataRv = (RecyclerView) findViewById(R.id.rv_recyclerview_data);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mAdapter = new StaggeredGridRecyclerViewAdapter(this);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);

        mDatas = DataEngine.loadInitDatas();
        mAdapter.setDatas(mDatas);
        mDataRv.setAdapter(mAdapter);

        // 使用addOnScrollListener，而不是setOnScrollListener();
//        mDataRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                Log.i(TAG, "测试自定义onScrollStateChanged被调用");
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                Log.i(TAG, "测试自定义onScrolled被调用");
//            }
//        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        DataEngine.loadNewData(new DataEngine.RefreshModelResponseHandler() {
            @Override
            public void onFailure() {
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onSuccess(List<RefreshModel> refreshModels) {
                mRefreshLayout.endRefreshing();
                mDatas.addAll(0, refreshModels);
                mAdapter.setDatas(mDatas);
            }
        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        DataEngine.loadMoreData(new DataEngine.RefreshModelResponseHandler() {
            @Override
            public void onFailure() {
                mRefreshLayout.endLoadingMore();
            }

            @Override
            public void onSuccess(List<RefreshModel> refreshModels) {
                mRefreshLayout.endLoadingMore();
                mAdapter.addDatas(refreshModels);
            }
        });
        return true;
    }

    @Override
    public void onRVItemClick(View v, int position) {
        Toast.makeText(this, "点击了条目 " + mAdapter.getItem(position).title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onRVItemLongClick(View v, int position) {
        Toast.makeText(this, "长按了条目 " + mAdapter.getItem(position).title, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onItemChildClick(View v, int position) {
        if (v.getId() == R.id.tv_item_staggered_delete) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(View v, int position) {
        if (v.getId() == R.id.tv_item_staggered_delete) {
            Toast.makeText(this, "长按了删除 " + mAdapter.getItem(position).title, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}