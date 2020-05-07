package com.travel.data;

import java.sql.Date;
import java.sql.Time;

public class SpecialClosed {

	private long id;
	private long spotId;
	private int repeat;
	private int repeatType;
	private Time timeStart;
	private Time timeEnd;
	private Date dateStart;
	private Date dateEnd;
	private String message;

	public SpecialClosed() {
	}

	public SpecialClosed(long id, long spotId, int repeat, int repeatType,
			Time timeStart, Time timeEnd, Date dateStart, Date dateEnd,
			String message) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.repeat = repeat;
		this.repeatType = repeatType;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
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

	public void setSpotId(long spotId) {
		this.spotId = spotId;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public int getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(int repeatType) {
		this.repeatType = repeatType;
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

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
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
		builder.append("SpecialClosed [id=").append(id).append(", spotId=")
				.append(spotId).append(", repeat=").append(repeat)
				.append(", repeatType=").append(repeatType)
				.append(", timeStart=").append(timeStart).append(", timeEnd=")
				.append(timeEnd).append(", dateStart=").append(dateStart)
				.append(", dateEnd=").append(dateEnd).append(", message=")
				.append(message).append("]");
		return builder.toString();
	}

}
