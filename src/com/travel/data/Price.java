package com.travel.data;

public class Price {
	private long id;
	private long spotId;
	private int price;
	private int ageFrom;
	private int ageTo;
	private int numberOfPerson;
	private String spCond;
	private String message;

	public Price() {
	}

	public Price(long id, long spotId, int price, int ageFrom, int ageTo,
			int numberOfPerson, String spCond, String message) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.price = price;
		this.ageFrom = ageFrom;
		this.ageTo = ageTo;
		this.numberOfPerson = numberOfPerson;
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
		builder.append("Price [id=").append(id).append(", spotId=")
				.append(spotId).append(", price=").append(price)
				.append(", ageFrom=").append(ageFrom).append(", ageTo=")
				.append(ageTo).append(", numberOfPerson=")
				.append(numberOfPerson).append(", spCond=").append(spCond)
				.append(", message=").append(message).append("]");
		return builder.toString();
	}

}
