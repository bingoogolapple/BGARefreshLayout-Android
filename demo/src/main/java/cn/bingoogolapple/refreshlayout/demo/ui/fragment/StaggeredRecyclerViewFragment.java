package cn.bingoogolapple.refreshlayout.demo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
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
public class StaggeredRecyclerViewFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener {
    private static final String TAG = StaggeredRecyclerViewFragment.class.getSimpleName();
    private StaggeredRecyclerViewAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView mDataRv;
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerview);
        mRefreshLayout = getViewById(R.id.rl_recyclerview_refresh);
        mDataRv = getViewById(R.id.rv_recyclerview_data);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);

        mAdapter = new StaggeredRecyclerViewAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mRefreshLayout.setCustomHeaderView(DataEngine.getCustomHeaderView(mApp), true);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mApp, true));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void onUserVisible() {
        mNewPageNumber = 0;
        mMorePageNumber = 0;
        DataEngine.loadDefaultStaggeredData(new DataEngine.StaggeredModelResponseHandler() {
            @Override
            public void onFailure() {
            }

            @Override
            public void onSuccess(List<StaggeredModel> staggeredModels) {
                mAdapter.setDatas(staggeredModels);
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        if (mNewPageNumber > 4) {
            mRefreshLayout.endRefreshing();
            showToast("没有最新数据了");
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
                mAdapter.addNewDatas(staggeredModels);
                mDataRv.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        if (mMorePageNumber > 5) {
            mRefreshLayout.endLoadingMore();
            showToast("没有更多数据了");
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
                mAdapter.addMoreDatas(staggeredModels);
            }
        });
        return true;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        showToast("点击了条目 " + mAdapter.getItem(position).desc);
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
        showToast("长按了条目 " + mAdapter.getItem(position).desc);
        return true;
    }
}