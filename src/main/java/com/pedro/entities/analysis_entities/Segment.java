package com.pedro.entities.analysis_entities;

public class Segment {

	private float start;
	private float duration;
	private float confidence;
	private float loudness_start;
	private float loudness_max_time;
	private float loudness_max;
	private float[] pitches;
	private float[] timbre;

	public Segment(float start, float duration, float confidence, float loudness_start, float loudness_max_time,
			float loudness_max, float[] pitches, float[] timbre) {
		super();
		this.start = start;
		this.duration = duration;
		this.confidence = confidence;
		this.loudness_start = loudness_start;
		this.loudness_max_time = loudness_max_time;
		this.loudness_max = loudness_max;
		this.pitches = pitches;
		this.timbre = timbre;
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

	public float getLoudness_start() {
		return loudness_start;
	}

	public void setLoudness_start(float loudness_start) {
		this.loudness_start = loudness_start;
	}

	public float getLoudness_max_time() {
		return loudness_max_time;
	}

	public void setLoudness_max_time(float loudness_max_time) {
		this.loudness_max_time = loudness_max_time;
	}

	public float getLoudness_max() {
		return loudness_max;
	}

	public void setLoudness_max(float loudness_max) {
		this.loudness_max = loudness_max;
	}

	public float[] getPitches() {
		return pitches;
	}

	public float getPitchAt(int index) {
		return pitches[index];
	}

	public void setPitches(float[] pitches) {
		this.pitches = pitches;
	}

	public void setPitchAt(int index, int value) {
		this.pitches[index] = value;
	}

	public float[] getTimbre() {
		return timbre;
	}

	public float getTimbreAt(int index) {
		return timbre[index];
	}

	public void setTimbre(float[] timbre) {
		this.timbre = timbre;
	}

	public void setTimbreAt(int index, int value) {
		this.timbre[index] = value;
	}
}