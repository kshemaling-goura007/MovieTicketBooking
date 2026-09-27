package com.movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        MovieListing movieListing = new MovieListing();
        TheaterManagement theaterManagement = new TheaterManagement();
        SeatBooking seatBooking = new SeatBooking();
        TicketGeneration ticketGeneration = new TicketGeneration();

        List<Integer> bookingIds = new ArrayList<>();
        
        while (true) {

            System.out.println();
            System.out.println("================================================");
            System.out.println("        MOVIE TICKET BOOKING SYSTEM ");
            System.out.println("================================================");

            System.out.println("1. Movie Listing");
            System.out.println("2. Theater Management");
            System.out.println("3. Seat Booking");
            System.out.println("4. Ticket Generation");
            System.out.println("5. Exit");

            System.out.print("\nEnter Choice : ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    movieListing.displayMovies();
                    break;

                    
                case 2:
                    theaterManagement.displayTheaters();
                    break;

                case 3:

                    bookingIds = seatBooking.bookSeat();
                    break;  
                
                    
                case 4:
                	
                    if (bookingIds.isEmpty()) {
                        System.out.println( "\nNo booking found!");
                        System.out.println("Please book seats first.");
                    } 
                    else {
                        ticketGeneration.generateTicket(bookingIds);
                    }

                    break;

                    
                case 5:

                    System.out.println("\nThank you for using Movie Ticket Booking System!");
                    sc.close();
                    System.exit(0);
                    break;

                    
                default:
                    System.out.println("\n Invalid Choice!");
            }
        }
    }
}