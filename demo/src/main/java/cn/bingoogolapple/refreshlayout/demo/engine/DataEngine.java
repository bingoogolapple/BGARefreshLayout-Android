package cn.bingoogolapple.refreshlayout.demo.engine;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.demo.R;
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
        List<View> datas = new ArrayList<>();
        datas.add(View.inflate(context, R.layout.view_one, null));
        datas.add(View.inflate(context,R.layout.view_two, null));
        datas.add(View.inflate(context,R.layout.view_three, null));
        datas.add(View.inflate(context,R.layout.view_four, null));

        View customHeaderView = View.inflate(context, R.layout.view_custom_header, null);
        BGABanner banner = (BGABanner) customHeaderView.findViewById(R.id.banner);
        banner.setViewPagerViews(datas);

        return customHeaderView;
    }

}