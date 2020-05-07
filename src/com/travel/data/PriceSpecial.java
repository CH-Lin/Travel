package com.travel.data;

import java.sql.Date;
import java.sql.Time;

public class PriceSpecial {
	private long id;
	private long spotId;
	private int price;
	private int ageFrom;
	private int ageTo;
	private int numberOfPerson;
	private Time timeStart;
	private Time timeEnd;
	private Date dateStart;
	private Date dateEnd;
	private String spCond;
	private String message;

	public PriceSpecial() {
	}

	public PriceSpecial(long id, long spotId, int price, int ageFrom,
			int ageTo, int numberOfPerson, Time timeStart, Time timeEnd,
			Date dateStart, Date dateEnd, String spCond, String message) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.price = price;
		this.ageFrom = ageFrom;
		this.ageTo = ageTo;
		this.numberOfPerson = numberOfPerson;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.spCond = spCond;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAgeFrom() {
		return ageFrom;
	}

	public void setAgeFrom(int ageFrom) {
		this.ageFrom = ageFrom;
	}

	public int getAgeTo() {
		return ageTo;
	}

	public void setAgeTo(int ageTo) {
		this.ageTo = ageTo;
	}

	public int getNumberOfPerson() {
		return numberOfPerson;
	}

	public void setNumberOfPerson(int numberOfPerson) {
		this.numberOfPerson = numberOfPerson;
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

	public String getSpCond() {
		return spCond;
	}

	public void setSpCond(String spCond) {
		this.spCond = spCond;
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
		builder.append("PriceSpecial [id=").append(id).append(", spotId=")
				.append(spotId).append(", price=").append(price)
				.append(", ageFrom=").append(ageFrom).append(", ageTo=")
				.append(ageTo).append(", numberOfPerson=")
				.append(numberOfPerson).append(", timeStart=")
				.append(timeStart).append(", timeEnd=").append(timeEnd)
				.append(", dateStart=").append(dateStart).append(", dateEnd=")
				.append(dateEnd).append(", spCond=").append(spCond)
				.append(", message=").append(message).append("]");
		return builder.toString();
	}

}
