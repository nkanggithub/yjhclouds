package com.nkang.kxmoment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFetcher {

	public static final String ACTIONPATH = "database-info.properties";

	public static final Properties prop = new Properties();

	public static InputStream fis;

	public static String result;

	public static String getKeyFromPropertyFile(String fileName, String key) throws IOException {

		try {
//			String path = PropertyFetcher.class.getClassLoader()
//					.getResource("").toURI().getPath();
			fis = PropertyFetcher.class.getClassLoader().getResourceAsStream(fileName);
//			fis = new FileInputStream(new File(path + ACTIONPATH));
			prop.load(fis);
			if (prop.isEmpty()) {
				result = "There is no matched file!";
			} else {
				result = prop.get(key).toString();
			}
		} catch (Exception e) {
			result = e.getMessage();
		} finally {
			fis.close();
		}

		return result;
	}

}