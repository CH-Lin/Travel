package com.travel.data;

public class Spot {

	private long spotId;
	private String name;
	private String country;
	private String address;
	private String type;
	private boolean checked;
	private String source;

	public Spot() {
	}

	public Spot(long spotId, String name, String country, String address,
			String type, boolean checked, String source) {
		super();
		this.spotId = spotId;
		this.name = name;
		this.country = country;
		this.address = address;
		this.type = type;
		this.checked = checked;
		this.source = source;
	}

	public long getSpotId() {
		return spotId;
	}

	public void setSpotId(long spotId) {
		this.spotId = spotId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Spot [spotId=").append(spotId).append(", name=")
				.append(name).append(", country=").append(country)
				.append(", address=").append(address).append(", type=")
				.append(type).append(", checked=").append(checked)
				.append(", source=").append(source).append("]");
		return builder.toString();
	}

}
