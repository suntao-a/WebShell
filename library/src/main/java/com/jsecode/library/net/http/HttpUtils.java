package com.jsecode.library.net.http;

import com.jsecode.library.utils.Logger;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import java.nio.charset.Charset;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {

    public static String DEFAULT_URL = "http://192.168.0.149:8888/Common";

    private static AsyncHttpClient client = new AsyncHttpClient();    //实例话对象

    static {
        client.setTimeout(10 * 1000);   //设置链接超时/返回超时，如果不设置，默认为10s
        client.setMaxRetriesAndTimeout(2, 1000);//重试次数 重试间隔
        client.addHeader("Content-Type", "application/json; charset=UTF-8");
        client.addHeader("Accept-Encoding", "gzip");
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    public static AsyncHttpClient c() {
        return getClient();
    }

    public static RequestHandle get(ResponseHandlerInterface handlerInterface) {
        return get(DEFAULT_URL, handlerInterface);
    }

    public static RequestHandle get(String URL, ResponseHandlerInterface handlerInterface) {
        return client.get(URL, handlerInterface);
    }

    public static RequestHandle post(Object postBody, ResponseHandlerInterface handlerInterface) {
        return post(DEFAULT_URL, postBody, handlerInterface);
    }

    public static RequestHandle post(String url, Object postBody, ResponseHandlerInterface handlerInterface) {
        String data = postBody.toString();
        Logger.i("NetWork", "request:" + data);
        HttpEntity entity = new StringEntity(data, Charset.forName("UTF-8"));
        if (handlerInterface == null) {
            handlerInterface = new JsonHttpResponseHandler();
        }
        return client.post(null, url, entity, null, handlerInterface);
    }

    public static RequestHandle head(ResponseHandlerInterface handlerInterface) {
        return head(DEFAULT_URL, handlerInterface);
    }

    public static RequestHandle head(String URL, ResponseHandlerInterface handlerInterface) {
        return client.head(URL, handlerInterface);
    }

}