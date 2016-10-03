package com.pedro.entities;

public class SimpleArtist {

	private ExternalUrls externalUrls;
	private String href;
	private String id;
	private String name;
	private SpotifyDataType type = SpotifyDataType.ARTIST;
	private String uri;

	public SimpleArtist(ExternalUrls externalUrls, String href, String id, String name, SpotifyDataType type,
			String uri) {
		super();
		this.externalUrls = externalUrls;
		this.href = href;
		this.id = id;
		this.name = name;
		this.type = type;
		this.uri = uri;
	}

	public ExternalUrls getExternalUrls() {
		return externalUrls;
	}

	public void setExternalUrls(ExternalUrls externalUrls) {
		this.externalUrls = externalUrls;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SpotifyDataType getType() {
		return type;
	}

	public void setType(SpotifyDataType type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}