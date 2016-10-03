package com.pedro.entities.analysis_entities;

public class Track {
	
	/*"num_samples"
        "duration"
        "sample_md5"
        "offset_seconds"
        "window_seconds"
        "analysis_sample_rate"
        "analysis_channels"
        "end_of_fade_in"
        "start_of_fade_out"
        "loudness"
        "tempo"
        "tempo_confidence"
        "time_signature"
        "time_signature_confidence"
        "key"
        "key_confidence"
        "mode"
        "mode_confidence"
        "codestring":
        "code_version"
        "echoprintstring"
        "echoprint_version"
        "synchstring"
        "synch_version"
        "rhythmstring"
        "rhythm_version"
        */

	private int num_samples;
	private float duration;
	private String sample_md5;
	private int offset_seconds;
	private int window_seconds;
	private int analysis_sample_rate;
	private int analysis_channels;
	private float end_of_fade_in;
	private float start_of_fade_out;
	private float loudness;
	private float tempo;
	private float tempo_confidence;
	private int time_signature;
	private float time_signature_confidence;
	private int key;
	private float key_confidence;
	private int mode;
	private float mode_confidence;
	private String codestring;
	private String code_version;
	private String echoprintstring;
	private String echoprint_version;
	private String synchstring;
	private String synch_version;
	private String rhythmstring;
	private String rhythm_version;
	
	public Track() { }

	public Track(int num_samples, float duration, String sample_md5, int offset_seconds,
			int window_seconds, int analysis_sample_rate, int analysis_channels, float end_of_fade_in,
			float start_of_fade_out, float loudness, float tempo, float tempo_confidence, int time_signature,
			float time_signature_confidence, int key, float key_confidence, int mode, float mode_confidence,
			String codestring, String code_version, String echoprintstring, String echoprint_version,
			String synchstring, String synch_version, String rhythmstring, String rhythm_version) {
		super();
		this.num_samples = num_samples;
		this.duration = duration;
		this.sample_md5 = sample_md5;
		this.offset_seconds = offset_seconds;
		this.window_seconds = window_seconds;
		this.analysis_sample_rate = analysis_sample_rate;
		this.analysis_channels = analysis_channels;
		this.end_of_fade_in = end_of_fade_in;
		this.start_of_fade_out = start_of_fade_out;
		this.loudness = loudness;
		this.tempo = tempo;
		this.tempo_confidence = tempo_confidence;
		this.time_signature = time_signature;
		this.time_signature_confidence = time_signature_confidence;
		this.key = key;
		this.key_confidence = key_confidence;
		this.mode = mode;
		this.mode_confidence = mode_confidence;
		this.codestring = codestring;
		this.code_version = code_version;
		this.echoprintstring = echoprintstring;
		this.echoprint_version = echoprint_version;
		this.synchstring = synchstring;
		this.synch_version = synch_version;
		this.rhythmstring = rhythmstring;
		this.rhythm_version = rhythm_version;
	}

	public int getNum_samples() {
		return num_samples;
	}

	public void setNum_samples(int num_samples) {
		this.num_samples = num_samples;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public String getSample_md5() {
		return sample_md5;
	}

	public void setSample_md5(String sample_md5) {
		this.sample_md5 = sample_md5;
	}

	public int getOffset_seconds() {
		return offset_seconds;
	}

	public void setOffset_seconds(int offset_seconds) {
		this.offset_seconds = offset_seconds;
	}

	public int getWindow_seconds() {
		return window_seconds;
	}

	public void setWindow_seconds(int window_seconds) {
		this.window_seconds = window_seconds;
	}

	public int getAnalysis_sample_rate() {
		return analysis_sample_rate;
	}

	public void setAnalysis_sample_rate(int analysis_sample_rate) {
		this.analysis_sample_rate = analysis_sample_rate;
	}

	public int getAnalysis_channels() {
		return analysis_channels;
	}

	public void setAnalysis_channels(int analysis_channels) {
		this.analysis_channels = analysis_channels;
	}

	public float getEnd_of_fade_in() {
		return end_of_fade_in;
	}

	public void setEnd_of_fade_in(float end_of_fade_in) {
		this.end_of_fade_in = end_of_fade_in;
	}

	public float getStart_of_fade_out() {
		return start_of_fade_out;
	}

	public void setStart_of_fade_out(float start_of_fade_out) {
		this.start_of_fade_out = start_of_fade_out;
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

	public String getCodestring() {
		return codestring;
	}

	public void setCodestring(String codestring) {
		this.codestring = codestring;
	}

	public String getCode_version() {
		return code_version;
	}

	public void setCode_version(String code_version) {
		this.code_version = code_version;
	}

	public String getEchoprintstring() {
		return echoprintstring;
	}

	public void setEchoprintstring(String echoprintstring) {
		this.echoprintstring = echoprintstring;
	}

	public String getEchoprint_version() {
		return echoprint_version;
	}

	public void setEchoprint_version(String echoprint_version) {
		this.echoprint_version = echoprint_version;
	}

	public String getSynchstring() {
		return synchstring;
	}

	public void setSynchstring(String synchstring) {
		this.synchstring = synchstring;
	}

	public String getSynch_version() {
		return synch_version;
	}

	public void setSynch_version(String synch_version) {
		this.synch_version = synch_version;
	}

	public String getRhythmstring() {
		return rhythmstring;
	}

	public void setRhythmstring(String rhythmstring) {
		this.rhythmstring = rhythmstring;
	}

	public String getRhythm_version() {
		return rhythm_version;
	}

	public void setRhythm_version(String rhythm_version) {
		this.rhythm_version = rhythm_version;
	}
}