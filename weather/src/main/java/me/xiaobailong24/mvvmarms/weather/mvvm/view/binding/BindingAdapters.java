package me.xiaobailong24.mvvmarms.weather.mvvm.view.binding;

import android.databinding.BindingAdapter;
import android.view.View;

import com.allen.library.SuperTextView;

/**
 * Created by xiaobailong24 on 2017/6/26.
 * Data Binding adapters specific to the app.
 * 自定义 DataBinding
 */

public class BindingAdapters {

    //自定义 DataBinding 控制 View 的显示和隐藏
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    //SuperTextView
    @BindingAdapter("sLeftTextString")
    public static void setTitle(SuperTextView view, String title) {
        view.setLeftString(title);
    }

    //SuperTextView
    @BindingAdapter("sRightTextString")
    public static void setContent(SuperTextView view, String content) {
        view.setRightString(content);
    }

}
