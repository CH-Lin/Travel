package com.travel.filter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.travel.data.BasicSpotData;

public class DataTypeFilter extends DataFilter {

	public DataTypeFilter(String... areas) {
	}

	@Override
	public void doFilter(BasicSpotData data) {
		System.out.println("TYPE filter");
	}

	public Time stringToTime(String time) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return new Time(sdf.parse(time).getTime());
	}

	public Date stringToDate(String time) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return new Date(sdf.parse(time).getTime());
	}

	public Timestamp stringToTimestamp(String time) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return new Timestamp(sdf.parse(time).getTime());
	}
}
