package com.travel.data;

public class Access {

	private long id;
	private long fromId;
	private long toId;
	private int transportType;
	private int duration;
	private int distance;
	private String result;

	public Access() {
	}

	public Access(long id, long fromId, long toId, int transportType,
			int duration, int distance, String result) {
		super();
		this.id = id;
		this.fromId = fromId;
		this.toId = toId;
		this.transportType = transportType;
		this.duration = duration;
		this.distance = distance;
		this.result = result;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFromId() {
		return fromId;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}

	public long getToId() {
		return toId;
	}

	public void setToId(long toId) {
		this.toId = toId;
	}

	public int getTransportType() {
		return transportType;
	}

	public void setTransportType(int transportType) {
		this.transportType = transportType;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
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
		builder.append("Access [id=").append(id).append(", fromId=")
				.append(fromId).append(", toId=").append(toId)
				.append(", transportType=").append(transportType)
				.append(", duration=").append(duration).append(", distance=")
				.append(distance).append(", result=").append(result)
				.append("]");
		return builder.toString();
	}

}
