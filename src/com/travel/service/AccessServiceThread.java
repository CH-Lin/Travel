package com.travel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.travel.data.Access;
import com.travel.data.Counter;
import com.travel.data.Spot;
import com.travel.db.DatabaseSession;
import com.travel.function.AccessResolve;

public class AccessServiceThread extends ServiceThread {

	private static Logger log = Logger.getLogger(AccessServiceThread.class);

	DatabaseSession db;

	public void run() {
		if (running)
			return;

		running = true;

		final int daylimit = 86405000;
		final int dayCountlimit = 2500;
		final int timelimit = 10500;
		final int timeCountlimit = 100;

		db = new DatabaseSession();
		Counter c = db.getCounterByKey("Config");

		AccessResolve reaolver = new AccessResolve();

		while (running) {

			int dayCount = c.getAccessDayCount();
			while (running && dayCount < dayCountlimit) {

				int timeCount = c.getAccessSecCount();
				while (running && timeCount < timeCountlimit) {

					log.info("Construct access table");
					int available = timeCountlimit - timeCount;
					List<Pair> list = getPairs(available);

					if (list.size() <= 0)
						running = false;

					timeCount += list.size();
					dayCount += list.size();

					for (Pair pair : list) {
						try {
							log.info(pair);
							Spot src = db.getSpotById(pair.getSrc());
							Spot dest = db.getSpotById(pair.getDest());

							Access access = reaolver.search(src.getAddress(), dest.getAddress());
							access.setFromId(src.getSpotId());
							access.setToId(dest.getSpotId());

							db.addAccess(access);

						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					if (timeCount >= timeCountlimit) {
						c.setAccessSecTime(Calendar.getInstance().getTimeInMillis());
					}

					c.setAccessSecCount(timeCount);
					c.setAccessDayCount(dayCount);
					db.updateCounter(c);

					pause(300);
					break;
				}

				long timeDelay = timelimit - (Calendar.getInstance().getTimeInMillis() - c.getAccessSecTime());

				if (timeDelay < 0) {
					log.info("Sec. Time available, reset to 0.");
					c.setAccessSecCount(0);
					c.setAccessSecTime(Calendar.getInstance().getTimeInMillis());
					db.updateCounter(c);
				} else if (timeDelay > 0 && timeCount >= timeCountlimit) {
					c.setAccessSecCount(0);
					c.setAccessSecTime(Calendar.getInstance().getTimeInMillis());
					db.updateCounter(c);
					log.info("Query limited. " + timeCountlimit + " elements per 10 seconds.");
					pause(timeDelay);
				}
			}

			long dayDelay = daylimit - (Calendar.getInstance().getTimeInMillis() - c.getAccessDayTime());

			if (dayDelay < 0) {
				log.info("Time available, reset to 0.");
				c.setAccessDayCount(0);
				c.setAccessDayTime(Calendar.getInstance().getTimeInMillis());
				db.updateCounter(c);
			} else if (dayDelay > 0 && dayCount >= dayCountlimit) {
				log.info("Query limited. " + dayCountlimit + " elements per 24 hour period.");
				pause(dayDelay);

				c.setAccessDayCount(0);
				c.setAccessDayTime(Calendar.getInstance().getTimeInMillis());
				db.updateCounter(c);
			}
		}

		log.info("Construct access table finish");
		update = false;
	}

	public void setUpdate(boolean updateAccess) {
		super.setUpdate(updateAccess);
		if (updateAccess) {

		}
	}

	private List<Pair> getPairs(int available) {

		ArrayList<Pair> list = new ArrayList<Pair>();

		List<Spot> spots = db.getAllSpot();

		for (Spot from : spots) {
			for (Spot to : spots) {

				if (from.getSpotId() != to.getSpotId() && from.getCountry().equals(to.getCountry())
						&& db.getAccess(from.getSpotId(), to.getSpotId()) == null) {
					list.add(new Pair(from.getSpotId(), to.getSpotId()));
				}

				if (list.size() >= available)
					return list;
			}
		}

		return list;
	}

	class Pair {
		long src;
		long dest;

		public Pair(long src, long dest) {
			super();
			this.src = src;
			this.dest = dest;
		}

		public long getSrc() {
			return src;
		}

		public void setSrc(long src) {
			this.src = src;
		}

		public long getDest() {
			return dest;
		}

		public void setDest(long dest) {
			this.dest = dest;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Pair [src=").append(src).append(", dest=").append(dest).append("]");
			return builder.toString();
		}

	}
}
