package com.travel.data;

public class Next {
	private long id;
	private long spotId;
	private long userId;
	private long next;

	public Next() {
	}

	public Next(long id, long spotId, long userId, long next) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.userId = userId;
		this.next = next;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getNext() {
		return next;
	}

	public void setNext(long next) {
		this.next = next;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Next [id=").append(id).append(", spotId=")
				.append(spotId).append(", userId=").append(userId)
				.append(", next=").append(next).append("]");
		return builder.toString();
	}

}
