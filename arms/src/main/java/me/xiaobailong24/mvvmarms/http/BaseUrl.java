package me.xiaobailong24.mvvmarms.http;

import okhttp3.HttpUrl;

/**
 * Created by xiaobailong24 on 2017/7/25.
 * Retrofit Url 接口
 */

public interface BaseUrl {
    /**
     * 针对于 BaseUrl 在 App 启动时不能确定,需要请求服务器接口动态获取的应用场景
     * 在调用 Retrofit 接口之前,使用 Okhttp 或其他方式,请求到正确的 BaseUrl 并通过此方法返回
     *
     * @return HttpUrl
     */
    HttpUrl url();
}
