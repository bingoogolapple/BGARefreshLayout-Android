package cn.bingoogolapple.refreshlayout.demo.adapter;

import android.content.Context;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.model.RefreshModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class StaggeredGridRecyclerViewAdapter extends BGARecyclerViewAdapter<RefreshModel> {
    public StaggeredGridRecyclerViewAdapter(Context context) {
        super(context, R.layout.item_staggered);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.tv_item_staggered_delete);
        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_staggered_delete);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, RefreshModel model) {
        viewHolderHelper.setText(R.id.tv_item_staggered_title, model.title).setText(R.id.tv_item_staggered_detail, model.detail);

        int test = position % 5;
        if (test == 0) {
            viewHolderHelper.getConvertView().getLayoutParams().height = 650;
        } else if (test == 1) {
            viewHolderHelper.getConvertView().getLayoutParams().height = 200;
        } else if (test == 2) {
            viewHolderHelper.getConvertView().getLayoutParams().height = 350;
        } else if (test == 3) {
            viewHolderHelper.getConvertView().getLayoutParams().height = 280;
        } else {
            viewHolderHelper.getConvertView().getLayoutParams().height = 300;
        }
    }
}