package com.sulan.webshell.entities;

import com.jsecode.library.utils.GsonUtils;

/**
 * Created by huangsx on 2016/11/7.
 */

public abstract class BaseRequestParam {

    public String toJson() {
        return GsonUtils.toJson(new RequestEntity(this));
    }
}
