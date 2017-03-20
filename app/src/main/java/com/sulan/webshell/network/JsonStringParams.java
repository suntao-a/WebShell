package com.sulan.webshell.network;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class JsonStringParams implements I_HttpParams {

	private String json;

	public JsonStringParams(String jsonString) {
		this.json = jsonString;
	}

	@Override
	public HttpEntity getEntity() {
		try {
			return new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
