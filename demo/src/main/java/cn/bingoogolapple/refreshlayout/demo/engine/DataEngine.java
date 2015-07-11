package cn.bingoogolapple.refreshlayout.demo.engine;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.model.BannerModel;
import cn.bingoogolapple.refreshlayout.demo.model.RefreshModel;
import cn.bingoogolapple.refreshlayout.demo.model.StaggeredModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/26 上午1:03
 * 描述:
 */
public class DataEngine {
    private static AsyncHttpClient sAsyncHttpClient = new AsyncHttpClient();

    public static void loadInitDatas(final RefreshModelResponseHandler responseHandler) {
        sAsyncHttpClient.get("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/api/defaultdata.json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                responseHandler.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                List<RefreshModel> refreshModels = new GsonBuilder().create().fromJson(responseString, new TypeToken<ArrayList<RefreshModel>>() {
                }.getType());
                responseHandler.onSuccess(refreshModels);
            }
        });
    }

    public static void loadNewData(final int pageNumber, final RefreshModelResponseHandler responseHandler) {
        // 测试数据放到七牛云存储的，再加上WiFi环境，加载数据非常快，看不出效果，这里延时2秒再请求的网络数据
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                sAsyncHttpClient.get("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/api/newdata" + pageNumber + ".json", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        responseHandler.onFailure();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        List<RefreshModel> refreshModels = new GsonBuilder().create().fromJson(responseString, new TypeToken<ArrayList<RefreshModel>>() {
                        }.getType());
                        responseHandler.onSuccess(refreshModels);
                    }
                });
            }
        }, 2000);
    }

    public static void loadMoreData(final int pageNumber, final RefreshModelResponseHandler responseHandler) {
        // 测试数据放到七牛云存储的，再加上WiFi环境，加载数据非常快，看不出效果，这里延时1秒再请求的网络数据
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                sAsyncHttpClient.get("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/api/moredata" + pageNumber + ".json", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        responseHandler.onFailure();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        List<RefreshModel> refreshModels = new GsonBuilder().create().fromJson(responseString, new TypeToken<ArrayList<RefreshModel>>() {
                        }.getType());
                        responseHandler.onSuccess(refreshModels);
                    }
                });
            }
        }, 1000);
    }

    public static void loadDefaultStaggeredData(final StaggeredModelResponseHandler responseHandler) {
        sAsyncHttpClient.get("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/api/staggered_default.json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                responseHandler.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                List<StaggeredModel> refreshModels = new GsonBuilder().create().fromJson(responseString, new TypeToken<ArrayList<StaggeredModel>>() {
                }.getType());
                responseHandler.onSuccess(refreshModels);
            }
        });
    }

    public static void loadNewStaggeredData(final int pageNumber, final StaggeredModelResponseHandler responseHandler) {
        // 测试数据放到七牛云存储的，再加上WiFi环境，加载数据非常快，看不出效果，这里延时2秒再请求的网络数据
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                sAsyncHttpClient.get("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/api/staggered_new" + pageNumber + ".json", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        responseHandler.onFailure();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        List<StaggeredModel> refreshModels = new GsonBuilder().create().fromJson(responseString, new TypeToken<ArrayList<StaggeredModel>>() {
                        }.getType());
                        responseHandler.onSuccess(refreshModels);
                    }
                });
            }
        }, 2000);
    }

    public static void loadMoreStaggeredData(final int pageNumber, final StaggeredModelResponseHandler responseHandler) {
        // 测试数据放到七牛云存储的，再加上WiFi环境，加载数据非常快，看不出效果，这里延时1秒再请求的网络数据
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                sAsyncHttpClient.get("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/api/staggered_more" + pageNumber + ".json", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        responseHandler.onFailure();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        List<StaggeredModel> refreshModels = new GsonBuilder().create().fromJson(responseString, new TypeToken<ArrayList<StaggeredModel>>() {
                        }.getType());
                        responseHandler.onSuccess(refreshModels);
                    }
                });
            }
        }, 1000);
    }

    public static View getCustomHeaderView(Context context) {
        View headerView = View.inflate(context, R.layout.view_custom_header, null);
        final BGABanner banner = (BGABanner) headerView.findViewById(R.id.banner);
        final List<View> views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            views.add(View.inflate(context, R.layout.view_image, null));
        }
        banner.setViews(views);
        sAsyncHttpClient.get("http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/5item.json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                BannerModel bannerModel = new Gson().fromJson(responseString, BannerModel.class);
                ImageLoader imageLoader = ImageLoader.getInstance();
                for (int i = 0; i < views.size(); i++) {
                    imageLoader.displayImage(bannerModel.imgs.get(i), (ImageView) views.get(i));
                }
                banner.setTips(bannerModel.tips);
            }
        });
        return headerView;
    }

    public interface RefreshModelResponseHandler {
        void onFailure();

        void onSuccess(List<RefreshModel> refreshModels);
    }

    public interface StaggeredModelResponseHandler {
        void onFailure();

        void onSuccess(List<StaggeredModel> staggeredModels);
    }
}