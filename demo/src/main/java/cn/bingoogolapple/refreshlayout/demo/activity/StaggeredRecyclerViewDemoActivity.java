package cn.bingoogolapple.refreshlayout.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.adapter.StaggeredRecyclerViewAdapter;
import cn.bingoogolapple.refreshlayout.demo.engine.DataEngine;
import cn.bingoogolapple.refreshlayout.demo.model.StaggeredModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class StaggeredRecyclerViewDemoActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener {
    private static final String TAG = StaggeredRecyclerViewDemoActivity.class.getSimpleName();
    private StaggeredRecyclerViewAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private List<StaggeredModel> mDatas;
    private RecyclerView mDataRv;
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;

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
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(this, true);
        mRefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);
    }

    private void initRecyclerView() {
        mDataRv = (RecyclerView) findViewById(R.id.rv_recyclerview_data);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mAdapter = new StaggeredRecyclerViewAdapter(this);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);

        loadInitData();
    }

    private void loadInitData() {
        DataEngine.loadDefaultStaggeredData(new DataEngine.StaggeredModelResponseHandler() {
            @Override
            public void onFailure() {
            }

            @Override
            public void onSuccess(List<StaggeredModel> staggeredModels) {
                mDatas = staggeredModels;
                mAdapter.setDatas(mDatas);
                mDataRv.setAdapter(mAdapter);
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        if (mNewPageNumber > 4) {
            mRefreshLayout.endRefreshing();
            Toast.makeText(this, "没有最新数据了", Toast.LENGTH_SHORT).show();
            return;
        }
        DataEngine.loadNewStaggeredData(mNewPageNumber, new DataEngine.StaggeredModelResponseHandler() {
            @Override
            public void onFailure() {
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onSuccess(List<StaggeredModel> staggeredModels) {
                mRefreshLayout.endRefreshing();
                mDatas.addAll(0, staggeredModels);
                mAdapter.setDatas(mDatas);
            }
        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        if (mMorePageNumber > 5) {
            mRefreshLayout.endLoadingMore();
            Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            return false;
        }
        DataEngine.loadMoreStaggeredData(mMorePageNumber, new DataEngine.StaggeredModelResponseHandler() {
            @Override
            public void onFailure() {
                mRefreshLayout.endLoadingMore();
            }

            @Override
            public void onSuccess(List<StaggeredModel> staggeredModels) {
                mRefreshLayout.endLoadingMore();
                mAdapter.addDatas(staggeredModels);
            }
        });
        return true;
    }

    @Override
    public void onRVItemClick(View v, int position) {
        Toast.makeText(this, "点击了条目 " + mAdapter.getItem(position).desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onRVItemLongClick(View v, int position) {
        Toast.makeText(this, "长按了条目 " + mAdapter.getItem(position).desc, Toast.LENGTH_SHORT).show();
        return true;
    }

}