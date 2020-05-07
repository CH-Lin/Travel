package com.travel.datahandler;

import java.util.LinkedList;

import com.travel.data.BasicSpotData;
import com.travel.filter.DataFilter;

public abstract class DataHandler {
	protected String domain[];
	protected LinkedList<DataFilter> filters;

	public DataHandler(String[] domain) {
		this.domain = domain;
		filters = new LinkedList<DataFilter>();
	}

	public String[] getDomain() {
		return this.domain;
	}

	public void addAnalyser(DataFilter analyzer) {
		filters.add(analyzer);
	}

	public abstract void insertData(BasicSpotData data);
}
