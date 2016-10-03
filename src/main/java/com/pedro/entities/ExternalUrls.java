package com.pedro.entities;

import java.util.HashMap;
import java.util.Map;

public class ExternalUrls {

	private Map<String, String> externalUrls = new HashMap<String, String>();

	public Map<String, String> getExternalUrls() {
		return this.externalUrls;
	}

	public String get(String key) {
		return this.externalUrls.get(key);
	}

}