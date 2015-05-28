:running:BGARefreshLayout-Android v1.0.0:running:
============
多种下拉刷新效果、上拉加载更多、可配置自定义头部广告位

#### 目前已经实现了三种下拉刷新效果:

1.新浪微博下拉刷新风格（可设置背景、各种状态是的文本）
2.慕课网下拉刷新风格（使用时可设置其中的logo和颜色成自己公司的风格）
3.类似qq好友列表黏性下拉刷新风格（三阶贝塞尔曲线没怎么调好，刚开始下拉时效果不太好）

#### 一种上拉加载更多效果

1.新浪微博上拉加载更多（可设置背景、状态文本）

大家也可以继承BGARefreshViewHolder这个抽象类，实现相应地抽象方法做出格式各样的下拉刷新效果
【例如实现handleScale(float scale, int moveYDistance)方法，根据scale实现各种下拉刷新动画】和上拉加载更多
特效，可参考BGAMoocStyleRefreshViewHolder、BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder的实现方式。

#### 目前存在的问题

1.当自定义头部广告位可滚动时，内容区域和广告位还不能平滑过度。
2.当RecyclerView的LayoutManager为StaggeredGridLayoutManager时，不知道怎样判断是否滚动到了顶部（处理下拉刷新）或者底部（处理上拉加载更多）。

#### 效果图
![Image of GridView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/1.gif)
![Image of 普通ListView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/2.gif)
![Image of 普通RecyclerView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/3.gif)
![Image of 滑动ListView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/4.gif)
![Image of 滑动RecyclerView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/5.gif)
![Image of ScrollView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/6.gif)
![Image of NormalView示例](https://raw.githubusercontent.com/bingoogolapple/BGARefreshLayout-Android/master/screenshots/7.gif)

>Gradle

```groovy
dependencies {
    compile 'com.android.support:recyclerview-v7:22.1.1'
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'cn.bingoogolapple:bga-refreshlayout:1.0.0@aar'
}
```

##### 详细用法请查看[Demo](https://github.com/bingoogolapple/BGARefreshLayout-Android/tree/master/demo):feet:
