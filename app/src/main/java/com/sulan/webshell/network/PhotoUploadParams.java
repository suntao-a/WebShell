package com.sulan.webshell.network;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.jsecode.library.utils.ByteUtils;
import com.jsecode.library.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Vector;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.BufferedHttpEntity;
import cz.msebera.android.httpclient.entity.InputStreamEntity;

public class PhotoUploadParams implements I_HttpParams {

    private JSONObject json;
    private Bitmap bmp;

    public PhotoUploadParams(JSONObject json, Bitmap bmp) {
        this.json = json;
        this.bmp = bmp;
    }

    @Override
    public HttpEntity getEntity() {
        if (json == null || bmp == null) {
            return null;
        }

        ByteArrayInputStream lis = null;
        ByteArrayInputStream jis = null;
        ByteArrayInputStream fis = null;
        SequenceInputStream sis = null;

        ByteArrayOutputStream bos = null;
        try {
            // jpg
            bos = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.JPEG, 100, bos);
            bos.toByteArray();
            byte[] jpgBytes = bos.toByteArray();
            fis = new ByteArrayInputStream(jpgBytes);

            // json
            json = json.put("fileLength", jpgBytes.length);
            byte[] jsonBytes = json.toString().getBytes();
            int length = jsonBytes.length;

            Vector<InputStream> vector = new Vector<InputStream>();
            // json 长度
            lis = new ByteArrayInputStream(ByteUtils.intToByte(length));
            vector.add(lis);
            jis = new ByteArrayInputStream(jsonBytes);
            vector.add(jis);
            vector.add(fis);

            sis = new SequenceInputStream(vector.elements());
            BufferedHttpEntity buffEntity = null;
            buffEntity = new BufferedHttpEntity(new InputStreamEntity(sis, 4 + length + jpgBytes.length));
            return buffEntity;
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(lis, jis, fis, sis, bos);
        }

        return null;
    }
}
