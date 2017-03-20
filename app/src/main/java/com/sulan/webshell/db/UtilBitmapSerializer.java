package com.sulan.webshell.db;

import android.graphics.Bitmap;

import com.activeandroid.serializer.TypeSerializer;
import com.jsecode.library.utils.BitmapUtils;

/**
 * Created by huangsx on 2016/11/24.
 */

public class UtilBitmapSerializer extends TypeSerializer {
    @Override
    public Class<?> getDeserializedType() {
        return Bitmap.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return byte[].class;
    }

    @Override
    public Object serialize(Object data) {
        return BitmapUtils.toByteArray((Bitmap) data);
    }

    @Override
    public Object deserialize(Object data) {
        return BitmapUtils.fromByteArray((byte[]) data);
    }
}
