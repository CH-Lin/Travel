package com.travel.crawler;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.travel.datahandler.DatabaseDataHandler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class BasicCrawler extends WebCrawler {

	public static Logger log = Logger.getLogger(BasicCrawler.class);

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf|ico"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private String[] myCrawlDomains;
	protected DatabaseDataHandler handler;

	@Override
	public void onStart() {
		handler = (DatabaseDataHandler) myController.getCustomData();
		myCrawlDomains = handler.getDomain();
	}

	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (FILTERS.matcher(href).matches()) {
			return false;
		}
		for (String crawlDomain : myCrawlDomains) {
			if (href.startsWith(crawlDomain)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		if (!myController.isShuttingDown()) {
			log.info("Visit URL: " + page.getWebURL().getURL());
		}
	}
}
