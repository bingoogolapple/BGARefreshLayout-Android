package cn.bingoogolapple.refreshlayout.demo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/21 下午10:13
 * 描述:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化Fresco，用于加载自定义headview中的网络图片
        Fresco.initialize(this);
    }

}