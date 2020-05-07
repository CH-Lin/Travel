package com.travel.data;

import java.sql.Time;

public class AccessInfo {

	private long id;
	private long spotId;
	private long fromId;
	private int transportType;
	private Time timeDeparture;
	private Time timeNecessary;
	private String information;

	public AccessInfo() {
	}

	public AccessInfo(long id, long spotID, long fromID, int transportType,
			Time timeDeparture, Time timeNecessary, String information) {
		super();
		this.id = id;
		this.spotId = spotID;
		this.fromId = fromID;
		this.transportType = transportType;
		this.timeDeparture = timeDeparture;
		this.timeNecessary = timeNecessary;
		this.information = information;
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

	public long getFromId() {
		return fromId;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}

	public int getTransportType() {
		return transportType;
	}

	public void setTransportType(int transportType) {
		this.transportType = transportType;
	}

	public Time getTimeDeparture() {
		return timeDeparture;
	}

	public void setTimeDeparture(Time timeDeparture) {
		this.timeDeparture = timeDeparture;
	}

	public Time getTimeNecessary() {
		return timeNecessary;
	}

	public void setTimeNecessary(Time timeNecessary) {
		this.timeNecessary = timeNecessary;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Access [id=").append(id).append(", spotId=")
				.append(spotId).append(", fromId=").append(fromId)
				.append(", transportType=").append(transportType)
				.append(", timeDeparture=").append(timeDeparture)
				.append(", timeNecessary=").append(timeNecessary)
				.append(", information=").append(information).append("]");
		return builder.toString();
	}

}
