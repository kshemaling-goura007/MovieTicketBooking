package com.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeatBooking {

    Scanner sc = new Scanner(System.in);

    public List<Integer> bookSeat() {

        List<Integer> bookingIds = new ArrayList<>();

        MovieListing movieListing = new MovieListing();

        TheaterManagement theaterManagement = new TheaterManagement();

        // Display movies
        movieListing.displayMovies();

        System.out.print("\nEnter Movie ID : ");
        int movieId = Integer.parseInt(sc.nextLine());

        System.out.print("Customer Name : ");
        String customerName = sc.nextLine();

        System.out.print("Customer Age : ");
        int customerAge = Integer.parseInt(sc.nextLine());

        // Display theaters
        theaterManagement.displayTheaters();

        System.out.print("\nEnter Theater ID : ");
        int theaterId = Integer.parseInt(sc.nextLine());

        // Number of tickets
        System.out.print("How Many Tickets : ");
        int ticketCount =  Integer.parseInt(sc.nextLine());

        System.out.print(
                "Enter Show Date (YYYY-MM-DD) : "
        );

        String showDate =
                sc.nextLine();

        System.out.print(
                "Enter Show Time (HH:MM:SS) : "
        );

        String showTime =
                sc.nextLine();
        Connection con = null;

        try {

            con = DBConnection.connect();

            // Start transaction
            con.setAutoCommit(false);

           
            // 1. Check movie age restriction
            
            String movieSql = "SELECT age_limit FROM movies " + "WHERE movie_id = ?";

            PreparedStatement moviePs = con.prepareStatement(movieSql);

            moviePs.setInt(1, movieId);

            ResultSet movieRs = moviePs.executeQuery();

            if (!movieRs.next()) {

                System.out.println("\n Movie not found!");
                con.rollback();
                movieRs.close();
                moviePs.close();

                return bookingIds;
            }

            int ageLimit = movieRs.getInt("age_limit");

            movieRs.close();
            moviePs.close();
            
            // Check age
            if (ageLimit == 18 && customerAge < 18) {

                System.out.println("\nBooking Not Allowed!");
                System.out.println("This movie is restricted to 18+.");
                con.rollback();

                return bookingIds;
            }

            
            
            
            
            // 2. Get ticket price            

            String priceSql = "SELECT ticket_price FROM theaters " + "WHERE theater_id = ?";

            PreparedStatement pricePs = con.prepareStatement(priceSql);
            pricePs.setInt(1, theaterId);

            ResultSet priceRs = pricePs.executeQuery();

            if (!priceRs.next()) {
                System.out.println("\n Theater not found!");
                  priceRs.close();
                  pricePs.close();
                  con.rollback();

                return bookingIds;
            }

               double amount = priceRs.getDouble("ticket_price");
                   priceRs.close();
                   pricePs.close();

          
            // 3. Book multiple seats
            

            for (int i = 1; i <= ticketCount; i++) {

                System.out.print("Enter Seat " + i + " (A1-E10) : ");
                String seatNo = sc.nextLine().toUpperCase();

                // Validate seat
                if (!seatNo.matches("^[A-E](10|[1-9])$")) {

                    System.out.println("\n Invalid Seat Number!");
                    con.rollback();
                    bookingIds.clear();

                    return bookingIds;
                }

                
                
                // Check duplicate seat
                String seatSql = "SELECT booking_id FROM bookings " +
                                 "WHERE theater_id = ? " + "AND seat_no = ?";

                PreparedStatement seatPs = con.prepareStatement(seatSql);

                seatPs.setInt(1, theaterId);
                seatPs.setString(2, seatNo);

                ResultSet seatRs = seatPs.executeQuery();

                if (seatRs.next()) {
                    System.out.println("\n Seat " + seatNo + " is already booked!");

                    seatRs.close();
                    seatPs.close();

                    con.rollback();
                    bookingIds.clear();

                    return bookingIds;
                }

                seatRs.close();
                seatPs.close();

                
               
                // Insert booking
                String insertSql =   "INSERT INTO bookings " + "(movie_id, theater_id, " + "customer_name, customer_age, " +
                		               "seat_no, amount, show_date, show_time) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement insertPs = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

                insertPs.setInt(1, movieId);
                insertPs.setInt(2, theaterId);
                insertPs.setString(3, customerName);
                insertPs.setInt(4, customerAge);
                insertPs.setString(5, seatNo);
                insertPs.setDouble(6, amount);
                insertPs.setString(7, showDate);
                insertPs.setString(8, showTime);

                insertPs.executeUpdate();

                
                // Get booking ID
                ResultSet keys = insertPs.getGeneratedKeys();

                if (keys.next()) {
                    int bookingId = keys.getInt(1);
                    bookingIds.add(bookingId);
                }

                keys.close();
                insertPs.close();
            }

  
            
            
            // 4. Commit all bookings
           
            con.commit();

            System.out.println();
            System.out.println(" ALL SEATS BOOKED SUCCESSFULLY!");

            System.out.println(
                    "Total Tickets : " + ticketCount
            );

            System.out.println(
                    "Total Amount   : ₹" +
                    (amount * ticketCount)
            );

        } 
        
        catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            bookingIds.clear();
            System.out.println("\nBooking Failed!");

            e.printStackTrace();

        } 
        
        finally {

            try {
                if (con != null) {
                    con.close();
                }

            } 
            
            
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bookingIds;
    }
}