package me.xiaobailong24.mvvmarms.repository.http;

/**
 * @author xiaobailong24
 * @date 2017/11/22
 * 网络请求状态与重试
 */
public interface IRetry {
    /**
     * 用于封装刷新操作，如果子类的业务有刷新逻辑，可以重写此方法
     */
    void retry();
}
