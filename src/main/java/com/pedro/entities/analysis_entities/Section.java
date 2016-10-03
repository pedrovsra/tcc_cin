package com.pedro.entities.analysis_entities;

public class Section {

	private float start;
	private float duration;
	private float confidence;
	private float loudness;
	private float tempo;
	private float tempo_confidence;
	private int key;
	private float key_confidence;
	private int mode;
	private float mode_confidence;
	private int time_signature;
	private float time_signature_confidence;

	public Section(float start, float duration, float confidence, float loudness, float tempo, float tempo_confidence,
			int key, float key_confidence, int mode, float mode_confidence, int time_signature,
			float time_signature_confidence) {
		super();
		this.start = start;
		this.duration = duration;
		this.confidence = confidence;
		this.loudness = loudness;
		this.tempo = tempo;
		this.tempo_confidence = tempo_confidence;
		this.key = key;
		this.key_confidence = key_confidence;
		this.mode = mode;
		this.mode_confidence = mode_confidence;
		this.time_signature = time_signature;
		this.time_signature_confidence = time_signature_confidence;
	}

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getConfidence() {
		return confidence;
	}

	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}

	public float getLoudness() {
		return loudness;
	}

	public void setLoudness(float loudness) {
		this.loudness = loudness;
	}

	public float getTempo() {
		return tempo;
	}

	public void setTempo(float tempo) {
		this.tempo = tempo;
	}

	public float getTempo_confidence() {
		return tempo_confidence;
	}

	public void setTempo_confidence(float tempo_confidence) {
		this.tempo_confidence = tempo_confidence;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public float getKey_confidence() {
		return key_confidence;
	}

	public void setKey_confidence(float key_confidence) {
		this.key_confidence = key_confidence;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public float getMode_confidence() {
		return mode_confidence;
	}

	public void setMode_confidence(float mode_confidence) {
		this.mode_confidence = mode_confidence;
	}

	public int getTime_signature() {
		return time_signature;
	}

	public void setTime_signature(int time_signature) {
		this.time_signature = time_signature;
	}

	public float getTime_signature_confidence() {
		return time_signature_confidence;
	}

	public void setTime_signature_confidence(float time_signature_confidence) {
		this.time_signature_confidence = time_signature_confidence;
	}
}