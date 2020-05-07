package com.travel.db;

import java.sql.Time;
import java.util.Calendar;

public class TimeGenerator {

	public static Time generateTime(int h) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, h);
		return new Time(c.getTimeInMillis());
	}

}
