package com.pedro.storage;

import java.util.Hashtable;

import com.pedro.entities.Chord;
import com.pedro.interfaces.IDictionary;

public class ChordsDictionaryHash implements IDictionary {

	/*
	 * http://www.michael-thomas.com/music/class/chords_notesinchords.htm
	 * http://www.scales-chords.com/chordid.php
	 * https://www.cs.cmu.edu/~scottd/chords_and_scales/music.html
	 */

	private Hashtable<String, String> chords;

	public ChordsDictionaryHash() {
		this.chords = new Hashtable<String, String>();
	}

	public void addChord(String notes, String chord) {
		this.chords.put(notes, chord);
	}

	public Chord getChordByNotes(String notes) {
		return new Chord(this.chords.get(notes));
	}
}