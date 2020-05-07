package com.travel.data;

public class City {

	private long id;
	private String language;
	private String country;
	private String area;
	private String subarea;
	private String prefecture;

	public City() {
	}

	public City(long id, String language, String country, String area,
			String subarea, String prefecture) {
		super();
		this.id = id;
		this.language = language;
		this.country = country;
		this.area = area;
		this.subarea = subarea;
		this.prefecture = prefecture;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSubarea() {
		return subarea;
	}

	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", language=" + language + ", country="
				+ country + ", area=" + area + ", subarea=" + subarea
				+ ", prefecture=" + prefecture + "]";
	}

}
