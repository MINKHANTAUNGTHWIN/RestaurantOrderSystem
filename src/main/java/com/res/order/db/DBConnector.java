package com.res.order.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
	private static Connection connection;
	
	public static Connection getConnection() {
		return connection == null ? createConnection() : connection;
	}
	
	private static Connection createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");
			connection = conn;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	


}
