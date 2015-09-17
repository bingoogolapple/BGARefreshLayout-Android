package cn.bingoogolapple.refreshlayout.demo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.adapter.NormalRecyclerViewAdapter;
import cn.bingoogolapple.refreshlayout.demo.model.RefreshModel;
import cn.bingoogolapple.refreshlayout.demo.util.ToastUtil;
import cn.bingoogolapple.refreshlayout.demo.widget.Divider;
import retrofit.Callback;
import retrofit.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class NormalRecyclerViewFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {
    private static final String TAG = NormalRecyclerViewFragment.class.getSimpleName();
    private NormalRecyclerViewAdapter mAdapter;
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

        mAdapter = new NormalRecyclerViewAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);

        // 使用addOnScrollListener，而不是setOnScrollListener();
        mDataRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.i(TAG, "测试自定义onScrollStateChanged被调用");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.i(TAG, "测试自定义onScrolled被调用");
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
//        mRefreshLayout.setCustomHeaderView(DataEngine.getCustomHeaderView(mApp), true);

        View headerView = View.inflate(mApp, R.layout.view_custom_header2, null);

        // 测试自定义header中控件的点击事件
        headerView.findViewById(R.id.btn_custom_header2_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("点击了测试按钮");
            }
        });
        // 模拟网络数据加载，测试动态改变自定义header的高度
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView) getViewById(R.id.tv_custom_header2_title)).setText(R.string.test_custom_header_title);
                ((TextView) getViewById(R.id.tv_custom_header2_desc)).setText(R.string.test_custom_header_desc);
            }
        }, 2000);
        mRefreshLayout.setCustomHeaderView(headerView, true);

//        BGAStickinessRefreshViewHolder stickinessRefreshViewHolder = new BGAStickinessRefreshViewHolder(mApp, true);
//        stickinessRefreshViewHolder.setStickinessColor(Color.parseColor("#11cd6e"));
//        stickinessRefreshViewHolder.setRotateDrawable(getResources().getDrawable(R.mipmap.custom_stickiness_roate));
//        mRefreshLayout.setRefreshViewHolder(stickinessRefreshViewHolder);

        mRefreshLayout.setRefreshViewHolder(new BGAMoocStyleRefreshViewHolder(mApp, true));

        mDataRv.addItemDecoration(new Divider(mApp));

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mApp, 2);
//        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
//        mDataRv.setLayoutManager(gridLayoutManager);

        mDataRv.setLayoutManager(new LinearLayoutManager(mApp, LinearLayoutManager.VERTICAL, false));

        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void onUserVisible() {
        mNewPageNumber = 0;
        mMorePageNumber = 0;
        mEngine.loadInitDatas().enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Response<List<RefreshModel>> response) {
                mAdapter.setDatas(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
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

        mLoadingDialog.show();
        mEngine.loadNewData(mNewPageNumber).enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Response<List<RefreshModel>> response) {
                mRefreshLayout.endRefreshing();
                mLoadingDialog.dismiss();
                mAdapter.addNewDatas(response.body());
                mDataRv.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Throwable t) {
                mRefreshLayout.endRefreshing();
                mLoadingDialog.dismiss();
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

        mLoadingDialog.show();
        mEngine.loadMoreData(mMorePageNumber).enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Response<List<RefreshModel>> response) {
                mRefreshLayout.endLoadingMore();
                mLoadingDialog.dismiss();
                mAdapter.addMoreDatas(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                mRefreshLayout.endLoadingMore();
                mLoadingDialog.dismiss();
            }
        });

        return true;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_normal_delete) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_normal_delete) {
            showToast("长按了删除 " + mAdapter.getItem(position).title);
            return true;
        }
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        showToast("点击了条目 " + mAdapter.getItem(position).title);
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
        showToast("长按了条目 " + mAdapter.getItem(position).title);
        return true;
    }
}