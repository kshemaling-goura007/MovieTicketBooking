CREATE DATABASE movie_booking;
USE movie_booking;

-- Movies Table
CREATE TABLE movies (
      movie_id INT PRIMARY KEY AUTO_INCREMENT,
	  movie_name VARCHAR(100),
      genre VARCHAR(50),
      language VARCHAR(30),
	  duration INT,
      age_limit INT
);


-- Threaters Table
CREATE TABLE theaters (
    theater_id INT PRIMARY KEY AUTO_INCREMENT,
    theater_name VARCHAR(100),
    location VARCHAR(100),
    total_seats INT,
    ticket_price DOUBLE
);

-- Bookings Table
CREATE TABLE bookings (
             booking_id INT PRIMARY KEY AUTO_INCREMENT,
             movie_id INT,
             theater_id INT,
             customer_name VARCHAR(100),
             customer_age INT,
             seat_no VARCHAR(10),
             amount DOUBLE,
             booking_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (theater_id) REFERENCES theaters(theater_id),

    UNIQUE (theater_id, seat_no)
);

             INSERT INTO movies(movie_name, genre, language, duration, age_limit)
                      VALUES
                           ('Kantara', 'Action', 'Kannada', 148, 0),
                           ('TOXIC', 'Action', 'Kannada', 168, 18),
                           ('The Paradise', 'Action Drama', 'Telugu', 182, 0),
                           ('SpiderMan BND', 'Action', 'English', 170, 0),
						   ('Pushpa 2', 'Action Drama', 'Telugu', 200, 18),
                           ('Salaar', 'Action', 'Telugu', 175, 18),
                           ('Leo', 'Action Thriller', 'Tamil', 164, 18),
                           ('Jailer', 'Action', 'Tamil', 168, 18),
                           ('Vikram', 'Action Thriller', 'Tamil', 174, 18),
                           ('Baahubali 2', 'Epic Action', 'Telugu', 167, 0),
						   ('Lucifer', 'Action Thriller', 'Malayalam', 175, 18),
                           ('Drishyam 2', 'Crime Thriller', 'Hindi', 140, 0),
                           ('Dangal', 'Sports Drama', 'Hindi', 161, 0),
						   ('Chhaava', 'Historical Drama', 'Hindi', 161, 0),
                           ('12th Fail', 'Drama', 'Hindi', 147, 0),
                           ('Kirik Party', 'Comedy Drama', 'Kannada', 165, 0),
                           ('Charlie 777', 'Adventure Drama', 'Kannada', 164, 0),
						   ('Ugramm', 'Action', 'Kannada', 132, 18),
                           ('Sapta Sagaradaache Ello', 'Romance Drama', 'Kannada', 140, 0);
                           
      
INSERT INTO theaters(theater_name, location, total_seats, ticket_price)
         VALUES
           ('PVR Cinemas', 'Mumbai', 50, 250),
		   ('INOX', 'Bengaluru', 50, 300),
           ('Cinepolis', 'Hyderabad', 50, 280);
           

DESC theaters;
SELECT * FROM theaters;

Select * from bookings;
CREATE TABLE bookings (
         booking_id INT PRIMARY KEY AUTO_INCREMENT,
         movie_id INT,
         theater_id INT,
         customer_name VARCHAR(100),
         customer_age INT,
         seat_no VARCHAR(10),
         amount DOUBLE,
    
         booking_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

         FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
         FOREIGN KEY (theater_id) REFERENCES theaters(theater_id),
       UNIQUE (theater_id, seat_no)
);

DESC bookings;

ALTER TABLE bookings
ADD show_date DATE,
ADD show_time TIME;


select * from bookings;

SHOW COLUMNS FROM bookings;