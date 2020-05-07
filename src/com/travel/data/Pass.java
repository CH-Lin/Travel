package com.travel.data;

public class Pass {
	private long id;
	private long spotId;
	private String name;

	public Pass() {
	}

	public Pass(long id, long spotId, String name) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pass [id=").append(id).append(", spotId=")
				.append(spotId).append(", name=").append(name).append("]");
		return builder.toString();
	}

}
