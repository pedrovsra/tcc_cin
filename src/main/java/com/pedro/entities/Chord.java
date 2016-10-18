package com.pedro.entities;

public class Chord {

	private String name;
	// private Note[] notes;

	public Chord(String name/* , Note[] notes */) {
		super();
		this.name = name;
		// this.notes = notes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public Note[] getNotes() {
	// return notes;
	// }
	//
	// public void setNotes(Note[] notes) {
	// this.notes = notes;
	// }
}