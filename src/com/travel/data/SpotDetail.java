package com.travel.data;

public class SpotDetail {

	private long id;
	private long spotId;
	private String address2;
	private String place;
	private String info;
	private String tel;
	private boolean parking;
	private String url;

	public SpotDetail() {
	}

	public SpotDetail(long id, long spotId, String address2, String place,
			String info, String tel, boolean parking, String url) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.address2 = address2;
		this.place = place;
		this.info = info;
		this.tel = tel;
		this.parking = parking;
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSpotId() {
		return spotId;
	}

	public void setSpotId(long spotId) {
		this.spotId = spotId;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpotDetail [id=").append(id).append(", spotId=")
				.append(spotId).append(", address2=").append(address2)
				.append(", place=").append(place).append(", info=")
				.append(info).append(", tel=").append(tel).append(", parking=")
				.append(parking).append(", url=").append(url).append("]");
		return builder.toString();
	}

}
