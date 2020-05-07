package com.travel.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.travel.data.Access;
import com.travel.data.AccessInfo;
import com.travel.data.BasicSpotData;
import com.travel.data.City;
import com.travel.data.Comment;
import com.travel.data.Counter;
import com.travel.data.Country;
import com.travel.data.GeoData;
import com.travel.data.SpotType;
import com.travel.data.Price;
import com.travel.data.PriceSpecial;
import com.travel.data.SpecialClosed;
import com.travel.data.SpecialEvent;
import com.travel.data.Spot;
import com.travel.data.SpotDetail;
import com.travel.data.SpotTimeTable;
import com.travel.data.TimeOpenClose;
import com.travel.datahandler.DatabaseDataHandler;
import com.travel.function.TimeTable;

public class DatabaseImport {

	public static Logger log = Logger.getLogger(DatabaseImport.class);

	public void importData(String file) {
		log.info("Import data from " + file + ".");
		try {
			SAXBuilder builder = new SAXBuilder();
			Document inputDocument = builder.build(new File(file));
			Element root = inputDocument.getRootElement();

			importCityData(root.getChildren("city").iterator());

			importCountryData(root.getChildren("country").iterator());

			importSpotData(root.getChildren("spot").iterator());

			importAccess(root.getChildren("access").iterator());

			importGeoData(root.getChildren("geo").iterator());

			importCommentData(root.getChildren("comment").iterator());

			importCounterData(root.getChildren("counter").iterator());

			log.info("Import data finish.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	private void importSpotData(Iterator<Element> spots) {
		DatabaseDataHandler handler = new DatabaseDataHandler(
				new String[] { "IMPORTTED SITE" });
		BasicSpotData spotData = null;
		while (spots.hasNext()) {

			spotData = new BasicSpotData();

			Element spot = (Element) spots.next();

			{
				String name = spot.getAttributeValue("name");
				String country = spot.getAttributeValue("country");
				String address = spot.getAttributeValue("address");
				String type = spot.getAttributeValue("type");
				String source = spot.getAttributeValue("source");
				spotData.setSpot(new Spot(-1, name, country, address, type,
						false, source));

				spotData.setArea(spot.getAttributeValue("area"));
				spotData.setSubarea(spot.getAttributeValue("subarea"));
			}
			{
				String address2 = spot.getAttributeValue("address2");
				String place = spot.getAttributeValue("place");
				String info = spot.getAttributeValue("info");
				String tel = spot.getAttributeValue("tel");
				String url = spot.getAttributeValue("url");
				boolean parking = Boolean.parseBoolean(spot
						.getAttributeValue("parking"));
				spotData.setSpotDetail(new SpotDetail(-1, -1, address2, place,
						info, tel, parking, url));
			}

			Iterator<Element> spotType = spot.getChildren("type").iterator();
			while (spotType.hasNext()) {
				Element type_e = (Element) spotType.next();

				int t = Integer.parseInt(type_e.getAttributeValue("type"));

				spotData.addSpotType(new SpotType(-1, -1, t));
			}

			Iterator<Element> prices = spot.getChildren("price").iterator();
			while (prices.hasNext()) {
				Element price_e = (Element) prices.next();

				int price = Integer
						.parseInt(price_e.getAttributeValue("price"));

				int ageFrom = Integer.parseInt(price_e
						.getAttributeValue("ageFrom"));

				int ageTo = Integer
						.parseInt(price_e.getAttributeValue("ageTo"));

				int numberOfPerson = Integer.parseInt(price_e
						.getAttributeValue("numberOfPerson"));

				String spCond = price_e.getAttributeValue("spCond");

				String message = price_e.getAttributeValue("message");

				spotData.addPrice(new Price(-1, -1, price, ageFrom, ageTo,
						numberOfPerson, spCond, message));
			}

			Iterator<Element> pricespecails = spot.getChildren("pricespecail")
					.iterator();
			while (pricespecails.hasNext()) {
				Element price_e = (Element) pricespecails.next();

				int price = Integer
						.parseInt(price_e.getAttributeValue("price"));

				int ageFrom = Integer.parseInt(price_e
						.getAttributeValue("ageFrom"));

				int ageTo = Integer
						.parseInt(price_e.getAttributeValue("ageTo"));

				int numberOfPerson = Integer.parseInt(price_e
						.getAttributeValue("numberOfPerson"));

				Time timeStart = Time.valueOf(price_e
						.getAttributeValue("timeStart"));

				Time timeEnd = Time.valueOf(price_e
						.getAttributeValue("timeEnd"));

				Date dateStart = Date.valueOf(price_e
						.getAttributeValue("dateStart"));

				Date dateEnd = Date.valueOf(price_e
						.getAttributeValue("dateEnd"));

				String spCond = price_e.getAttributeValue("spCond");

				String message = price_e.getAttributeValue("message");

				spotData.addPriceSpecial(new PriceSpecial(-1, -1, price,
						ageFrom, ageTo, numberOfPerson, timeStart, timeEnd,
						dateStart, dateEnd, spCond, message));
			}

			Iterator<Element> timeOpens = spot.getChildren("businessHours")
					.iterator();
			while (timeOpens.hasNext()) {
				Element timeOpen = (Element) timeOpens.next();

				Time timeStart = Time.valueOf(timeOpen
						.getAttributeValue("timeStart"));

				Time timeEnd = Time.valueOf(timeOpen
						.getAttributeValue("timeEnd"));

				int dayStart = Integer.parseInt(timeOpen
						.getAttributeValue("dayStart")) + 1;

				int dayEnd = Integer.parseInt(timeOpen
						.getAttributeValue("dayEnd")) + 1;

				int monthStart = Integer.parseInt(timeOpen
						.getAttributeValue("monthStart"));

				int monthEnd = Integer.parseInt(timeOpen
						.getAttributeValue("monthEnd"));

				String dayInterval = timeOpen.getAttributeValue("week");

				String message = timeOpen.getAttributeValue("message");

				spotData.addOpen(new TimeOpenClose(-1, -1, timeStart, timeEnd,
						dayStart, dayEnd, monthStart, monthEnd, dayInterval,
						message));
			}

			Iterator<Element> timeCloses = spot.getChildren("closed")
					.iterator();
			while (timeCloses.hasNext()) {
				Element timeClose = (Element) timeCloses.next();

				Time timeStart = Time.valueOf(timeClose
						.getAttributeValue("timeStart"));

				Time timeEnd = Time.valueOf(timeClose
						.getAttributeValue("timeEnd"));

				int dayStart = Integer.parseInt(timeClose
						.getAttributeValue("dayStart")) + 1;

				int dayEnd = Integer.parseInt(timeClose
						.getAttributeValue("dayEnd")) + 1;

				int monthStart = Integer.parseInt(timeClose
						.getAttributeValue("monthStart"));

				int monthEnd = Integer.parseInt(timeClose
						.getAttributeValue("monthEnd"));

				String dayInterval = timeClose.getAttributeValue("week");

				String message = timeClose.getAttributeValue("message");

				spotData.addClose(new TimeOpenClose(-1, -1, timeStart, timeEnd,
						dayStart, dayEnd, monthStart, monthEnd, dayInterval,
						message));
			}

			Iterator<Element> events = spot.getChildren("specialEvent")
					.iterator();
			while (events.hasNext()) {
				Element sev = (Element) events.next();

				Time timeStart = Time.valueOf(sev
						.getAttributeValue("timeStart"));

				Time timeEnd = Time.valueOf(sev.getAttributeValue("timeEnd"));

				Date dayStart = Date
						.valueOf(sev.getAttributeValue("dateStart"));

				Date dayEnd = Date.valueOf(sev.getAttributeValue("dateEnd"));

				String message = sev.getAttributeValue("message");

				int repeat = Integer.parseInt(sev.getAttributeValue("repeat"));

				int repeatType = Integer.parseInt(sev
						.getAttributeValue("repeatType"));

				spotData.addSpecialEvent(new SpecialEvent(-1, -1, repeat,
						repeatType, timeStart, timeEnd, dayStart, dayEnd,
						message));
			}

			Iterator<Element> specialClosed = spot.getChildren("specialClosed")
					.iterator();
			while (specialClosed.hasNext()) {
				Element scl = (Element) specialClosed.next();

				Time timeStart = Time.valueOf(scl
						.getAttributeValue("timeStart"));

				Time timeEnd = Time.valueOf(scl.getAttributeValue("timeEnd"));

				Date dayStart = Date
						.valueOf(scl.getAttributeValue("dateStart"));

				Date dayEnd = Date.valueOf(scl.getAttributeValue("dateEnd"));

				String message = scl.getAttributeValue("message");

				int repeat = Integer.parseInt(scl.getAttributeValue("repeat"));

				int repeatType = Integer.parseInt(scl
						.getAttributeValue("repeatType"));

				spotData.addSpecialClosed(new SpecialClosed(-1, -1, repeat,
						repeatType, timeStart, timeEnd, dayStart, dayEnd,
						message));
			}

			Iterator<Element> accessInfos = spot.getChildren("accessInfo")
					.iterator();
			while (accessInfos.hasNext()) {
				Element access = (Element) accessInfos.next();

				long fromID = Long.parseLong(access.getAttributeValue("from"));

				int transportType = Integer.parseInt(access
						.getAttributeValue("transportType"));

				Time timeDeparture = Time.valueOf(access
						.getAttributeValue("timeDeparture"));

				Time timeNecessary = Time.valueOf(access
						.getAttributeValue("timeNecessary"));

				String information = access.getAttributeValue("information");

				spotData.addAccessInfo(new AccessInfo(-1, -1, fromID,
						transportType, timeDeparture, timeNecessary,
						information));
			}

			Calendar c = Calendar.getInstance();
			TimeTable table = TimeTable.createBySpotYearMonth(
					spotData.getBusinessHours(), spotData.getClosed(),
					spotData.getSpecialEvent(), spotData.getSpecialClosed(),
					c.get(Calendar.YEAR), c.get(Calendar.MONTH));

			// for test
			boolean test = false;
			if (test) {
				Calendar c2 = Calendar.getInstance();
				c2.set(Calendar.HOUR_OF_DAY, 0);
				c2.set(Calendar.MINUTE, 0);
				c2.set(Calendar.SECOND, 0);
				c2.set(Calendar.MILLISECOND, 0);
				c2.set(Calendar.DAY_OF_MONTH,
						c2.getActualMinimum(Calendar.DAY_OF_MONTH));
				Timestamp start = new Timestamp(c2.getTimeInMillis());
				c2.set(Calendar.HOUR_OF_DAY, 23);
				c2.set(Calendar.MINUTE, 59);
				c2.set(Calendar.SECOND, 59);
				c2.set(Calendar.DAY_OF_MONTH,
						c2.getActualMaximum(Calendar.DAY_OF_MONTH));
				Timestamp end = new Timestamp(c2.getTimeInMillis());
				TimeTable.createByTravelTime(start, end);
				TimeTable table2 = TimeTable.createBySpotTime(
						spotData.getBusinessHours(), spotData.getClosed(),
						spotData.getSpecialEvent(),
						spotData.getSpecialClosed(), start, end);
				boolean co = table.compare(table2);
				System.out.println("COMPARE " + co);
			} // for test

			HashMap<Date, String> temp = table.getTimeTableJson();
			for (Date y_m : temp.keySet()) {

				SpotTimeTable spotTimeTable = new SpotTimeTable(-1, -1, y_m,
						temp.get(y_m));
				spotData.addSpotTable(spotTimeTable);

				// System.out.println(data);
				// table.toTimeTableArray(data);
			}

			handler.insertData(spotData);
		}
	}

	private void importCityData(Iterator<Element> cities) {
		DatabaseSession db = new DatabaseSession();
		while (cities.hasNext()) {
			Element city = (Element) cities.next();
			String lang = city.getAttributeValue("lang");

			String c = city.getAttributeValue("country");

			String area = city.getAttributeValue("area");

			String subarea = city.getAttributeValue("subarea");

			String prefecture = city.getAttributeValue("prefecture");

			log.info("Insert city data: " + prefecture + ".");
			try {
				db.addCity(new City(-1, lang, c, area, subarea, prefecture));
			} catch (Exception e) {
				if (e.getMessage().contains("duplicate key value"))
					log.info(prefecture + " already exist.");
				else
					e.printStackTrace();
			}
		}
	}

	private void importCountryData(Iterator<Element> countries) {
		DatabaseSession db = new DatabaseSession();
		while (countries.hasNext()) {
			Element country = (Element) countries.next();

			String lang = country.getAttributeValue("lang");

			String c = country.getAttributeValue("country");

			log.info("Insert country data: " + c + ".");

			try {
				db.addCountry(new Country(-1, lang, c));
			} catch (Exception e) {
				if (e.getMessage().contains("duplicate key value"))
					log.info(c + " already exist.");
				else
					e.printStackTrace();
			}
		}

	}

	private void importAccess(Iterator<Element> accesses) {
		DatabaseSession db = new DatabaseSession();
		while (accesses.hasNext()) {
			Element access = (Element) accesses.next();

			long fromId = Long.parseLong(access.getAttributeValue("fromId"));
			long toId = Long.parseLong(access.getAttributeValue("toId"));
			int transportType = Integer.parseInt(access
					.getAttributeValue("transportType"));
			int duration = Integer.parseInt(access
					.getAttributeValue("duration"));
			int distance = Integer.parseInt(access
					.getAttributeValue("distance"));

			String result = access.getAttributeValue("result");

			log.info("Insert access: from " + fromId + " to " + toId);

			try {
				db.addAccess(new Access(-1, fromId, toId, transportType,
						duration, distance, result));
			} catch (Exception e) {
				if (e.getMessage().contains("duplicate key value"))
					log.info("Access: from " + fromId + " to " + toId
							+ " already exist.");
				else
					e.printStackTrace();
			}
		}
	}

	private void importGeoData(Iterator<Element> geos) {
		DatabaseSession db = new DatabaseSession();
		while (geos.hasNext()) {
			Element geo = (Element) geos.next();

			String address = geo.getAttributeValue("address");

			double latitude = Double.parseDouble(geo
					.getAttributeValue("latitude"));

			double longitude = Double.parseDouble(geo
					.getAttributeValue("longitude"));

			String result = geo.getAttributeValue("result");

			log.info(address + "\t" + result);

			try {
				db.addGeoData(new GeoData(address, latitude, longitude, result));
			} catch (Exception e) {
				if (e.getMessage().contains("duplicate key value"))
					log.info(address + " already exist.");
				else
					e.printStackTrace();
			}
		}
	}

	private void importCommentData(Iterator<Element> comments) {
		DatabaseSession db = new DatabaseSession();
		while (comments.hasNext()) {

			Element comment = (Element) comments.next();
			long userID = Long.parseLong(comment.getAttributeValue("userID"));

			long spotID = Long.parseLong(comment.getAttributeValue("spotID"));

			int ranking = Integer
					.parseInt(comment.getAttributeValue("ranking"));

			Timestamp postTime = Timestamp.valueOf(comment
					.getAttributeValue("postTime"));

			Time timeNecessary = Time.valueOf(comment
					.getAttributeValue("timeNecessart"));

			String message = comment.getValue();

			log.info("Insert comment from user: " + userID + ".");
			db.addComment(new Comment(-1, userID, spotID, ranking, postTime,
					timeNecessary, message));
		}
	}

	private void importCounterData(Iterator<Element> counters) {
		DatabaseSession db = new DatabaseSession();
		while (counters.hasNext()) {
			Element counter = (Element) counters.next();
			try {
				log.info("Import Counter");

				long gpsOffset = Long.parseLong(counter
						.getAttributeValue("gpsOffset"));
				int gpsCount = Integer.parseInt(counter
						.getAttributeValue("gpsCount"));
				long gpsTime = Long.parseLong(counter
						.getAttributeValue("gpsTime"));
				int accessDayCount = Integer.parseInt(counter
						.getAttributeValue("accessDayCount"));
				long accessDayTime = Long.parseLong(counter
						.getAttributeValue("accessDayTime"));
				int accessSecCount = Integer.parseInt(counter
						.getAttributeValue("accessSecCount"));
				long accessSecTime = Long.parseLong(counter
						.getAttributeValue("accessSecTime"));

				db.updateCounter(new Counter("Config", gpsOffset, gpsCount,
						gpsTime, accessDayCount, accessDayTime, accessSecCount,
						accessSecTime));

			} catch (Exception e) {
				if (e.getMessage().contains("duplicate key value")) {
				} else
					e.printStackTrace();
			}
		}
	}
}
