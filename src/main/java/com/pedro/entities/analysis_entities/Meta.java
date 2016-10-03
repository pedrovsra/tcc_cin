package com.pedro.entities.analysis_entities;

public class Meta {

	/*
	 * "analyzer_version"
	 * "platform"
	 * "detailed_status"
	 * "status_code"
	 * "timestamp"
	 * "analysis_time":
	 * "input_process":
	 */

	private String analyzer_version;
	private String platform;
	private String detailed_status;
	private int timestamp;
	private float analysis_time;
	private String input_process;

	public Meta(String analyzer_version, String platform, String detailed_status, int timestamp, float analysis_time,
			String input_process) {
		super();
		this.analyzer_version = analyzer_version;
		this.platform = platform;
		this.detailed_status = detailed_status;
		this.timestamp = timestamp;
		this.analysis_time = analysis_time;
		this.input_process = input_process;
	}

	public Meta() {
		super();
	}

	public String getAnalyzer_version() {
		return analyzer_version;
	}

	public void setAnalyzer_version(String analyzer_version) {
		this.analyzer_version = analyzer_version;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getDetailed_status() {
		return detailed_status;
	}

	public void setDetailed_status(String detailed_status) {
		this.detailed_status = detailed_status;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public float getAnalysis_time() {
		return analysis_time;
	}

	public void setAnalysis_time(float analysis_time) {
		this.analysis_time = analysis_time;
	}

	public String getInput_process() {
		return input_process;
	}

	public void setInput_process(String input_process) {
		this.input_process = input_process;
	}
}