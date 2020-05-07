package com.travel.datahandler;

import org.apache.log4j.Logger;

import com.travel.data.AccessInfo;
import com.travel.data.BasicSpotData;
import com.travel.data.Price;
import com.travel.data.PriceSpecial;
import com.travel.data.SpecialClosed;
import com.travel.data.SpecialEvent;
import com.travel.data.SpotDetail;
import com.travel.data.SpotType;
import com.travel.data.TimeOpenClose;
import com.travel.db.DatabaseSession;
import com.travel.filter.DataFilter;

public class DatabaseDataHandler extends DataHandler {

	public static Logger log = Logger.getLogger(DatabaseDataHandler.class);

	public DatabaseDataHandler(String[] domain) {
		super(domain);
	}

	public void insertData(BasicSpotData data) {

		// analysis data before insert
		if (filters.size() > 0) {
			for (DataFilter analyzer : filters) {
				analyzer.doFilter(data);
			}
		}

		// insert into database
		log.info("Insert " + data.getName() + ".");
		try {
			DatabaseSession db = new DatabaseSession();
			long id = db.addSpot(data.getSpot());

			SpotDetail sdl = data.getSpotDetail();
			sdl.setSpotId(id);
			db.addSpotDetail(sdl);

			for (SpotType cmt : data.getSpotType()) {
				cmt.setSpotId(id);
				db.addSpotType(cmt);
			}

			for (Price cmt : data.getPrice()) {
				cmt.setSpotId(id);
				db.addPrice(cmt);
			}

			for (PriceSpecial cmt : data.getPriceSpecial()) {
				cmt.setSpotId(id);
				db.addPriceSpecial(cmt);
			}

			for (AccessInfo cmt : data.getAccess()) {
				cmt.setSpotId(id);
				db.addAccessInfo(cmt);
			}

			for (TimeOpenClose cmt : data.getBusinessHours()) {
				cmt.setSpotId(id);
				db.addBusinessHours(cmt);
			}

			for (TimeOpenClose cmt : data.getClosed()) {
				cmt.setSpotId(id);
				db.addClosed(cmt);
			}

			for (SpecialEvent cmt : data.getSpecialEvent()) {
				cmt.setSpotId(id);
				db.addSpecialEvent(cmt);
			}

			for (SpecialClosed cmt : data.getSpecialClosed()) {
				cmt.setSpotId(id);
				db.addSpecialClosed(cmt);
			}

		} catch (Exception e) {
			if (e.getMessage().contains("duplicate key value"))
				log.info(data.getName() + " " + data.getSource()
						+ " already exist.");
			else
				e.printStackTrace();
		}

	}

}
