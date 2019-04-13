CREATE DATABASE  IF NOT EXISTS `StarStucom` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `StarStucom`;
-- MySQL dump 10.13  Distrib 5.6.41, for Linux (x86_64)
--
-- Host: localhost    Database: StarStucom
-- ------------------------------------------------------
-- Server version	5.6.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `runway`
--

DROP TABLE IF EXISTS `runway`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `runway` (
  `spaceport` varchar(100) NOT NULL,
  `number` int(11) NOT NULL,
  `status` varchar(10) NOT NULL,
  `numlandings` int(11) NOT NULL,
  `spaceship` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`spaceport`,`number`),
  KEY `fk_runway_2_idx` (`spaceship`),
  CONSTRAINT `fk_runway_1` FOREIGN KEY (`spaceport`) REFERENCES `spaceport` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_runway_2` FOREIGN KEY (`spaceship`) REFERENCES `spaceship` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `runway`
--

LOCK TABLES `runway` WRITE;
/*!40000 ALTER TABLE `runway` DISABLE KEYS */;
/*!40000 ALTER TABLE `runway` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spaceport`
--

DROP TABLE IF EXISTS `spaceport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spaceport` (
  `name` varchar(100) NOT NULL,
  `planet` varchar(100) NOT NULL,
  `galaxy` varchar(100) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spaceport`
--

LOCK TABLES `spaceport` WRITE;
/*!40000 ALTER TABLE `spaceport` DISABLE KEYS */;
/*!40000 ALTER TABLE `spaceport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spaceship`
--

DROP TABLE IF EXISTS `spaceship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spaceship` (
  `name` varchar(100) NOT NULL,
  `capacity` int(11) NOT NULL,
  `status` varchar(10) NOT NULL,
  `numflights` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spaceship`
--

LOCK TABLES `spaceship` WRITE;
/*!40000 ALTER TABLE `spaceship` DISABLE KEYS */;
/*!40000 ALTER TABLE `spaceship` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-10 11:55:28
