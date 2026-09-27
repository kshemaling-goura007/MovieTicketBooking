package com.movie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TheaterManagement {

    public void displayTheaters() {

        String sql = "SELECT * FROM theaters";

        try {

            Connection con = DBConnection.connect();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            System.out.println();
            System.out.println("=============== THEATER MANAGEMENT ===============");

            System.out.printf("| %-5s | %-20s | %-18s | %-8s | %-10s |%n",
                    "ID",
                    "Theater",
                    "Location",
                    "Seats",
                    "Price"
            );

            System.out.println("+-------+----------------------+--------------------+----------+------------+");

            while (rs.next()) {

                System.out.printf("| %-5d | %-20s | %-18s | %-8d | ₹%-9.2f |%n",

                        rs.getInt("theater_id"),
                        rs.getString("theater_name"),
                        rs.getString("location"),
                        rs.getInt("total_seats"),
                        rs.getDouble("ticket_price"));
            }

            System.out.println("+-------+----------------------+--------------------+----------+------------+");

            con.close();

        } 
        
          catch (Exception e) {
                e.printStackTrace();
        }
    }
}
