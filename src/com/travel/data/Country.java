package com.travel.data;

public class Country {

	private long id;
	private String language;
	private String country;

	public Country() {
	}

	public Country(long id, String language, String country) {
		super();
		this.id = id;
		this.language = language;
		this.country = country;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", language=" + language + ", country="
				+ country + "]";
	}

}
