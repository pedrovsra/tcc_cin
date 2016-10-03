package com.pedro.entities;

import java.util.List;

public class Album {
	private AlbumType albumType;
	private List<SimpleArtist> artists;
	private List<String> availableMarkets;
	private List<Copyright> copyrights;
	private ExternalIds externalIds;
	private ExternalUrls externalUrls;
	private List<String> genres;
	private String href;
	private String id;
	private List<Image> images;
	private String name;
	private int popularity;
	private String releaseDate;
	private String releaseDatePrecision;
	private Page<SimpleTrack> tracks;
	private SpotifyDataType type = SpotifyDataType.ALBUM;
	private String uri;

	public Album(AlbumType albumType, List<SimpleArtist> artists, List<String> availableMarkets,
			List<Copyright> copyrights, ExternalIds externalIds, ExternalUrls externalUrls, List<String> genres,
			String href, String id, List<Image> images, String name, int popularity, String releaseDate,
			String releaseDatePrecision, Page<SimpleTrack> tracks, SpotifyDataType type, String uri) {
		super();
		this.albumType = albumType;
		this.artists = artists;
		this.availableMarkets = availableMarkets;
		this.copyrights = copyrights;
		this.externalIds = externalIds;
		this.externalUrls = externalUrls;
		this.genres = genres;
		this.href = href;
		this.id = id;
		this.images = images;
		this.name = name;
		this.popularity = popularity;
		this.releaseDate = releaseDate;
		this.releaseDatePrecision = releaseDatePrecision;
		this.tracks = tracks;
		this.type = type;
		this.uri = uri;
	}

	public AlbumType getAlbumType() {
		return albumType;
	}

	public void setAlbumType(AlbumType albumType) {
		this.albumType = albumType;
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

	public List<Copyright> getCopyrights() {
		return copyrights;
	}

	public void setCopyrights(List<Copyright> copyrights) {
		this.copyrights = copyrights;
	}

	public ExternalIds getExternalIds() {
		return externalIds;
	}

	public void setExternalIds(ExternalIds externalIds) {
		this.externalIds = externalIds;
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

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseDatePrecision() {
		return releaseDatePrecision;
	}

	public void setReleaseDatePrecision(String releaseDatePrecision) {
		this.releaseDatePrecision = releaseDatePrecision;
	}

	public Page<SimpleTrack> getTracks() {
		return tracks;
	}

	public void setTracks(Page<SimpleTrack> tracks) {
		this.tracks = tracks;
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