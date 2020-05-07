package com.travel;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.travel.net.TravelControlClient;
import com.travel.net.TravelControlServer;
import com.travel.ui.InfoFrame;

/***
 * The TravelControl class is the enter point of the travel control crawler.
 * 
 * Four types of mode can be used, including command line, server, client, and
 * desktop mode.
 * 
 * The client mode must be used with server mode. The server mode waits clients'
 * connections to perform specific tasks.
 ***/
public class Travel {

	public static Logger log = Logger.getLogger(Travel.class);

	public static void main(String args[]) {

		PropertyConfigurator.configure("config"
				+ System.getProperty("file.separator") + "Log4j.pr");

		// TravelCore class, used to perform all of the task.
		TravelControl tc = new TravelControl();

		// parse the input arguments
		for (String arg : args) {

			switch (arg.toLowerCase()) {
			case "server": // server mode
				new TravelControlServer(new TravelConfig().getServerModePort());
				break;
			case "client": // client mode
				new TravelControlClient();
				break;
			default: // directly pass the arguments to the kernel
				if (!tc.commandParser(arg)) {
					// show parameters
					log.info("Unknow Arguments");
				}
			}

		}

		if (args.length == 0) {
			InfoFrame frame = new InfoFrame(tc);
			frame.initLog();
			frame.setVisible(true);
		}

	}
}
