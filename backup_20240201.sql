-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: edusmart
-- ------------------------------------------------------
-- Server version	8.0.32

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

--
-- Table structure for table `intake`
--

DROP TABLE IF EXISTS `intake`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intake` (
  `intake_id` int NOT NULL AUTO_INCREMENT,
  `intake_name` varchar(45) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `program_program_id` int NOT NULL,
  PRIMARY KEY (`intake_id`),
  KEY `fk_intake_program1_idx` (`program_program_id`),
  CONSTRAINT `fk_intake_program1` FOREIGN KEY (`program_program_id`) REFERENCES `program` (`program_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intake`
--

LOCK TABLES `intake` WRITE;
/*!40000 ALTER TABLE `intake` DISABLE KEYS */;
/*!40000 ALTER TABLE `intake` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `pay_id` int NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `is_verified` tinyint DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `registraion_registration_id` int NOT NULL,
  PRIMARY KEY (`pay_id`),
  KEY `fk_payment_registraion1_idx` (`registraion_registration_id`),
  CONSTRAINT `fk_payment_registraion1` FOREIGN KEY (`registraion_registration_id`) REFERENCES `registraion` (`registration_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program` (
  `program_id` int NOT NULL AUTO_INCREMENT,
  `hours` int DEFAULT NULL,
  `program_name` varchar(100) DEFAULT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `user_email` varchar(100) NOT NULL,
  `trainer_trainer_id` int NOT NULL,
  PRIMARY KEY (`program_id`),
  KEY `fk_program_user1_idx` (`user_email`),
  KEY `fk_program_trainer1_idx` (`trainer_trainer_id`),
  CONSTRAINT `fk_program_trainer1` FOREIGN KEY (`trainer_trainer_id`) REFERENCES `trainer` (`trainer_id`),
  CONSTRAINT `fk_program_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program_content`
--

DROP TABLE IF EXISTS `program_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_content` (
  `property_id` int NOT NULL AUTO_INCREMENT,
  `header` varchar(100) DEFAULT NULL,
  `program_program_id` int NOT NULL,
  PRIMARY KEY (`property_id`),
  KEY `fk_program_content_program1_idx` (`program_program_id`),
  CONSTRAINT `fk_program_content_program1` FOREIGN KEY (`program_program_id`) REFERENCES `program` (`program_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_content`
--

LOCK TABLES `program_content` WRITE;
/*!40000 ALTER TABLE `program_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `program_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registraion`
--

DROP TABLE IF EXISTS `registraion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registraion` (
  `registration_id` int NOT NULL AUTO_INCREMENT,
  `register_date` date DEFAULT NULL,
  `program_amount` decimal(10,0) DEFAULT NULL,
  `intake_intake_id` int NOT NULL,
  `student_student_id` int NOT NULL,
  PRIMARY KEY (`registration_id`),
  KEY `fk_registraion_intake1_idx` (`intake_intake_id`),
  KEY `fk_registraion_student1_idx` (`student_student_id`),
  CONSTRAINT `fk_registraion_intake1` FOREIGN KEY (`intake_intake_id`) REFERENCES `intake` (`intake_id`),
  CONSTRAINT `fk_registraion_student1` FOREIGN KEY (`student_student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registraion`
--

LOCK TABLES `registraion` WRITE;
/*!40000 ALTER TABLE `registraion` DISABLE KEYS */;
/*!40000 ALTER TABLE `registraion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `student_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `dob` date NOT NULL,
  `address` varchar(255) NOT NULL,
  `status` tinyint NOT NULL,
  `user_email` varchar(100) NOT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_student_user_idx` (`user_email`),
  CONSTRAINT `fk_student_user` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainer`
--

DROP TABLE IF EXISTS `trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainer` (
  `trainer_id` int NOT NULL AUTO_INCREMENT,
  `trainer_name` varchar(100) DEFAULT NULL,
  `trainer_email` varchar(100) DEFAULT NULL,
  `nic` varchar(45) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `trainer_status` tinyint DEFAULT NULL,
  PRIMARY KEY (`trainer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer`
--

LOCK TABLES `trainer` WRITE;
/*!40000 ALTER TABLE `trainer` DISABLE KEYS */;
/*!40000 ALTER TABLE `trainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `email` varchar(100) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `active_state` tinyint DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('sandul@gmail.com','Sandul','Tharuna','$2a$10$p1yF12ZpZsKwQG2d6M4F4u4UiS7G0WzEw6fBzY7hrkRflEIpmfEcu',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-01  9:23:04
