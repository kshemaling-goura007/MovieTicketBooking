package com.movie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MovieListing {

	public void displayMovies() { 
		
		String sql = "Select * From Movies";
		
		try { 
			
			Connection con = DBConnection.connect();
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			System.out.println();
			System.out.println(
				    "================ MOVIE LISTING ==================");
			
			System.out.printf(
					"| %-5s | %-20s | %-15s | %-10s | %-8s | %-10s |%n",
                    "ID",
                    "Movie",
                    "genre",
                    "Language",
                    "Minutes",
                    "Age"
			 );
			
			System.out.println(
	                "+-------+----------------------+-----------------+------------+----------+------------+"
	            );
			
			
			while(rs.next()) {
				int ageLimit = rs.getInt("age_limit");
				
				String ageText;
				
				if(ageLimit == 18) {
					ageText = "18+";
				}
				else {
					ageText = "All Ages"; 
				}
				
			System.out.printf(
					"| %-5d | %-20s | %-15s | %-10s | %-8d | %-10s |%n",

                        rs.getInt("movie_id"),
                        rs.getString("movie_name"),
                        rs.getString("genre"),
                        rs.getString("language"),
                        rs.getInt("duration"),
                        ageText
			      );
		}
		
		System.out.println(
                "+-------+----------------------+-----------------+------------+----------+------------+"
            );
		
		  con.close();

	}catch(Exception e) { 
		
		e.printStackTrace();
		}
	}
}
