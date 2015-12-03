:running:BGARefreshLayout-Android:running:
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BGARefreshLayout-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1909)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-refreshlayout/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-refreshlayout)

开发者使用BGARefreshLayout-Android可以对各种控件实现多种下拉刷新效果、上拉加载更多以及配置自定义头部广告位

[测试BGARefreshLayout与Activity、Fragment、ViewPager的各种嵌套的Demo](https://github.com/bingoogolapple/BGARefreshLayoutDemo)

### 目前已经实现了四种下拉刷新效果:

* 新浪微博下拉刷新风格（可设置各种状态是的文本，可设置整个刷新头部的背景）
* 慕课网下拉刷新风格（可设置其中的logo和颜色成自己公司的风格，可设置整个刷新头部的背景）
* 美团下拉刷新风格（可设置其中的图片和动画成自己公司的风格，可设置整个刷新头部的背景）
* 类似qq好友列表黏性下拉刷新风格（三阶贝塞尔曲线没怎么调好，刚开始下拉时效果不太好，可设置整个刷新头部的背景）

### 一种上拉加载更多效果

* 新浪微博上拉加载更多（可设置背景、状态文本）

***开发者也可以继承BGARefreshViewHolder这个抽象类，实现相应地抽象方法做出格式各样的下拉刷新效果【例如实现handleScale(float scale, int moveYDistance)方法，根据scale实现各种下拉刷新动画】和上拉加载更多特效，可参考BGAMoocStyleRefreshViewHolder、BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder、BGAMeiTuanRefreshViewHolder的实现方式。***

### 目前存在的问题

* 当配置自定义头部广告位可滚动时，内容区域和广告位还不能平滑过度。

### 效果图
![Image of StickyViewPager示例](http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/screenshots/bga_refreshlayout1.gif)
![Image of 慕课网下拉刷新示例](http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/screenshots/bga_refreshlayout2.gif)
![Image of 美团下拉刷新示例](http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/screenshots/bga_refreshlayout3.gif)
![Image of 黏性下拉刷新示例](http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/screenshots/bga_refreshlayout4.gif)
![Image of 瀑布流示例](http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/screenshots/bga_refreshlayout5.gif)
![Image of GridView示例](http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/screenshots/bga_refreshlayout6.gif)

### 基本使用

#### 1.添加Gradle依赖

> 没有支持eclipse，建议还在用eclipse的小伙伴都开始转android studio吧

**latestVersion是指对应库的最新版本号,别再问我为什么找不到xxxxxxxlatestVersion了!**

```groovy
dependencies {
    compile 'com.android.support:recyclerview-v7:latestVersion'
    // 记得添加nineoldandroids
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'cn.bingoogolapple:bga-refreshlayout:latestVersion@aar'
}
```

#### 2.在布局文件中添加BGARefreshLayout

**注意：内容控件的高度请使用android:layout_height="0dp"和android:layout_weight="1"**

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

#### 3.在Activity或者Fragment中配置BGARefreshLayout

```java
// 让activity或者fragment实现BGARefreshLayoutDelegate接口
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
        // 为BGARefreshLayout设置代理
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
        // 设置整个加载更多控件的背景颜色资源id
        refreshViewHolder.setLoadMoreBackgroundColorRes(loadMoreBackgroundColorRes);
        // 设置整个加载更多控件的背景drawable资源id
        refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
        // 设置下拉刷新控件的背景颜色资源id
        refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
        // 设置下拉刷新控件的背景drawable资源id
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
                    // 加载完毕后在UI线程结束下拉刷新
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
            // 如果网络可用，则异步加载网络数据，并返回true，显示正在加载更多
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
                    // 加载完毕后在UI线程结束加载更多
                    mRefreshLayout.endLoadingMore();
                    mAdapter.addDatas(DataEngine.loadMoreData());
                }
            }.execute();

            return true;
        } else {
            // 网络不可用，返回false，不显示正在加载更多
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }

}
```

### 更多详细用法请查看[Wiki](https://github.com/bingoogolapple/BGARefreshLayout-Android/wiki)或者[Demo](https://github.com/bingoogolapple/BGARefreshLayout-Android/tree/master/demo)

### Demo中使用到了我的另外三个库，欢迎大家Star :smile:

* [Splash界面滑动导航+自动轮播效果](https://github.com/bingoogolapple/BGABanner-Android)
* [对AdapterView和RecyclerView通用的Adapter和ViewHolder](https://github.com/bingoogolapple/BGAAdapter-Android)
* [类似新浪微博消息列表，带弹簧效果的左右滑动控件](https://github.com/bingoogolapple/BGASwipeItemLayout-Android)

### 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群 |
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

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
