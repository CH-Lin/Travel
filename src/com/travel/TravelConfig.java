package com.travel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/***
 * The TravelConfig class is used to load the configurations from congfig.xml
 * file.
 ***/
public class TravelConfig {

	private static String path = "config/config.xml";

	private String crawlDelay;
	private String lang;
	private String maxPages;
	private String thread;
	private String serverModePort;

	private String folder_win;
	private String folder_mac;

	private String filterPath;
	private String visitorPath;

	private ArrayList<Agent> agentList;
	private ArrayList<Filter> filterList;
	private ArrayList<Visitor> visitorList;

	private Document inputDocument;

	public TravelConfig() {

		agentList = new ArrayList<Agent>();
		filterList = new ArrayList<Filter>();
		visitorList = new ArrayList<Visitor>();

		try {
			SAXBuilder builder = new SAXBuilder();
			inputDocument = builder.build(new File(path));
			loadContent();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

	private void loadContent() {
		Element root = inputDocument.getRootElement();

		crawlDelay = root.getAttributeValue("crawldelay");
		lang = root.getAttributeValue("lang");
		maxPages = root.getAttributeValue("MaxPages");
		thread = root.getAttributeValue("thread");
		serverModePort = root.getAttributeValue("serverModePort");

		Iterator<Element> agent = root.getChildren("agent").iterator();
		while (agent.hasNext()) {
			Element e = (Element) agent.next();
			String str = e.getAttributeValue("str");
			boolean selected = Boolean.parseBoolean(e.getAttributeValue("selected"));

			agentList.add(new Agent(str, selected));
		}

		Iterator<Element> folder = root.getChildren("tempfolder").iterator();
		while (folder.hasNext()) {
			Element e = (Element) folder.next();
			folder_win = e.getChild("win").getValue();
			folder_mac = e.getChild("mac").getValue();
		}

		Iterator<Element> filters = root.getChildren("filters").iterator();
		while (filters.hasNext()) {
			Element filters_e = (Element) filters.next();

			filterPath = filters_e.getAttributeValue("path");

			Iterator<Element> filter = filters_e.getChildren("filter").iterator();
			while (filter.hasNext()) {
				Element filter_e = (Element) filter.next();

				String name = filter_e.getAttributeValue("name");
				String path = filter_e.getAttributeValue("path");

				Filter c = new Filter(name, path);

				Iterator<Element> data = filter_e.getChildren("data").iterator();

				while (data.hasNext()) {
					Element data_e = (Element) data.next();
					c.addData(data_e.getValue());
				}

				filterList.add(c);
			}
		}

		Iterator<Element> visitors = root.getChildren("visitors").iterator();
		while (visitors.hasNext()) {
			Element visitors_e = (Element) visitors.next();

			visitorPath = visitors_e.getAttributeValue("path");

			Iterator<Element> visitor = visitors_e.getChildren("visitor").iterator();
			while (visitor.hasNext()) {
				Element visitor_e = (Element) visitor.next();

				String url = visitor_e.getAttributeValue("url");
				String path = visitor_e.getAttributeValue("path");
				boolean selected = Boolean.parseBoolean(visitor_e.getAttributeValue("selected"));

				Visitor s = new Visitor(url, path, selected);

				Iterator<Element> filter = visitor_e.getChildren("filter").iterator();

				while (filter.hasNext()) {
					Element filter_e = (Element) filter.next();
					s.addFilter(filter_e.getValue());
				}

				Iterator<Element> seed = visitor_e.getChildren("seed").iterator();

				while (seed.hasNext()) {
					Element seed_e = (Element) seed.next();
					s.addSeed(seed_e.getValue());
				}

				visitorList.add(s);
			}
		}
	}

	public short getCrawlDelay() {
		return Short.parseShort(crawlDelay);
	}

	public String[] getLang() {
		return lang.split(",");
	}

	public int getMaxPages() {
		return Integer.parseInt(maxPages);
	}

	public int getThreadNum() {
		return Integer.parseInt(thread);
	}

	public int getServerModePort() {
		return Integer.parseInt(serverModePort);
	}

	public String getAgent() {
		for (Agent agent : agentList) {
			if (agent.isSelected())
				return agent.getStr();
		}
		return null;
	}

	public String getTempFolder() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("mac") >= 0) {
			return folder_mac;
		}
		if (os.indexOf("win") >= 0) {
			return folder_win;
		}
		return "/";
	}

	public String getFilterPath() {
		return filterPath;
	}

	public Filter[] getFilters() {
		return filterList.toArray(new Filter[0]);
	}

	public String getVisitorPath() {
		return visitorPath;
	}

	public Visitor[] getSelectedVisitors() {
		ArrayList<Visitor> list = new ArrayList<Visitor>();

		for (Visitor v : visitorList) {
			if (v.isSelected())
				list.add(v);
		}

		return list.toArray(new Visitor[0]);
	}

	class Agent {
		private String str;
		private boolean selected;

		public Agent(String str, boolean selected) {
			super();
			this.str = str;
			this.selected = selected;
		}

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

	}

	class Visitor {

		private String url;
		private String visitor;
		private LinkedList<String> filters;
		private LinkedList<String> seeds;
		private boolean selected;

		public Visitor(String url, String visitor, boolean selected) {
			this.url = url;
			this.visitor = visitor;
			this.selected = selected;
			this.filters = new LinkedList<String>();
			this.seeds = new LinkedList<String>();
		}

		public void addFilter(String filter) {
			filters.add(filter);
		}

		public void addSeed(String seed) {
			seeds.add(seed);
		}

		public String getUrl() {
			return url;
		}

		public String getVisitor() {
			return visitor;
		}

		public boolean isSelected() {
			return selected;
		}

		public String[] getfilters() {
			return filters.toArray(new String[0]);
		}

		public String[] getSeeds() {
			return seeds.toArray(new String[0]);
		}
	}

	class Filter {

		private String name;
		private String path;
		private LinkedList<String> datas;

		public Filter(String name, String path) {
			this.name = name;
			this.path = path;

			datas = new LinkedList<String>();
		}

		public void addData(String data) {
			datas.add(data);
		}

		public String getName() {
			return name;
		}

		public String getPath() {
			return path;
		}

		public String[] getDatas() {
			return datas.toArray(new String[0]);
		}

	}

}
