:running:BGARefreshLayout-Android:running:
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BGARefreshLayout-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1909)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-refreshlayout/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-refreshlayout)

开发者使用 BGARefreshLayout-Android 可以对各种控件实现多种下拉刷新效果、上拉加载更多以及配置自定义头部广告位

[测试 BGARefreshLayout 与 Activity、Fragment、ViewPager 的各种嵌套的 Demo](https://github.com/bingoogolapple/BGARefreshLayoutDemo)

## 常见问题-加载更多视图无法显示
> 1.BGARefreshLayout 的直接子控件的高度请使用 android:layout_height="0dp" 和 android:layout_weight="1"

```xml
<cn.bingoogolapple.refreshlayout.BGARefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rl_modulename_refresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- BGARefreshLayout 的直接子控件 -->
    <AnyView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />
</cn.bingoogolapple.refreshlayout.BGARefreshLayout>
```
> 2.如果是在 Fragment 中使用 BGARefreshLayout

```
请在 onCreateView 方法中初始化 BGARefreshLayout，不要在 onActivityCreated 方法中初始化
```

### 目前已经实现了四种下拉刷新效果:

* 新浪微博下拉刷新风格（可设置各种状态是的文本，可设置整个刷新头部的背景）
* 慕课网下拉刷新风格（可设置其中的 logo 和颜色成自己公司的风格，可设置整个刷新头部的背景）
* 美团下拉刷新风格（可设置其中的图片和动画成自己公司的风格，可设置整个刷新头部的背景）
* 类似 qq 好友列表黏性下拉刷新风格（三阶贝塞尔曲线没怎么调好，刚开始下拉时效果不太好，可设置整个刷新头部的背景）

### 一种上拉加载更多效果

* 新浪微博上拉加载更多（可设置背景、状态文本）

***开发者也可以继承 BGARefreshViewHolder 这个抽象类，实现相应地抽象方法做出格式各样的下拉刷新效果【例如实现 handleScale(float scale, int moveYDistance) 方法，根据 scale 实现各种下拉刷新动画】和上拉加载更多特效，可参考 BGAMoocStyleRefreshViewHolder、BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder、BGAMeiTuanRefreshViewHolder 的实现方式。***

### 目前存在的问题

* 当配置自定义头部广告位可滚动时，内容区域和广告位还不能平滑过度。
* 当 BGAStickyNavLayout 中嵌套 RecyclerView 或 AbsListView，并且第一页的最后一个 item 刚好在最底部时，加载更多视图会悬浮在最后一个 item 上面
* 正在刷新或加载更多时，用户上下滑动不会让下拉刷新视图和加载更多视图跟着滑动

### 效果图
![bga_refreshlayout1](https://cloud.githubusercontent.com/assets/8949716/17475813/6f7e338c-5d8f-11e6-846f-889414742b78.gif)
![bga_refreshlayout2](https://cloud.githubusercontent.com/assets/8949716/17475812/6f79b0aa-5d8f-11e6-8934-ab5f61bff177.gif)
![bga_refreshlayout3](https://cloud.githubusercontent.com/assets/8949716/17475814/6f900742-5d8f-11e6-832e-eaded3640154.gif)
![bga_refreshlayout4](https://cloud.githubusercontent.com/assets/8949716/17475815/6fb4a0c0-5d8f-11e6-94c9-5eaed30c5060.gif)
![bga_refreshlayout5](https://cloud.githubusercontent.com/assets/8949716/17475816/6fbb501e-5d8f-11e6-9dc5-fcb5b497247e.gif)
![bga_refreshlayout6](https://cloud.githubusercontent.com/assets/8949716/17475817/6fe51674-5d8f-11e6-994b-b8248f164181.gif)

### 基本使用

#### 1.添加 Gradle 依赖

> 没有支持 Eclipse，建议还在用 Eclipse 的小伙伴都开始转 Android Studio 吧

**latestVersion 是指对应库的最新版本号,别再问我为什么找不到 xxxxxxxlatestVersion 了!**

```groovy
dependencies {
    compile 'com.android.support:recyclerview-v7:latestVersion'
    compile 'com.android.support:appcompat-v7:latestVersion'
    compile 'cn.bingoogolapple:bga-refreshlayout:latestVersion@aar'
}
```

#### 2.在布局文件中添加 BGARefreshLayout

**注意：内容控件的高度请使用 android:layout_height="0dp" 和 android:layout_weight="1"**

```xml
<cn.bingoogolapple.refreshlayout.BGARefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rl_modulename_refresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- 内容控件 -->
    <AnyView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />
</cn.bingoogolapple.refreshlayout.BGARefreshLayout>
```

#### 3.在 Activity 或者 Fragment 中配置 BGARefreshLayout

```java
// 让 activity 或者 fragment 实现 BGARefreshLayoutDelegate 接口
public class ModuleNameActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moudlename);

        initRefreshLayout();
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new XXXImplRefreshViewHolder(this, true))
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);


        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        // mRefreshLayout.setIsShowLoadingMoreView(false);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText(loadingMoreText);
        // 设置整个加载更多控件的背景颜色资源 id
        refreshViewHolder.setLoadMoreBackgroundColorRes(loadMoreBackgroundColorRes);
        // 设置整个加载更多控件的背景 drawable 资源 id
        refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
        // 设置下拉刷新控件的背景颜色资源 id
        refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
        // 设置下拉刷新控件的背景 drawable 资源 id
        refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        mRefreshLayout.setCustomHeaderView(mBanner, false);
        // 可选配置  -------------END
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        // 在这里加载最新数据

        if (mIsNetworkEnabled) {
            // 如果网络可用，则加载网络数据
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(MainActivity.LOADING_DURATION);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    // 加载完毕后在 UI 线程结束下拉刷新
                    mRefreshLayout.endRefreshing();
                    mDatas.addAll(0, DataEngine.loadNewData());
                    mAdapter.setDatas(mDatas);
                }
            }.execute();
        } else {
            // 网络不可用，结束下拉刷新
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            mRefreshLayout.endRefreshing();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以

        if (mIsNetworkEnabled) {
            // 如果网络可用，则异步加载网络数据，并返回 true，显示正在加载更多
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(MainActivity.LOADING_DURATION);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    // 加载完毕后在 UI 线程结束加载更多
                    mRefreshLayout.endLoadingMore();
                    mAdapter.addDatas(DataEngine.loadMoreData());
                }
            }.execute();

            return true;
        } else {
            // 网络不可用，返回 false，不显示正在加载更多
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在 activity 的 onStart 方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }

}
```

### 更多详细用法请查看 [Wiki](https://github.com/bingoogolapple/BGARefreshLayout-Android/wiki) 或者 [Demo](https://github.com/bingoogolapple/BGARefreshLayout-Android/tree/master/demo)

### Demo 中使用到了我的另外三个库，欢迎大家 Star :smile:

* [Splash 界面滑动导航+自动轮播效果](https://github.com/bingoogolapple/BGABanner-Android)
* [在 AdapterView 和 RecyclerView 中通用的 Adapter 和 ViewHolder。RecyclerView 支持 DataBinding 、多种 Item 类型、添加 Header 和 Footer](https://github.com/bingoogolapple/BGAAdapter-Android)
* [类似新浪微博消息列表，带弹簧效果的左右滑动控件](https://github.com/bingoogolapple/BGASwipeItemLayout-Android)

## 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

## 打赏支持

如果觉得 BGA 系列开源库对您有用，请随意打赏。如果猿友有打算购买 [Lantern](https://github.com/getlantern/forum)，可以使用我的邀请码「YFQ9Q3B」购买，双方都赠送三个月的专业版使用时间。

<p align="center">
  <img src="http://7xk9dj.com1.z0.glb.clouddn.com/bga_pay.png" width="450">
</p>

## License

    Copyright 2015 bingoogolapple

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
