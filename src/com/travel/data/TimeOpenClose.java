package com.travel.data;

import java.sql.Time;

public class TimeOpenClose {

	private long id;
	private long spotId;
	private Time timeStart;
	private Time timeEnd;
	private int dayStart;
	private int dayEnd;
	private int monthStart;
	private int monthEnd;
	private String week;
	private String message;

	public TimeOpenClose() {
	}

	public TimeOpenClose(long id, long spotId, Time timeStart, Time timeEnd,
			int dayStart, int dayEnd, int monthStart, int monthEnd,
			String week, String message) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.dayStart = dayStart;
		this.dayEnd = dayEnd;
		this.monthStart = monthStart;
		this.monthEnd = monthEnd;
		this.week = week;
		this.message = message;
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

	public void setSpotId(long spotID) {
		this.spotId = spotID;
	}

	public Time getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}

	public Time getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int getDayStart() {
		return dayStart;
	}

	public void setDayStart(int dayStart) {
		this.dayStart = dayStart;
	}

	public int getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(int dayEnd) {
		this.dayEnd = dayEnd;
	}

	public int getMonthStart() {
		return monthStart;
	}

	public void setMonthStart(int monthStart) {
		this.monthStart = monthStart;
	}

	public int getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(int monthEnd) {
		this.monthEnd = monthEnd;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimeOpenClose [id=").append(id).append(", spotId=")
				.append(spotId).append(", timeStart=").append(timeStart)
				.append(", timeEnd=").append(timeEnd).append(", dayStart=")
				.append(dayStart).append(", dayEnd=").append(dayEnd)
				.append(", monthStart=").append(monthStart)
				.append(", monthEnd=").append(monthEnd).append(", week=")
				.append(week).append(", message=").append(message).append("]");
		return builder.toString();
	}

}
