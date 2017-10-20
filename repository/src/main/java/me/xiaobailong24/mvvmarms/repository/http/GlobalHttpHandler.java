package me.xiaobailong24.mvvmarms.repository.http;

import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryConfigModule;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * 处理 Http 请求和响应结果的处理类
 * 使用 {@link RepositoryConfigModule.Builder#globalHttpHandler(GlobalHttpHandler)} 方法配置
 */
public interface GlobalHttpHandler {
    /**
     * 这里可以先客户端一步拿到每一次 http 请求的结果,可以解析成 json,做一些操作,
     * 如检测到 token 过期后重新请求 token,并重新执行请求
     * <p>
     * 这里如果发现 token 过期,可以先请求最新的 token,然后在拿新的 token 放入 request 里去重新请求
     * 注意在这个回调之前已经调用过 proceed,所以这里必须自己去建立网络请求,如使用 okhttp 使用新的 request 去请求
     * create a new request and modify it accordingly using the new token
     * {@code Request newRequest = chain.request().newBuilder().header("token", newToken).build();}
     * <p>
     * retry the request
     * {@code response.body().close();}
     * 如果使用 okhttp 将新的请求,请求成功后,将返回的 response  return 出去即可
     * 如果不需要返回新的结果,则直接把 response 参数返回出去
     *
     * @param httpResult String
     * @param chain      Interceptor.Chain
     * @param response   原 Response
     * @return 处理后的 Response
     */
    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    /**
     * 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的 request。
     * 如增加 header,不做操作则直接返回 request 参数
     * {@code  return chain.request().newBuilder().header("token", tokenId).build();}
     *
     * @param chain   Interceptor.
     * @param request 原 Request
     * @return 处理后的 Request
     */
    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

    GlobalHttpHandler EMPTY = new GlobalHttpHandler() {
        @Override
        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
            //不管是否处理,都必须将 response 返回出去
            return response;
        }

        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
            //不管是否处理,都必须将 request 返回出去
            return request;
        }
    };

}
