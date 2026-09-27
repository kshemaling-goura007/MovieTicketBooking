package com.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TicketGeneration {

    public void generateTicket(List<Integer> bookingIds) {

        String sql =
                "SELECT " +
                "b.booking_id, " +
                "b.customer_name, " +
                "b.customer_age, " +
                "b.seat_no, " +
                "b.amount, " +
                "b.booking_date_time, " +
                "b.show_date AS show_date, " +
                "b.show_time AS show_time, " +
                "m.movie_name, " +
                "m.genre, " +
                "m.language, " +
                "t.theater_name, " +
                "t.location " +
                "FROM bookings b " +
                "JOIN movies m " +
                "ON b.movie_id = m.movie_id " +
                "JOIN theaters t " +
                "ON b.theater_id = t.theater_id " +
                "WHERE b.booking_id = ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DBConnection.connect();

            String customerName = "";
            int customerAge = 0;

            String movieName = "";
            String genre = "";
            String language = "";

            String theaterName = "";
            String location = "";

            String bookingDateTime = "";
            
            double amount = 0;
            
            String showDate = "";

            String showTime = "";

            StringBuilder seats = new StringBuilder();

            for (int i = 0; i < bookingIds.size(); i++) {

                ps = con.prepareStatement(sql);
                ps.setInt(1, bookingIds.get(i));

                rs = ps.executeQuery();

                if (rs.next()) {

                    customerName = rs.getString("customer_name");
                    customerAge = rs.getInt("customer_age");
                    movieName = rs.getString("movie_name");
                    genre = rs.getString("genre");
                    language = rs.getString("language");
                    theaterName = rs.getString("theater_name");
                    location = rs.getString("location");
                    bookingDateTime = rs.getTimestamp("booking_date_time").toString();
                    amount += rs.getDouble("amount");
                    showDate = rs.getDate("show_date").toString();
                    showTime = rs.getTime("show_time").toString();

                    if (seats.length() > 0) {
                        seats.append(", ");
                    }

                    seats.append(rs.getString("seat_no"));
                }

                rs.close();
                ps.close();
            }

            System.out.println();

            System.out.println("|-----------------------------------------------|");
            System.out.println("|              MOVIE TICKET                     |");
            System.out.println("|-----------------------------------------------|");

            System.out.printf("| Customer   : %-32s|%n", customerName);
            System.out.printf("| Age        : %-32d|%n", customerAge);
            System.out.printf("| Movie      : %-32s|%n", movieName);
            System.out.printf("| Genre      : %-32s|%n", genre);
            System.out.printf("| Language   : %-32s|%n", language);
            System.out.printf("| Theater    : %-32s|%n", theaterName);
            System.out.printf("| Location   : %-32s|%n", location);
            System.out.printf("| Seats      : %-32s|%n", seats);
            System.out.printf("| Tickets    : %-32d|%n", bookingIds.size());
            System.out.printf("| Total Amount : ₹%-29.2f|%n", amount);
            System.out.printf("| Show Date    : %-29s|%n", showDate);
            System.out.printf( "| Show Time    : %-29s|%n", showTime);

            System.out.println("|-----------------------------------------------|");
            System.out.println("|             ENJOY YOUR MOVIE!                 |");
            System.out.println("|-----------------------------------------------|");

        } 
        catch (Exception e) {
            e.printStackTrace();

        } 
        
        finally {

                 try {

                     if (rs != null) {
                      rs.close();
                     }

                     if (ps != null) {
                      ps.close();
                     }

                    if (con != null) {
                     con.close();
                    }

                 } 
                 catch (Exception e) {
                     e.printStackTrace();
            }
        }
    }
}