package cn.bingoogolapple.refreshlayout.demo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.adapter.NormalRecyclerViewAdapter;
import cn.bingoogolapple.refreshlayout.demo.model.RefreshModel;
import cn.bingoogolapple.refreshlayout.demo.ui.activity.MainActivity;
import cn.bingoogolapple.refreshlayout.demo.ui.activity.ViewPagerActivity;
import cn.bingoogolapple.refreshlayout.demo.util.ThreadUtil;
import cn.bingoogolapple.refreshlayout.demo.widget.Divider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/9/27 下午12:38
 * 描述:
 */
public class StickyNavRecyclerViewFragment extends BaseFragment implements BGAOnItemChildClickListener, BGAOnItemChildLongClickListener, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private RecyclerView mDataRv;
    private NormalRecyclerViewAdapter mAdapter;
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerview_sticky_nav);
        mDataRv = getViewById(R.id.data);
    }

    @Override
    protected void setListener() {
        mAdapter = new NormalRecyclerViewAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDataRv.addItemDecoration(new Divider(mApp));
//        mDataRv.setLayoutManager(new GridLayoutManager(mApp, 2, GridLayoutManager.VERTICAL, false));
        mDataRv.setLayoutManager(new LinearLayoutManager(mApp, LinearLayoutManager.VERTICAL, false));
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void onLazyLoadOnce() {
        mNewPageNumber = 0;
        mMorePageNumber = 0;
        mEngine.loadInitDatas().enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, Response<List<RefreshModel>> response) {
                mAdapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onItemChildClick(ViewGroup viewGroup, View childView, int position) {
        if (childView.getId() == R.id.tv_item_normal_delete) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup viewGroup, View childView, int position) {
        if (childView.getId() == R.id.tv_item_normal_delete) {
            showToast("长按了删除 " + mAdapter.getItem(position).title);
            return true;
        }
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View itemView, int position) {
        showToast("点击了条目 " + mAdapter.getItem(position).title);
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup viewGroup, View itemView, int position) {
        showToast("长按了条目 " + mAdapter.getItem(position).title);
        return true;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        if (mNewPageNumber > 4) {
            ((ViewPagerActivity) getActivity()).endRefreshing();
            showToast("没有最新数据了");
            return;
        }

        showLoadingDialog();
        mEngine.loadNewData(mNewPageNumber).enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, final Response<List<RefreshModel>> response) {
                // 数据放在七牛云上的比较快，这里加载完数据后模拟延时
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ViewPagerActivity) getActivity()).endRefreshing();
                        dismissLoadingDialog();
                        mAdapter.addNewData(response.body());
                        mDataRv.smoothScrollToPosition(0);
                    }
                }, MainActivity.LOADING_DURATION);
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
                ((ViewPagerActivity) getActivity()).endRefreshing();
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        if (mMorePageNumber > 4) {
            ((ViewPagerActivity) getActivity()).endLoadingMore();
            showToast("没有更多数据了");
            return false;
        }

        showLoadingDialog();
        mEngine.loadMoreData(mMorePageNumber).enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, final Response<List<RefreshModel>> response) {
                // 数据放在七牛云上的比较快，这里加载完数据后模拟延时
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ViewPagerActivity) getActivity()).endLoadingMore();
                        dismissLoadingDialog();
                        mAdapter.addMoreData(response.body());
                    }
                }, MainActivity.LOADING_DURATION);
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
                ((ViewPagerActivity) getActivity()).endLoadingMore();
                dismissLoadingDialog();
            }
        });

        return true;
    }
}