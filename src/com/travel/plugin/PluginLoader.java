package com.travel.plugin;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PluginLoader extends ClassLoader {

	public Class<?> load(String name) {

		Class<?> c = null;
		try {
			c = Class.forName(name);
		} catch (ClassNotFoundException e) {
			byte[] b = loadClassData(name);
			c = defineClass(name, b, 0, b.length);
		}

		return c;
	}

	private byte[] loadClassData(String name) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(name.replace(".",
					System.getProperty("file.separator"))
					+ ".class");

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int data = fis.read();

			while (data != -1) {
				buffer.write(data);
				data = fis.read();
			}

			fis.close();

			byte[] classData = buffer.toByteArray();
			return classData;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
