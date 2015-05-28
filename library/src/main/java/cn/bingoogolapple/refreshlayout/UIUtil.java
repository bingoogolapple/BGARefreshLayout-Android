package cn.bingoogolapple.refreshlayout;

import android.content.Context;
import android.util.TypedValue;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/24 上午11:44
 * 描述:
 */
public class UIUtil {

    public static int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

}