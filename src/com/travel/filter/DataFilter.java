package com.travel.filter;

import com.travel.data.BasicSpotData;

public abstract class DataFilter {

	public DataFilter(String... areas) {
	}

	public abstract void doFilter(BasicSpotData data);
}
