package com.travel.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.travel.data.Access;
import com.travel.data.AccessInfo;
import com.travel.data.Counter;
import com.travel.data.GeoData;
import com.travel.data.Price;
import com.travel.data.PriceSpecial;
import com.travel.data.SpecialClosed;
import com.travel.data.SpecialEvent;
import com.travel.data.Spot;
import com.travel.data.SpotDetail;
import com.travel.data.TimeOpenClose;

public class DatabaseExport {

	public static Logger log = Logger.getLogger(DatabaseExport.class);

	public void exportSpot(String file) {

		DatabaseSession db = new DatabaseSession();

		log.info("Get data from table spot.");
		HashMap<Long, Spot> spot = new HashMap<Long, Spot>();
		for (Spot s : db.getAllSpot()) {
			spot.put(s.getSpotId(), s);
		}

		log.info("Get data from table spotDetail.");
		HashMap<Long, ArrayList<SpotDetail>> spotDetail = new HashMap<Long, ArrayList<SpotDetail>>();
		for (SpotDetail s : db.getAllSpotDetail()) {
			ArrayList<SpotDetail> list;
			if ((list = spotDetail.get(s.getSpotId())) == null) {
				list = new ArrayList<SpotDetail>();
			}
			list.add(s);
			spotDetail.put(s.getSpotId(), list);
		}

		log.info("Get data from table businessHours.");
		HashMap<Long, ArrayList<TimeOpenClose>> businessHours = new HashMap<Long, ArrayList<TimeOpenClose>>();
		for (TimeOpenClose s : db.getAllBusinessHours()) {
			ArrayList<TimeOpenClose> list;
			if ((list = businessHours.get(s.getSpotId())) == null) {
				list = new ArrayList<TimeOpenClose>();
			}
			list.add(s);
			businessHours.put(s.getSpotId(), list);
		}

		log.info("Get data from table closed.");
		HashMap<Long, ArrayList<TimeOpenClose>> closed = new HashMap<Long, ArrayList<TimeOpenClose>>();
		for (TimeOpenClose s : db.getAllClosed()) {
			ArrayList<TimeOpenClose> list;
			if ((list = closed.get(s.getSpotId())) == null) {
				list = new ArrayList<TimeOpenClose>();
			}
			list.add(s);
			closed.put(s.getSpotId(), list);
		}

		log.info("Get data from table specialEvent.");
		HashMap<Long, ArrayList<SpecialEvent>> specialEvent = new HashMap<Long, ArrayList<SpecialEvent>>();
		for (SpecialEvent s : db.getAllSpecialEvent()) {
			ArrayList<SpecialEvent> list;
			if ((list = specialEvent.get(s.getSpotId())) == null) {
				list = new ArrayList<SpecialEvent>();
			}
			list.add(s);
			specialEvent.put(s.getSpotId(), list);
		}

		log.info("Get data from table specialClosed.");
		HashMap<Long, ArrayList<SpecialClosed>> specialClosed = new HashMap<Long, ArrayList<SpecialClosed>>();
		for (SpecialClosed s : db.getAllSpecialClosed()) {
			ArrayList<SpecialClosed> list;
			if ((list = specialClosed.get(s.getSpotId())) == null) {
				list = new ArrayList<SpecialClosed>();
			}
			list.add(s);
			specialClosed.put(s.getSpotId(), list);
		}

		log.info("Get data from table price.");
		HashMap<Long, ArrayList<Price>> price = new HashMap<Long, ArrayList<Price>>();
		for (Price s : db.getAllPrice()) {
			ArrayList<Price> list;
			if ((list = price.get(s.getSpotId())) == null) {
				list = new ArrayList<Price>();
			}
			list.add(s);
			price.put(s.getSpotId(), list);
		}

		log.info("Get data from table priceSpecial.");
		HashMap<Long, ArrayList<PriceSpecial>> priceSpecial = new HashMap<Long, ArrayList<PriceSpecial>>();
		for (PriceSpecial s : db.getAllPriceSpecial()) {
			ArrayList<PriceSpecial> list;
			if ((list = priceSpecial.get(s.getSpotId())) == null) {
				list = new ArrayList<PriceSpecial>();
			}
			list.add(s);
			priceSpecial.put(s.getSpotId(), list);
		}

		log.info("Get data from table accessInfo.");
		HashMap<Long, ArrayList<AccessInfo>> accessInfo = new HashMap<Long, ArrayList<AccessInfo>>();
		for (AccessInfo s : db.getAllAccessInfo()) {
			ArrayList<AccessInfo> list;
			if ((list = accessInfo.get(s.getSpotId())) == null) {
				list = new ArrayList<AccessInfo>();
			}
			list.add(s);
			accessInfo.put(s.getSpotId(), list);
		}

		int index = 1;
		log.info("Construct spot XML file.");
		Document spot_doc = new Document();
		Element travel_root = new Element("travel");
		spot_doc.addContent(travel_root);
		for (Long key : spot.keySet()) {
			Element e_spot = new Element("spot");
			Spot s = spot.get(key);
			e_spot.setAttribute("name", s.getName());
			e_spot.setAttribute("country", s.getCountry());
			e_spot.setAttribute("address", s.getAddress());
			e_spot.setAttribute("type", s.getType());
			e_spot.setAttribute("source", s.getSource());

			ArrayList<SpotDetail> list_spotDetail = spotDetail.get(key);
			if (list_spotDetail != null)
				for (SpotDetail sd : list_spotDetail) {
					if (sd.getAddress2() != null)
						e_spot.setAttribute("address2", sd.getAddress2());
					if (sd.getPlace() != null)
						e_spot.setAttribute("place", sd.getPlace());
					e_spot.setAttribute("info", sd.getInfo());
					e_spot.setAttribute("tel", sd.getTel());
					e_spot.setAttribute("parking",
							new StringBuffer().append(sd.isParking())
									.toString());
					e_spot.setAttribute("url", sd.getUrl());
				}

			ArrayList<TimeOpenClose> list_businessHours = businessHours
					.get(key);
			if (list_businessHours != null)
				for (TimeOpenClose bh : list_businessHours) {
					Element e_bh = new Element("businessHours");
					e_bh.setAttribute("timeStart", bh.getTimeStart().toString());
					e_bh.setAttribute("timeEnd", bh.getTimeEnd().toString());
					e_bh.setAttribute("dayStart", "" + (bh.getDayStart() - 1));
					e_bh.setAttribute("dayEnd", "" + (bh.getDayEnd() - 1));
					e_bh.setAttribute("monthStart", "" + (bh.getMonthStart()));
					e_bh.setAttribute("monthEnd", "" + (bh.getMonthEnd()));
					e_bh.setAttribute("week", bh.getWeek());
					e_bh.setAttribute("message", bh.getMessage());
					e_spot.addContent(e_bh);
				}

			ArrayList<TimeOpenClose> list_closed = closed.get(key);
			if (list_closed != null)
				for (TimeOpenClose cl : list_closed) {
					Element e_cl = new Element("closed");
					e_cl.setAttribute("timeStart", cl.getTimeStart().toString());
					e_cl.setAttribute("timeEnd", cl.getTimeEnd().toString());
					e_cl.setAttribute("dayStart", "" + (cl.getDayStart() - 1));
					e_cl.setAttribute("dayEnd", "" + (cl.getDayEnd() - 1));
					e_cl.setAttribute("monthStart", "" + (cl.getMonthStart()));
					e_cl.setAttribute("monthEnd", "" + (cl.getMonthEnd()));
					e_cl.setAttribute("week", cl.getWeek());
					e_cl.setAttribute("message", cl.getMessage());
					e_spot.addContent(e_cl);
				}

			ArrayList<SpecialEvent> list_specialEvent = specialEvent.get(key);
			if (list_specialEvent != null)
				for (SpecialEvent se : list_specialEvent) {
					Element e_se = new Element("specialEvent");
					e_se.setAttribute("repeat", "" + se.getRepeat());
					e_se.setAttribute("repeatType", "" + se.getRepeatType());
					e_se.setAttribute("timeStart", se.getTimeStart().toString());
					e_se.setAttribute("timeEnd", se.getTimeEnd().toString());
					e_se.setAttribute("dateStart", se.getDateStart().toString());
					e_se.setAttribute("dateEnd", se.getDateEnd().toString());
					e_se.setAttribute("message", se.getMessage());
					e_spot.addContent(e_se);
				}

			ArrayList<SpecialClosed> list_specialClosed = specialClosed
					.get(key);
			if (list_specialClosed != null)
				for (SpecialClosed sc : list_specialClosed) {
					Element e_sc = new Element("specialClosed");
					e_sc.setAttribute("repeat", "" + sc.getRepeat());
					e_sc.setAttribute("repeatType", "" + sc.getRepeatType());
					e_sc.setAttribute("timeStart", sc.getTimeStart().toString());
					e_sc.setAttribute("timeEnd", sc.getTimeEnd().toString());
					e_sc.setAttribute("dateStart", sc.getDateStart().toString());
					e_sc.setAttribute("dateEnd", sc.getDateEnd().toString());
					e_sc.setAttribute("message", sc.getMessage());
					e_spot.addContent(e_sc);
				}

			ArrayList<Price> list_price = price.get(key);
			if (list_price != null)
				for (Price p : list_price) {
					Element e_p = new Element("price");
					e_p.setAttribute("price", "" + p.getPrice());
					e_p.setAttribute("ageFrom", "" + p.getAgeFrom());
					e_p.setAttribute("ageTo", "" + p.getAgeTo());
					e_p.setAttribute("numberOfPerson",
							"" + p.getNumberOfPerson());
					e_p.setAttribute("spCond",
							p.getSpCond() != null ? p.getSpCond() : "");
					e_p.setAttribute("message", p.getMessage());
					e_spot.addContent(e_p);
				}

			ArrayList<PriceSpecial> list_priceSpecial = priceSpecial.get(key);
			if (list_priceSpecial != null)
				for (PriceSpecial ps : list_priceSpecial) {
					Element e_ps = new Element("pricespecail");
					e_ps.setAttribute("price", "" + ps.getPrice());
					e_ps.setAttribute("ageFrom", "" + ps.getAgeFrom());
					e_ps.setAttribute("ageTo", "" + ps.getAgeTo());
					e_ps.setAttribute("numberOfPerson",
							"" + ps.getNumberOfPerson());
					e_ps.setAttribute("timeStart", ps.getTimeStart().toString());
					e_ps.setAttribute("timeEnd", ps.getTimeEnd().toString());
					e_ps.setAttribute("dateStart", ps.getDateStart().toString());
					e_ps.setAttribute("dateEnd", ps.getDateEnd().toString());
					e_ps.setAttribute("spCond",
							ps.getSpCond() != null ? ps.getSpCond() : "");
					e_ps.setAttribute("message", ps.getMessage());
					e_spot.addContent(e_ps);
				}

			ArrayList<AccessInfo> list_accessInfo = accessInfo.get(key);
			if (list_accessInfo != null)
				for (AccessInfo a : list_accessInfo) {
					Element e_a = new Element("accessInfo");
					e_a.setAttribute("from", "" + a.getFromId());
					e_a.setAttribute("transportType", "" + a.getTransportType());
					e_a.setAttribute("timeDeparture", a.getTimeDeparture()
							.toString());
					e_a.setAttribute("timeNecessary", a.getTimeNecessary()
							.toString());
					e_a.setAttribute("information", a.getInformation());
					e_spot.addContent(e_a);
				}

			travel_root.addContent(e_spot);

			if (index % 100 == 0 || index == spot.keySet().size()) {
				String out = file;
				XMLOutputter outputter = new XMLOutputter(
						Format.getPrettyFormat());
				try {
					if (!out.endsWith(".xml"))
						out = out.concat(".xml");

					out = out.replace(".xml", "_spot.xml");

					if ((index / 100) != 0) {
						out = out.replace(".xml", "" + (index / 100) + ".xml");
					}

					log.info("Export spot file: " + out + ". index: " + index);
					FileOutputStream fos = new FileOutputStream(out);
					outputter.output(spot_doc, fos);
					fos.close();

					spot_doc = new Document();
					travel_root = new Element("travel");
					spot_doc.addContent(travel_root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			index++;
		}

		log.info("Export spot filished.");

		exportGeoData(file);
		exportAccess(file);
		exportCounter(file);
	}

	private void exportGeoData(String file) {

		int index = 1;
		log.info("Construct geo data XML file.");
		Document geo_doc = new Document();
		Element geo_root = new Element("travel");
		geo_doc.addContent(geo_root);

		DatabaseSession db = new DatabaseSession();
		List<GeoData> data = db.getAllGeoData();
		for (GeoData geodata : data) {
			Element e_geo = new Element("geo");
			e_geo.setAttribute("address", geodata.getAddress());
			e_geo.setAttribute("latitude", "" + geodata.getLatitude());
			e_geo.setAttribute("longitude", "" + geodata.getLongitude());
			e_geo.setAttribute("result", geodata.getResult());

			geo_root.addContent(e_geo);

			if (index % 100 == 0 || index == data.size()) {
				String out = file;
				XMLOutputter outputter = new XMLOutputter(
						Format.getPrettyFormat());
				try {
					if (!out.endsWith(".xml"))
						out = out.concat(".xml");

					out = out.replace(".xml", "_geo.xml");

					if ((index / 100) != 0) {
						out = out.replace(".xml", "" + (index / 100) + ".xml");
					}

					log.info("Export geo file: " + out + ". index: " + index);
					FileOutputStream fos = new FileOutputStream(out);
					outputter.output(geo_doc, fos);
					fos.close();

					geo_doc = new Document();
					geo_root = new Element("travel");
					geo_doc.addContent(geo_root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			index++;
		}

		log.info("Export geo data filished.");
	}

	private void exportAccess(String file) {

		int index = 1;
		log.info("Construct access XML file.");
		Document access_doc = new Document();
		Element access_root = new Element("travel");
		access_doc.addContent(access_root);

		DatabaseSession db = new DatabaseSession();
		List<Access> data = db.getAllAccess();
		for (Access access : data) {
			Element e_geo = new Element("access");
			e_geo.setAttribute("fromId", "" + access.getFromId());
			e_geo.setAttribute("toId", "" + access.getToId());
			e_geo.setAttribute("transportType", "" + access.getTransportType());
			e_geo.setAttribute("duration", "" + access.getDuration());
			e_geo.setAttribute("distance", "" + access.getDistance());
			e_geo.setAttribute("result", access.getResult());

			access_root.addContent(e_geo);

			if (index % 100 == 0 || index == data.size()) {
				String out = file;
				XMLOutputter outputter = new XMLOutputter(
						Format.getPrettyFormat());
				try {
					if (!out.endsWith(".xml"))
						out = out.concat(".xml");

					out = out.replace(".xml", "_access.xml");

					if ((index / 100) != 0) {
						out = out.replace(".xml", "" + (index / 100) + ".xml");
					}

					log.info("Export access file: " + out + ". index: " + index);
					FileOutputStream fos = new FileOutputStream(out);
					outputter.output(access_doc, fos);
					fos.close();

					access_doc = new Document();
					access_root = new Element("travel");
					access_doc.addContent(access_root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			index++;
		}

		log.info("Export access data filished.");
	}

	private void exportCounter(String file) {

		log.info("Construct counter XML file.");
		Document counter_doc = new Document();
		Element counter_root = new Element("travel");
		counter_doc.addContent(counter_root);

		DatabaseSession db = new DatabaseSession();
		Counter data = db.getCounterByKey("Config");
		if (data != null) {
			Element e_counter = new Element("counter");
			e_counter.setAttribute("gpsOffset", "" + data.getGpsOffset());
			e_counter.setAttribute("gpsCount", "" + data.getGpsCount());
			e_counter.setAttribute("gpsTime", "" + data.getGpsTime());
			e_counter.setAttribute("accessDayCount",
					"" + data.getAccessDayCount());
			e_counter.setAttribute("accessDayTime",
					"" + data.getAccessDayTime());
			e_counter.setAttribute("accessSecCount",
					"" + data.getAccessSecCount());
			e_counter.setAttribute("accessSecTime",
					"" + data.getAccessSecTime());

			counter_root.addContent(e_counter);

			String out = file;
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			try {
				if (!out.endsWith(".xml"))
					out = out.concat(".xml");

				out = out.replace(".xml", "_Counter.xml");

				log.info("Export counter file: " + out);
				FileOutputStream fos = new FileOutputStream(out);
				outputter.output(counter_doc, fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		log.info("Export counter data filished.");
	}
}
