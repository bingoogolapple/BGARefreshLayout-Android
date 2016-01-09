/**
 * Copyright 2015 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.bingoogolapple.refreshlayout.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ScrollView;

import cn.bingoogolapple.refreshlayout.BGAStickyNavLayout;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/10/28 上午2:26
 * 描述:
 */
public class BGARefreshScrollingUtil {

    private BGARefreshScrollingUtil() {
    }


    public static boolean isScrollViewOrWebViewToTop(View view) {
        return view != null && view.getScrollY() == 0;
    }

    public static boolean isAbsListViewToTop(AbsListView absListView) {
        if (absListView != null) {
            int firstChildTop = 0;
            if (absListView.getChildCount() > 0) {
                // 如果AdapterView的子控件数量不为0，获取第一个子控件的top
                firstChildTop = absListView.getChildAt(0).getTop() - absListView.getPaddingTop();
            }
            if (absListView.getFirstVisiblePosition() == 0 && firstChildTop == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRecyclerViewToTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            int firstChildTop = 0;
            if (recyclerView.getChildCount() > 0) {
                // 如果RecyclerView的子控件数量不为0，获取第一个子控件的top

                // 解决item的topMargin不为0时不能触发下拉刷新
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getChildAt(0).getLayoutParams();
                firstChildTop = recyclerView.getChildAt(0).getTop() - layoutParams.topMargin - recyclerView.getPaddingTop();
            }

            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager == null) {
                return true;
            }
            if (manager.getItemCount() == 0) {
                return true;
            }

            if (manager instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
                if (layoutManager.findFirstCompletelyVisibleItemPosition() < 1 && firstChildTop == 0) {
                    return true;
                }
            } else if (manager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;

                int[] out = layoutManager.findFirstCompletelyVisibleItemPositions(null);
                if (out[0] < 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isStickyNavLayoutToTop(BGAStickyNavLayout stickyNavLayout) {
        return isScrollViewOrWebViewToTop(stickyNavLayout) && stickyNavLayout.isContentViewToTop();
    }


    public static boolean isWebViewToBottom(WebView webView) {
        return webView != null && webView.getContentHeight() * webView.getScale() == (webView.getScrollY() + webView.getMeasuredHeight());
    }

    public static boolean isScrollViewToBottom(ScrollView scrollView) {
        if (scrollView != null) {
            int scrollContentHeight = scrollView.getScrollY() + scrollView.getMeasuredHeight() - scrollView.getPaddingTop() - scrollView.getPaddingBottom();
            int realContentHeight = scrollView.getChildAt(0).getMeasuredHeight();
            if (scrollContentHeight == realContentHeight) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAbsListViewToBottom(AbsListView absListView) {
        if (absListView != null && absListView.getAdapter() != null) {
            int lastChildBottom = 0;
            if (absListView.getChildCount() > 0) {
                // 如果AdapterView的子控件数量不为0，获取最后一个子控件的bottom
                lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
            }
            return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
        }
        return false;
    }

    public static boolean isRecyclerViewToBottom(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager == null || manager.getItemCount() == 0) {
                return false;
            }

            if (manager instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) manager;

                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    BGAStickyNavLayout stickyNavLayout = getStickyNavLayout(recyclerView);
                    if (stickyNavLayout != null) {
                        // 处理BGAStickyNavLayout中findLastCompletelyVisibleItemPosition失效问题
                        View lastChild = layoutManager.getChildAt(layoutManager.findLastCompletelyVisibleItemPosition());
                        if (lastChild == null) {
                            return true;
                        } else {
                            // 0表示x，1表示y
                            int[] location = new int[2];
                            lastChild.getLocationOnScreen(location);
                            int lastChildBottomOnScreen = location[1] + lastChild.getMeasuredHeight();
                            stickyNavLayout.getLocationOnScreen(location);
                            int stickyNavLayoutBottomOnScreen = location[1] + stickyNavLayout.getMeasuredHeight();
                            return lastChildBottomOnScreen <= stickyNavLayoutBottomOnScreen;
                        }
                    } else {
                        return true;
                    }
                }
            } else if (manager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;

                int[] out = layoutManager.findLastCompletelyVisibleItemPositions(null);
                int lastPosition = layoutManager.getItemCount() - 1;
                for (int position : out) {
                    if (position == lastPosition) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static void scrollToBottom(final ScrollView scrollView) {
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    public static void scrollToBottom(final AbsListView absListView) {
        if (absListView != null) {
            if (absListView.getAdapter() != null && absListView.getAdapter().getCount() > 0) {
                absListView.post(new Runnable() {
                    @Override
                    public void run() {
                        absListView.setSelection(absListView.getAdapter().getCount() - 1);
                    }
                });
            }
        }
    }

    public static void scrollToBottom(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            if (recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() > 0) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                });
            }
        }
    }

    public static BGAStickyNavLayout getStickyNavLayout(View view) {
        ViewParent viewParent = view.getParent();
        while (viewParent != null) {
            if (viewParent instanceof BGAStickyNavLayout) {
                return (BGAStickyNavLayout) viewParent;
            }
            viewParent = viewParent.getParent();
        }
        return null;
    }
}