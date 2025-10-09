-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: e_commerce
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `carousel`
--

DROP TABLE IF EXISTS `carousel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carousel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '标题',
  `description` char(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '描述',
  `status` tinyint NOT NULL COMMENT '状态',
  `target` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '打开方式',
  `sort` int NOT NULL COMMENT '排序',
  `subtitle` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci DEFAULT NULL COMMENT '副标题',
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '图片地址',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci DEFAULT NULL COMMENT '跳转链接',
  `button_text` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '按钮文字',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_cs_0900_ai_ci COMMENT='轮播图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carousel`
--

LOCK TABLES `carousel` WRITE;
/*!40000 ALTER TABLE `carousel` DISABLE KEYS */;
INSERT INTO `carousel` VALUES (22,'132','qwe',1,'qwe',1,'qw','/api/image/Carousel/2025-09-29/a1273813-胆大党_第二季_1757496400778.jpg','qwe','qew','2025-09-27 15:05:51','2025-10-08 13:18:21'),(24,'达娃大大·','大王大大',1,'_self',1,'达娃大','/api/image/Carousel/2025-09-29/1eef2431-1756888554351.jpg','','了解更多','2025-09-29 16:25:22','2025-10-08 13:18:21');
/*!40000 ALTER TABLE `carousel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` char(100) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` int NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL,
  `title` char(30) NOT NULL COMMENT '商品名称',
  `image_path` varchar(255) DEFAULT NULL,
  `original_price` double NOT NULL COMMENT '原价',
  `current_price` double NOT NULL COMMENT '现价',
  `reviews` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `rating` tinyint NOT NULL DEFAULT '5' COMMENT '评价',
  `created_at` datetime NOT NULL COMMENT '上传时间',
  `update_at` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='特色商品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1972871107687178241,'任务图我','/api/image/Featured/2025-09-30/2002b402-更衣人偶坠入爱河_第二季_1757496429774.jpg',123,12,0,9,'2025-09-30 11:48:21','2025-09-30 11:48:21'),(1972967206737379330,'title','/api/image/Featured/2025-09-30/c2b40d1f-更衣人偶坠入爱河_第二季_1757496429774.jpg',111,11,0,6,'2025-09-30 18:10:13','2025-09-30 18:10:13'),(1975377800786591745,'123','/api/image/Featured/2025-10-07/7fab4e3c-1756888554351.jpg',1231,12,0,7,'2025-10-07 09:49:03','2025-10-07 09:49:03'),(1975444339632099330,'123','/api/image/Featured/2025-10-07/bd6dd458-banner1.jpg',12,1,0,8,'2025-10-07 14:13:27','2025-10-07 14:13:27'),(1975454258263478273,'qwe','/api/image/Featured/2025-10-07/f1aed5dc-更衣人偶坠入爱河_第二季_1757496429774.jpg',12,12,0,9,'2025-10-07 14:52:52','2025-10-07 14:52:52'),(1975454451461509122,'12','/api/image/Featured/2025-10-07/b6bacdc2-ligg.jpg',12,12,0,8,'2025-10-07 14:53:38','2025-10-07 14:53:38'),(1975456583048716289,'123','/api/image/Featured/2025-10-07/6cb4de9b-banner1.jpg',12,12,0,6,'2025-10-07 15:02:06','2025-10-07 15:02:06'),(1975458224468598785,'1212','/api/image/Featured/2025-10-07/37c04348-更衣人偶坠入爱河_第二季_1757496429774.jpg',12,1,0,8,'2025-10-07 15:08:38','2025-10-07 15:08:38'),(1975826121053409282,'1231','/api/image/Featured/2025-10-08/85e19877-banner1.jpg',123,12,0,5,'2025-10-08 15:30:31','2025-10-08 15:30:31'),(1975826152879788034,'123','/api/image/Featured/2025-10-08/039d9139-更衣人偶坠入爱河_第二季_1757496429774.jpg',12,12,0,6,'2025-10-08 15:30:39','2025-10-08 15:30:39'),(1975826189739331586,'123','/api/image/Featured/2025-10-08/9f0a2a38-更衣人偶坠入爱河_第二季_1757496429774.jpg',12,2,0,5,'2025-10-08 15:30:47','2025-10-08 15:30:47'),(1975826252200906753,'123','/api/image/Featured/2025-10-08/f4ebd120-ligg.jpg',123,123,0,10,'2025-10-08 15:31:02','2025-10-08 15:31:02'),(1975826314847031297,'123','/api/image/Featured/2025-10-08/0a230584-胆大党_第二季_1757496400778.jpg',123,123,0,6,'2025-10-08 15:31:17','2025-10-08 15:31:17'),(1975826397772615681,'123','/api/image/Featured/2025-10-08/fbf32e86-1756888554351.jpg',167,34,0,10,'2025-10-08 15:31:37','2025-10-08 15:31:37'),(1975826470644453377,'123','/api/image/Featured/2025-10-08/76964102-1756888554351.jpg',123,12,0,9,'2025-10-08 15:31:54','2025-10-08 15:31:54'),(1975826470644456877,'商品','/api/image/Featured/2025-10-08/76964102-1756888554351.jpg',123,12,0,9,'2025-10-08 15:31:54','2025-10-08 15:31:54'),(1975826470658453377,'123','/api/image/Featured/2025-10-08/76964102-1756888554351.jpg',123,12,0,9,'2025-10-08 15:31:54','2025-10-08 15:31:54'),(1975826470688453377,'123','/api/image/Featured/2025-10-08/76964102-1756888554351.jpg',123,12,0,9,'2025-10-08 15:31:54','2025-10-08 15:31:54'),(1975826476658453377,'123','/api/image/Featured/2025-10-08/76964102-1756888554351.jpg',123,12,0,9,'2025-10-08 15:31:54','2025-10-08 15:31:54'),(1975826476829453377,'123','/api/image/Featured/2025-10-08/76964102-1756888554351.jpg',123,12,0,9,'2025-10-08 15:31:54','2025-10-08 15:31:54'),(1975826596658453377,'123','/api/image/Featured/2025-10-08/76964102-1756888554351.jpg',123,12,0,9,'2025-10-08 15:31:54','2025-10-08 15:31:54');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_detail`
--

DROP TABLE IF EXISTS `product_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品id',
  `description` varchar(255) NOT NULL COMMENT '商品描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='精选商品详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_detail`
--

LOCK TABLES `product_detail` WRITE;
/*!40000 ALTER TABLE `product_detail` DISABLE KEYS */;
INSERT INTO `product_detail` VALUES (1,1972960556001492994,'daw'),(2,1972967206737379330,'描述'),(3,1975377800786591745,'123'),(4,1975444339632099330,'123'),(5,1975454258263478273,'qew'),(6,1975454296553279490,'qewqe'),(7,1975454347845423106,'123'),(8,1975454451461509122,'12'),(9,1975456583048716289,'123'),(10,1975458224468598785,'12'),(11,1975458294454755329,'123'),(12,1975826121053409282,'1231'),(13,1975826152879788034,'123'),(14,1975826189739331586,'123'),(15,1975826252200906753,'1231'),(16,1975826314847031297,'123'),(17,1975826397772615681,'132'),(18,1975826470644453377,'123');
/*!40000 ALTER TABLE `product_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品id',
  `sort` int DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`),
  KEY `featured_image_featured_id_index` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (1,1972967206737379330,0,'/api/image/Featured/2025-10-07/688edd05-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(2,1972967206737379330,0,'/api/image/Featured/2025-10-07/de82bee4-banner1.jpg'),(3,1972967206737379330,0,'/api/image/Featured/2025-10-07/e02b0d17-1756888554351.jpg'),(4,1972967206737379330,0,'/api/image/Featured/2025-10-07/384bf176-banner1.jpg'),(5,1972967206737379330,0,'/api/image/Featured/2025-10-07/7cfa87a6-1756888554351.jpg'),(6,1972967206737379330,0,'/api/image/Featured/2025-10-07/51ed742a-胆大党_第二季_1757496400778.jpg'),(7,1972967206737379330,0,'/api/image/Featured/2025-10-07/9235b1ca-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(8,1975377800786591745,0,'/api/image/Featured/2025-10-07/ecf34050-banner1.jpg'),(9,1975377800786591745,0,'/api/image/Featured/2025-10-07/b6a64e81-胆大党_第二季_1757496400778.jpg'),(10,1975377800786591745,0,'/api/image/Featured/2025-10-07/b705bd02-1756888554351.jpg'),(11,1975377800786591745,0,'/api/image/Featured/2025-10-07/7a8c8b39-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(12,1975377800786591745,0,'/api/image/Featured/2025-10-07/edfa8754-banner1.jpg'),(13,1975377800786591745,0,'/api/image/Featured/2025-10-07/8140bd11-胆大党_第二季_1757496400778.jpg'),(14,1975377800786591745,0,'/api/image/Featured/2025-10-07/e332ebf7-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(15,1975377800786591745,0,'/api/image/Featured/2025-10-07/973b3a0e-1756888554351.jpg'),(16,1972967206737379330,0,'/api/image/Featured/2025-10-07/042e7d37-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(17,1972967206737379330,0,'/api/image/Featured/2025-10-07/7363451d-1756888554351.jpg'),(18,1972967206737379330,0,'/api/image/Featured/2025-10-07/94b74f19-1756888554351.jpg'),(19,1972967206737379330,0,'/api/image/Featured/2025-10-07/41f4335a-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(20,1972967206737379330,0,'/api/image/Featured/2025-10-07/e2d694ec-1756888554351.jpg'),(21,1972967206737379330,0,'/api/image/Featured/2025-10-07/b17e8b39-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(22,1972967206737379330,0,'/api/image/Featured/2025-10-07/50262fce-banner1.jpg'),(23,1972967206737379330,0,'/api/image/Featured/2025-10-07/3f5bb81f-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(24,1972967206737379330,0,'/api/image/Featured/2025-10-07/2929095c-banner1.jpg'),(25,1972967206737379330,0,'/api/image/Featured/2025-10-07/2de928c0-1756888554351.jpg'),(26,1972967206737379330,0,'/api/image/Featured/2025-10-07/47cb2923-banner1.jpg'),(27,1972967206737379330,0,'/api/image/Featured/2025-10-07/5cca6d04-1756888554351.jpg'),(28,1972967206737379330,0,'/api/image/Featured/2025-10-07/779178c1-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(29,1972967206737379330,0,'/api/image/Featured/2025-10-07/9b785bee-1756888554351.jpg'),(30,1972967206737379330,0,'/api/image/Featured/2025-10-07/b70affdb-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(31,1972967206737379330,0,'/api/image/Featured/2025-10-07/6a06e21e-1756888554351.jpg'),(32,1975458224468598785,0,'/api/image/Featured/2025-10-07/5d019b37-1756888554351.jpg'),(33,1975458224468598785,0,'/api/image/Featured/2025-10-07/503a7d00-banner1.jpg'),(34,1975458224468598785,0,'/api/image/Featured/2025-10-07/98bb7a55-1756888554351.jpg'),(35,1975458224468598785,0,'/api/image/Featured/2025-10-07/01d08f2f-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(36,1975458224468598785,0,'/api/image/Featured/2025-10-07/47beae39-banner1.jpg'),(37,1975458224468598785,0,'/api/image/Featured/2025-10-07/8b685cc2-1756888554351.jpg'),(38,1975458224468598785,0,'/api/image/Featured/2025-10-07/b3873ebe-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(39,1972967206737379330,0,'/api/image/Featured/2025-10-07/4607852f-更衣人偶坠入爱河_第二季_1757496429774.jpg'),(40,1972967206737379330,0,'/api/image/Featured/2025-10-07/50e4ca2c-banner1.jpg'),(41,1972871107687178241,0,'/api/image/Featured/2025-10-09/e35285c5-1756888554351.jpg'),(42,1972871107687178241,0,'/api/image/Featured/2025-10-09/ff0c9fa8-1756888554351.jpg'),(43,1972871107687178241,0,'/api/image/Featured/2025-10-09/322f3b16-1756888554351.jpg'),(44,1972871107687178241,0,'/api/image/Featured/2025-10-09/3a7d31e0-1756888554351.jpg'),(45,1972871107687178241,0,'/api/image/Featured/2025-10-09/14184446-1756888554351.jpg'),(46,1972871107687178241,0,'/api/image/Featured/2025-10-09/dfde2b37-1756888554351.jpg'),(47,1972871107687178241,0,'/api/image/Featured/2025-10-09/94825d6f-1756888554351.jpg'),(48,1972871107687178241,0,'/api/image/Featured/2025-10-09/4c98a019-1756888554351.jpg'),(49,1972871107687178241,0,'/api/image/Featured/2025-10-09/799f83fb-1756888554351.jpg'),(50,1972871107687178241,0,'/api/image/Featured/2025-10-09/35dca588-1756888554351.jpg'),(51,1972871107687178241,0,'/api/image/Featured/2025-10-09/274735d6-1756888554351.jpg'),(52,1972871107687178241,0,'/api/image/Featured/2025-10-09/088a25d6-1756888554351.jpg'),(53,1975826470644456877,0,'/api/image/Featured/2025-10-09/79589ec2-胆大党_第二季_1757496400778.jpg'),(54,1975826470644456877,0,'/api/image/Featured/2025-10-09/ce532a01-1756888554351.jpg'),(55,1975826470644456877,0,'/api/image/Featured/2025-10-09/5b4f532c-ligg.jpg');
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spec`
--

DROP TABLE IF EXISTS `spec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spec` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品id',
  `name` char(50) NOT NULL COMMENT '规格名称：如“颜色”、“尺寸”',
  `sort` int NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `spec_product_id_index` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品规格';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spec`
--

LOCK TABLES `spec` WRITE;
/*!40000 ALTER TABLE `spec` DISABLE KEYS */;
INSERT INTO `spec` VALUES (1,95,'宰建国',1,'2025-10-09 12:41:46','2025-10-09 12:41:46'),(2,95,'宰建国',1,'2025-10-09 12:42:21','2025-10-09 12:42:21'),(3,95,'宰建国',1,'2025-10-09 12:42:23','2025-10-09 12:42:23'),(4,95,'宰建国',1,'2025-10-09 12:42:25','2025-10-09 12:42:25'),(5,95,'宰建国',1,'2025-10-09 12:42:27','2025-10-09 12:42:27'),(6,95,'宰建国',1,'2025-10-09 12:42:29','2025-10-09 12:42:29'),(7,1975826470644456877,'颜色',50,'2025-10-09 13:50:24','2025-10-09 13:50:24'),(8,1975826470644456877,'型号',50,'2025-10-09 13:50:30','2025-10-09 13:50:30'),(9,1975826470644456877,'尺寸',50,'2025-10-09 13:50:32','2025-10-09 13:50:32');
/*!40000 ALTER TABLE `spec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spec_value`
--

DROP TABLE IF EXISTS `spec_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spec_value` (
  `id` int NOT NULL AUTO_INCREMENT,
  `spec_id` int NOT NULL COMMENT '规格id',
  `value` varchar(100) NOT NULL COMMENT '值(内容)',
  `sort` int NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `price` double DEFAULT '0' COMMENT '规格价格',
  PRIMARY KEY (`id`),
  KEY `spec_value_spec_id_index` (`spec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='规则值';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spec_value`
--

LOCK TABLES `spec_value` WRITE;
/*!40000 ALTER TABLE `spec_value` DISABLE KEYS */;
INSERT INTO `spec_value` VALUES (1,82,'voluptate',94,'2025-10-09 13:01:49','2025-10-09 13:01:49',NULL),(2,6,'voluptate',94,'2025-10-09 13:09:13','2025-10-09 13:09:13',NULL),(3,6,'voluptate',94,'2025-10-09 13:09:17','2025-10-09 13:09:17',NULL),(4,6,'voluptate',94,'2025-10-09 13:09:19','2025-10-09 13:09:19',NULL),(5,6,'voluptate',94,'2025-10-09 13:09:20','2025-10-09 13:09:20',NULL),(6,6,'voluptate',94,'2025-10-09 13:09:22','2025-10-09 13:09:22',NULL),(7,6,'voluptate',94,'2025-10-09 13:09:24','2025-10-09 13:09:24',NULL),(8,6,'voluptate',94,'2025-10-09 13:09:26','2025-10-09 13:09:26',NULL),(9,10,'aliquip dolor cupidatat',46,'2025-10-09 13:51:21','2025-10-09 13:51:21',2),(10,10,'aliquip dolor cupidatat',46,'2025-10-09 13:51:23','2025-10-09 13:51:23',10),(11,9,'13寸',46,'2025-10-09 13:51:24','2025-10-09 13:51:24',10),(12,9,'14寸',46,'2025-10-09 13:51:26','2025-10-09 13:51:26',1),(13,7,'红色',46,'2025-10-09 13:51:28','2025-10-09 13:51:28',0),(14,8,'pro',42,'2025-10-09 13:52:44','2025-10-09 13:52:44',10),(15,7,'绿色',42,'2025-10-09 13:52:47','2025-10-09 13:52:47',0),(16,8,'max',42,'2025-10-09 13:52:49','2025-10-09 13:52:49',NULL);
/*!40000 ALTER TABLE `spec_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL,
  `nick_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '昵称',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL,
  `account` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '账号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci DEFAULT NULL,
  `role` int NOT NULL DEFAULT '1' COMMENT '角色(用户、管理员)',
  `status` int NOT NULL DEFAULT '1' COMMENT '用户状态',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_cs_0900_ai_ci COMMENT='账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1971025962012487682','user_3299b3','$2a$12$JTMI2j0uvoJm/wTUMZawvOZZd3VtjNYkyOpyzUTdH7P93GZBCv7Mq','123456','/',1,1,'2025-09-25 09:36:24');
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

-- Dump completed on 2025-10-09 14:51:25
