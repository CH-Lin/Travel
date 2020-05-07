package com.travel.data;

public class SpotType {
	private long id;
	private long spotId;
	private int type;

	public SpotType() {
	}

	public SpotType(long id, long spotId, int type) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.type = type;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Type [id=").append(id).append(", spotId=")
				.append(spotId).append(", type=").append(type).append("]");
		return builder.toString();
	}

}
