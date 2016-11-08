package com.pedro.entities;

public class Template {

	private String chord_name;
	private double[] template_vector;

	public Template(String chord_name, double[] template_vector) {
		super();
		this.chord_name = chord_name;
		this.template_vector = template_vector;
	}

	public String getChord_name() {
		return chord_name;
	}

	public void setChord_name(String chord_name) {
		this.chord_name = chord_name;
	}

	public double[] getTemplate_vector() {
		return template_vector;
	}

	public void setTemplate_vector(double[] template_vector) {
		this.template_vector = template_vector;
	}

	public double calculateEmissionProbability(double[] pcp, Template template) {

		double ep = 0;
		double tmp[] = template.template_vector;
		double aux[] = new double[12];

		for (int i = 0; i < 12; i++) {
			aux[i] = pcp[i] * tmp[i];
		}

		for (int i = 0; i < 12; i++)
			ep += aux[i];

		return ep;
	}
}