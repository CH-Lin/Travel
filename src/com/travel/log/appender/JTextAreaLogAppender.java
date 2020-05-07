package com.travel.log.appender;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

public class JTextAreaLogAppender extends WriterAppender {

	public static JTextArea logTextArea;
	public static JScrollPane logScrollPane;

	@Override
	public void append(LoggingEvent loggingEvent) {

		final String logMessage = this.layout.format(loggingEvent);

		if (logTextArea != null && logScrollPane != null) {
			logTextArea.setText(logTextArea.getText() + logMessage);
			logScrollPane.getVerticalScrollBar().setValue(
					logScrollPane.getVerticalScrollBar().getMaximum());
		}
	}
}