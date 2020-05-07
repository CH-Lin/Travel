package com.travel.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.travel.TravelControl;

public class TravelControlServer {

	public static Logger log = Logger.getLogger(TravelControlServer.class);

	// private Vector<AcceptClient> Clients;

	public TravelControlServer(int port) {
		ServerSocket soc = null;
		try {
			soc = new ServerSocket(port);

			// Clients = new Vector<AcceptClient>();
			log.info("Crawler server start.");

			while (true) {
				Socket CSoc = soc.accept();
				new AcceptClient(CSoc);
				// Clients.add(new AcceptClient(CSoc));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				soc.close();
			} catch (IOException e) {
			}
		}
	}

	class AcceptClient extends Thread {

		private Socket ClientSocket;
		private DataInputStream din;
		private DataOutputStream dout;

		private TravelControl k;

		AcceptClient(Socket CSoc) throws IOException, SQLException {
			ClientSocket = CSoc;

			k = new TravelControl();
			din = new DataInputStream(ClientSocket.getInputStream());
			dout = new DataOutputStream(ClientSocket.getOutputStream());

			log.info("Client [" + ClientSocket.getInetAddress().toString()
					+ "] is connected.");

			start(); // start client thread
		}

		public void run() {
			while (true) {
				try {
					String msg = din.readUTF();
					log.info("Command " + msg + " received.");

					if (msg.toLowerCase().equals("logout")) {
						break;
					} else if (msg.toLowerCase().equals("upload")) {

						String filename = din.readUTF();

						log.info("Receiving file: " + filename + ".");

						FileOutputStream fos = new FileOutputStream(filename);

						byte recv[] = new byte[2048];
						int read = din.read(recv);

						while (read != -1) {
							fos.write(recv, 0, read);
							fos.flush();
							if (read < 2048)
								break;
							read = din.read(recv);
						}
						fos.close();

						dout.writeUTF("Receiving file: " + filename
								+ " success.");
						log.info("Receiving file: " + filename + " success.");
						dout.writeUTF("Import file: " + filename + ".");
						log.info("Import file: " + filename + ".");
						k.commandParser("import:" + filename);
						log.info("Import file: " + filename + " success.");
						dout.writeUTF("Import file: " + filename + " success.");

					} else {
						if (k.commandParser(msg)) {
							log.info("Command " + msg
									+ " is executed successfully.");
							dout.writeUTF("success");
						} else {
							log.info("Execution of command " + msg + " FAIL.");
							dout.writeUTF("fail");
						}
					}

				} catch (SocketException ex) {
					if (ex.getMessage().equals("Connection reset")) {
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			log.info("Client [" + ClientSocket.getInetAddress().toString()
					+ "] leaved.");
		}
	}
}
