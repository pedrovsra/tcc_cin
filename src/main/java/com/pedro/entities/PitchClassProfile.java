package com.pedro.entities;

public class PitchClassProfile {

	public double[] values;

	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	public PitchClassProfile(double[] values) {
		super();
		this.values = values;
	}
}