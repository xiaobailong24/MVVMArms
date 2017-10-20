package me.xiaobailong24.mvvmarms.repository.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.xiaobailong24.mvvmarms.repository.utils.ZipHelper;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;

import static timber.log.Timber.w;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Http 请求/响应拦截器
 */
@Singleton
public class RequestInterceptor implements Interceptor {
    private GlobalHttpHandler mHandler;
    private final Level printLevel;

    public enum Level {
        /**
         * 不打印log
         */
        NONE,

        /**
         * 只打印请求信息
         */
        REQUEST,

        /**
         * 只打印响应信息
         */
        RESPONSE,

        /**
         * 所有数据全部打印
         */
        ALL
    }

    @Inject
    public RequestInterceptor(@Nullable GlobalHttpHandler handler, @Nullable Level level) {
        this.mHandler = handler;
        if (level == null) {
            printLevel = Level.ALL;
        } else {
            printLevel = level;
        }
    }


    /**
     * Response 拦截
     *
     * @param chain Chain
     * @return Response
     * @throws IOException IOException
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        boolean logRequest = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.REQUEST);

        if (logRequest) {
            boolean hasRequestBody = request.body() != null;
            //打印请求信息
            Timber.w("HTTP REQUEST >>>%n 「 %s 」%nParams : 「 %s 」%nConnection : 「 %s 」%nHeaders : %n「 %s 」",
                    getTag(request),
                    hasRequestBody ? parseParams(request.newBuilder().build().body()) : "Null",
                    chain.connection(),
                    request.headers());
        }

        boolean logResponse = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.RESPONSE);

        long t1 = logResponse ? System.nanoTime() : 0;
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            w("Http Error: " + e);
            throw e;
        }
        long t2 = logResponse ? System.nanoTime() : 0;

        if (logResponse) {
            String bodySize = originalResponse.body().contentLength() != -1
                    ? originalResponse.body().contentLength() + "-byte"
                    : "unknown-length";
            //打印响应时间以及响应头
            Timber.w("HTTP RESPONSE in [ %d-ms ] , [ %s ] >>>%n%s",
                    TimeUnit.NANOSECONDS.toMillis(t2 - t1), bodySize, originalResponse.headers());
        }

        //打印响应结果
        String bodyString = printResult(request, originalResponse.newBuilder().build(), logResponse);

        //这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
        if (mHandler != null) {
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);
        }

        return originalResponse;
    }

    /**
     * 打印响应结果
     *
     * @param request     Request
     * @param response    Response
     * @param logResponse 是否打印
     * @return String
     * @throws IOException IOException
     */
    @Nullable
    private String printResult(Request request, Response response, boolean logResponse) throws IOException {
        //读取服务器返回的结果
        ResponseBody responseBody = response.body();
        String bodyString = null;
        if (isParseable(responseBody.contentType())) {
            try {
                BufferedSource source = responseBody.source();
                // Buffer the entire body.
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();

                //获取content的压缩类型
                String encoding = response
                        .headers()
                        .get("Content-Encoding");

                Buffer clone = buffer.clone();


                //解析response content
                bodyString = parseContent(responseBody, encoding, clone);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (logResponse) {
                Timber.w("HTTP RESPONSE >>>%n「 %s 」%nResponse Content:%n%s",
                        getTag(request),
                        isJson(responseBody.contentType()) ?
                                CharacterHandler.jsonFormat(bodyString) : isXml(responseBody.contentType()) ?
                                CharacterHandler.xmlFormat(bodyString) : bodyString);
            }

        } else {
            if (logResponse) {
                Timber.w("HTTP RESPONSE >>>%n「 %s 」%n%s",
                        getTag(request),
                        "This result isn't parsed");
            }
        }
        return bodyString;
    }


    private String getTag(Request request) {
        return String.format(" [%s] 「 %s 」", request.method(), request.url().toString());
    }


    /**
     * 解析服务器响应的内容
     *
     * @param responseBody ResponseBody
     * @param encoding     编码
     * @param clone        Buffer
     * @return String
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        //content 使用 gzip 压缩
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            //解压
            return ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset));
            //content 使用 zlib 压缩
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {
            //解压
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));
        } else {
            //content没有被压缩
            return clone.readString(charset);
        }
    }

    /**
     * 解析请求服务器的请求参数
     *
     * @param body RequestBody
     * @return String
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String parseParams(RequestBody body) throws UnsupportedEncodingException {
        if (isParseable(body.contentType())) {
            try {
                Buffer requestBuffer = new Buffer();
                body.writeTo(requestBuffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                return URLDecoder.decode(requestBuffer.readString(charset), convertCharset(charset));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "This params isn't parsed";
    }

    /**
     * 是否可以解析
     *
     * @param mediaType MediaType
     * @return 是否可以解析
     */
    public static boolean isParseable(MediaType mediaType) {
        return mediaType != null
                && (mediaType.toString().toLowerCase().contains("text")
                || isJson(mediaType)
                || isForm(mediaType)
                || isHtml(mediaType)
                || isXml(mediaType));
    }

    public static boolean isJson(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1) {
            return s;
        }
        return s.substring(i + 1, s.length() - 1);
    }
}
