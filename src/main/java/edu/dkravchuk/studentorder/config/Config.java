package edu.dkravchuk.studentorder.config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	public static String DB_URL = "db.url";
	public static String DB_LOGIN = "db.login";
	public static String DB_PASSWORD = "db.password";
	public static String DB_LIMIT = "db.limit";

	private static Properties properties = new Properties();

	public synchronized static String getProperty(String name) {

		if (properties.isEmpty()) {
			try (InputStream is = Config.class.getClassLoader().getResourceAsStream("dao.properties")) {

				properties.load(is);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return properties.getProperty(name);
	}
}
