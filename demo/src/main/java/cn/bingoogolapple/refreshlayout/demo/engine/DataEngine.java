package cn.bingoogolapple.refreshlayout.demo.engine;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.model.BannerModel;
import cn.bingoogolapple.refreshlayout.demo.model.RefreshModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/26 上午1:03
 * 描述:
 */
public class DataEngine {

    public static List<RefreshModel> loadInitDatas() {
        List<RefreshModel> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new RefreshModel("title" + i, "detail" + i));
        }
        return datas;
    }

    public static List<RefreshModel> loadNewData() {
        List<RefreshModel> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            datas.add(new RefreshModel("newTitle" + i, "newDetail" + i));
        }
        return datas;
    }

    public static List<RefreshModel> loadMoreData() {
        List<RefreshModel> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            datas.add(new RefreshModel("moreTitle" + i, "moreDetail" + i));
        }
        return datas;
    }

    public static View getCustomHeaderOrFooterView(Context context) {
        View headerView = View.inflate(context, R.layout.view_custom_header, null);
        final BGABanner banner = (BGABanner) headerView.findViewById(R.id.banner);
        final List<View> views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            views.add(View.inflate(context, R.layout.view_image, null));
        }
        banner.setViews(views);
        new AsyncHttpClient().get("https://raw.githubusercontent.com/bingoogolapple/BGABanner-Android/server/api/5item.json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                BannerModel bannerModel = new Gson().fromJson(responseString, BannerModel.class);
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < views.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) views.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                banner.setTips(bannerModel.tips);
            }
        });
        return headerView;
    }

}