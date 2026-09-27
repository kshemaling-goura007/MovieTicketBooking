package com.movie;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	static final String url = "jdbc:mysql://localhost:3306/movie_booking";
	static final String user = "root";
	static final String password = "tiger";
	
	public static Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection(url, user, password);
			

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
		
	}
}
