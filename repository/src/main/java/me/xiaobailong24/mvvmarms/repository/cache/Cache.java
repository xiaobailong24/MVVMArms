package me.xiaobailong24.mvvmarms.repository.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author xiaobailong24
 * @date 2017/10/10
 * 缓存接口
 */
public interface Cache<K, V> {
    interface Factory {
        /**
         * 默认缓存大小
         */
        int DEFAULT_CACHE_SIZE = 100;

        /**
         * Returns a new cache
         *
         * @param type 框架中模块类型的 Id
         * @return Cache
         */
        @NonNull
        Cache build(CacheType type);
    }

    /**
     * 返回当前缓存已占用的总 size
     *
     * @return 当前缓存已占用的总 size
     */
    int size();

    /**
     * 返回当前缓存所能允许的最大 size
     *
     * @return 当前缓存所能允许的最大 size
     */
    int getMaxSize();

    /**
     * 返回这个 {@code key} 在缓存中对应的 {@code value}, 如果返回 {@code null} 说明这个 {@code key} 没有对应的 {@code value}
     *
     * @param key Key
     * @return Value
     */
    @Nullable
    V get(K key);

    /**
     * 将 {@code key} 和 {@code value} 以条目的形式加入缓存,如果这个 {@code key} 在缓存中已经有对应的 {@code value}
     * 则此 {@code value} 被新的 {@code value} 替换并返回,如果为 {@code null} 说明是一个新条目
     *
     * @param key   Key
     * @param value 新的 Value
     * @return 旧的 Value
     */
    @Nullable
    V put(K key, V value);

    /**
     * 移除缓存中这个 {@code key} 所对应的条目,并返回所移除条目的 value
     * 如果返回为 {@code null} 则有可能时因为这个 {@code key} 对应的 value 为 {@code null} 或条目不存在
     *
     * @param key Key
     * @return Value
     */
    @Nullable
    V remove(K key);

    /**
     * 如果这个 {@code key} 在缓存中有对应的 value 并且不为 {@code null}, 则返回 {@code true}
     *
     * @param key Key
     * @return 存在缓存怎返回 true
     */
    boolean containsKey(K key);

    /**
     * 清除缓存中所有的内容
     */
    void clear();
}

