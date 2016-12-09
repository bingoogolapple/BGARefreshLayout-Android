package cn.bingoogolapple.refreshlayout.demo.engine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.demo.App;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.model.BannerModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/26 上午1:03
 * 描述:
 */
public class DataEngine {

    public static View getCustomHeaderView(final Context context) {
        View headerView = View.inflate(context, R.layout.view_custom_header, null);
        final BGABanner banner = (BGABanner) headerView.findViewById(R.id.banner);
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(itemView.getContext())
                        .load(model)
                        .placeholder(R.mipmap.holder)
                        .error(R.mipmap.holder)
                        .dontAnimate()
                        .centerCrop()
                        .into(itemView);
            }
        });
        banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView imageView, String model, int position) {
                Toast.makeText(banner.getContext(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });

        App.getInstance().getEngine().getBannerModel().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                banner.setData(R.layout.view_image, bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
        return headerView;
    }

}