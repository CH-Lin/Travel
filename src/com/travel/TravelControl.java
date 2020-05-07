package com.travel;

import com.travel.db.DatabaseExport;
import com.travel.db.DatabaseImport;
import com.travel.db.DatabaseTable;
import com.travel.service.AccessServiceThread;
import com.travel.service.GPSServiceThread;

/***
 * The TravelCore class is used to control the flow and receiving the command
 * from GUI or command line of this application. All of the tasks, including
 * data analysis, database operation, etc. are performed using other classes
 ***/
public class TravelControl {

	private TravelCrawler crawler = new TravelCrawler();
	private GPSServiceThread GPSThread;
	private AccessServiceThread accessThread;

	/**
	 * Parse the input command
	 * 
	 * @param arg
	 *            command parameter
	 * @return boolean false if no such command
	 */
	public boolean commandParser(String arg) {

		String temp = arg;
		if (temp.indexOf(":") != -1) {
			temp = temp.substring(0, temp.indexOf(":"));
		}

		switch (temp.toLowerCase()) {

		case "crawl data": { // crawl
			crawler.startCrawl();
			break;
		}

		case "stop crawl": { // stop crawl
			crawler.stopCrawl();
			break;
		}

		case "resolve gps": { // resolve GPS
			if (GPSThread != null && GPSThread.isRunning()) {
				break;
			}

			GPSThread = new GPSServiceThread();
			GPSThread.setUpdate(false);
			GPSThread.start();
			break;
		}

		case "update gps": { // update GPS
			if (GPSThread != null && GPSThread.isRunning()) {
				break;
			}

			GPSThread = new GPSServiceThread();
			GPSThread.setUpdate(true);
			GPSThread.start();
			break;
		}

		case "stop gps": { // stop resolve GPS
			if (GPSThread != null && GPSThread.isRunning()) {
				GPSThread.stopRunning();
				GPSThread = null;
			}
			break;
		}

		case "build access": { // build access
			if (accessThread != null && accessThread.isRunning()) {
				break;
			}

			accessThread = new AccessServiceThread();
			accessThread.setUpdate(false);
			accessThread.start();
			break;
		}

		case "stop build access": { // stop build
			if (accessThread != null && accessThread.isRunning()) {
				accessThread.stopRunning();
				accessThread = null;
			}
			break;
		}

		case "create tables": { // create
			new DatabaseTable().createSpotTables();
			break;
		}

		case "drop tables": {
			new DatabaseTable().dropSpotTables();
			break;
		}

		case "list tables": {
			new DatabaseTable().listTables();
			break;
		}

		case "special": { // special
			new DatabaseTable().spAction();
			break;
		}

		case "reset": { // reset
			new DatabaseTable().reset();
			break;
		}

		case "import data": { // import
			String args = arg.substring(arg.indexOf(":") + 1);
			new DatabaseImport().importData(args);
			break;
		}

		case "export data": { // export
			String args = arg.substring(arg.indexOf(":") + 1);
			new DatabaseExport().exportSpot(args);
			break;
		}

		default:
			return false;
		}

		return true;
	}
}
