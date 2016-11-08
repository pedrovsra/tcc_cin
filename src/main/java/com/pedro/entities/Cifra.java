package com.pedro.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Cifra {

	private Document html;

	private String tom;

	private List<String> acordes;

	private Map<String, Integer> mapChords;

	private Map<String, Double> chordInitialProbabilities;

	public Cifra(Document html) {
		this.html = html;
		this.acordes = new ArrayList<String>();
		this.mapChords = new HashMap<String, Integer>();
		this.chordInitialProbabilities = new HashMap<String, Double>();
		this.getChords();
		this.initChords();
		this.initialProbabilities();
		this.printChords();
		this.printProbabilities();
	}

	private void getChords() {
		Element pre = this.html.select("pre").first();

		ArrayList<Element> arr = pre.getElementsByTag("b");
		for (int i = 0; i < arr.size(); i++) {
			this.acordes.add(arr.get(i).html());
		}
	}

	private void printChords() {
		for (Map.Entry<String, Integer> entry : this.mapChords.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}

	private void printProbabilities() {
		for (Map.Entry<String, Double> entry : this.chordInitialProbabilities.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}

	public void initChords() {
		for (int i = 0; i < this.acordes.size(); i++) {
			if (this.mapChords.containsKey(this.acordes.get(i))) {
				this.mapChords.put(this.acordes.get(i), this.mapChords.get(this.acordes.get(i)) + 1);
			} else {
				this.mapChords.put(this.acordes.get(i), new Integer(1));
			}
		}
	}

	public void initialProbabilities() {
		double total = this.getNumChords();

		for (Map.Entry<String, Integer> entry : this.mapChords.entrySet()) {
			this.chordInitialProbabilities.put(entry.getKey(), new Double(entry.getValue().doubleValue() / total));
		}
	}

	public Map<String, Integer> getMapChords() {
		return mapChords;
	}

	public void setMapChords(Map<String, Integer> mapChords) {
		this.mapChords = mapChords;
	}

	public Map<String, Double> getChordInitialProbabilities() {
		return chordInitialProbabilities;
	}

	public void setChordInitialProbabilities(Map<String, Double> chordInitialProbabilities) {
		this.chordInitialProbabilities = chordInitialProbabilities;
	}

	public int getNumChords() {
		return this.acordes.size();
	}

	public int qtdByChord(String chord) {
		return this.mapChords.get(chord).intValue();
	}

	public Document getHtml() {
		return html;
	}

	public void setHtml(Document html) {
		this.html = html;
	}

	public String getTom() {
		return tom;
	}

	public void setTom(String tom) {
		this.tom = tom;
	}

	public List<String> getAcordes() {
		return acordes;
	}

	public void setAcordes(List<String> acordes) {
		this.acordes = acordes;
	}
}