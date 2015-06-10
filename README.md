:running:BGARefreshLayout-Android v1.0.2:running:
============
>关于我<br/>
>微博：<a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a>&nbsp;&nbsp;&nbsp;&nbsp;主页：<a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>&nbsp;&nbsp;&nbsp;&nbsp;邮箱：<a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a>

开发者使用BGARefreshLayout-Android可以对所有控件实现多种下拉刷新效果、上拉加载更多以及配置自定义头部广告位

### 目前已经实现了三种下拉刷新效果:

* 新浪微博下拉刷新风格（可设置背景、各种状态是的文本）
* 慕课网下拉刷新风格（使用时可设置其中的logo和颜色成自己公司的风格）
* 类似qq好友列表黏性下拉刷新风格（三阶贝塞尔曲线没怎么调好，刚开始下拉时效果不太好）

### 一种上拉加载更多效果

* 新浪微博上拉加载更多（可设置背景、状态文本）

***开发者也可以继承BGARefreshViewHolder这个抽象类，实现相应地抽象方法做出格式各样的下拉刷新效果【例如实现handleScale(float scale, int moveYDistance)方法，根据scale实现各种下拉刷新动画】和上拉加载更多特效，可参考BGAMoocStyleRefreshViewHolder、BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder的实现方式。***

### 目前存在的问题

* 当配置自定义头部广告位可滚动时，内容区域和广告位还不能平滑过度。
* 当RecyclerView的LayoutManager为StaggeredGridLayoutManager时，不知道怎样判断是否滚动到了顶部（处理下拉刷新）或者底部（处理上拉加载更多）。

### 效果图
![Image of GridView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/1.gif)
![Image of 普通ListView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/2.gif)
![Image of 普通RecyclerView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/3.gif)
![Image of 滑动ListView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/4.gif)
![Image of 滑动RecyclerView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/5.gif)
![Image of ScrollView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/6.gif)
![Image of NormalView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/7.gif)

### 基本使用

#### 1.添加Gradle依赖

> 没有支持eclipse，建议还在用eclipse的小伙伴都开始转android studio吧

**在build.gradle添加以下依赖（请使用最新版RecyclerView。新版RecyclerView的addOnScrollListener比旧版的setOnScrollListener的扩展性更强。可以让开发者更具项目需求再次添加自定义滚动监听器。这一点比ListView的设计好很多，在处理ListView时还得用反射获取开发者设置的自定义滚动监听器。）**

```groovy
dependencies {
    compile 'com.android.support:recyclerview-v7:22.1.1'
    // 记得添加nineoldandroids
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'cn.bingoogolapple:bga-refreshlayout:1.0.2@aar'
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

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new XXXImplRefreshViewHolder(this, true))
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);


        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
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

        // 加载完毕后在UI线程结束下拉刷新
        mRefreshLayout.endRefreshing();
    }

    @Override
    public void onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以

        // 加载完毕后在UI线程结束加载更多
        mRefreshLayout.endLoadingMore();
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
        onBGARefreshLayoutBeginRefreshing(mRefreshLayout);
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
        onBGARefreshLayoutBeginLoadingMore(mRefreshLayout);
    }

}
```

### 更多详细用法请查看[Wiki](https://github.com/bingoogolapple/BGARefreshLayout-Android/wiki)或者[Demo](https://github.com/bingoogolapple/BGARefreshLayout-Android/tree/master/demo)

### Demo中使用到了我的另外三个库，欢迎大家Star :smile:

* [Splash界面滑动导航+自动轮播效果](https://github.com/bingoogolapple/BGABanner-Android)
* [对AdapterView和RecyclerView通用的Adapter和ViewHolder](https://github.com/bingoogolapple/BGAAdapter-Android)
* [类似新浪微博消息列表，带弹簧效果的左右滑动控件](https://github.com/bingoogolapple/BGASwipeItemLayout-Android)

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
