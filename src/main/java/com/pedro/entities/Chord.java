package com.pedro.entities;

public class Chord {

	private String name;
	private float[] template;
	// private Note[] notes;

	public Chord(String name, float[] template) {
		super();
		this.name = name;
		this.template = template;
		// this.notes = notes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float[] getTemplate() {
		return this.template;
	}

	public void setTemplate(float[] template) {
		this.template = template;
	}
}