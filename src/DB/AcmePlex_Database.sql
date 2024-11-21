-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: acmeplex
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS acmeplex;
CREATE DATABASE acmeplex;
USE acmeplex;

-- Drop the user if it exists
DROP USER IF EXISTS 'ENSF480'@'localhost';

-- Create the user and grant privileges
CREATE USER 'ENSF480'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON acmeplex.* TO 'ENSF480'@'localhost';
FLUSH PRIVILEGES;

DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies` (
  `movieID` int auto_increment NOT NULL,
  `Movie_name` varchar(60) DEFAULT NULL,
  `Movie_description` varchar(150) DEFAULT NULL,
  `Movie_Genre` varchar(30) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  PRIMARY KEY (`movieID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for movies
INSERT INTO `movies` (`Movie_name`, `Movie_description`, `Movie_Genre`, `duration`) VALUES
('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology.', 'Sci-Fi', 100),
('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'Crime', 120),
('The Dark Knight', 'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 'Action', 58);

DROP TABLE IF EXISTS `theatre`;
CREATE TABLE `theatre` (
  `theatre_id` int auto_increment NOT NULL,
  `theatre_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`theatre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for theatre
INSERT INTO `theatre` (`theatre_name`) VALUES
('Main Theatre'),
('VIP Theatre');

DROP TABLE IF EXISTS `auditorium`;
CREATE TABLE `auditorium` (
  `auditorium_id` int auto_increment NOT NULL,
  `theatre_id` int DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  PRIMARY KEY (`auditorium_id`),
  CONSTRAINT `auditorium_ibfk_1` FOREIGN KEY (`theatre_id`) REFERENCES `theatre` (`theatre_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for auditorium
INSERT INTO `auditorium` (`theatre_id`, `capacity`) VALUES
(1, 50),
(1, 50),
(2, 50),
(2, 50);


DROP TABLE IF EXISTS `showtimes`;
CREATE TABLE `showtimes` (
  `showtimeID` int auto_increment NOT NULL,
  `time` DateTime DEFAULT NULL,
  `movieID` int DEFAULT NULL,
  `auditoriumID` int DEFAULT NULL,
  PRIMARY KEY (`showtimeID`),
  CONSTRAINT `fk_movieID` FOREIGN KEY (`movieID`) REFERENCES `movies` (`movieID`) ON DELETE CASCADE,
  CONSTRAINT `fk_theatreID` FOREIGN KEY (`auditoriumID`) REFERENCES `auditorium` (`auditorium_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for showtimes
INSERT INTO `showtimes` (`time`, `movieID`, `auditoriumID`) VALUES
('2024-11-12 19:00:00', 1, 1),
('2024-11-12 21:00:00', 2, 1),
('2024-11-13 18:00:00', 3, 2),
('2024-11-13 17:00:00', 1, 1);

DROP TABLE IF EXISTS `containsMovie`;
CREATE TABLE `containsMovie` (
  `theatre_id` int DEFAULT NULL,
  `movieID` int DEFAULT NULL,
  CONSTRAINT `containsMovie_ibfk_1` FOREIGN KEY (`theatre_id`) REFERENCES `theatre` (`theatre_id`) ON DELETE CASCADE,
  CONSTRAINT `containsMovie_ibfk_2` FOREIGN KEY (`movieID`) REFERENCES `movies` (`movieID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for containsMovie
INSERT INTO `containsMovie` (`theatre_id`, `movieID`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3);

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `CustomerID` int DEFAULT NULL,
  `TheaterID` int DEFAULT NULL,
  `movieID` int DEFAULT NULL,
  `SeatRow` char(1) DEFAULT NULL,
  `SeatColumn` int DEFAULT NULL,
  `SeatNumber` int DEFAULT NULL, -- Corrected column definition
  PRIMARY KEY (`ID`),
  KEY `CustomerID` (`CustomerID`),
  KEY `TheaterID` (`TheaterID`),
  KEY `movieID` (`movieID`),
  CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `user` (`UserId`) ON DELETE CASCADE,
  CONSTRAINT `tickets_ibfk_2` FOREIGN KEY (`TheaterID`) REFERENCES `theatre` (`theatre_id`) ON DELETE CASCADE,
  CONSTRAINT `tickets_ibfk_3` FOREIGN KEY (`movieID`) REFERENCES `movies` (`movieID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserId` int auto_increment NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `credits` decimal(10,2) DEFAULT '0.00',
  `Tickets` int DEFAULT '0',
  `Address` text,
  `PaymentInfo` int DEFAULT NULL,
  `IsRegisteredUser` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for user
INSERT INTO `user` (`Username`, `Email`, `Password`, `credits`, `Tickets`, `Address`, `PaymentInfo`, `IsRegisteredUser`) VALUES
('john_doe', 'john@example.com', 'password123', 100.00, 2, '123 Main St', 1234, 1),
('jane_smith', 'jane@example.com', 'password456', 50.00, 1, '456 Elm St', 5678, 1);


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;