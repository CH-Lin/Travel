package com.travel.ui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.text.AbstractDocument;

import com.travel.TravelConfig;
import com.travel.TravelControl;
import com.travel.log.appender.JTextAreaLogAppender;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.io.File;
import java.util.Vector;

public class InfoFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 5534901101184186461L;
	private JScrollPane logScrollPane;
	private JTextArea logTextArea;
	private TravelControl core;
	private JComboBox<String> comboBox;

	public InfoFrame(TravelControl k) {
		this.core = k;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				core.commandParser("stbu");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				core = null;
			}
		});

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		getContentPane().setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1280, 450));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		logTextArea = new JTextArea();
		logTextArea.setColumns(20);
		logTextArea.setRows(5);
		// ((AbstractDocument) logTextArea.getDocument())
		// .setDocumentFilter(new MyDocumentFilter(logTextArea, 100));
		logScrollPane = new JScrollPane();
		logScrollPane.setViewportView(logTextArea);
		getContentPane().add(logScrollPane);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(140, 10));
		panel.setOpaque(false);
		getContentPane().add(panel, BorderLayout.WEST);

		Vector<String> v = new Vector<String>();
		v.add("DEFAULT");
		v.add("ALL");
		for (String langs : new TravelConfig().getLang())
			v.add(langs.toUpperCase());
		comboBox = new JComboBox<String>(v);
		comboBox.setPreferredSize(new Dimension(140, 21));
		comboBox.setOpaque(false);
		panel.add(comboBox);

		JButton btnShow = new JButton("List Tables");
		btnShow.addActionListener(this);
		btnShow.setPreferredSize(new Dimension(140, 23));
		btnShow.setOpaque(false);
		panel.add(btnShow);

		JButton btnDrop = new JButton("Drop Tables");
		btnDrop.setPreferredSize(new Dimension(140, 23));
		btnDrop.setOpaque(false);
		btnDrop.addActionListener(this);
		panel.add(btnDrop);

		JButton btnCreate = new JButton("Create Tables");
		btnCreate.setPreferredSize(new Dimension(140, 23));
		btnCreate.setOpaque(false);
		btnCreate.addActionListener(this);
		panel.add(btnCreate);

		JButton btnUpload = new JButton("Import Data");
		btnUpload.addActionListener(this);
		btnUpload.setOpaque(false);
		btnUpload.setPreferredSize(new Dimension(140, 23));
		panel.add(btnUpload);

		JButton btnExport = new JButton("Export Data");
		btnExport.addActionListener(this);
		btnExport.setPreferredSize(new Dimension(140, 23));
		btnExport.setOpaque(false);
		panel.add(btnExport);

		JButton btnGPS = new JButton("Resolve GPS");
		btnGPS.addActionListener(this);
		btnGPS.setPreferredSize(new Dimension(140, 23));
		btnGPS.setOpaque(false);
		panel.add(btnGPS);

		JButton btnUpdateGPS = new JButton("Update GPS");
		btnUpdateGPS.addActionListener(this);
		btnUpdateGPS.setPreferredSize(new Dimension(140, 23));
		btnUpdateGPS.setOpaque(false);
		panel.add(btnUpdateGPS);

		JButton btnStopGPS = new JButton("Stop GPS");
		btnStopGPS.addActionListener(this);
		btnStopGPS.setPreferredSize(new Dimension(140, 23));
		btnStopGPS.setOpaque(false);
		panel.add(btnStopGPS);

		JButton btnBuild = new JButton("Build Access");
		btnBuild.addActionListener(this);
		btnBuild.setPreferredSize(new Dimension(140, 23));
		btnBuild.setOpaque(false);
		panel.add(btnBuild);

		JButton btnStopBuild = new JButton("Stop Build Access");
		btnStopBuild.addActionListener(this);
		btnStopBuild.setPreferredSize(new Dimension(140, 23));
		btnStopBuild.setOpaque(false);
		panel.add(btnStopBuild);

		JButton btnFind = new JButton("Crawl Data");
		btnFind.setPreferredSize(new Dimension(140, 23));
		btnFind.setOpaque(false);
		btnFind.addActionListener(this);
		panel.add(btnFind);

		JButton btnStop = new JButton("Stop Crawl");
		btnStop.addActionListener(this);
		btnStop.setOpaque(false);
		btnStop.setPreferredSize(new Dimension(140, 23));
		panel.add(btnStop);

		JButton btnClear = new JButton("Clear Console");
		btnClear.addActionListener(this);
		btnClear.setOpaque(false);
		btnClear.setPreferredSize(new Dimension(140, 23));
		panel.add(btnClear);

		pack();
		setLocationRelativeTo(null);
	}

	public void initLog() {
		JTextAreaLogAppender.logTextArea = logTextArea;
		JTextAreaLogAppender.logScrollPane = logScrollPane;
	}

	public void actionPerformed(ActionEvent e) {
		final String cmd = e.getActionCommand().toLowerCase();

		if (cmd.equalsIgnoreCase("Import Data")) {
			final JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("XML",
					"xml");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(true);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				new Thread() {
					public void run() {
						for (File f : chooser.getSelectedFiles()) {
							core.commandParser(cmd + ":" + f.getAbsolutePath());
						}
					}
				}.start();
			} else
				return;
		} else if (cmd.equalsIgnoreCase("Export Data")) {
			final JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("XML",
					"xml");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			int returnVal = chooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				new Thread() {
					public void run() {
						core.commandParser(cmd + ":"
								+ chooser.getSelectedFile().getAbsolutePath());
					}
				}.start();
			} else
				return;
		} else if (cmd.equalsIgnoreCase("Clear Console")) {
			logTextArea.setText("");
		} else {
			new Thread() {
				public void run() {
					core.commandParser(cmd);
				}
			}.start();
		}
	}
}