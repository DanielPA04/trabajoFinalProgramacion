CREATE DATABASE  IF NOT EXISTS `musicMy` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `musicMy`;
-- MariaDB dump 10.19  Distrib 10.6.16-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: musicMy
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album` (
  `codAlbum` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `generos` varchar(100) DEFAULT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `discografica` varchar(255) DEFAULT NULL,
  `imagen` mediumblob,
  `Artista` int DEFAULT NULL,
  PRIMARY KEY (`codAlbum`),
  KEY `fk_album_artista_idx` (`Artista`),
  CONSTRAINT `fk_album_artista` FOREIGN KEY (`Artista`) REFERENCES `artista` (`codArtista`)
) ENGINE=InnoDB AUTO_INCREMENT=753 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (752,'Good Kid M A A D City','2012-10-22','Hip hop Rap','El álbum fue grabado, en su mayoría, en varios estudios en California con productores como Doctor Dre, Just Blaze, Pharrell Williams, Hit Boy, Scoop DeVille, Jack Splash, T Minus, entre otros. Anunciado como \"cortometraje por Kendrick Lamar\" en la portada, el concepto del álbum sigue la historia de las experiencias adolescentes de Kendrick en las calles infestadas de drogas y pandillas en su nativa Compton, California.','304',NULL,152);
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album_SEQ`
--

DROP TABLE IF EXISTS `album_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_SEQ`
--

LOCK TABLES `album_SEQ` WRITE;
/*!40000 ALTER TABLE `album_SEQ` DISABLE KEYS */;
INSERT INTO `album_SEQ` VALUES (851);
/*!40000 ALTER TABLE `album_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artista`
--

DROP TABLE IF EXISTS `artista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artista` (
  `codArtista` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `fechaNac` date DEFAULT NULL,
  `imagen` mediumblob,
  PRIMARY KEY (`codArtista`)
) ENGINE=InnoDB AUTO_INCREMENT=953 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artista`
--

LOCK TABLES `artista` WRITE;
/*!40000 ALTER TABLE `artista` DISABLE KEYS */;
INSERT INTO `artista` VALUES (152,'Kendrick Lamar','1987-06-18','');
/*!40000 ALTER TABLE `artista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artista_SEQ`
--

DROP TABLE IF EXISTS `artista_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artista_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artista_SEQ`
--

LOCK TABLES `artista_SEQ` WRITE;
/*!40000 ALTER TABLE `artista_SEQ` DISABLE KEYS */;
INSERT INTO `artista_SEQ` VALUES (1051);
/*!40000 ALTER TABLE `artista_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artista_discografica`
--

DROP TABLE IF EXISTS `artista_discografica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artista_discografica` (
  `codDis` int NOT NULL,
  `codArt` int NOT NULL,
  KEY `FKtcy1sdkn09wh7m81lh0uuj47v` (`codDis`),
  KEY `FK5gpx0jlt8be31b5a1yor0i9bd` (`codArt`),
  CONSTRAINT `FK5gpx0jlt8be31b5a1yor0i9bd` FOREIGN KEY (`codArt`) REFERENCES `artista` (`codArtista`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKtcy1sdkn09wh7m81lh0uuj47v` FOREIGN KEY (`codDis`) REFERENCES `discografica` (`codDiscografica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artista_discografica`
--

LOCK TABLES `artista_discografica` WRITE;
/*!40000 ALTER TABLE `artista_discografica` DISABLE KEYS */;
INSERT INTO `artista_discografica` VALUES (302,152),(303,152),(304,152);
/*!40000 ALTER TABLE `artista_discografica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discografica`
--

DROP TABLE IF EXISTS `discografica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discografica` (
  `codDiscografica` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `pais` varchar(45) DEFAULT NULL,
  `fundacion` date DEFAULT NULL,
  `imagen` mediumblob,
  PRIMARY KEY (`codDiscografica`)
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discografica`
--

LOCK TABLES `discografica` WRITE;
/*!40000 ALTER TABLE `discografica` DISABLE KEYS */;
INSERT INTO `discografica` VALUES (302,'Top Dawg Entertainment','Estados Unidos','2004-01-01',NULL),(303,'PGLang','Estados Unidos','2020-03-05',NULL),(304,'Aftermath Entertainment ','Estados Unidos','1996-01-01',NULL);
/*!40000 ALTER TABLE `discografica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discografica_SEQ`
--

DROP TABLE IF EXISTS `discografica_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discografica_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discografica_SEQ`
--

LOCK TABLES `discografica_SEQ` WRITE;
/*!40000 ALTER TABLE `discografica_SEQ` DISABLE KEYS */;
INSERT INTO `discografica_SEQ` VALUES (401);
/*!40000 ALTER TABLE `discografica_SEQ` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-17 17:23:52
