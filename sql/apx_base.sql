/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : apx_base

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 29/02/2024 09:25:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apx_mining_record
-- ----------------------------
DROP TABLE IF EXISTS `apx_mining_record`;
CREATE TABLE `apx_mining_record`  (
  `id` int(11) NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `user_pet_id` int(11) NULL DEFAULT NULL,
  `output` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `start_time` bigint(20) NULL DEFAULT NULL,
  `end_time` bigint(20) NULL DEFAULT NULL,
  `next_time` datetime(0) NULL DEFAULT NULL,
  `last_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apx_mining_record
-- ----------------------------

-- ----------------------------
-- Table structure for apx_pet_11
-- ----------------------------
DROP TABLE IF EXISTS `apx_pet_11`;
CREATE TABLE `apx_pet_11`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `satiety_max` int(11) NULL DEFAULT NULL,
  `satiety_speed` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apx_pet_11
-- ----------------------------
INSERT INTO `apx_pet_11` VALUES (1, '鸡', 'https://img1.baidu.com/it/u=3332199712,943601215&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=666', 120, 30);
INSERT INTO `apx_pet_11` VALUES (2, '猴', 'https://img1.baidu.com/it/u=3746969077,2283253897&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500', 100, 30);
INSERT INTO `apx_pet_11` VALUES (3, '熊', 'https://img2.baidu.com/it/u=630313551,2931951926&fm=253&fmt=auto&app=138&f=JPEG?w=550&h=412', 100, 32);

-- ----------------------------
-- Table structure for apx_prop_11
-- ----------------------------
DROP TABLE IF EXISTS `apx_prop_11`;
CREATE TABLE `apx_prop_11`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `iq_value_bonus` int(11) NULL DEFAULT NULL,
  `luck_value_bonus` int(11) NULL DEFAULT NULL,
  `satiety_value_bonus` int(11) NULL DEFAULT NULL,
  `iq_value_add` int(11) NULL DEFAULT NULL,
  `balance_add` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `luck_value_add` int(11) NULL DEFAULT NULL,
  `satiety_max_add` int(11) NULL DEFAULT NULL,
  `satiety_speed_add` int(11) NULL DEFAULT NULL,
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apx_prop_11
-- ----------------------------
INSERT INTO `apx_prop_11` VALUES (1, '小红帽', 'http://t14.baidu.com/it/u=204986681,771885082&fm=224&app=112&f=JPEG?w=350&h=350', 1, 0, 0, 0, 0, '0.1', 10, 30, 1, '0.5', NULL, NULL);
INSERT INTO `apx_prop_11` VALUES (2, '汉堡', 'https://img1.baidu.com/it/u=733557995,2184033731&fm=253&fmt=auto&app=138&f=JPEG?w=634&h=417', 2, 5, 0, 30, 0, '0', 0, 0, 0, '0.1', NULL, NULL);
INSERT INTO `apx_prop_11` VALUES (3, '宝箱', 'https://img2.baidu.com/it/u=4020787438,2306740685&fm=253&fmt=auto&app=138&f=PNG?w=300&h=300', 3, 0, 0, 0, 0, '0', 0, 0, 0, '0', NULL, NULL);
INSERT INTO `apx_prop_11` VALUES (4, '手套', 'http://t13.baidu.com/it/u=4012085318,1090827073&fm=224&app=112&f=JPEG?w=500&h=500', 1, 0, 0, 0, 0, '0.2', 2, 0, 4, '0.9', NULL, NULL);
INSERT INTO `apx_prop_11` VALUES (5, '薯条', 'https://img2.baidu.com/it/u=1294942428,2442499240&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=540', 2, 4, 1, 5, 0, '0', 0, 0, 0, '0.2', NULL, NULL);

-- ----------------------------
-- Table structure for apx_user
-- ----------------------------
DROP TABLE IF EXISTS `apx_user`;
CREATE TABLE `apx_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tp_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方id',
  `tp_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方名称',
  `tp_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方用户名',
  `fa` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `encrypted_private_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `balance` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '余额',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `my_invitation_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `invitation_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apx_user
-- ----------------------------
INSERT INTO `apx_user` VALUES (2, '1593865634064248832', 'LinTianHong', 'lintianhong2333', NULL, '0xDF357E75d1ec955Bdfa5Fc9E000b22A798c81b50', '9cc06904d87ed9a67ea59e1b4286a9687bd418b0896864e82080df7c7b801452889acfbe7c0ccbdab0f32e936783e97e5543b6e11c022b8f6db4ceea', '0.0000000000', NULL, NULL, 'apxl-nmu12m7y', NULL);

-- ----------------------------
-- Table structure for apx_user_pet
-- ----------------------------
DROP TABLE IF EXISTS `apx_user_pet`;
CREATE TABLE `apx_user_pet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `pet_id` int(11) NULL DEFAULT NULL,
  `iq_level` int(11) NULL DEFAULT NULL COMMENT '等级',
  `iq_value` int(11) NULL DEFAULT NULL COMMENT '经验值',
  `satiety_value` int(11) NULL DEFAULT NULL COMMENT '饱腹值',
  `luck_value` int(11) NULL DEFAULT NULL COMMENT '幸运值',
  `active` int(11) NULL DEFAULT NULL,
  `busy` int(11) NULL DEFAULT NULL COMMENT 'active=1时，这个字段才能是1',
  `satiety_max` int(11) NULL DEFAULT NULL,
  `satiety_speed` int(11) NULL DEFAULT NULL,
  `last_time` bigint(11) NULL DEFAULT NULL,
  `next_time` bigint(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apx_user_pet
-- ----------------------------
INSERT INTO `apx_user_pet` VALUES (1, 2, 1, 2, 20, 100, -959, 0, 0, 30, 30, 3418220736531, 3418220736531);
INSERT INTO `apx_user_pet` VALUES (2, 2, 2, 5, 80, 110, 4, 1, 0, 30, 30, 3418220976548, 3418220976548);
INSERT INTO `apx_user_pet` VALUES (3, 1, 3, 1, 3, 99, 960, 1, 0, 30, 30, 3418220676558, 3418220676558);

-- ----------------------------
-- Table structure for apx_user_prop
-- ----------------------------
DROP TABLE IF EXISTS `apx_user_prop`;
CREATE TABLE `apx_user_prop`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `prop_id` int(11) NULL DEFAULT NULL,
  `user_pet_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apx_user_prop
-- ----------------------------
INSERT INTO `apx_user_prop` VALUES (1, 2, 1, 2);
INSERT INTO `apx_user_prop` VALUES (2, 2, 3, NULL);
INSERT INTO `apx_user_prop` VALUES (10, 2, 1, NULL);
INSERT INTO `apx_user_prop` VALUES (13, 2, 1, NULL);
INSERT INTO `apx_user_prop` VALUES (14, 2, 1, NULL);
INSERT INTO `apx_user_prop` VALUES (16, 2, 1, NULL);
INSERT INTO `apx_user_prop` VALUES (17, 2, 1, NULL);
INSERT INTO `apx_user_prop` VALUES (18, 2, 3, NULL);
INSERT INTO `apx_user_prop` VALUES (19, 2, 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
