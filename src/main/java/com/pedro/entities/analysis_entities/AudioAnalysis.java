package com.pedro.entities.analysis_entities;

import java.util.List;

public class AudioAnalysis {

	private Meta meta;
	private Track track;
	private List<Bar> bars;
	private List<Beat> beats;
	private List<Tatum> tatums;
	private List<Section> sections;
	private List<Segment> segments;
	
	public AudioAnalysis() { }

	public AudioAnalysis(Meta meta, Track track, List<Bar> bars, List<Beat> beats, List<Tatum> tatums,
			List<Section> sections, List<Segment> segments) {
		super();
		this.meta = meta;
		this.track = track;
		this.bars = bars;
		this.beats = beats;
		this.tatums = tatums;
		this.sections = sections;
		this.segments = segments;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public List<Bar> getBars() {
		return bars;
	}

	// public Bar getBarAt(int index) {
	// return bars.get(index);
	// }

	public void setBars(List<Bar> bars) {
		this.bars = bars;
	}

	// public void setBarAt(int index, Bar bar) {
	// this.bars.set(index, bar);
	// }

	public List<Beat> getBeats() {
		return beats;
	}

	// public Beat getBeatAt(int index) {
	// return beats.get(index);
	// }

	public void setBeats(List<Beat> beats) {
		this.beats = beats;
	}

	// public void setBeatAt(int index, Beat beat) {
	// this.beats.set(index, beat);
	// }

	public List<Tatum> getTatums() {
		return tatums;
	}

	public void setTatums(List<Tatum> tatums) {
		this.tatums = tatums;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}
}