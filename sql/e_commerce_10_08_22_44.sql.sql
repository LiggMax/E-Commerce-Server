-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: e_commerce
-- ------------------------------------------------------
-- Server version	8.0.39

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_cs_0900_ai_ci COMMENT='轮播图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carousel`
--

LOCK TABLES `carousel` WRITE;
/*!40000 ALTER TABLE `carousel` DISABLE KEYS */;
INSERT INTO `carousel` VALUES (27,'轮播','miaos1',1,'_self',1,'副标题','/api/image/Carousel/2025-10-06/2e3c0bb6-idea.png','','了解更多','2025-10-06 17:26:05','2025-10-06 17:26:05'),(28,'qwe','123',1,'_self',2,'qwe','/api/image/Carousel/2025-10-06/d982aa75-1756731509018.jpg','','了解更多','2025-10-06 17:26:22','2025-10-06 17:26:22');
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
  `email` char(100) COLLATE utf8mb4_bg_0900_ai_ci NOT NULL,
  `created_at` datetime NOT NULL COMMENT '保持时间',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_ai_ci COMMENT='邮箱';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` VALUES (1,'123.com','2025-10-08 22:05:08',NULL),(2,'123@qq.com','2025-10-08 22:19:35',NULL),(3,'123@qq.com=','2025-10-08 22:20:34',NULL),(4,'123@qq.co','2025-10-08 22:20:40',NULL),(5,'wenzhou@gmail.com','2025-10-08 22:42:43',NULL),(6,'qwej@qq.com','2025-10-08 22:43:46',NULL);
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
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
INSERT INTO `featured` VALUES (1975130684262764546,'123','/api/image/Featured/2025-10-06/513b37fd-更衣人偶坠入爱河_1756915020203.jpg',12,11,5,0,'2025-10-06 17:27:06','2025-10-08 21:02:42'),(1975130785450348545,'123','/api/image/Featured/2025-10-08/54b3e70e-1756731509018.jpg',111,80,6,0,'2025-10-06 17:27:30','2025-10-08 21:02:49'),(1975909533074448385,'123','/api/image/Featured/2025-10-08/95c863fa-更衣人偶坠入爱河_1756915020203.jpg',12,12,7,8,'2025-10-08 21:01:58','2025-10-08 21:01:58'),(1975909575990566914,'132','/api/image/Featured/2025-10-08/398c2e12-1756913887943.jpg',12,12,4,8,'2025-10-08 21:02:08','2025-10-08 21:02:08'),(1975909890580144129,'123','/api/image/Featured/2025-10-08/37ebba50-更衣人偶坠入爱河_1756915020203.jpg',123,12,4,10,'2025-10-08 21:03:23','2025-10-08 21:03:23'),(1975917655390195714,'123','/api/image/Featured/2025-10-08/d2eb4cba-微信图片_20250927211021.png',123,123,0,6,'2025-10-08 21:34:14','2025-10-08 21:34:14'),(1975917696876056578,'12','/api/image/Featured/2025-10-08/d4571426-更衣人偶坠入爱河_1756915020203.jpg',12,12,0,6,'2025-10-08 21:34:24','2025-10-08 21:34:24'),(1975917775989018625,'123123','/api/image/Featured/2025-10-08/c5a6ff37-更衣人偶坠入爱河_1756915020203.jpg',123,12,0,10,'2025-10-08 21:34:43','2025-10-08 21:34:43'),(1975918080361271298,'123','/api/image/Featured/2025-10-08/7787d43c-微信图片_20250927211021.png',123,12,0,7,'2025-10-08 21:35:56','2025-10-08 21:35:56'),(1975918118697209857,'12','/api/image/Featured/2025-10-08/0693a8c6-1756731509018.jpg',12,12,0,9,'2025-10-08 21:36:05','2025-10-08 21:36:05'),(1975918176809291777,'123','/api/image/Featured/2025-10-08/eb8ee170-1756731509018.jpg',12,3,0,6,'2025-10-08 21:36:19','2025-10-08 21:36:19'),(1975918320040579073,'123','/api/image/Featured/2025-10-08/f7701812-idea.png',123,123,0,8,'2025-10-08 21:36:53','2025-10-08 21:36:53'),(1975918354756833282,'123','/api/image/Featured/2025-10-08/f95a3068-微信图片_20250927211021.png',12,12,0,5,'2025-10-08 21:37:01','2025-10-08 21:37:01'),(1975919271774294018,'123','/api/image/Featured/2025-10-08/ad2f8f07-微信图片_20250927211021.png',123,12,0,5,'2025-10-08 21:40:40','2025-10-08 21:40:40'),(1975919321216749570,'123','/api/image/Featured/2025-10-08/b087f7f9-微信图片_20250927211021.png',123,12,0,10,'2025-10-08 21:40:52','2025-10-08 21:40:52');
/*!40000 ALTER TABLE `featured` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `featured_detail`
--

DROP TABLE IF EXISTS `featured_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `featured_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `featured_id` bigint NOT NULL COMMENT '商品id',
  `description` varchar(255) NOT NULL COMMENT '商品描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='精选商品详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `featured_detail`
--

LOCK TABLES `featured_detail` WRITE;
/*!40000 ALTER TABLE `featured_detail` DISABLE KEYS */;
INSERT INTO `featured_detail` VALUES (1,1972960556001492994,'daw'),(2,1972967206737379330,'描述'),(3,1975130684262764546,'qwdwad'),(4,1975130785450348545,'描述'),(5,1975909533074448385,'123'),(6,1975909575990566914,'123'),(7,1975909890580144129,'123'),(8,1975917655390195714,'123'),(9,1975917696876056578,'12'),(10,1975917775989018625,'详情'),(11,1975918080361271298,'123'),(12,1975918118697209857,'12'),(13,1975918176809291777,'123'),(14,1975918320040579073,'123'),(15,1975918354756833282,'123'),(16,1975919271774294018,'123'),(17,1975919321216749570,'123');
/*!40000 ALTER TABLE `featured_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `featured_image`
--

DROP TABLE IF EXISTS `featured_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `featured_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `featured_id` binary(1) NOT NULL COMMENT '商品id',
  `sort` int DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `featured_image`
--

LOCK TABLES `featured_image` WRITE;
/*!40000 ALTER TABLE `featured_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `featured_image` ENABLE KEYS */;
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

-- Dump completed on 2025-10-08 22:44:31
