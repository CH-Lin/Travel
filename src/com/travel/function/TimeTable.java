package com.travel.function;

import java.io.StringReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Vector;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import com.travel.data.SpecialClosed;
import com.travel.data.SpecialEvent;
import com.travel.data.TimeOpenClose;

public class TimeTable {

	private HashMap<Date, boolean[][]> month;

	public TimeTable() {
		month = new HashMap<Date, boolean[][]>();
	}

	public void add(Date date, boolean[][] data) {
		month.put(date, data);
	}

	public void add(HashMap<Date, boolean[][]> month) {
		month.putAll(month);
	}

	public void add(TimeTable timeTAble) {
		month.putAll(timeTAble.getTimeTables());
	}

	public int size() {
		return month.keySet().size();
	}

	public static TimeTable createByTravelTime(Timestamp start, Timestamp end) {
		TimeTable t_table = new TimeTable();

		Calendar start_c = Calendar.getInstance();
		start_c.setTime(start);

		Calendar end_c = Calendar.getInstance();
		end_c.setTime(end);

		int s_y = start_c.get(Calendar.YEAR);
		int s_m = start_c.get(Calendar.MONTH);
		int e_y = end_c.get(Calendar.YEAR);
		int e_m = end_c.get(Calendar.MONTH);

		t_table.month = new HashMap<Date, boolean[][]>();

		while ((s_y == e_y && s_m <= e_m) || (s_y < e_y)) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, s_y);
			c.set(Calendar.MONTH, s_m);

			boolean[][] timetable = new boolean[c
					.getActualMaximum(Calendar.DAY_OF_MONTH)][24];

			for (int d = 0; d < timetable.length; d++) {
				for (int h = 0; h < timetable[d].length; h++) {

					c.set(Calendar.DAY_OF_MONTH, (d + 1));
					c.set(Calendar.HOUR_OF_DAY, h);
					c.set(Calendar.SECOND, 0);
					if ((d + 1) == start_c.get(Calendar.DAY_OF_MONTH))
						c.set(Calendar.MINUTE, 59);
					else if ((d + 1) == end_c.get(Calendar.DAY_OF_MONTH))
						c.set(Calendar.MINUTE, 30);

					if (c.getTimeInMillis() > start_c.getTimeInMillis()
							&& c.getTimeInMillis() <= end_c.getTimeInMillis()) {
						timetable[d][h] = true;
					}

				}
			}

			t_table.month.put(Date.valueOf(s_y + "-" + (s_m + 1) + "-1"),
					timetable);

