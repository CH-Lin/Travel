package com.travel.db;

import org.apache.log4j.Logger;

import com.travel.data.Access;
import com.travel.data.AccessInfo;
import com.travel.data.City;
import com.travel.data.Comment;
import com.travel.data.Counter;
import com.travel.data.Country;
import com.travel.data.GeoData;
import com.travel.data.Price;
import com.travel.data.PriceSpecial;
import com.travel.data.SpecialClosed;
import com.travel.data.SpecialEvent;
import com.travel.data.Spot;
import com.travel.data.SpotDetail;
import com.travel.data.SpotTimeTable;
import com.travel.data.TimeOpenClose;
import com.travel.data.SpotType;

public class DatabaseTable {

	public static Logger log = Logger.getLogger(DatabaseTable.class);

	/**
	 * Reset all table
	 */
	public void reset() {
		log.info("RESET TABLES");
		DatabaseSession spot = new DatabaseSession();
		spot.dropTables();
		spot.createTables();
		log.info("RESET TABLES FINISH");
	}

	/**
	 * Perform specail actions
	 */
	public void spAction() {
		log.info("SP ACTION");
		new DatabaseSession().spAction();
		log.info("SP ACTION FINISH");
	}

	/**
	 * Create all table
	 */
	public void createSpotTables() {
		log.info("CREATE TABLES");
		new DatabaseSession().createTables();
		log.info("CREATE TABLES FINISH");
	}

	/**
	 * Drop all table
	 */
	public void dropSpotTables() {
		log.info("DROP TABLES");
		new DatabaseSession().dropTables();
		log.info("DROP TABLES FINISH");
	}

	/**
	 * List all data
	 */
	public void listTables() {
		DatabaseSession db = new DatabaseSession();

		log.info("Table City:");
		for (City c : db.getAllCity()) {
			log.info(c.toString());
		}

		log.info("Table Country:");
		for (Country c : db.getAllCountry()) {
			log.info(c.toString());
		}

		log.info("Table Spot:");
		for (Spot s : db.getAllSpot()) {
			log.info(s.toString());
		}

		log.info("Table SpotDetail:");
		for (SpotDetail s : db.getAllSpotDetail()) {
			log.info(s.toString());
		}

		log.info("Table Price:");
		for (Price s : db.getAllPrice()) {
			log.info(s.toString());
		}

		log.info("Table PriceSpecial:");
		for (PriceSpecial s : db.getAllPriceSpecial()) {
			log.info(s.toString());
		}

		log.info("Table BusinessHours:");
		for (TimeOpenClose s : db.getAllBusinessHours()) {
			log.info(s.toString());
		}

		log.info("Table Closed:");
		for (TimeOpenClose s : db.getAllClosed()) {
			log.info(s.toString());
		}

		log.info("Table Special Event:");
		for (SpecialEvent s : db.getAllSpecialEvent()) {
			log.info(s.toString());
		}

		log.info("Table Special Closed:");
		for (SpecialClosed s : db.getAllSpecialClosed()) {
			log.info(s.toString());
		}

		log.info("Table Spot Time Table:");
		for (SpotTimeTable s : db.getAllSpotTimeTable()) {
			log.info(s.toString());
		}

		log.info("Table Access:");
		for (Access s : db.getAllAccess()) {
			log.info(s.toString());
		}

		log.info("Table AccessInfo:");
		for (AccessInfo s : db.getAllAccessInfo()) {
			log.info(s.toString());
		}

		log.info("Table Comment:");
		for (Comment c : db.getAllComment()) {
			log.info(c.toString());
		}

		log.info("Table Type:");
		for (SpotType s : db.getAllSpotType()) {
			log.info(s.toString());
		}

		log.info("Table GeoData:");
		for (GeoData g : db.getAllGeoData()) {
			log.info(g.toString());
		}

		log.info("Table Counter:");
		Counter c = db.getCounterByKey("Config");
		if (c != null) {
			log.info(db.getCounterByKey("Config").toString());
		}

	}
}
