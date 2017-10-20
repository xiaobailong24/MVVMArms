package me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * @author xiaobailong24
 * @date 2017/8/19
 * DiffUtil.Callback 主要就是为了限定两个数据集中，子项的比对规则
 * 提高 RecyclerView 效率
 * @link {}https://juejin.im/post/5995ba616fb9a024747ed8e8}
 */
public class RecyclerViewDiffCallback<T> extends DiffUtil.Callback {
    private List<T> mOldList;
    private List<T> mNewList;

    public RecyclerViewDiffCallback(List<T> oldList, List<T> newList) {
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getClass()
                .equals(mNewList.get(newItemPosition).getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldT = mOldList.get(oldItemPosition);
        T newT = mNewList.get(newItemPosition);
        return oldT.equals(newT);
    }
}
