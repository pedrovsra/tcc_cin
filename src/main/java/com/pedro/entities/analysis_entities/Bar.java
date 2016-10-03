package com.pedro.entities.analysis_entities;

public class Bar {

	private float start;
	private float duration;
	private float confidence;

	public Bar(float start, float duration, float confidence) {
		super();
		this.start = start;
		this.duration = duration;
		this.confidence = confidence;
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
}