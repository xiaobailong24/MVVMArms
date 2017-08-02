package me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.allen.library.SuperTextView;

import java.util.List;

import me.xiaobailong24.mvvmarms.mvvm.binding.BaseBindAdapter;
import me.xiaobailong24.mvvmarms.mvvm.binding.BaseBindHolder;
import me.xiaobailong24.mvvmarms.weather.BR;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.pojo.TextContent;

/**
 * Created by xiaobailong24 on 2017/6/28.
 * RecyclerView DataBinding Adapter
 */

public class TextContentAdapter extends BaseBindAdapter<TextContent> {

    public TextContentAdapter(@LayoutRes int layoutResId, @Nullable List<TextContent> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseBindHolder helper, TextContent item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.content, item);
        binding.executePendingBindings();
        ((SuperTextView) helper.getView(R.id.super_item))
                .setLeftString(item.getTitle())
                .setRightString(item.getContent());
    }

}
