package com.travel.data;

public class Counter {

	private String id;
	private long gpsOffset;
	private int gpsCount;
	private long gpsTime;
	private int accessDayCount;
	private long accessDayTime;
	private int accessSecCount;
	private long accessSecTime;

	public Counter() {
	}

	public Counter(String id, long gpsOffset, int gpsCount, long gpsTime,
			int accessDayCount, long accessDayTime, int accessSecCount,
			long accessSecTime) {
		super();
		this.id = id;
		this.gpsOffset = gpsOffset;
		this.gpsCount = gpsCount;
		this.gpsTime = gpsTime;
		this.accessDayCount = accessDayCount;
		this.accessDayTime = accessDayTime;
		this.accessSecCount = accessSecCount;
		this.accessSecTime = accessSecTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getGpsOffset() {
		return gpsOffset;
	}

	public void setGpsOffset(long gpsOffset) {
		this.gpsOffset = gpsOffset;
	}

	public int getGpsCount() {
		return gpsCount;
	}

	public void setGpsCount(int gpsCount) {
		this.gpsCount = gpsCount;
	}

	public long getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(long gpsTime) {
		this.gpsTime = gpsTime;
	}

	public int getAccessDayCount() {
		return accessDayCount;
	}

	public void setAccessDayCount(int accessDayCount) {
		this.accessDayCount = accessDayCount;
	}

	public long getAccessDayTime() {
		return accessDayTime;
	}

	public void setAccessDayTime(long accessDayTime) {
		this.accessDayTime = accessDayTime;
	}

	public int getAccessSecCount() {
		return accessSecCount;
	}

	public void setAccessSecCount(int accessSecCount) {
		this.accessSecCount = accessSecCount;
	}

	public long getAccessSecTime() {
		return accessSecTime;
	}

	public void setAccessSecTime(long accessSecTime) {
		this.accessSecTime = accessSecTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Counter [id=").append(id).append(", gpsOffset=")
				.append(gpsOffset).append(", gpsCount=").append(gpsCount)
				.append(", gpsTime=").append(gpsTime)
				.append(", accessDayCount=").append(accessDayCount)
				.append(", accessDayTime=").append(accessDayTime)
				.append(", accessSecCount=").append(accessSecCount)
				.append(", accessSecTime=").append(accessSecTime).append("]");
		return builder.toString();
	}

}
