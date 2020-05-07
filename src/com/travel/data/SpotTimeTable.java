package com.travel.data;

import java.sql.Date;

public class SpotTimeTable {

	private long id;
	private long spotId;
	private Date yearMonth;
	private String data;

	public SpotTimeTable() {
	}

	public SpotTimeTable(long id, long spotId, Date yearMonth, String data) {
		super();
		this.id = id;
		this.spotId = spotId;
		this.yearMonth = yearMonth;
		this.data = data;
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

	public Date getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpotTimeTable [id=").append(id).append(", spotId=")
				.append(spotId).append(", yearMonth=").append(yearMonth)
				.append(", data=").append(data).append("]");
		return builder.toString();
	}

}
