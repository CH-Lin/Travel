package com.travel.data;

public class Picture {

	private long id;
	private long userId;
	private long spotId;
	private String fileName;
	private int sizeX;
	private int sizeY;
	private String message;

	public Picture() {
	}

	public Picture(long id, long userId, long spotId, String fileName,
			int sizeX, int sizeY, String message) {
		super();
		this.id = id;
		this.userId = userId;
		this.spotId = spotId;
		this.fileName = fileName;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Picture [id=" + id + ", userId=" + userId + ", spotId="
				+ spotId + ", fileName=" + fileName + ", sizeX=" + sizeX
				+ ", sizeY=" + sizeY + ", message=" + message + "]";
	}
}
