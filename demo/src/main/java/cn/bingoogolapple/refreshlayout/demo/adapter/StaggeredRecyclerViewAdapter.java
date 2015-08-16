package cn.bingoogolapple.refreshlayout.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.model.StaggeredModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class StaggeredRecyclerViewAdapter extends BGARecyclerViewAdapter<StaggeredModel> {
    private ImageLoader mImageLoader;

    public StaggeredRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_staggered);
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, StaggeredModel model) {
        viewHolderHelper.setText(R.id.tv_item_staggered_desc, model.desc);
        mImageLoader.displayImage(model.icon,  (ImageView)viewHolderHelper.getView(R.id.iv_item_staggered_icon));
    }
}