package com.pedro.entities;

import java.util.List;

public class SimpleTrack {

	private SimpleAlbum album;
	private List<SimpleArtist> artists;
	private List<String> availableMarkets;
	private int discNumber;
	private int duration;
	private boolean explicit;
	private ExternalUrls externalUrls;
	private String href;
	private String id;
	private String name;
	private String previewUrl;
	private int trackNumber;
	private SpotifyDataType type = SpotifyDataType.TRACK;
	private String uri;

	public SimpleTrack(SimpleAlbum album, List<SimpleArtist> artists, List<String> availableMarkets, int discNumber,
			int duration, boolean explicit, ExternalUrls externalUrls, String href, String id, String name,
			String previewUrl, int trackNumber, SpotifyDataType type, String uri) {
		super();
		this.album = album;
		this.artists = artists;
		this.availableMarkets = availableMarkets;
		this.discNumber = discNumber;
		this.duration = duration;
		this.explicit = explicit;
		this.externalUrls = externalUrls;
		this.href = href;
		this.id = id;
		this.name = name;
		this.previewUrl = previewUrl;
		this.trackNumber = trackNumber;
		this.type = type;
		this.uri = uri;
	}

	public SimpleAlbum getAlbum() {
		return album;
	}

	public void setAlbum(SimpleAlbum album) {
		this.album = album;
	}

	public List<SimpleArtist> getArtists() {
		return artists;
	}

	public void setArtists(List<SimpleArtist> artists) {
		this.artists = artists;
	}

	public List<String> getAvailableMarkets() {
		return availableMarkets;
	}

	public void setAvailableMarkets(List<String> availableMarkets) {
		this.availableMarkets = availableMarkets;
	}

	public int getDiscNumber() {
		return discNumber;
	}

	public void setDiscNumber(int discNumber) {
		this.discNumber = discNumber;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isExplicit() {
		return explicit;
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
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

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
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