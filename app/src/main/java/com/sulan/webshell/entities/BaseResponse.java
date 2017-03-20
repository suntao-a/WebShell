package com.sulan.webshell.entities;

import com.jsecode.library.utils.GsonUtils;

/**
 * Created by huangsx on 2016/11/8.
 */

public class BaseResponse {
    private boolean success = true;
    private String note;

    public boolean isSuccess() {
        return success;
    }

    public BaseResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getNote() {
        return note;
    }

    public BaseResponse setNote(String note) {
        this.note = note;
        return this;
    }

    public String toJson() {
        return GsonUtils.toJson(this);
    }
}
