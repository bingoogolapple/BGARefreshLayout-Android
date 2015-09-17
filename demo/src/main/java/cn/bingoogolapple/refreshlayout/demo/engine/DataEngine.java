package cn.bingoogolapple.refreshlayout.demo.engine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.demo.App;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.model.BannerModel;
import retrofit.Callback;
import retrofit.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/26 上午1:03
 * 描述:
 */
public class DataEngine {

    public static View getCustomHeaderView(Context context) {
        View headerView = View.inflate(context, R.layout.view_custom_header, null);
        final BGABanner banner = (BGABanner) headerView.findViewById(R.id.banner);
        final List<View> views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            views.add(View.inflate(context, R.layout.view_image, null));
        }
        banner.setViews(views);
        App.getInstance().getEngine().getBannerModel().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                ImageLoader imageLoader = ImageLoader.getInstance();
                for (int i = 0; i < views.size(); i++) {
                    imageLoader.displayImage(bannerModel.imgs.get(i), (ImageView) views.get(i));
                }
                banner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return headerView;
    }

}