package me.xiaobailong24.mvvmarms.weather.app;

import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import me.xiaobailong24.mvvmarms.lifecycle.utils.UiUtils;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * @author xiaobailong24
 * @date 2017/9/5
 * 全局处理所有错误的监听
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {
    final int serverError = 500;
    final int notFoundError = 404;
    final int serverForbiddenError = 403;
    final int redirectionError = 307;

    @Override
    public void handleResponseError(Context context, Throwable t) {
        //用来提供处理所有错误的监听
        //rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效
        Timber.tag("Catch-Error").w(t.getMessage());
        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
        String msg = "未知错误";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误";
        }
        UiUtils.snackbarText(msg);
    }


    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == serverError) {
            msg = "服务器发生错误";
        } else if (httpException.code() == notFoundError) {
            msg = "请求地址不存在";
        } else if (httpException.code() == serverForbiddenError) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == redirectionError) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
