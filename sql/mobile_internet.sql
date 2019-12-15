/*
 Navicat Premium Data Transfer

 Source Server         : demo
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : mobile_internet

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 15/12/2019 13:41:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_student
-- ----------------------------
DROP TABLE IF EXISTS `course_student`;
CREATE TABLE `course_student`  (
  `student` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course` int(10) UNSIGNED NOT NULL,
  INDEX `cours_id`(`course`) USING BTREE,
  INDEX `student_id`(`student`) USING BTREE,
  CONSTRAINT `cours_id` FOREIGN KEY (`course`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_id` FOREIGN KEY (`student`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_table
-- ----------------------------
DROP TABLE IF EXISTS `course_table`;
CREATE TABLE `course_table`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `teacher` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `max_vol` int(4) NOT NULL,
  `destination` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` datetime(0) NOT NULL,
  `end_time` datetime(0) NOT NULL,
  `total_time` int(3) UNSIGNED NOT NULL,
  `real_vol` int(3) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `course_table`(`teacher`) USING BTREE,
  CONSTRAINT `course_table` FOREIGN KEY (`teacher`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for discussion_table
-- ----------------------------
DROP TABLE IF EXISTS `discussion_table`;
CREATE TABLE `discussion_table`  (
  `id` int(32) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `user` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NOT NULL,
  `title` char(52) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `course` int(10) UNSIGNED NULL DEFAULT NULL,
  `reply_count` int(10) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user`) USING BTREE,
  INDEX `reply_course`(`course`) USING BTREE,
  CONSTRAINT `reply_course` FOREIGN KEY (`course`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user`) REFERENCES `user_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_table
-- ----------------------------
DROP TABLE IF EXISTS `file_table`;
CREATE TABLE `file_table`  (
  `path` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`path`) USING BTREE,
  INDEX `post_user`(`user`) USING BTREE,
  CONSTRAINT `post_user` FOREIGN KEY (`user`) REFERENCES `user_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for homework_table
-- ----------------------------
DROP TABLE IF EXISTS `homework_table`;
CREATE TABLE `homework_table`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deadline` datetime(6) NOT NULL,
  `time` datetime(6) NOT NULL,
  `course` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `homework_poster`(`user`) USING BTREE,
  INDEX `homework_course`(`course`) USING BTREE,
  CONSTRAINT `homework_course` FOREIGN KEY (`course`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `homework_poster` FOREIGN KEY (`user`) REFERENCES `user_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for reply_table
-- ----------------------------
DROP TABLE IF EXISTS `reply_table`;
CREATE TABLE `reply_table`  (
  `discussion_id` int(10) UNSIGNED NOT NULL,
  `user` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reply_user_id`(`user`) USING BTREE,
  INDEX `reply_discussion_id`(`discussion_id`) USING BTREE,
  CONSTRAINT `reply_discussion_id` FOREIGN KEY (`discussion_id`) REFERENCES `discussion_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reply_user_id` FOREIGN KEY (`user`) REFERENCES `user_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `id` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` enum('管理员','教师','学生') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
