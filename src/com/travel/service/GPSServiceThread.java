package com.travel.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.travel.data.Counter;
import com.travel.data.GeoData;
import com.travel.data.Spot;
import com.travel.db.DatabaseSession;
import com.travel.function.GPSResolve;

public class GPSServiceThread extends ServiceThread {

	public static Logger log = Logger.getLogger(GPSServiceThread.class);

	public void run() {
		if (running)
			return;

		running = true;

		final int daylimit = 86405000;
		final int dayCountlimit = 2500;

		DatabaseSession db = new DatabaseSession();
		Counter c = db.getCounterByKey("Config");
		if (c == null) {
			c = new Counter();
			c.setId("Config");
			long now = Calendar.getInstance().getTimeInMillis();
			c.setGpsTime(now);
			c.setAccessDayTime(now);
			c.setAccessSecTime(now);
			new DatabaseSession().addCounter(c);
		}

		while (running) {

			log.info("Resolve GPS information start");

			int gpsCount = c.getGpsCount();

			while (running && gpsCount < dayCountlimit) {

				long GPSOffset = c.getGpsOffset();
				if (GPSOffset >= db.getSpotCount()) {
					running = false;
				}

				List<Spot> spots = db.getSpotWithOffset(dayCountlimit
						- gpsCount, GPSOffset);

				GPSOffset += spots.size();
				c.setGpsOffset(GPSOffset);
				db.updateCounter(c);

				for (Spot spot : spots) {
					String address = spot.getAddress();
					try {
						GeoData d = db.getGeoDataFromKey(address);

						if (d == null) {
							log.info("-> Resolve GPS information of address "
									+ address + " from Google.");
							GPSResolve gps = new GPSResolve();
							d = gps.search(address);
							db.addGeoData(d);

							log.info("-> Resolve GPS information of address "
									+ address + " success.");

							c.setGpsCount(++gpsCount);
							db.updateCounter(c);
							pause(300);
						} else {
							log.info("-> Found information of address "
									+ address + " in database.");

							if (update) {
								log.info("-> Update GPS information of address "
										+ address + " from Google.");
								GPSResolve gps = new GPSResolve();
								GeoData data = gps.search(address);
								db.updateGeoData(data);

								log.info("-> Update GPS information of address "
										+ address + " success.");

								c.setGpsCount(++gpsCount);
								db.updateCounter(c);
								pause(300);
							}
						}

					} catch (IOException e) {
						log.info("-> Resolve GPS information of address "
								+ address + " fail.");
					}

					if (!running)
						break;
				}
			}

			long dayDelay = daylimit
					- (Calendar.getInstance().getTimeInMillis() - c
							.getGpsTime());

			if (dayDelay < 0) {
				log.info("Time available, reset to 0.");
				c.setGpsCount(0);
				c.setGpsTime(Calendar.getInstance().getTimeInMillis());
				db.updateCounter(c);
			} else if (dayDelay > 0 && gpsCount >= dayCountlimit) {
				log.info("Query limited. " + dayCountlimit
						+ " elements per 24 hour period.");
				pause(dayDelay);

				c.setGpsCount(0);
				c.setGpsTime(Calendar.getInstance().getTimeInMillis());
				db.updateCounter(c);
			}
		}

		log.info("Resolve GPS information finish");
		update = false;
	}

	public void setUpdate(boolean updateGPS) {
		super.setUpdate(updateGPS);
		if (updateGPS) {
			DatabaseSession db = new DatabaseSession();
			Counter c = db.getCounterByKey("Config");
			try {
				c.setGpsOffset(0);
				db.updateCounter(c);
			} catch (NullPointerException e) {
			}
		}
	}

}
