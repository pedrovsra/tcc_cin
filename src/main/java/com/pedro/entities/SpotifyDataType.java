package com.pedro.entities;

public enum SpotifyDataType {
	
	ALBUM("album"),
	TRACK("track"),
	ARTIST("artist"),
	USER("user"),
	PLAYLIST("playlist");
	
	public final String type;
	
	SpotifyDataType(String type) {
		this.type = type; 
	}

	public String getType() {
		return this.type;
	}
}