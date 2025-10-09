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
INSERT INTO `carousel` VALUES (22,'132','qwe',1,'qwe',2,'qw','/api/image/Carousel/2025-09-29/a1273813-胆大党_第二季_1757496400778.jpg','qwe','qew','2025-09-27 15:05:51','2025-09-30 11:34:55'),(24,'达娃大大·','大王大大',0,'_self',1,'达娃大','/api/image/Carousel/2025-09-29/1eef2431-1756888554351.jpg','','了解更多','2025-09-29 16:25:22','2025-09-30 15:46:41');
/*!40000 ALTER TABLE `carousel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `featured`
--

DROP TABLE IF EXISTS `featured`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `featured` (
  `id` bigint NOT NULL,
  `title` char(30) NOT NULL COMMENT '商品名称',
  `image_path` varchar(255) DEFAULT NULL,
  `original_price` double NOT NULL COMMENT '原价',
  `current_price` double NOT NULL COMMENT '现价',
  `reviews` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `rating` float NOT NULL DEFAULT '5' COMMENT '评价',
  `created_at` datetime NOT NULL COMMENT '上传时间',
  `update_at` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='特色商品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `featured`
--

LOCK TABLES `featured` WRITE;
/*!40000 ALTER TABLE `featured` DISABLE KEYS */;
INSERT INTO `featured` VALUES (1972871107687178241,'任务图我','/api/image/Featured/2025-09-30/2002b402-更衣人偶坠入爱河_第二季_1757496429774.jpg',123,12,0,9,'2025-09-30 11:48:21','2025-09-30 11:48:21'),(1972967206737379330,'title','/api/image/Featured/2025-09-30/c2b40d1f-更衣人偶坠入爱河_第二季_1757496429774.jpg',111,11,0,6,'2025-09-30 18:10:13','2025-09-30 18:10:13');
/*!40000 ALTER TABLE `featured` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `featured_detail`
--

DROP TABLE IF EXISTS product_detail;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `featured_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `featured_id` bigint NOT NULL COMMENT '商品id',
  `description` varchar(255) NOT NULL COMMENT '商品描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='精选商品详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `featured_detail`
--

LOCK TABLES product_detail WRITE;
/*!40000 ALTER TABLE product_detail DISABLE KEYS */;
INSERT INTO product_detail VALUES (1,1972960556001492994,'daw'),(2,1972967206737379330,'描述');
/*!40000 ALTER TABLE product_detail ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS product_image;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` binary(1) NOT NULL COMMENT '商品id',
  `sort` int DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES product_image WRITE;
/*!40000 ALTER TABLE product_image DISABLE KEYS */;
/*!40000 ALTER TABLE product_image ENABLE KEYS */;
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

-- Dump completed on 2025-09-30 19:16:12
