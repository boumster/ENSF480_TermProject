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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `movieID` int auto_increment NOT NULL,
  `Movie_name` varchar(60) DEFAULT NULL,
  `Movie_description` varchar(150) DEFAULT NULL,
  `Movie_Genre` varchar(30) DEFAULT NULL,
  `Movie_rating` varchar(20) DEFAULT NULL,
  `Movie_duration` int NOT NULL,
  PRIMARY KEY (`movieID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Sample data for movies
INSERT INTO `movies` (`Movie_name`, `Movie_description`, `Movie_Genre`, `Movie_rating`, `Movie_duration`) VALUES
('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology.', 'Sci-Fi', 'E', 120 ),
('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'Crime', 'R', 120),
('The Dark Knight', 'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 'Action', 'T', 120),
('Oppenheimer', 'The story of J. Robert Oppenheimer, the father of the atomic bomb, and his role in the Manhattan Project during World War II.', 'Biography/Drama', 'R', 180),
('The Imitation Game', 'During World War II, mathematician Alan Turing and his team of cryptanalysts work to crack the German Enigma code.', 'Biography/Drama', 'T', 120),
('How To Train Your Dragon', 'A young Viking befriends a dragon, defying his village’s tradition of dragon hunting.', 'Animated/Adventure', 'E', 120),
('Harry Potter and The Deathly Hallows', 'Harry, Ron, and Hermione embark on a quest to destroy Horcruxes and defeat Voldemort once and for all.', 'Fantasy/Adventure', 'T', 120),
('Trial of The Chicago 7', 'The story of a group of anti-Vietnam War protesters who were charged with conspiracy and inciting riots in 1969.', 'Biography/Drama', 'T', 120),
('Attack on Titan: The Last Attack', 'In the final battle, Eren faces his ultimate fate as humanity fights for survival.', 'Anime/Action', 'T', 120),
('Avengers: Infinity War', 'The Avengers assemble to stop Thanos from collecting all six Infinity Stones and decimating half of the universe.', 'Superhero/Action', 'T', 180),
('Avengers: Endgame', 'After the devastating events of Infinity War, the Avengers regroup to undo Thanos’s snap and restore balance to the universe.', 'Superhero/Action', 'T', 180),
('Spider-Man: No Way Home', 'Spider-Man faces multiverse chaos when past villains and alternate Spider-Men enter his world.', 'Superhero/Action', 'T', 120),
('Star Wars: Revenge of The Sith', 'Anakin Skywalker succumbs to the dark side, becoming Darth Vader, as the Jedi Order collapses.', 'Sci-Fi/Fantasy', 'T', 140),
('The Wolf of Wall Street', 'The rise and fall of Jordan Belfort, a stockbroker who amasses wealth through corruption and fraud.', 'Biography/Drama', 'R', 180),
('Gladiator', 'A betrayed Roman general seeks vengeance against the corrupt emperor who murdered his family and sent him into slavery.', 'Historical/Drama', 'R', 155),
('The Social Network', 'The story of Mark Zuckerberg and the creation of Facebook, alongside the legal battles that followed.', 'Biography/Drama', 'T', 120),
('Kung Fu Panda', 'A clumsy panda discovers he is the prophesied Dragon Warrior destined to protect the Valley of Peace.', 'Animated/Comedy', 'E', 92),
('The Pursuit of Happyness', 'A struggling salesman and single father faces countless hardships as he strives to build a better future for his young son.', 'Biography/Drama', 'T', 117),
('The Guilty', 'A demoted police officer working at a 911 dispatch center becomes entangled in a tense and life-changing emergency call.', 'Thriller/Drama', 'T', 90),
('Interstellar', 'A group of explorers travel through a wormhole in search of a new habitable planet for humanity.', 'Sci-Fi/Adventure', 'T', 169);


DROP TABLE IF EXISTS `theatre`;
CREATE TABLE `theatre` (
  `theatre_id` int auto_increment NOT NULL,
  `theatre_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`theatre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for theatre
INSERT INTO `theatre` (`theatre_name`) VALUES
('Acmeplex Odeon Crowfoot Crossing Cinemas'),
('Acmeplex VIP Cinemas University District'),
('Acmeplex Odeon Westhills Cinemas'),
('Scotiabank Theatre Chinook'),
('Acmeplex Odeon Sunridge Spectrum Cinemas'),
('Acmeplex CrossIron MIlls Cinemas and Entertainment'),
('Acmeplex Cinemas East Hills');

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
(1, 50), (1, 50), (1, 50), (1, 50), (1, 50), (1, 50), (1, 50),

(2, 50), (2, 50), (2, 50), (2, 50), (2, 50), (2, 50), (2, 50),

(3, 50), (3, 50), (3, 50), (3, 50), (3, 50), (3, 50), (3, 50),

(4, 50), (4, 50), (4, 50), (4, 50), (4, 50), (4, 50), (4, 50),

(5, 50), (5, 50), (5, 50), (5, 50), (5, 50), (5, 50), (5, 50),

(6, 50), (6, 50), (6, 50), (6, 50), (6, 50), (6, 50), (6, 50),

(7, 50), (5, 50), (5, 50), (5, 50), (5, 50), (5, 50), (7, 50), (7, 50);



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
-- Movie 1
('2024-12-03 10:00:00', 1, 1), ('2024-12-03 10:30:00', 1, 2),
('2024-12-03 13:00:00', 1, 3), ('2024-12-03 13:30:00', 1, 4),
('2024-12-03 16:00:00', 1, 5), ('2024-12-03 16:30:00', 1, 6),
('2024-12-03 19:00:00', 1, 7), ('2024-12-03 19:30:00', 1, 8),
('2024-12-03 22:00:00', 1, 9), ('2024-12-03 22:30:00', 1, 10),
('2024-12-04 10:00:00', 1, 11), ('2024-12-04 10:30:00', 1, 12),
('2024-12-04 13:00:00', 1, 13), ('2024-12-04 13:30:00', 1, 14),
('2024-12-04 16:00:00', 1, 15), ('2024-12-04 16:30:00', 1, 16),

-- Movie 2
('2024-12-03 10:30:00', 2, 1), ('2024-12-03 13:30:00', 2, 2),
('2024-12-03 16:30:00', 2, 3), ('2024-12-03 19:30:00', 2, 4),
('2024-12-03 22:30:00', 2, 5), ('2024-12-04 10:30:00', 2, 6),
('2024-12-04 13:30:00', 2, 7), ('2024-12-04 12:30:00', 2, 18),
('2024-12-03 14:30:00', 2, 31), ('2024-12-14 17:30:00', 2, 33),
('2024-12-14 20:30:00', 2, 48), ('2024-12-15 23:30:00', 2, 10),
('2024-12-15 02:30:00', 2, 17),

-- Movie 3
('2024-12-03 11:00:00', 3, 8), ('2024-12-03 14:00:00', 3, 9),
('2024-12-03 17:00:00', 3, 10), ('2024-12-03 20:00:00', 3, 11),
('2024-12-03 23:00:00', 3, 12), ('2024-12-04 11:00:00', 3, 13),
('2024-12-04 14:00:00', 3, 14), ('2024-12-04 18:30:00', 3, 20),
('2024-12-14 21:30:00', 3, 22), ('2024-12-15 01:00:00', 3, 24),
('2024-12-15 03:00:00', 3, 44), ('2024-12-15 09:00:00', 3, 11),

-- Movie 4
('2024-12-03 11:30:00', 4, 12), ('2024-12-03 14:30:00', 4, 13),
('2024-12-03 17:30:00', 4, 14), ('2024-12-03 20:30:00', 4, 15),
('2024-12-03 23:30:00', 4, 16), ('2024-12-04 11:30:00', 4, 17),
('2024-12-04 14:30:00', 4, 18), ('2024-12-04 12:00:00', 4, 19),
('2024-12-03 16:30:00', 4, 22), ('2024-12-14 21:00:00', 4, 25),
('2024-12-14 00:00:00', 4, 30), ('2024-12-15 03:00:00', 4, 33),
('2024-12-15 05:00:00', 4, 44),

-- Movie 5
('2024-12-03 12:00:00', 5, 18), ('2024-12-03 15:00:00', 5, 19),
('2024-12-03 18:00:00', 5, 20), ('2024-12-03 21:00:00', 5, 21),
('2024-12-04 00:00:00', 5, 22), ('2024-12-04 12:00:00', 5, 23),
('2024-12-04 15:00:00', 5, 24), ('2024-12-04 13:00:00', 5, 25),
('2024-12-14 17:00:00', 5, 26), ('2024-12-14 20:00:00', 5, 27),
('2024-12-15 10:00:00', 5, 28), ('2024-12-15 12:00:00', 5, 29),
('2024-12-15 22:00:00', 5, 30),

-- Movie 6
('2024-12-03 12:30:00', 6, 31), ('2024-12-03 15:30:00', 6, 32),
('2024-12-03 18:30:00', 6, 33), ('2024-12-03 21:30:00', 6, 34),
('2024-12-04 00:30:00', 6, 35), ('2024-12-04 12:30:00', 6, 36),
('2024-12-04 15:30:00', 6, 37), ('2024-12-04 18:00:00', 6, 38),
('2024-12-03 14:00:00', 6, 39), ('2024-12-14 17:30:00', 6, 40),
('2024-12-14 23:00:00', 6, 41), ('2024-12-15 03:30:00', 6, 42),
('2024-12-15 09:30:00', 6, 43),

-- Movie 7
('2024-12-03 13:00:00', 7, 44), ('2024-12-03 16:00:00', 7, 45),
('2024-12-03 19:00:00', 7, 46), ('2024-12-03 22:00:00', 7, 47),
('2024-12-04 01:00:00', 7, 48), ('2024-12-04 13:00:00', 7, 49),
('2024-12-04 16:00:00', 7, 1), ('2024-12-03 12:30:00', 7, 2),
('2024-12-03 15:30:00', 7, 3), ('2024-12-14 20:30:00', 7, 4),
('2024-12-14 00:30:00', 7, 5), ('2024-12-15 03:30:00', 7, 6),
('2024-12-15 05:30:00', 7, 7),

-- Movie 8
('2024-12-03 13:30:00', 8, 5), ('2024-12-03 16:30:00', 8, 6),
('2024-12-03 19:30:00', 8, 7), ('2024-12-03 22:30:00', 8, 8),
('2024-12-04 01:30:00', 8, 9), ('2024-12-04 13:30:00', 8, 10),
('2024-12-04 16:30:00', 8, 11), ('2024-12-04 18:00:00', 8, 12),
('2024-12-14 22:30:00', 8, 13), ('2024-12-14 02:30:00', 8, 14),
('2024-12-15 04:00:00', 8, 15), ('2024-12-15 10:30:00', 8, 16),

-- Movie 9
('2024-12-03 14:00:00', 9, 1), ('2024-12-03 17:00:00', 9, 2),
('2024-12-03 20:00:00', 9, 3), ('2024-12-03 23:00:00', 9, 4),
('2024-12-04 02:00:00', 9, 5), ('2024-12-04 13:00:00', 9, 6),
('2024-12-04 16:00:00', 9, 7), ('2024-12-04 19:00:00', 9, 8),
('2024-12-04 22:00:00', 9, 9), ('2024-12-14 00:30:00', 9, 10),
('2024-12-14 03:00:00', 9, 11), ('2024-12-14 06:30:00', 9, 12),
('2024-12-15 09:30:00', 9, 13),

-- Movie 10
('2024-12-03 15:00:00', 10, 1), ('2024-12-03 18:00:00', 10, 2),
('2024-12-03 21:00:00', 10, 3), ('2024-12-04 00:00:00', 10, 4),
('2024-12-04 03:00:00', 10, 5), ('2024-12-04 06:00:00', 10, 6),
('2024-12-04 09:00:00', 10, 7), ('2024-12-04 12:00:00', 10, 8),
('2024-12-14 18:00:00', 10, 9), ('2024-12-15 02:00:00', 10, 10),

-- Movie 11
('2024-12-03 16:00:00', 11, 1), ('2024-12-03 19:00:00', 11, 2),
('2024-12-03 22:00:00', 11, 3), ('2024-12-04 01:00:00', 11, 4),
('2024-12-04 04:00:00', 11, 5), ('2024-12-04 07:00:00', 11, 6),
('2024-12-04 10:00:00', 11, 7), ('2024-12-04 13:00:00', 11, 8),
('2024-12-14 19:00:00', 11, 9), ('2024-12-15 04:00:00', 11, 10),

-- Movie 12
('2024-12-03 17:30:00', 12, 1), ('2024-12-03 20:30:00', 12, 2),
('2024-12-03 23:30:00', 12, 3), ('2024-12-04 02:30:00', 12, 4),
('2024-12-04 05:30:00', 12, 5), ('2024-12-04 08:30:00', 12, 6),
('2024-12-04 11:30:00', 12, 7), ('2024-12-04 14:30:00', 12, 8),
('2024-12-14 20:30:00', 12, 9), ('2024-12-15 05:30:00', 12, 10),

-- Movie 13
('2024-12-03 18:00:00', 13, 1), ('2024-12-03 21:00:00', 13, 2),
('2024-12-04 00:00:00', 13, 3), ('2024-12-04 03:00:00', 13, 4),
('2024-12-04 06:00:00', 13, 5), ('2024-12-04 09:00:00', 13, 6),
('2024-12-04 12:00:00', 13, 7), ('2024-12-04 15:00:00', 13, 8),
('2024-12-14 21:00:00', 13, 9), ('2024-12-15 06:00:00', 13, 10),

-- Movie 14
('2024-12-03 19:30:00', 14, 1), ('2024-12-03 22:30:00', 14, 2),
('2024-12-04 01:30:00', 14, 3), ('2024-12-04 04:30:00', 14, 4),
('2024-12-04 07:30:00', 14, 5), ('2024-12-04 10:30:00', 14, 6),
('2024-12-04 13:30:00', 14, 7), ('2024-12-04 16:30:00', 14, 8),
('2024-12-14 22:00:00', 14, 9), ('2024-12-15 07:00:00', 14, 10),

-- Movie 15
('2024-12-03 20:00:00', 15, 1), ('2024-12-03 23:00:00', 15, 2),
('2024-12-04 02:00:00', 15, 3), ('2024-12-04 05:00:00', 15, 4),
('2024-12-04 08:00:00', 15, 5), ('2024-12-04 11:00:00', 15, 6),
('2024-12-04 14:00:00', 15, 7), ('2024-12-04 17:00:00', 15, 8),
('2024-12-14 23:00:00', 15, 9), ('2024-12-15 08:00:00', 15, 10),

-- Movie 16
('2024-12-03 21:30:00', 16, 1), ('2024-12-03 00:30:00', 16, 2),
('2024-12-03 03:30:00', 16, 3), ('2024-12-03 06:30:00', 16, 4),
('2024-12-03 09:30:00', 16, 5), ('2024-12-03 12:30:00', 16, 6),
('2024-12-03 15:30:00', 16, 7), ('2024-12-03 18:30:00', 16, 8),
('2024-12-14 00:30:00', 16, 9), ('2024-12-14 03:30:00', 16, 10),

-- Movie 17
('2024-12-03 23:00:00', 17, 1), ('2024-12-04 02:00:00', 17, 2),
('2024-12-04 05:00:00', 17, 3), ('2024-12-04 08:00:00', 17, 4),
('2024-12-04 11:00:00', 17, 5), ('2024-12-04 14:00:00', 17, 6),
('2024-12-04 17:00:00', 17, 7), ('2024-12-04 20:00:00', 17, 8),
('2024-12-14 01:00:00', 17, 9), ('2024-12-14 04:00:00', 17, 10),

-- Movie 18
('2024-12-04 00:30:00', 18, 1), ('2024-12-04 03:30:00', 18, 2),
('2024-12-04 06:30:00', 18, 3), ('2024-12-04 09:30:00', 18, 4),
('2024-12-04 12:30:00', 18, 5), ('2024-12-04 15:30:00', 18, 6),
('2024-12-04 18:30:00', 18, 7), ('2024-12-04 21:30:00', 18, 8),
('2024-12-14 02:30:00', 18, 9), ('2024-12-14 05:30:00', 18, 10),

-- Movie 19
('2024-12-04 01:00:00', 19, 1), ('2024-12-04 04:00:00', 19, 2),
('2024-12-04 07:00:00', 19, 3), ('2024-12-04 10:00:00', 19, 4),
('2024-12-04 13:00:00', 19, 5), ('2024-12-04 16:00:00', 19, 6),
('2024-12-04 19:00:00', 19, 7), ('2024-12-04 22:00:00', 19, 8),
('2024-12-14 03:00:00', 19, 9), ('2024-12-14 06:00:00', 19, 10),

-- Movie 20
('2024-12-04 02:30:00', 20, 1), ('2024-12-04 05:30:00', 20, 2),
('2024-12-04 08:30:00', 20, 3), ('2024-12-04 11:30:00', 20, 4),
('2024-12-04 14:30:00', 20, 5), ('2024-12-04 17:30:00', 20, 6),
('2024-12-04 20:30:00', 20, 7), ('2024-12-04 23:30:00', 20, 8),
('2024-12-14 04:30:00', 20, 9), ('2024-12-14 07:30:00', 20, 10);
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
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(2, 15),
(2, 16),
(2, 17),
(2, 18),
(2, 19),
(2, 20),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(3, 10),
(3, 11),
(3, 12),
(3, 13),
(3, 14),
(3, 15),
(3, 16),
(3, 17),
(3, 18),
(3, 19),
(3, 20),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(4, 9),
(4, 10),
(4, 11),
(4, 12),
(4, 13),
(4, 14),
(4, 15),
(4, 16),
(4, 17),
(4, 18),
(4, 19),
(4, 20),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(2, 15),
(2, 16),
(2, 17),
(2, 18),
(2, 19),
(2, 20),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(3, 10),
(3, 11),
(3, 12),
(3, 13),
(3, 14),
(3, 15),
(3, 16),
(3, 17),
(3, 18),
(3, 19),
(3, 20),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(4, 9),
(4, 10),
(4, 11),
(4, 12),
(4, 13),
(4, 14),
(4, 15),
(4, 16),
(4, 17),
(4, 18),
(4, 19),
(4, 20);

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
  `IsRegisteredUser` bit DEFAULT 0,
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for user
INSERT INTO `user` (`Username`, `Email`, `Password`, `credits`, `Tickets`, `Address`, `PaymentInfo`, `IsRegisteredUser`) VALUES
('john_doe', 'john@example.com', 'password123', 100.00, 2, '123 Main St', 1234, 1),
('jane_smith', 'jane@example.com', 'password456', 50.00, 1, '456 Elm St', 5678, 1),
('alice_jones', 'alice@example.com', 'password789', 75.00, 3, '789 Oak St', 9101, 1),
('bob_brown', 'bob@example.com', 'password101', 20.00, 0, '101 Pine St', 1121, 0),
('charlie_davis', 'charlie@example.com', 'password202', 150.00, 4, '202 Maple St', 3141, 1),
('diana_evans', 'diana@example.com', 'password303', 60.00, 2, '303 Cedar St', 5161, 1),
('edward_frank', 'edward@example.com', 'password404', 90.00, 1, '404 Birch St', 7181, 0),
('fiona_green', 'fiona@example.com', 'password505', 30.00, 0, '505 Spruce St', 9201, 1),
('george_harris', 'george@example.com', 'password606', 110.00, 3, '606 Willow St', 1221, 1),
('hannah_ivan', 'hannah@example.com', 'password707', 80.00, 2, '707 Fir St', 3241, 0);


DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `userID` int DEFAULT NULL,
  `movieID` int DEFAULT NULL,
  `showtimeID` int DEFAULT NULL,
  `SeatNumber` int DEFAULT NULL, -- Corrected column definition
  `price` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`ID`),
  KEY `userID` (`userID`),
  KEY `movieID` (`movieID`),
  KEY `showtimeID` (`showtimeID`),
  CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`UserId`) ON DELETE CASCADE,
  CONSTRAINT `tickets_ibfk_2` FOREIGN KEY (`movieID`) REFERENCES `movies` (`movieID`) ON DELETE CASCADE,
  CONSTRAINT `tickets_ibfk_3` FOREIGN KEY (`showtimeID`) REFERENCES `showtimes` (`showtimeID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample data for tickets
INSERT INTO `tickets` (`userID`, `movieID`, `showtimeID`, `SeatNumber`, `price`) VALUES
(1, 1, 1, 1, 10.00),
(2, 2, 2, 2, 10.00),
(1, 1, 5, 1, 10.00),
(3, 3, 3, 3, 12.00),
(4, 4, 4, 4, 15.00),
(5, 5, 5, 5, 8.00),
(6, 6, 6, 6, 10.00),
(7, 7, 7, 7, 9.00),
(8, 8, 8, 8, 11.00),
(9, 9, 9, 9, 14.00),
(10, 10, 10, 10, 13.00),
(1, 2, 11, 11, 10.00),
(2, 3, 12, 12, 10.00),
(3, 4, 13, 13, 12.00),
(4, 5, 14, 14, 15.00),
(1, 1, 5, 1, 10.00),
(3, 3, 3, 3, 12.00),
(4, 4, 4, 4, 15.00),
(5, 5, 5, 5, 8.00),
(6, 6, 6, 6, 10.00),
(7, 7, 7, 7, 9.00),
(8, 8, 8, 8, 11.00),
(9, 9, 9, 9, 14.00),
(10, 10, 10, 10, 13.00),
(1, 2, 11, 11, 10.00),
(2, 3, 12, 12, 10.00),
(3, 4, 13, 13, 12.00),
(4, 5, 14, 14, 15.00);


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;