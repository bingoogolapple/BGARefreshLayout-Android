package cn.bingoogolapple.refreshlayout.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.adapter.SwipeAdapterViewAdapter;
import cn.bingoogolapple.refreshlayout.demo.engine.DataEngine;
import cn.bingoogolapple.refreshlayout.demo.model.RefreshModel;
import cn.bingoogolapple.refreshlayout.demo.ui.activity.MainActivity;
import cn.bingoogolapple.refreshlayout.demo.util.ThreadUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RefreshSwipeListViewFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {
    private static final String TAG = RefreshSwipeListViewFragment.class.getSimpleName();
    private BGARefreshLayout mRefreshLayout;
    private ListView mDataLv;
    private SwipeAdapterViewAdapter mAdapter;
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_listview_refresh);
        mRefreshLayout = getViewById(R.id.rl_listview_refresh);
        mDataLv = getViewById(R.id.lv_listview_data);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);

        mDataLv.setOnItemClickListener(this);
        mDataLv.setOnItemLongClickListener(this);
        mDataLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        mAdapter = new SwipeAdapterViewAdapter(mApp);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(mApp, true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);

        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
        mRefreshLayout.setCustomHeaderView(DataEngine.getCustomHeaderView(mApp), true);

        mDataLv.setAdapter(mAdapter);
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
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        if (mNewPageNumber > 4) {
            mRefreshLayout.endRefreshing();
            showToast("没有最新数据了");
            return;
        }
        mEngine.loadNewData(mNewPageNumber).enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, final Response<List<RefreshModel>> response) {
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.endRefreshing();
                        mAdapter.addNewData(response.body());
                    }
                }, MainActivity.LOADING_DURATION);
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
                mRefreshLayout.endRefreshing();
            }
        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        if (mMorePageNumber > 4) {
            mRefreshLayout.endLoadingMore();
            showToast("没有更多数据了");
            return false;
        }
        mEngine.loadMoreData(mMorePageNumber).enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, final Response<List<RefreshModel>> response) {
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.endLoadingMore();
                        mAdapter.addMoreData(response.body());
                    }
                }, MainActivity.LOADING_DURATION);
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
                mRefreshLayout.endLoadingMore();
            }
        });
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showToast("点击了条目 " + mAdapter.getItem(position).title);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showToast("长按了" + mAdapter.getItem(position).title);
        return true;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_swipe_delete) {
            // 作为ListView的item使用时，如果删除了某一个item，请先关闭已经打开的item，否则其他item会显示不正常（RecyclerView不会有这个问题）
            mAdapter.closeOpenedSwipeItemLayout();
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_swipe_delete) {
            showToast("长按了删除 " + mAdapter.getItem(position).title);
            return true;
        }
        return false;
    }
}