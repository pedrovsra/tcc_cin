package com.pedro.entities;

import java.util.HashMap;
import java.util.Map;

public class ExternalIds {

	private Map<String, String> externalIds = new HashMap<String, String>();

	public Map<String, String> getExternalIds() {
		return this.externalIds;
	}

	public String get(String key) {
		return this.externalIds.get(key);
	}
}