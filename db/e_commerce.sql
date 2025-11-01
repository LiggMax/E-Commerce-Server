/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80040
 Source Host           : localhost:3306
 Source Schema         : e_commerce

 Target Server Type    : MySQL
 Target Server Version : 80040
 File Encoding         : 65001

 Date: 01/11/2025 15:49:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '标题',
  `description` char(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '描述',
  `status` tinyint NOT NULL COMMENT '状态',
  `target` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '打开方式',
  `sort` int NOT NULL COMMENT '排序',
  `subtitle` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NULL DEFAULT NULL COMMENT '副标题',
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '图片地址',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `button_text` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '按钮文字',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_cs_0900_ai_ci COMMENT = '轮播图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for email
-- ----------------------------
DROP TABLE IF EXISTS `email`;
CREATE TABLE `email`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` int NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` bigint NOT NULL,
  `order_id` bigint NOT NULL COMMENT '订单id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '下单商品名称(冗余)',
  `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '下单商品价格(冗余)',
  `quantity` int NOT NULL COMMENT '购买数量',
  `subtotal` decimal(10, 2) NOT NULL COMMENT '小计金额(价格x数量)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_item_product_id_index`(`product_id` ASC) USING BTREE,
  INDEX `order_item_order_id_index`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_item_spec
-- ----------------------------
DROP TABLE IF EXISTS `order_item_spec`;
CREATE TABLE `order_item_spec`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_item_id` bigint NOT NULL COMMENT '订单明细ID',
  `spec_value_id` bigint NOT NULL COMMENT '规格值ID',
  `spec_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规格名称（冗余存储）',
  `spec_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规格内容（冗余存储）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细规格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL,
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '订单总价',
  `pay_type` tinyint NOT NULL DEFAULT 0 COMMENT '支付方式',
  `status` tinyint NOT NULL COMMENT '订单状态（0=待支付，1=已支付，2=已发货，3=已收货，4=已取消，5=退款中，6=已退款）',
  `address_id` bigint NOT NULL COMMENT '收货地址id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建使时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_pk_2`(`order_no` ASC) USING BTREE,
  INDEX `orders_create_time_index`(`create_time` ASC) USING BTREE,
  INDEX `orders_update_time_index`(`update_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for payment_log
-- ----------------------------
DROP TABLE IF EXISTS `payment_log`;
CREATE TABLE `payment_log`  (
  `id` bigint NOT NULL,
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `pay_type` tinyint NULL DEFAULT NULL COMMENT '支付方式',
  `transaction_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方支付流水号',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `pay_status` tinyint NOT NULL COMMENT '支付状态（0=未支付，1=支付成功）',
  `pay_time` datetime NOT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint NOT NULL,
  `title` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `original_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '原价',
  `current_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '现价',
  `reviews` int NOT NULL DEFAULT 0 COMMENT '评论数',
  `rating` tinyint NOT NULL DEFAULT 5 COMMENT '评价',
  `created_at` datetime NOT NULL COMMENT '上传时间',
  `update_at` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint NOT NULL COMMENT '状态(1=上架，0=下架)',
  `stock` int NULL DEFAULT 0 COMMENT '商品库存',
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `product_chk_1` CHECK (`stock` >= 0)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_comment
-- ----------------------------
DROP TABLE IF EXISTS `product_comment`;
CREATE TABLE `product_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
  `rating` tinyint NOT NULL COMMENT '评分（1~5星）',
  `images` json NULL COMMENT '评论图片，JSON数组格式',
  `type` tinyint NOT NULL DEFAULT 1 COMMENT '评论类型（1初评/2追评/3回复）',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '评论状态',
  `is_anonymous` tinyint(1) NULL DEFAULT 0 COMMENT '是否匿名评论',
  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论时的IP',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product`(`product_id` ASC) USING BTREE,
  INDEX `idx_user`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_detail
-- ----------------------------
DROP TABLE IF EXISTS `product_detail`;
CREATE TABLE `product_detail`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品id',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '精选商品详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_favorite
-- ----------------------------
DROP TABLE IF EXISTS `product_favorite`;
CREATE TABLE `product_favorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `product_favorite_pk`(`user_id` ASC, `product_id` ASC) USING BTREE,
  INDEX `product_favorite_product_id_fk`(`product_id` ASC) USING BTREE,
  CONSTRAINT `product_favorite_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_image
-- ----------------------------
DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品id',
  `sort` int NULL DEFAULT NULL,
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `featured_image_featured_id_index`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_spec
-- ----------------------------
DROP TABLE IF EXISTS `product_spec`;
CREATE TABLE `product_spec`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品id',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规格名称：如“颜色”、“尺寸”',
  `sort` int NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `spec_product_id_index`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品规格' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_spec_value
-- ----------------------------
DROP TABLE IF EXISTS `product_spec_value`;
CREATE TABLE `product_spec_value`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `spec_id` int NOT NULL COMMENT '规格id',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值(内容)',
  `sort` int NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '规格价格',
  `product_id` bigint NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `spec_value_spec_id_index`(`spec_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '规则值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL,
  `nick_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '昵称',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL,
  `account` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NOT NULL COMMENT '账号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NULL DEFAULT NULL,
  `role` int NOT NULL DEFAULT 1 COMMENT '角色(1用户、2管理员)',
  `status` int NOT NULL DEFAULT 1 COMMENT '用户状态(1=正常  0=禁用)',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `account_balance` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '余额',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_pk`(`account` ASC) USING BTREE,
  CONSTRAINT `check_name` CHECK (`account_balance` >= 0)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '账户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `receiver_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收件人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系电话',
  `province` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份',
  `city` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市',
  `district` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区/县',
  `is_default` tinyint NULL DEFAULT 0 COMMENT '是否默认地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详情地址',
  `user_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_address_user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收货地址' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
