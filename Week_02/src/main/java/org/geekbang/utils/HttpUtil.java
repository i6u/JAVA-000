package org.geekbang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.*;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

/**
 * @author :  witt on 2020/7/19 2:35 上午 星期日 :)
 */
public enum HttpUtil {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static final OkHttpClient client;

    static {
        final Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(128);
        dispatcher.setMaxRequestsPerHost(20);

        final RetryInterceptor retryInterceptor = new RetryInterceptor(5, 1500);

        client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .dispatcher(dispatcher)
                .followRedirects(false)
                .addInterceptor(retryInterceptor)
                .build();
    }

    private Headers headers = Headers.of("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36");

    /**
     * 下载响应字符串
     */
    public String loadString(String url) {
        Request request = new Request.Builder()
                .headers(headers)
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            log.trace("code:" + response.code() + " \n" + " url :" + url);
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            throw new RuntimeException("请求失败\n" + url);
        } catch (IOException e) {
            throw new RuntimeException("下载失败\n" + url, e);
        }
    }

    /**
     * okHttp 使用拦截器实现重试机制
     */
    private static class RetryInterceptor implements Interceptor {

        private static final Logger log = LoggerFactory.getLogger(RetryInterceptor.class);

        /**
         * 最大重试次数
         */
        private int executionCount;
        /**
         * 重试的间隔
         */
        private long retryInterval;

        RetryInterceptor(int executionCount, long retryInterval) {
            this.executionCount = executionCount;
            this.retryInterval = retryInterval;
        }

        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            // head 请求不做重试
            if (request.method().equalsIgnoreCase("HEAD")) {
                return response;
            }
            int retryNum = 0;
            while (!response.isSuccessful() && retryNum <= executionCount) {
                log.trace("intercept Request is not successful - retry: " + retryNum + "" +
                        " state: " + response.code() + "  {} url :" + request.url().toString());
                final long nextInterval = retryInterval;
                try {
                    log.trace("Wait for " + nextInterval);
                    Thread.sleep(nextInterval);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new InterruptedIOException();
                }
                retryNum++;
                response.close();
                response = chain.proceed(request);
            }
            return response;
        }

    }
}
