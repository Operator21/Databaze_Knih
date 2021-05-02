package com.standarts.databazeknih;

public class Kniha {

	String nazev;
	int ps;
	long id;
	String autor;

	public Kniha(long id, String nazev, int pocetStran, String autor) {
		this.nazev = nazev;
		this.ps = pocetStran;
		this.id = id;
		this.autor = autor;
	}

	public String getNazev() {
		return nazev;
	}

	public int getStrany() {
		return ps;
	}

	public long getId() {
		return id;
	}

	public String getAutor() {
		return autor;
	}
}
