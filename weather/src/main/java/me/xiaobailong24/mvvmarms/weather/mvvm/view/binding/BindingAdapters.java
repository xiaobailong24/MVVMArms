package me.xiaobailong24.mvvmarms.weather.mvvm.view.binding;

import android.databinding.BindingAdapter;
import android.view.View;

import com.allen.library.SuperTextView;

/**
 * @author xiaobailong24
 * @date 2017/6/26
 * Data Binding adapters specific to the app.
 * 自定义 DataBinding
 */
public class BindingAdapters {

    /**
     * 自定义 DataBinding 控制 View 的显示和隐藏
     *
     * @param view View
     * @param show 是否显示
     */
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    //SuperTextView
    @BindingAdapter("sLeftTextString")
    public static void setLeftTextString(SuperTextView view, String left) {
        view.setLeftString(left);
    }

    @BindingAdapter("sRightTextString")
    public static void setRightTextString(SuperTextView view, String right) {
        view.setRightString(right);
    }

    @BindingAdapter("sLeftTopTextString")
    public static void setLeftTopTextString(SuperTextView view, String leftTop) {
        view.setLeftTopString(leftTop);
    }

    @BindingAdapter("sLeftBottomTextString")
    public static void setLeftBottomTextString(SuperTextView view, String leftBottom) {
        view.setLeftBottomString(leftBottom);
    }

    @BindingAdapter("sRightTopTextString")
    public static void setRightTopTextString(SuperTextView view, String rightTop) {
        view.setRightTopString(rightTop);
    }

    @BindingAdapter("sRightBottomTextString")
    public static void setRightBottomTextString(SuperTextView view, String rightBottom) {
        view.setRightBottomString(rightBottom);
    }

}
