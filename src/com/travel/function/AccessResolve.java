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

import com.travel.data.Access;

public class AccessResolve {

	public static Logger log = Logger.getLogger(AccessResolve.class);

	public Access search(String from, String to) throws IOException {

		String url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins="
				+ from + "&destinations=" + to + "&language=En&sensor=false";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

		log.debug("-> Sending 'GET' request to URL : " + url);

		int responseCode = con.getResponseCode();

		log.debug("-> Response Code : " + responseCode);

		Access data = null;

		if (responseCode > 150 && responseCode < 250) {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			data = new Access();

			ResolveData(response.toString(), data);
		}
		return data;
	}

	private void ResolveData(String json, Access data) {
		data.setResult(json);
		JsonParser content = Json.createParser(new StringReader(json));

		while (content.hasNext()) {
			if (content.next() == JsonParser.Event.KEY_NAME) {

				switch (content.getString().toLowerCase()) {

				case "distance": {
					boolean end = false;
					while (content.hasNext() && !end) {
						switch (content.next()) {
						case KEY_NAME: {

							switch (content.getString().toLowerCase()) {
							case "value":
								if (content.next() == JsonParser.Event.VALUE_NUMBER) {
									data.setDistance(Integer.parseInt(content
											.getString()));
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
				case "duration": {
					boolean end = false;
					while (content.hasNext() && !end) {
						switch (content.next()) {
						case KEY_NAME: {

							switch (content.getString().toLowerCase()) {
							case "value":
								if (content.next() == JsonParser.Event.VALUE_NUMBER) {
									data.setDuration(Integer.parseInt(content
											.getString()));
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
				case "status": {
					if (content.next() == JsonParser.Event.VALUE_STRING) {
						if (content.getString()
								.equalsIgnoreCase("ZERO_RESULTS")) {
							data.setDistance(-Integer.MAX_VALUE);
							data.setDuration(-Integer.MAX_VALUE);
						}
					}
					break;
				}
				}
			}
		}

		log.debug("-> Resolved information: " + data);
	}
}
