package com.pedro.entities;

import java.util.List;

public class Artist {
	private ExternalUrls externalUrls;
	private List<String> genres;
	private String href;
	private String id;
	private List<Image> images;
	private String name;
	private int popularity;
	private SpotifyDataType type = SpotifyDataType.ARTIST;
	private String uri;

	public Artist(ExternalUrls externalUrls, List<String> genres, String href, String id, List<Image> images,
			String name, int popularity, SpotifyDataType type, String uri) {
		super();
		this.externalUrls = externalUrls;
		this.genres = genres;
		this.href = href;
		this.id = id;
		this.images = images;
		this.name = name;
		this.popularity = popularity;
		this.type = type;
		this.uri = uri;
	}

	public ExternalUrls getExternalUrls() {
		return externalUrls;
	}

	public void setExternalUrls(ExternalUrls externalUrls) {
		this.externalUrls = externalUrls;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
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

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
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