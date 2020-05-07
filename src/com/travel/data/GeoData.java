package com.travel.data;

public class GeoData {

	private String address;
	private double latitude;
	private double longitude;
	private String result;

	public GeoData() {
	}

	public GeoData(String address, double latitude, double longitude,
			String result) {
		super();
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.result = result;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GeoData [address=").append(address)
				.append(", latitude=").append(latitude).append(", longitude=")
				.append(longitude).append(", result=").append(result)
				.append("]");
		return builder.toString();
	}

}
