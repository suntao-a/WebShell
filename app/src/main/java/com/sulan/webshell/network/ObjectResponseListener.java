package com.sulan.webshell.network;

import com.jsecode.library.utils.GsonUtils;
import com.sulan.webshell.entities.BaseResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by huangsx on 2016/11/8.
 */

public abstract class ObjectResponseListener<T extends BaseResponse> implements NetWorkHandler.SoapRequestListener {

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public ObjectResponseListener() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (params.length > 0) {
            entityClass = (Class<T>) params[0];
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void requestComplete(boolean success, int tag, String response, String errorMsg) {
        if (success) {
            if (response.startsWith("[") && response.endsWith("]")) {
                response = "{\"list\":" + response + "}";
            }
            onSuccess(tag, GsonUtils.fromJson(response, entityClass));
        } else {
            try {
                onFailed(tag, (T) entityClass.newInstance().setNote(errorMsg).setSuccess(false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onFinished(tag);
    }

    public abstract void onSuccess(int tag, T response);

    public abstract void onFailed(int tag, T error);

    public void onFinished(int tag) {

    }
}
