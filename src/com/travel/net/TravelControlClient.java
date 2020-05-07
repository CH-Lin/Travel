package com.travel.net;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.travel.TravelConfig;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TravelControlClient extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5534901101184186461L;
	private JTextField text_IP;

	Socket socket;
	DataOutputStream dout;
	DataInputStream din;
	private JTextField text_Port;

	public TravelControlClient() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		getContentPane().setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1160, 70));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setOpaque(false);
		getContentPane().add(panel, BorderLayout.CENTER);

		Vector<String> v = new Vector<String>();
		v.add("DEFAULT");
		v.add("ALL");
		for (String langs : new TravelConfig().getLang())
			v.add(langs.toUpperCase());

		JLabel lblServerIp = new JLabel("Server IP:");
		panel.add(lblServerIp);

		text_IP = new JTextField("127.0.0.1");
		text_IP.setColumns(10);
		panel.add(text_IP);

		JLabel lblPort = new JLabel("Port:");
		panel.add(lblPort);

		text_Port = new JTextField("16842");
		text_Port.setColumns(10);
		panel.add(text_Port);

		JButton btnConnect = new JButton("Connect");
		btnConnect.setPreferredSize(new Dimension(90, 23));
		btnConnect.addActionListener(this);
		panel.add(btnConnect);

		JButton btnDrop = new JButton("Drop");
		btnDrop.setPreferredSize(new Dimension(90, 23));
		btnDrop.setOpaque(false);
		btnDrop.addActionListener(this);
		panel.add(btnDrop);

		JButton btnCreate = new JButton("Create");
		btnCreate.setPreferredSize(new Dimension(90, 23));
		btnCreate.setOpaque(false);
		btnCreate.addActionListener(this);
		panel.add(btnCreate);

		JButton btnShow = new JButton("List");
		btnShow.addActionListener(this);
		btnShow.setPreferredSize(new Dimension(90, 23));
		btnShow.setOpaque(false);
		panel.add(btnShow);

		JButton btnFind = new JButton("Crawl");
		btnFind.setPreferredSize(new Dimension(90, 23));
		btnFind.setOpaque(false);
		btnFind.addActionListener(this);
		panel.add(btnFind);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(this);
		btnStop.setOpaque(false);
		btnStop.setPreferredSize(new Dimension(90, 23));
		panel.add(btnStop);

		JButton btnUpload = new JButton("Upload");
		btnUpload.addActionListener(this);
		btnUpload.setOpaque(false);
		btnUpload.setPreferredSize(new Dimension(90, 23));
		panel.add(btnUpload);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		final String cmd = e.getActionCommand();

		if (cmd.equalsIgnoreCase("connect")) {

			try {
				socket = new Socket(text_IP.getText(),
						Integer.parseInt(text_Port.getText()));
				din = new DataInputStream(socket.getInputStream());
				dout = new DataOutputStream(socket.getOutputStream());
				JButton jb = (JButton) e.getSource();
				jb.setText("Disconnect");
			} catch (IOException e1) {
				if (e1.getMessage().equalsIgnoreCase(
						"Connection refused: connect")) {
					JOptionPane.showMessageDialog(this,
							"Connot connect to server");
				} else {
					e1.printStackTrace();
				}
			}

		} else if (cmd.equalsIgnoreCase("disconnect")) {

			try {
				dout.writeUTF("logout");
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JButton jb = (JButton) e.getSource();
			jb.setText("Connect");

		} else if (cmd.equalsIgnoreCase("upload")) { // upload spot file

			try {

				String filename = null;

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"XML", "xml");
				chooser.setFileFilter(filter);
				chooser.setMultiSelectionEnabled(true);
				int returnVal = chooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					for (File f : chooser.getSelectedFiles()) {

						filename = f.getAbsolutePath();

						dout.writeUTF("upload");
						dout.writeUTF(filename.substring(filename
								.lastIndexOf(System
										.getProperty("file.separator")) + 1));

						FileInputStream fis = new FileInputStream(filename);

						byte buf[] = new byte[2048];
						int read = fis.read(buf);

						while (read != -1) {
							dout.write(buf, 0, read);
							dout.flush();
							read = fis.read(buf);
						}

						fis.close();

						System.out.println(din.readUTF());
						System.out.println(din.readUTF());
						System.out.println(din.readUTF());
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else {

			if (dout != null) {
				try {
					dout.writeUTF(cmd);
					System.out.println("Command " + cmd + " execute: "
							+ din.readUTF() + ".");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
	}
}
