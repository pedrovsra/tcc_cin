package com.pedro.entities;

public enum AlbumType {
	
	ALBUM("album"),
	SINGLE("single"),
	COMPILATION("compilation");
	
	public final String type;
	
	AlbumType(String type) {
		this.type = type; 
	}

	public String getType() {
		return this.type;
	}
}