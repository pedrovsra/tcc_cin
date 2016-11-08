package com.pedro.auxiliary;

public enum ChordTemplates {

	C (1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0),
	Cs(0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0),
	D(0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0),
	Ds(0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0),
	E (0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1),
	F (1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0),
	Fs(0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0),
	G (0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1),
	Gs(1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0),
	A (0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0),
	As(0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0),
	B (0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1);

	private double[] chordTemplates;

	private ChordTemplates(double... tmp) {
		this.chordTemplates = tmp;
	}

	public double[] getTemplate() {
		return this.chordTemplates;
	}
}