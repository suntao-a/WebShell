package com.jsecode.library.net.http;

import com.jsecode.library.utils.GsonUtils;
import com.jsecode.library.utils.Logger;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * Created by huangsx on 15/10/13.
 */
public abstract class ObjectResponseHandler<T> extends TextHttpResponseHandler {

    private Class<T> entityClass;

    @SuppressWarnings(value = {"unchecked"})
    public ObjectResponseHandler() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (params.length > 0) {
            entityClass = (Class<T>) params[0];
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Logger.i("NetWork", "response:" + responseString);
        Logger.w(this, "onFailure(int, Header[], String, Throwable) was not overriden, but callback was received", throwable);

    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Logger.i("NetWork", "response:" + responseString);
        T result = GsonUtils.fromJson(responseString, entityClass);
        if (result != null) {
            onSuccess(statusCode, headers, result);
        } else {
            onFailure(statusCode, headers, responseString, new Throwable("error json format or doesn't match entity class."));
        }
    }

    public abstract void onSuccess(int statusCode, Header[] headers, T result);

}
