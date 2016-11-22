package com.pedro.auxiliary;

import com.pedro.entities.Chord;

public enum ChordTemplates {
	
	// https://www.cs.cmu.edu/~scottd/chords_and_scales/music.html
	// http://www.michael-thomas.com/music/class/chords_notesinchords.htm
	
	// MAJOR CHORDS
	// C  C# D  D# E  F  F# G  G# A  A# B
	C (1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0),
	Cs(0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0),
	D (0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0),
	Ds(0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0),
	E (0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1),
	F (1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0),
	Fs(0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0),
	G (0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1),
	Gs(1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0),
	A (0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0),
	As(0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0),
	B (0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1),
	
	// MINOR CHORDS
	//  C  C# D  D# E  F  F# G  G# A  A# B
	Cm (1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0),
	Csm(0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0),
	Dm (0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0),
	Dsm(0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0),
	Em (0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1),
	Fm (1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0),
	Fsm(0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
	Gm (0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0),
	Gsm(0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1),
	Am (1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0),
	Asm(0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0),
	Bm (0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1),
	
	// 7th CHORDS
	//  C  C# D  D# E  F  F# G  G# A  A# B
	C7 (1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0),
	Cs7(0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1),
	D7 (1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0),
	Ds7(0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0),
	E7 (0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1),
	F7 (1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0),
	Fs7(0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0),
	G7 (0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1),
	Gs7(1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0),
	A7 (0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0),
	As7(0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0),
	B7 (0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1),
	
	// 5th CHORDS
	//  C  C# D  D# E  F  F# G  G# A  A# B
	C5 (1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0),
	Cs5(0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
	D5 (0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0),
	Ds5(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0),
	E5 (0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1),
	F5 (1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
	Fs5(0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
	G5 (0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0),
	Gs5(0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0),
	A5 (0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0),
	As5(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0),
	B5 (0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
	

	private float[] chordTemplates;

	private ChordTemplates(float... tmp) {
		this.chordTemplates = tmp;
	}

	public float[] getTemplate() {
		return this.chordTemplates;
	}
}