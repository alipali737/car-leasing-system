CREATE DATABASE  IF NOT EXISTS `main` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `main`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: main
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `doors` int NOT NULL,
  `engine_size` decimal(3,1) NOT NULL,
  `color` varchar(255) NOT NULL,
  `fuel_type` varchar(255) NOT NULL,
  `seats` int NOT NULL,
  `body_type` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `spec` varchar(255) DEFAULT NULL,
  `prod_year` int NOT NULL,
  `description` varchar(255) NOT NULL,
  `registration` varchar(255) NOT NULL,
  `mileage` int NOT NULL,
  `value` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=240 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (199,4,2.0,'Black','Petrol',5,'Saloon','Audi','A4','S line',2021,'Luxury sedan with advanced technology features','EM71 WDY',10000,28000),(200,5,1.5,'White','Petrol',5,'Hatchback','Audi','A3 Sportback','S line',2022,'Compact hatchback with upgraded interior features','OY72 ZAM',5000,24000),(201,2,2.0,'Red','Petrol',2,'Convertible','Audi','TT Roadster','S line',2020,'Luxurious convertible with retractable soft top','GX20 BUE',20000,33000),(202,2,2.5,'Blue','Petrol',4,'Coupe','Audi','TT Coupe','S line',2021,'Sporty two-door coupe with advanced technology features','AU71 YOR',15000,37000),(203,5,2.0,'Silver','Diesel',5,'Estate','Audi','A4 Avant','S line',2022,'Stylish and practical estate with advanced features','RF72 XKY',8000,27000),(204,5,3.0,'Grey','Petrol',7,'Seven-Seater','Audi','Q7','S line',2020,'Luxury seven-seater SUV with advanced safety features','FG20 YOF',25000,45000),(205,5,2.0,'Green','Petrol',5,'SUV','Audi','Q5','S line',2021,'Stylish and sporty SUV with advanced technology features','AB21 ECB',12000,35000),(206,5,1.4,'Black','Hybrid',5,'Hatchback','Audi','A3 Sportback e-tron','S line',2022,'Efficient and eco-friendly hatchback with advanced features','JH72 TIJ',3000,28000),(207,4,0.0,'White','Electric',4,'Coupe','Audi','e-tron GT','S line',2023,'High-performance electric coupe with advanced features','DJ23 ZCO',500,100000),(208,4,2.0,'Black','Petrol',5,'Saloon','BMW','3 Series','M Sport',2021,'Sporty and luxurious sedan with advanced technology features','DA71 JXU',10000,30000),(209,5,1.5,'White','Petrol',5,'Hatchback','BMW','1 Series','Sport',2022,'Compact and stylish hatchback with advanced features','HF22 VQP',5000,22000),(210,2,2.0,'Red','Petrol',4,'Convertible','BMW','4 Series Convertible','M Sport',2020,'Sleek and stylish convertible with retractable hard top','QZ70 JOT',20000,37000),(211,2,3.0,'Blue','Petrol',4,'Coupe','BMW','2 Series Coupe','M Sport',2021,'Sporty two-door coupe with advanced features','RL21 TAC',15000,34000),(212,5,2.0,'Silver','Diesel',5,'Estate','BMW','3 Series Touring','M Sport',2022,'Practical and stylish estate with advanced features','ZB72 DTO',8000,29000),(213,7,2.0,'Grey','Petrol',7,'Seven-Seater','BMW','2 Series Gran Tourer','M Sport',2020,'Versatile and spacious seven-seater with advanced features','DY20 QBK',25000,32000),(214,5,3.0,'Green','Diesel',5,'SUV','BMW','X5','M Sport',2021,'Powerful and luxurious SUV with advanced technology features','NJ21 ITM',12000,50000),(215,5,2.0,'Black','Hybrid',5,'Saloon','BMW','7 Series Hybrid','M Sport',2022,'Luxurious and eco-friendly hybrid sedan with advanced features','YX22 MGX',3000,60000),(216,5,0.0,'White','Electric',5,'Hatchback','BMW','i3','Sport',2023,'Stylish and eco-friendly electric hatchback with advanced features','EF73 OIK',500,35000),(217,4,1.5,'Blue','Petrol',5,'Saloon','Ford','Fusion','Titanium',2022,'Comfortable and spacious sedan with advanced features','RE22 UKJ',5000,18000),(218,5,1.0,'Red','Petrol',5,'Hatchback','Ford','Fiesta','ST-Line',2021,'Fun and stylish hatchback with advanced features','KL71 NME',10000,15000),(219,2,5.0,'Black','Petrol',4,'Convertible','Ford','Mustang Convertible','GT',2020,'Powerful and stylish convertible with advanced features','QG70 VOG',20000,35000),(220,2,5.0,'Yellow','Petrol',4,'Coupe','Ford','Mustang Coupe','GT',2021,'Iconic and powerful two-door coupe with advanced features','GR71 GQK',15000,32000),(221,5,2.0,'White','Diesel',5,'Estate','Ford','Focus Estate','ST-Line',2022,'Spacious and practical estate with advanced features','EV22 DVE',8000,20000),(222,7,2.0,'Silver','Diesel',7,'Seven-Seater','Ford','Galaxy','Titanium',2020,'Versatile and spacious seven-seater with advanced features','FO20 HHH',25000,22000),(223,5,3.0,'Grey','Petrol',5,'SUV','Ford','Explorer','ST-Line',2021,'Powerful and spacious SUV with advanced technology features','YS21 LAT',12000,45000),(224,5,2.0,'Blue','Hybrid',5,'Saloon','Ford','Fusion Hybrid','Titanium',2022,'Eco-friendly and comfortable hybrid sedan with advanced features','WC22 YEJ',3000,22000),(225,5,0.0,'Black','Electric',5,'SUV','Ford','Mustang Mach-E','GT',2023,'Stylish and eco-friendly electric SUV with advanced features','CR23 EZA',500,50000),(226,4,2.5,'Blue','Petrol',5,'Saloon','Nissan','Altima','SL',2021,'Luxurious and spacious sedan with advanced features','MZ71 JOH',10000,20000),(227,5,0.0,'White','Electric',5,'Hatchback','Nissan','Leaf','S',2022,'Eco-friendly and practical hatchback with advanced features','BD72 UWX',5000,25000),(228,2,3.7,'Red','Petrol',2,'Convertible','Nissan','370Z Roadster','NISMO',2020,'Powerful and stylish convertible with advanced features','LP70 YJI',20000,35000),(229,2,3.7,'Black','Petrol',2,'Coupe','Nissan','370Z Coupe','NISMO',2021,'Iconic and powerful two-door coupe with advanced features','HR21 MGL',15000,32000),(230,5,2.5,'Silver','Petrol',5,'Estate','Nissan','Stagea','Autech',2002,'Spacious and practical estate with advanced features','KH52 JTQ',60000,10000),(231,4,2.9,'Black','Petrol',4,'Saloon','Porsche','Panamera','4S',2021,'Luxurious and sporty sedan with advanced features','ML71 AJK',5000,90000),(232,2,2.0,'White','Petrol',2,'Hatchback','Porsche','718 Cayman','S',2022,'Agile and dynamic sports car with advanced features','MT72 VSE',1000,60000),(233,2,3.0,'Red','Petrol',4,'Convertible','Porsche','911 Cabriolet','Turbo S',2021,'Iconic and powerful convertible with advanced features','YN71 JMY',2000,150000),(234,2,3.0,'Silver','Petrol',2,'Coupe','Porsche','911 Coupe','GTS',2021,'Iconic and powerful coupe with advanced features','MG21 FHZ',5000,140000),(235,5,2.9,'Blue','Petrol',5,'Estate','Porsche','Panamera','GTS Sport Turismo',2020,'Spacious and sporty estate with advanced features','OT70 LHE',10000,80000),(236,5,3.0,'Gray','Petrol',5,'SUV','Porsche','Cayenne','Coupe Turbo S',2022,'Luxurious and powerful SUV coupe with advanced features','MR22 GHN',100,200000),(237,4,2.9,'White','Hybrid',4,'Saloon','Porsche','Panamera Hybrid','4 E-Hybrid',2021,'Powerful and eco-friendly hybrid sedan with advanced features','MZ71 HUM',5000,100000),(238,4,0.0,'Black','Electric',4,'Saloon','Porsche','Taycan','Turbo S',2022,'Stylish and eco-friendly electric sports sedan with advanced features','OA72 EMM',500,150000),(239,2,4.0,'Orange','Petrol',2,'Coupe','Porsche','911 GT3 RS','',2021,'Track-inspired and high-performance coupe with advanced features','DA21 ZXM',100,250000);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `address_line_1` varchar(255) NOT NULL,
  `address_line_2` varchar(255) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `driver_license_number` varchar(255) NOT NULL,
  `payment_card_name` varchar(255) NOT NULL,
  `payment_card_number` varchar(255) NOT NULL,
  `payment_card_expiry_month` int NOT NULL,
  `payment_card_expiry_year` int NOT NULL,
  `payment_card_cvv` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_items`
--

DROP TABLE IF EXISTS `inventory_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory_items` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `vehicle_id` bigint unsigned NOT NULL,
  `creation_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `vehicle_in_stock` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vehicle_id` (`vehicle_id`),
  CONSTRAINT `fk_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `cars` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_items`
--

LOCK TABLES `inventory_items` WRITE;
/*!40000 ALTER TABLE `inventory_items` DISABLE KEYS */;
INSERT INTO `inventory_items` VALUES (1,199,'2023-04-28 13:01:44',1),(2,200,'2023-04-28 13:01:44',1),(3,201,'2023-04-28 13:01:44',1),(4,202,'2023-04-28 13:01:44',1),(5,203,'2023-04-28 13:01:44',1),(6,204,'2023-04-28 13:01:44',1),(7,205,'2023-04-28 13:01:44',1),(8,206,'2023-04-28 13:01:44',1),(9,207,'2023-04-28 13:01:44',1),(10,208,'2023-04-28 13:01:44',1),(11,209,'2023-04-28 13:01:44',1),(12,210,'2023-04-28 13:01:44',1),(13,211,'2023-04-28 13:01:44',1),(14,212,'2023-04-28 13:01:44',1),(15,213,'2023-04-28 13:01:44',1),(16,214,'2023-04-28 13:01:44',1),(17,215,'2023-04-28 13:01:44',1),(18,216,'2023-04-28 13:01:44',1),(19,217,'2023-04-28 13:01:44',1),(20,218,'2023-04-28 13:01:44',1),(21,219,'2023-04-28 13:01:44',1),(22,220,'2023-04-28 13:01:44',1),(23,221,'2023-04-28 13:01:44',1),(24,222,'2023-04-28 13:01:44',1),(25,223,'2023-04-28 13:01:44',1),(26,224,'2023-04-28 13:01:44',1),(27,225,'2023-04-28 13:01:44',1),(28,226,'2023-04-28 13:01:44',1),(29,227,'2023-04-28 13:01:44',1),(30,228,'2023-04-28 13:01:44',1),(31,229,'2023-04-28 13:01:44',1),(32,230,'2023-04-28 13:01:44',1),(33,231,'2023-04-28 13:01:44',1),(34,232,'2023-04-28 13:01:44',1),(35,233,'2023-04-28 13:01:44',1),(36,234,'2023-04-28 13:01:44',1),(37,235,'2023-04-28 13:01:44',1),(38,236,'2023-04-28 13:01:44',1),(39,237,'2023-04-28 13:01:44',1),(40,238,'2023-04-28 13:01:44',1),(41,239,'2023-04-28 13:01:44',1);
/*!40000 ALTER TABLE `inventory_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lease_agreements`
--

DROP TABLE IF EXISTS `lease_agreements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lease_agreements` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventory_item_id` bigint unsigned NOT NULL,
  `customer_id` bigint unsigned NOT NULL,
  `policy_start_date` date NOT NULL,
  `policy_expiry_date` date NOT NULL,
  `policy_term` int NOT NULL,
  `initial_deposit_months` int NOT NULL,
  `total_price` decimal(10,0) NOT NULL,
  `daily_late_fee_percentage` int NOT NULL,
  `annual_mileage_allowed` int NOT NULL,
  `policy_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_id` (`customer_id`),
  KEY `fk_inventory_item_id` (`inventory_item_id`),
  CONSTRAINT `fk_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `fk_inventory_item_id` FOREIGN KEY (`inventory_item_id`) REFERENCES `inventory_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lease_agreements`
--

LOCK TABLES `lease_agreements` WRITE;
/*!40000 ALTER TABLE `lease_agreements` DISABLE KEYS */;
/*!40000 ALTER TABLE `lease_agreements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-02 15:03:52
