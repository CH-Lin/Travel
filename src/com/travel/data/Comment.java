package com.travel.data;

import java.sql.Time;
import java.sql.Timestamp;

public class Comment {

	private long id;
	private long userId;
	private long spotId;
	private int ranking;
	private Timestamp postTime;
	private Time timeNecessary;
	private String message;

	public Comment() {
	}

	public Comment(long id, long userId, long spotId, int ranking,
			Timestamp postTime, Time timeNecessary, String message) {
		super();
		this.id = id;
		this.userId = userId;
		this.spotId = spotId;
		this.ranking = ranking;
		this.postTime = postTime;
		this.timeNecessary = timeNecessary;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getSpotId() {
		return spotId;
	}

	public void setSpotId(long spotId) {
		this.spotId = spotId;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public Timestamp getPostTime() {
		return postTime;
	}

	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}

	public Time getTimeNecessary() {
		return timeNecessary;
	}

	public void setTimeNecessary(Time timeNecessary) {
		this.timeNecessary = timeNecessary;
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
		builder.append("Comment [id=").append(id).append(", userId=")
				.append(userId).append(", spotId=").append(spotId)
				.append(", ranking=").append(ranking).append(", postTime=")
				.append(postTime).append(", timeNecessary=")
				.append(timeNecessary).append(", message=").append(message)
				.append("]");
		return builder.toString();
	}

}
