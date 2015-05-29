:running:BGARefreshLayout-Android v1.0.0:running:
============
多种下拉刷新效果、上拉加载更多、可配置自定义头部广告位

#### 目前已经实现了三种下拉刷新效果:

* 新浪微博下拉刷新风格（可设置背景、各种状态是的文本）
* 慕课网下拉刷新风格（使用时可设置其中的logo和颜色成自己公司的风格）
* 类似qq好友列表黏性下拉刷新风格（三阶贝塞尔曲线没怎么调好，刚开始下拉时效果不太好）

#### 一种上拉加载更多效果

* 新浪微博上拉加载更多（可设置背景、状态文本）

`大家也可以继承BGARefreshViewHolder这个抽象类，实现相应地抽象方法做出格式各样的下拉刷新效果
【例如实现handleScale(float scale, int moveYDistance)方法，根据scale实现各种下拉刷新动画】和上拉加载更多
特效，可参考BGAMoocStyleRefreshViewHolder、BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder的实现方式。`

#### 目前存在的问题

* 当配置自定义头部广告位可滚动时，内容区域和广告位还不能平滑过度。
* 当RecyclerView的LayoutManager为StaggeredGridLayoutManager时，不知道怎样判断是否滚动到了顶部（处理下拉刷新）或者底部（处理上拉加载更多）。

#### 效果图
![Image of GridView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/1.gif)
![Image of 普通ListView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/2.gif)
![Image of 普通RecyclerView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/3.gif)
![Image of 滑动ListView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/4.gif)
![Image of 滑动RecyclerView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/5.gif)
![Image of ScrollView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/6.gif)
![Image of NormalView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/7.gif)

#### 用法
>添加以下依赖（提示：请依赖新版recyclerview。新版RecyclerView的addOnScrollListener比旧版的setOnScrollListener的扩展性更强。可以让最终的使用者更具项目需求再次添加自定义滚动监听器。这一点比ListView的设计好很多，在处理ListView时还得用反射获取最终使用者设置的自定义滚动监听器。）

```groovy
dependencies {
    compile 'com.android.support:recyclerview-v7:22.1.1'
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'cn.bingoogolapple:bga-refreshlayout:1.0.0@aar'
}
```

>编写布局文件

```xml
<cn.bingoogolapple.refreshlayout.BGARefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rl_recyclerview_refresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- 内容控件的高度请使用android:layout_height="0dp"和android:layout_weight="1" -->
    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_recyclerview_data"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:scrollbars="none" />
</cn.bingoogolapple.refreshlayout.BGARefreshLayout>
```

>在Java代码配置BGARefreshLayout

```Java
// 实现让activity或者fragment实现BGARefreshLayoutDelegate接口
public class NormalRecyclerViewDemoActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate
.
.
.
// 为BGARefreshLayout设置代理
mRefreshLayout.setDelegate(this);
// 设置下拉刷新和上拉加载更多的风格（这里用时的新浪微博刷新风格，也可配置慕课网下拉刷新风格和QQ好友列表黏性下拉刷新风格）
mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
.
.
.
// 实现下拉刷新回调方法
@Override
public void onBGARefreshLayoutBeginRefreshing() {
    // 在这里加载最新数据

    // 加载完毕后结束下拉刷新
    mRefreshLayout.endRefreshing();
}

// 实现上拉加载更多回调方法
@Override
public void onBGARefreshLayoutBeginLoadingMore() {
    // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以

    // 加载完毕后结束下拉刷新
    mRefreshLayout.endRefreshing();
}
```

### 更多详细用法请查看[Wiki](https://github.com/bingoogolapple/BGARefreshLayout-Android/wiki)或者[Demo](https://github.com/bingoogolapple/BGARefreshLayout-Android/tree/master/demo)

#### Demo中使用到了我的另外三个库

* [Splash界面滑动导航+自动轮播效果](https://github.com/bingoogolapple/BGABanner)
* [在AdapterView和RecyclerView中通用的Adapter和ViewHolder](https://github.com/bingoogolapple/BGAAdapter-Android)
* [带弹簧效果的左右滑动控件](https://github.com/bingoogolapple/BGASwipeItemLayout-Android)