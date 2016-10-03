package com.pedro.entities;

import java.util.List;

public class SimpleAlbum {
	private AlbumType albumType;
	private ExternalUrls externalUrls;
	private String href;
	private String id;
	private List<Image> images;
	private String name;
	private SpotifyDataType type = SpotifyDataType.ALBUM;
	private String uri;
	private List<String> availableMarkets;

	public SimpleAlbum(AlbumType albumType, ExternalUrls externalUrls, String href, String id, List<Image> images,
			String name, SpotifyDataType type, String uri, List<String> availableMarkets) {
		super();
		this.albumType = albumType;
		this.externalUrls = externalUrls;
		this.href = href;
		this.id = id;
		this.images = images;
		this.name = name;
		this.type = type;
		this.uri = uri;
		this.availableMarkets = availableMarkets;
	}

	public AlbumType getAlbumType() {
		return albumType;
	}

	public void setAlbumType(AlbumType albumType) {
		this.albumType = albumType;
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
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

	public List<String> getAvailableMarkets() {
		return availableMarkets;
	}

	public void setAvailableMarkets(List<String> availableMarkets) {
		this.availableMarkets = availableMarkets;
	}
}