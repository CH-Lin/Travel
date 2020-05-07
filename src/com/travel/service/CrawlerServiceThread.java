package com.travel.service;

import org.apache.log4j.Logger;

import com.travel.db.DatabaseSession;

public class CrawlerServiceThread extends ServiceThread {

	private static Logger log = Logger.getLogger(GPSServiceThread.class);

	DatabaseSession db;

}
