package com.travel.filter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.travel.data.BasicSpotData;

public class DataJPFilter extends DataFilter {

	public static Logger log = Logger.getLogger(DataJPFilter.class);

	HashMap<String, Prefecture> prefectureMap;

	public DataJPFilter(String... areas) {

		prefectureMap = new HashMap<String, Prefecture>();

		for (String area : areas) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(area), "UTF-8"));

				String read = "";
				while ((read = br.readLine()) != null) {
					String line[] = read.split(",");
					prefectureMap.put(line[2], new Prefecture(line[0], line[1],
							line[2], line[3]));
				}
				br.close();
			} catch (IOException e) {
				log.error(e);
			}
		}

	}

	@Override
	public void doFilter(BasicSpotData data) {

		for (Prefecture p : prefectureMap.values()) {
			if (data.getSpot().getAddress().indexOf(p.getName()) != -1) {
				data.setArea(p.getArea());
				data.setSubarea(p.getSubarea());
			}
		}

	}

	class Prefecture {
		private String name;
		private String area;
		private String subarea;
		private String capital;

		public Prefecture(String area, String subarea, String name,
				String capital) {
			this.name = name;
			this.area = area;
			this.subarea = subarea;
			this.capital = capital;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getSubarea() {
			return subarea;
		}

		public void setSubarea(String subarea) {
			this.subarea = subarea;
		}

		public String getCapital() {
			return capital;
		}

		public void setCapital(String capital) {
			this.capital = capital;
		}

		@Override
		public String toString() {
			return "Prefecture [name=" + name + ", area=" + area + ", subarea="
					+ subarea + ", capital=" + capital + "]";
		}
	}

}
