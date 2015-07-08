Change Log
==========

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