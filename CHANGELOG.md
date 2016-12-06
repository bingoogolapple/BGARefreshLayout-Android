Change Log
==========

Version 1.1.7 *(2015-12-06)*
----------------------------

- 移除对 nineoldandroids 的依赖

Version 1.1.6 *(2015-07-27)*
----------------------------

- 修复 Item 高度大于 RecyclerView 高度时无法上拉加载更多

Version 1.1.5 *(2015-05-27)*
----------------------------

- 处理上下拉过于灵敏  fix #39  fix #58

Version 1.1.4 *(2015-04-08)*
----------------------------

- 开放返回当前状态getCurrentRefreshStatus方法和isLoadingMore方法
- 解决使用ItemDecoration设置top后下拉刷新失效

Version 1.1.3 *(2015-01-10)*
----------------------------

- 解决GridLayoutManager加载更多时，最后一行只显示第一个item
- 处理BGAStickyNavLayout中嵌套RecyclerView时findLastCompletelyVisibleItemPosition失效问题
- 处理BGAStickyNavLayout中嵌套AbsListView时lastChild.getBottom() <= absListView.getMeasuredHeight()失效问题

Version 1.1.2 *(2014-12-03)*
----------------------------

- 添加设置下拉刷新是否可用的开关

Version 1.1.1 *(2014-11-24)*
----------------------------

- 解决RecyclerView第一个单元格的高度过大无法下拉刷新

Version 1.1.0 *(2014-11-18)*
----------------------------

- 解决ListView上拉加载更多视图遮盖最后一个item

Version 1.0.9 *(2014-11-15)*
----------------------------

- 添加BGAMeiTuanRefreshViewHolder美团下拉刷新样式
- 修改BGAMoocStyleRefreshViewHolder的方法public void setOriginalBitmap(Bitmap originalBitmap)为public void setOriginalImage(@DrawableRes int resId)
- 修改BGAMoocStyleRefreshViewHolder的方法public void setUltimateColor(int ultimateColor)为public void setUltimateColor(@ColorRes int resId)
- 修改BGAStickinessRefreshViewHolder的方法public void setRotateDrawable(Drawable rotateDrawable)为public void setRotateImage(@DrawableRes int resId)
- 修改BGAStickinessRefreshViewHolder的方法public void setStickinessColor(int stickinessColor)为public void setStickinessColor(@ColorRes int resId)

Version 1.0.8 *(2014-11-1)*
----------------------------

- 添加BGAStickyNavLayout

Version 1.0.7 *(2014-7-22)*
----------------------------

- 解决自定义头部视图抢占事件导致下拉刷新错乱的问题
- 新增支持WebView为内容视图时的下拉刷新

Version 1.0.6 *(2014-7-18)*
----------------------------

- 解决内容视图的padding不为0和列表item的margin不为0时不能下拉刷新和上拉加载更多的问题

Version 1.0.5 *(2014-7-11)*
----------------------------

- 支持RecyclerView的LayoutManager为StaggeredGridLayoutManager

Version 1.0.4 *(2014-7-8)*
----------------------------

- 解决WiFi环境下加载数据非常快时，最后几个item无法显示的问题

Version 1.0.3 *(2014-6-14)*
----------------------------

- 修改beginRefreshing和beginLoadingMore方法，调用这两个方法时会触发delegate方法
- 为delegate的onBGARefreshLayoutBeginLoadingMore方法添加boolean类型的返回参数。如果要开始加载更多则返回true，否则返回false。（返回false的场景：没有网络、一共只有x页数据并且已经加载了x页数据了）

Version 1.0.2 *(2014-6-9)*
----------------------------

- 公开BGARefreshLayout的beginRefreshing和beginLoadingMore方法，方便刚进入界面时自动进入刷新状态。但是调用这两个方法时不会触发delegate方法，开发者需单独调用onBGARefreshLayoutBeginRefreshing(mRefreshLayout)方法（参考iOS中的UIRefreshControl，不晓得这样是好是坏）
- 为delegate方法增加BGARefreshLayout参数

Version 1.0.1 *(2014-6-6)*
----------------------------

- 结束上拉加载更多从以前的endRefreshing()改为endLoadingMore()
- 刷新和加载更多过程中，内容控件可接收触摸事件

Version 1.0.0 *(2014-5-28)*
----------------------------

Initial release.
