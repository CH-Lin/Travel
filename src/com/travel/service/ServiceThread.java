package com.travel.service;

import org.apache.log4j.Logger;

public class ServiceThread extends Thread {

	public static Logger log = Logger.getLogger(ServiceThread.class);

	protected boolean running = false;
	protected boolean update = false;

	public void pause(long sec) {
		try {
			if (sec > 1000)
				log.info("Waiting for " + sec / 1000 + " seconds.");
			Thread.sleep(sec);
		} catch (InterruptedException e) {
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void stopRunning() {
		running = false;
		super.interrupt();
	}

	public void setUpdate(boolean updateGPS) {
		this.update = updateGPS;
	}
}
