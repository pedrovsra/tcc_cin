package com.pedro.entities.analysis_entities;

public class AudioFeature {

	private float danceability;
	private float energy;
	private int key;
	private float loudness;
	private int mode;
	private float speechiness;
	private float acousticness;
	private float instrumentalness;
	private float liveness;
	private float valente;
	private float tempo;
	private String type;
	private String id;
	private String uri;
	private String track_href;
	private String analysis_url;
	private int duration_ms;
	private int time_signature;

	public AudioFeature() { }

	public AudioFeature(float danceability, float energy, int key, float loudness, int mode, float speechiness,
			float acousticness, float instrumentalness, float liveness, float valente, float tempo, String type,
			String id, String uri, String track_href, String analysis_url, int duration_ms, int time_signature) {
		super();
		this.danceability = danceability;
		this.energy = energy;
		this.key = key;
		this.loudness = loudness;
		this.mode = mode;
		this.speechiness = speechiness;
		this.acousticness = acousticness;
		this.instrumentalness = instrumentalness;
		this.liveness = liveness;
		this.valente = valente;
		this.tempo = tempo;
		this.type = type;
		this.id = id;
		this.uri = uri;
		this.track_href = track_href;
		this.analysis_url = analysis_url;
		this.duration_ms = duration_ms;
		this.time_signature = time_signature;
	}

	public float getDanceability() {
		return danceability;
	}

	public void setDanceability(float danceability) {
		this.danceability = danceability;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public float getLoudness() {
		return loudness;
	}

	public void setLoudness(float loudness) {
		this.loudness = loudness;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public float getSpeechiness() {
		return speechiness;
	}

	public void setSpeechiness(float speechiness) {
		this.speechiness = speechiness;
	}

	public float getAcousticness() {
		return acousticness;
	}

	public void setAcousticness(float acousticness) {
		this.acousticness = acousticness;
	}

	public float getInstrumentalness() {
		return instrumentalness;
	}

	public void setInstrumentalness(float instrumentalness) {
		this.instrumentalness = instrumentalness;
	}

	public float getLiveness() {
		return liveness;
	}

	public void setLiveness(float liveness) {
		this.liveness = liveness;
	}

	public float getValente() {
		return valente;
	}

	public void setValente(float valente) {
		this.valente = valente;
	}

	public float getTempo() {
		return tempo;
	}

	public void setTempo(float tempo) {
		this.tempo = tempo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTrack_href() {
		return track_href;
	}

	public void setTrack_href(String track_href) {
		this.track_href = track_href;
	}

	public String getAnalysis_url() {
		return analysis_url;
	}

	public void setAnalysis_url(String analysis_url) {
		this.analysis_url = analysis_url;
	}

	public int getDuration_ms() {
		return duration_ms;
	}

	public void setDuration_ms(int duration_ms) {
		this.duration_ms = duration_ms;
	}

	public int getTime_signature() {
		return time_signature;
	}

	public void setTime_signature(int time_signature) {
		this.time_signature = time_signature;
	}
}