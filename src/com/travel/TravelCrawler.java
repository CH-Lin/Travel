package com.travel;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.travel.TravelConfig.Visitor;
import com.travel.TravelConfig.Filter;
import com.travel.datahandler.DatabaseDataHandler;
import com.travel.filter.DataFilter;
import com.travel.plugin.PluginLoader;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class TravelCrawler {

	public static Logger log = Logger.getLogger(TravelCrawler.class);

	private static String folder = System.getProperty("file.separator");

	private static LinkedList<CrawlController> controllerlist;
	private static HashMap<String, DataFilter> filterMap;

	private boolean crawling = false;

	/**
	 * Start crawl task
	 */
	public void startCrawl() {

		if (crawling)
			return;

		crawling = true;

		initialController();
		initialfilter();

		TravelConfig travelConfig = new TravelConfig();
		String path = travelConfig.getVisitorPath();
		Visitor[] visitors = travelConfig.getSelectedVisitors();

		// initial crawlers according to the visitor information
		for (Visitor visitor : visitors) {

			DatabaseDataHandler handler = new DatabaseDataHandler(
					new String[] { visitor.getUrl() });
			for (String s : visitor.getfilters()) {
				handler.addAnalyser(filterMap.get(s));
			}

			try {

				CrawlConfig config = new CrawlConfig();
				if (travelConfig.getAgent() != null)
					config.setUserAgentString(travelConfig.getAgent());
				config.setSocketTimeout(50000);
				config.setConnectionTimeout(70000);
				config.setCrawlStorageFolder(travelConfig.getTempFolder()
						+ folder + "Crawler" + folder + path
						+ visitor.getVisitor());
				config.setPolitenessDelay(travelConfig.getCrawlDelay());
				config.setMaxPagesToFetch(travelConfig.getMaxPages());

				PageFetcher pageFetcher = new PageFetcher(config);
				RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
				RobotstxtServer robotstxtServer = new RobotstxtServer(
						robotstxtConfig, pageFetcher);

				// The CrawlController is used to control all of the
				// crawling tasks
				CrawlController controller = new CrawlController(config,
						pageFetcher, robotstxtServer);

				controllerlist.add(controller);

				for (String seed : visitor.getSeeds())
					controller.addSeed(seed);

				controller.setCustomData(handler);

				// load crawler classes
				log.info("Load crawler plugin: " + visitor.getVisitor());
				@SuppressWarnings("unchecked")
				Class<WebCrawler> c = (Class<WebCrawler>) new PluginLoader()
						.load(path + visitor.getVisitor());

				if (c != null)
					controller.startNonBlocking(c, travelConfig.getThreadNum());

			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	/**
	 * stop all crawler
	 */
	public void stopCrawl() {

		crawling = false;

		if (controllerlist == null)
			return;

		// convert to array to avoid nullPointException
		final CrawlController lists[] = controllerlist
				.toArray(new CrawlController[0]);

		for (int i = 0; i < lists.length; i++) {
			final CrawlController c = lists[i];
			new Thread() {
				public void run() {
					c.shutdown();
					c.waitUntilFinish();
				}
			}.start();
		}

	}

	private void initialController() {
		if (controllerlist != null)
			controllerlist = null;

		controllerlist = new LinkedList<CrawlController>();
	}

	/**
	 * initial all analyzer according to the content of configuration file
	 */
	private void initialfilter() {
		if (filterMap != null)
			filterMap = null;

		filterMap = new HashMap<String, DataFilter>();

		TravelConfig config = new TravelConfig();
		String path = config.getFilterPath();
		Filter[] filters = config.getFilters();

		for (Filter filter : filters) {
			try {
				log.info("Load filter plugin: " + filter.getPath());
				Class<?> c = new PluginLoader().load(path + filter.getPath());

				Class<?>[] params = new Class[1];
				params[0] = String[].class;

				Constructor<?> constructor = c.getConstructor(params);

				Object[] paramObjs = new Object[1];
				paramObjs[0] = filter.getDatas();

				DataFilter obj = (DataFilter) constructor
						.newInstance(paramObjs);

				filterMap.put(filter.getName(), obj);

			} catch (Exception e) {
				log.error(e);
			}
		}
	}
}