			if (++s_m == 12) {
				s_y++;
				s_m = 0;
			}
		}

		return t_table;
	}

	public static TimeTable createBySpotTime(
			Vector<TimeOpenClose> businessHours, Vector<TimeOpenClose> closeds,
			Vector<SpecialEvent> specialEvents,
			Vector<SpecialClosed> specialCloseds, Timestamp start, Timestamp end) {

		TimeTable t_table = new TimeTable();

		Calendar travel_start_c = Calendar.getInstance();
		travel_start_c.setTime(start);

		Calendar travel_end_c = Calendar.getInstance();
		travel_end_c.setTime(end);

		int start_year = travel_start_c.get(Calendar.YEAR);
		int start_month = travel_start_c.get(Calendar.MONTH);
		int end_year = travel_end_c.get(Calendar.YEAR);
		int end_month = travel_end_c.get(Calendar.MONTH);

		t_table.month = new HashMap<Date, boolean[][]>();

		while ((start_year == end_year && start_month <= end_month)
				|| (start_year < end_year)) {

			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, start_year);
			c.set(Calendar.MONTH, start_month);

			boolean[][] timetable = new boolean[c
					.getActualMaximum(Calendar.DAY_OF_MONTH)][24];

			for (TimeOpenClose businessHour : businessHours) {

				if (start_month < businessHour.getMonthStart()
						|| start_month > businessHour.getMonthEnd())
					continue;

				int daystart = businessHour.getDayStart();
				int dayend = businessHour.getDayEnd();

				Calendar business_start_c = Calendar.getInstance();
				business_start_c.setTime(businessHour.getTimeStart());
				Calendar business_end_c = Calendar.getInstance();
				business_end_c.setTime(businessHour.getTimeEnd());

				String weeks[] = businessHour.getWeek().split(",");

				for (int d = daystart; d <= dayend; d++) {
					c.set(Calendar.DAY_OF_WEEK, d);
					for (String w : weeks) {

						c.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(w));
						if (c.get(Calendar.MONTH) != start_month) {
							c.set(Calendar.MONTH, start_month);
							continue;
						}
						for (int h = business_start_c.get(Calendar.HOUR_OF_DAY); h <= business_end_c
								.get(Calendar.HOUR_OF_DAY); h++) {
							c.set(Calendar.HOUR_OF_DAY, h);
							if (c.getTimeInMillis() >= start.getTime()
									&& c.getTimeInMillis() <= end.getTime()) {
								timetable[c.get(Calendar.DAY_OF_MONTH) - 1][h] = true;
							}
						}
					}
				}
			}

			for (SpecialEvent specialEvent : specialEvents) {

				Calendar specail_start_c = Calendar.getInstance();
				specail_start_c.setTime(Timestamp.valueOf(specialEvent
						.getDateStart() + " " + specialEvent.getTimeStart()));

				Calendar specail_end_c = Calendar.getInstance();
				specail_end_c.setTime(Timestamp.valueOf(specialEvent
						.getDateEnd() + " " + specialEvent.getTimeEnd()));

				int specail_start_y = specail_start_c.get(Calendar.YEAR);
				int specail_start_m = specail_start_c.get(Calendar.MONTH);
				int specail_end_y = specail_end_c.get(Calendar.YEAR);
				int specail_end_m = specail_end_c.get(Calendar.MONTH);

				specail_end_m = specail_end_m
						+ (specail_end_y - specail_start_y) * 12;

				for (int m = specail_start_m; m <= specail_end_m; m++) {
					if (m % 12 != start_month
							|| (specail_start_y + m / 12) != start_year)
						continue;

					int start_d = (m == specail_start_m) ? specail_start_c
							.get(Calendar.DAY_OF_MONTH) : 1;

					int end_d = (m == specail_end_m) ? specail_end_c
							.get(Calendar.DAY_OF_MONTH) : c
							.getActualMaximum(Calendar.DAY_OF_MONTH);

					for (int d = start_d; d <= end_d; d++) {
						c.set(Calendar.DAY_OF_MONTH, d);

						for (int h = specail_start_c.get(Calendar.HOUR_OF_DAY); h <= specail_end_c
								.get(Calendar.HOUR_OF_DAY); h++) {
							c.set(Calendar.HOUR_OF_DAY, h);
							if (c.getTimeInMillis() >= start.getTime()
									&& c.getTimeInMillis() <= end.getTime()) {
								timetable[c.get(Calendar.DAY_OF_MONTH) - 1][h] = true;
							}
						}
					}
				}
			}

			for (TimeOpenClose closed : closeds) {

				if (start_month < closed.getMonthStart()
						|| start_month > closed.getMonthEnd())
					continue;

				int daystart = closed.getDayStart();
				int dayend = closed.getDayEnd();

				Calendar start_b_c = Calendar.getInstance();
				start_b_c.setTime(closed.getTimeStart());
				Calendar end_b_c = Calendar.getInstance();
				end_b_c.setTime(closed.getTimeEnd());

				String weeks[] = closed.getWeek().split(",");

				for (int d = daystart; d <= dayend; d++) {
					c.set(Calendar.DAY_OF_WEEK, d);
					for (String w : weeks) {

						c.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(w));
						if (c.get(Calendar.MONTH) != start_month) {
							c.set(Calendar.MONTH, start_month);
							continue;
						}
						for (int h = start_b_c.get(Calendar.HOUR_OF_DAY); h <= end_b_c
								.get(Calendar.HOUR_OF_DAY); h++) {
							c.set(Calendar.HOUR_OF_DAY, h);
							if (c.getTimeInMillis() >= start.getTime()
									&& c.getTimeInMillis() <= end.getTime()) {
								timetable[c.get(Calendar.DAY_OF_MONTH) - 1][h] = false;
							}
						}
					}
				}
			}

			for (SpecialClosed specialClosed : specialCloseds) {

				Calendar specail_start_c = Calendar.getInstance();
				specail_start_c.setTime(Timestamp.valueOf(specialClosed
						.getDateStart() + " " + specialClosed.getTimeStart()));

				Calendar specail_end_c = Calendar.getInstance();
				specail_end_c.setTime(Timestamp.valueOf(specialClosed
						.getDateEnd() + " " + specialClosed.getTimeEnd()));

				int specail_start_y = specail_start_c.get(Calendar.YEAR);
				int specail_start_m = specail_start_c.get(Calendar.MONTH);
				int specail_end_y = specail_end_c.get(Calendar.YEAR);
				int specail_end_m = specail_end_c.get(Calendar.MONTH);

				specail_end_m = specail_end_m
						+ (specail_end_y - specail_start_y) * 12;

				for (int m = specail_start_m; m <= specail_end_m; m++) {
					if (m % 12 != start_month
							|| (specail_start_y + m / 12) != start_year)
						continue;

					int start_d = (m == specail_start_m) ? specail_start_c
							.get(Calendar.DAY_OF_MONTH) : 1;

					int end_d = (m == specail_end_m) ? specail_end_c
							.get(Calendar.DAY_OF_MONTH) : c
							.getActualMaximum(Calendar.DAY_OF_MONTH);

					for (int d = start_d; d <= end_d; d++) {
						c.set(Calendar.DAY_OF_MONTH, d);

						for (int h = specail_start_c.get(Calendar.HOUR_OF_DAY); h <= specail_end_c
								.get(Calendar.HOUR_OF_DAY); h++) {
							c.set(Calendar.HOUR_OF_DAY, h);
							if (c.getTimeInMillis() >= start.getTime()
									&& c.getTimeInMillis() <= end.getTime()) {
								timetable[c.get(Calendar.DAY_OF_MONTH) - 1][h] = false;
							}
						}
					}
				}
			}

			t_table.month.put(
					Date.valueOf(start_year + "-" + (start_month + 1) + "-1"),
					timetable);

			if (++start_month == 12) {
				start_year++;
				start_month = 0;
			}
		}

		return t_table;
	}

	public static TimeTable createBySpotYearMonth(
			Vector<TimeOpenClose> businessHours, Vector<TimeOpenClose> closeds,
			Vector<SpecialEvent> specialEvents,
			Vector<SpecialClosed> specialCloseds, int year, int month) {

		TimeTable t_table = new TimeTable();
		t_table.month = new HashMap<Date, boolean[][]>();

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);

		boolean[][] timetable = new boolean[c
				.getActualMaximum(Calendar.DAY_OF_MONTH)][24];

		for (TimeOpenClose businessHour : businessHours) {

			if (month < businessHour.getMonthStart()
					|| month > businessHour.getMonthEnd())
				continue;

			int daystart = businessHour.getDayStart();
			int dayend = businessHour.getDayEnd();

			Calendar business_start_c = Calendar.getInstance();
			business_start_c.setTime(businessHour.getTimeStart());
			Calendar business_end_c = Calendar.getInstance();
			business_end_c.setTime(businessHour.getTimeEnd());

			String weeks[] = businessHour.getWeek().split(",");

			for (int d = daystart; d <= dayend; d++) {
				c.set(Calendar.DAY_OF_WEEK, d);
				for (String w : weeks) {

					c.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(w));
					if (c.get(Calendar.MONTH) != month) {
						c.set(Calendar.MONTH, month);
						continue;
					}
					for (int h = business_start_c.get(Calendar.HOUR_OF_DAY); h <= business_end_c
							.get(Calendar.HOUR_OF_DAY); h++) {
						c.set(Calendar.HOUR_OF_DAY, h);
						timetable[c.get(Calendar.DAY_OF_MONTH) - 1][h] = true;
					}
				}
			}
		}

		for (SpecialEvent specialEvent : specialEvents) {

			Calendar specail_start_c = Calendar.getInstance();
			specail_start_c.setTime(Timestamp.valueOf(specialEvent
					.getDateStart() + " " + specialEvent.getTimeStart()));

			Calendar specail_end_c = Calendar.getInstance();
			specail_end_c.setTime(Timestamp.valueOf(specialEvent.getDateEnd()
					+ " " + specialEvent.getTimeEnd()));

			int specail_start_y = specail_start_c.get(Calendar.YEAR);
			int specail_start_m = specail_start_c.get(Calendar.MONTH);
			int specail_end_y = specail_end_c.get(Calendar.YEAR);
			int specail_end_m = specail_end_c.get(Calendar.MONTH);

			specail_end_m = specail_end_m + (specail_end_y - specail_start_y)
					* 12;

			for (int m = specail_start_m; m <= specail_end_m; m++) {
				if (m % 12 != month || (specail_start_y + m / 12) != year)
					continue;

				int start_d = (m == specail_start_m) ? specail_start_c
						.get(Calendar.DAY_OF_MONTH) : 1;

				int end_d = (m == specail_end_m) ? specail_end_c
						.get(Calendar.DAY_OF_MONTH) : c
						.getActualMaximum(Calendar.DAY_OF_MONTH);

				for (int d = start_d; d <= end_d; d++) {
					c.set(Calendar.DAY_OF_MONTH, d);

					for (int h = specail_start_c.get(Calendar.HOUR_OF_DAY); h <= specail_end_c
							.get(Calendar.HOUR_OF_DAY); h++) {
						c.set(Calendar.HOUR_OF_DAY, h);
						timetable[c.get(Calendar.DAY_OF_MONTH) - 1][h] = true;
					}
				}
			}
		}

		for (TimeOpenClose closed : closeds) {

			if (month < closed.getMonthStart() || month > closed.getMonthEnd())
				continue;

			int daystart = closed.getDayStart();
			int dayend = closed.getDayEnd();

			Calendar start_b_c = Calendar.getInstance();
			start_b_c.setTime(closed.getTimeStart());
			Calendar end_b_c = Calendar.getInstance();
			end_b_c.setTime(closed.getTimeEnd());

			String weeks[] = closed.getWeek().split(",");

			for (int d = daystart; d <= dayend; d++) {
				c.set(Calendar.DAY_OF_WEEK, d);
				for (String w : weeks) {

					c.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(w));
					if (c.get(Calendar.MONTH) != month) {
						c.set(Calendar.MONTH, month);
						continue;
					}
					for (int h = start_b_c.get(Calendar.HOUR_OF_DAY); h <= end_b_c
							.get(Calendar.HOUR_OF_DAY); h++) {
						c.set(Calendar.HOUR_OF_DAY, h);
						timetable[c.get(Calendar.DAY_OF_MONTH) - 1][h] = false;
					}
				}
			}
		}

		for (SpecialClosed specialclosed : specialCloseds) {

			Calendar specail_start_c = Calendar.getInstance();
			specail_start_c.setTime(Timestamp.valueOf(specialclosed
					.getDateStart() + " " + specialclosed.getTimeStart()));

			Calendar specail_end_c = Calendar.getInstance();
			specail_end_c.setTime(Timestamp.valueOf(specialclosed.getDateEnd()
					+ " " + specialclosed.getTimeEnd()));

			int specail_start_y = specail_start_c.get(Calendar.YEAR);
			int specail_start_m = specail_start_c.get(Calendar.MONTH);
			int specail_end_y = specail_end_c.get(Calendar.YEAR);
			int specail_end_m = specail_end_c.get(Calendar.MONTH);

			specail_end_m = specail_end_m + (specail_end_y - specail_start_y)
					* 12;

			for (int m = specail_start_m; m <= specail_end_m; m++) {
				if (m % 12 != month || (specail_start_y + m / 12) != year)
					continue;

				int start_d = (m == specail_start_m) ? specail_start_c
						.get(Calendar.DAY_OF_MONTH) : 1;

				int end_d = (m == specail_end_m) ? specail_end_c
						.get(Calendar.DAY_OF_MONTH) : c
						.getActualMaximum(Calendar.DAY_OF_MONTH);

				for (int d = start_d; d <= end_d; d++) {
					c.set(Calendar.DAY_OF_MONTH, d);

					for (int h = specail_start_c.get(Calendar.HOUR_OF_DAY); h <= specail_end_c
							.get(Calendar.HOUR_OF_DAY); h++) {
						c.set(Calendar.HOUR_OF_DAY, h);
						timetable[c.get(Calendar.DAY_OF_MONTH) - 1][h] = false;
					}
				}
			}
		}

		t_table.month.put(Date.valueOf(year + "-" + (month + 1) + "-1"),
				timetable);

		return t_table;
	}

	public boolean compare(TimeTable table) {

		if (this.month.keySet().size() != table.month.keySet().size())
			return false;

		for (Date s : this.month.keySet()) {
			if (table.month.get(s) == null)
				return false;

			boolean t1[][] = this.month.get(s);
			boolean t2[][] = table.month.get(s);

			for (int i = 0; i < t1.length; i++) {
				for (int j = 0; j < t1[i].length; j++) {
					if (t1[i][j] != t2[i][j])
						return false;
				}
			}
		}

		return true;
	}

	public HashMap<Date, boolean[][]> getTimeTables() {
		return month;
	}

	public HashMap<Date, String> getTimeTableJson() {
		HashMap<Date, String> result = new HashMap<Date, String>();
		for (Date s : this.month.keySet()) {
			result.put(s, toJsonString(this.month.get(s)));
		}
		return result;
	}

	private String toJsonString(boolean t[][]) {
		JsonObjectBuilder dayArray = Json.createObjectBuilder();
		for (int i = 0; i < t.length; i++) {

			JsonArrayBuilder hourArray = Json.createArrayBuilder();
			for (int j = 0; j < t[i].length; j++) {
				hourArray.add(t[i][j]);
			}
			dayArray.add("" + i, hourArray);
		}

		return dayArray.build().toString();
	}

	public boolean[][] toTimeTableArray(String data) {
		boolean table[][];

		JsonReader jsonReader = Json.createReader(new StringReader(data));
		JsonObject object = jsonReader.readObject();

		table = new boolean[object.size()][];

		for (String d : object.keySet()) {
			table[Integer.parseInt(d)] = new boolean[object.getJsonArray(d)
					.size()];

			int i = 0;
			for (JsonValue h : object.getJsonArray(d)) {
				table[Integer.parseInt(d)][i] = Boolean.parseBoolean(h
						.toString());
				i++;
			}
		}

		return table;
	}

	public String toString() {

		StringBuffer str = new StringBuffer();
		for (Date m : this.month.keySet()) {
			str.append(m.toString()).append("\n");
			boolean table[][] = month.get(m);
			for (int d = 0; d < table.length; d++) {
				str.append(String.format("D=%1$2d", (d + 1)));
				for (int h = 0; h < table[d].length; h++) {
					str.append(String
							.format(" [H=%1$2d %2$5s]", h, table[d][h]));
					if (h == 12)
						str.append(String.format(" D=%1$2d", (d + 1)));
				}
				str.append("\n");
			}
		}

		return str.toString();
	}
}
