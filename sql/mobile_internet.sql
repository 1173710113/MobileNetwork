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

 Date: 07/01/2020 17:51:13
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
  INDEX `course_student`(`student`) USING BTREE,
  CONSTRAINT `cours_id` FOREIGN KEY (`course`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_student` FOREIGN KEY (`student`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `real_vol` int(3) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `start_time`(`start_time`) USING BTREE,
  INDEX `start_time_2`(`start_time`, `end_time`) USING BTREE,
  INDEX `teacher`(`teacher`) USING BTREE,
  CONSTRAINT `teacher` FOREIGN KEY (`teacher`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for discussion_table
-- ----------------------------
DROP TABLE IF EXISTS `discussion_table`;
CREATE TABLE `discussion_table`  (
  `id` int(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NOT NULL,
  `title` char(52) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `course` int(10) UNSIGNED NULL DEFAULT NULL,
  `reply_count` int(10) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reply_course`(`course`) USING BTREE,
  INDEX `discussion_poster`(`user`) USING BTREE,
  CONSTRAINT `discussion_poster` FOREIGN KEY (`user`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reply_course` FOREIGN KEY (`course`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for enroll_table
-- ----------------------------
DROP TABLE IF EXISTS `enroll_table`;
CREATE TABLE `enroll_table`  (
  `code` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_id` int(10) UNSIGNED NOT NULL,
  `due_time` datetime(0) NOT NULL,
  INDEX `enroll_course`(`course_id`) USING BTREE,
  INDEX `enroll_due`(`due_time`) USING BTREE,
  CONSTRAINT `enroll_course` FOREIGN KEY (`course_id`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_table
-- ----------------------------
DROP TABLE IF EXISTS `file_table`;
CREATE TABLE `file_table`  (
  `id` int(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  `path` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `size` bigint(50) UNSIGNED NOT NULL DEFAULT 0,
  `user` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course` int(10) UNSIGNED NOT NULL,
  `time` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `file_course`(`course`) USING BTREE,
  INDEX `file_poster`(`user`) USING BTREE,
  CONSTRAINT `file_course` FOREIGN KEY (`course`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `file_poster` FOREIGN KEY (`user`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  INDEX `homework_course`(`course`) USING BTREE,
  INDEX `homework-poster`(`user`) USING BTREE,
  CONSTRAINT `homework-poster` FOREIGN KEY (`user`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `homework_course` FOREIGN KEY (`course`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_table
-- ----------------------------
DROP TABLE IF EXISTS `question_table`;
CREATE TABLE `question_table`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `test_id` int(10) UNSIGNED NOT NULL,
  `score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `question_test`(`test_id`) USING BTREE,
  CONSTRAINT `question_test` FOREIGN KEY (`test_id`) REFERENCES `test_table` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for reply_table
-- ----------------------------
DROP TABLE IF EXISTS `reply_table`;
CREATE TABLE `reply_table`  (
  `discussion_id` int(32) UNSIGNED NOT NULL,
  `user` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reply_discussion_id`(`discussion_id`) USING BTREE,
  INDEX `reply_poster`(`user`) USING BTREE,
  CONSTRAINT `reply_discussion_id` FOREIGN KEY (`discussion_id`) REFERENCES `discussion_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reply_poster` FOREIGN KEY (`user`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for score_table
-- ----------------------------
DROP TABLE IF EXISTS `score_table`;
CREATE TABLE `score_table`  (
  `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `test_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `every_score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `score_test`(`test_id`) USING BTREE,
  INDEX `score-student`(`student_id`) USING BTREE,
  CONSTRAINT `score-student` FOREIGN KEY (`student_id`) REFERENCES `user_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `score_test` FOREIGN KEY (`test_id`) REFERENCES `test_table` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for test_table
-- ----------------------------
DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table`  (
  `test_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` datetime(0) NOT NULL,
  `end_time` datetime(0) NOT NULL,
  `course_id` int(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`test_id`) USING BTREE,
  INDEX `test_course`(`course_id`) USING BTREE,
  CONSTRAINT `test_course` FOREIGN KEY (`course_id`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` enum('管理员','教师','学生') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
