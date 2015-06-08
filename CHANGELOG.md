Change Log
==========

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