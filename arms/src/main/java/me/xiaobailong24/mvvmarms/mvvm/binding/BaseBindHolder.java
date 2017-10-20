package me.xiaobailong24.mvvmarms.mvvm.binding;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import me.xiaobailong24.mvvmarms.R;

/**
 * @author xiaobailong24
 * @date 2017/6/30
 * DataBinding BaseBindHolder
 */
public class BaseBindHolder extends BaseViewHolder {

    public BaseBindHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }
}
