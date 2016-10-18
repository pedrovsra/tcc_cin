package com.pedro.auth;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class SpotifyUrl extends GenericUrl {

	@Key
	private String fields;

	public SpotifyUrl(String url) {
		super(url);
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}
}