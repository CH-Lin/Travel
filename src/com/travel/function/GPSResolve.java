package com.travel.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.apache.log4j.Logger;

import com.travel.data.GeoData;

public class GPSResolve {

	public static Logger log = Logger.getLogger(GPSResolve.class);

	public GeoData search(String address) throws IOException {

		String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
				+ address;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

		log.info("-> Sending 'GET' request to URL : " + url);

		int responseCode = con.getResponseCode();

		log.info("-> Response Code : " + responseCode);

		GeoData data = null;

		if (responseCode > 150 && responseCode < 250) {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			data = new GeoData();
			data.setAddress(address);
			data.setResult(response.toString());

			ResolveData(response.toString(), data);
		}
		return data;
	}

	private void ResolveData(String json, GeoData data) {
		JsonParser content = Json.createParser(new StringReader(json));

		String formatted_address = "";

		while (content.hasNext()) {
			if (content.next() == JsonParser.Event.KEY_NAME) {

				switch (content.getString().toLowerCase()) {
				case "formatted_address":
					if (content.next() == JsonParser.Event.VALUE_STRING) {
						formatted_address = content.getString();
					}
					break;

				case "location":
					boolean end = false;
					while (content.hasNext() && !end) {
						switch (content.next()) {
						case KEY_NAME: {

							switch (content.getString().toLowerCase()) {
							case "lat":
								if (content.next() == JsonParser.Event.VALUE_NUMBER) {
									data.setLatitude(Double.parseDouble(content
											.getString()));
								}
								break;
							case "lng":
								if (content.next() == JsonParser.Event.VALUE_NUMBER) {
									data.setLongitude(Double
											.parseDouble(content.getString()));
								}
								break;
							}

							break;
						}
						case END_OBJECT:
							end = true;
						default:
						}
					}
					break;
				}
			}
		}

		StringBuilder builder = new StringBuilder();
		builder.append("Location [formatted_address=")
				.append(formatted_address).append(", lat=")
				.append(data.getLatitude()).append(", lng=")
				.append(data.getLongitude()).append("]");

		log.info("-> Resolved information: " + builder.toString());
	}
}
