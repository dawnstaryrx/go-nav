-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.36 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 gonav 的数据库结构
CREATE DATABASE IF NOT EXISTS `gonav` /*!40100 DEFAULT CHARACTER SET armscii8 COLLATE armscii8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gonav`;

-- 导出  表 gonav.app 结构
CREATE TABLE IF NOT EXISTS `app` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
  `name` varchar(30) DEFAULT NULL COMMENT '应用名',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用地址，唯一',
  `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图标路径',
  `category_id` bigint unsigned NOT NULL COMMENT '分类ID，逻辑外键',
  `description` varchar(255) DEFAULT NULL COMMENT '应用描述',
  `weight` int NOT NULL DEFAULT '0' COMMENT '应用权重，范围0-100，默认0',
  `click_count` int NOT NULL DEFAULT '0' COMMENT '点击量，默认0',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态： 1:启用, 2:隐藏, 3:私有4:禁用,',
  `create_user` bigint unsigned NOT NULL COMMENT '创建者ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint unsigned DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `url_create_user` (`url`,`create_user`),
  KEY `app_category_idx` (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用表';

-- 导出  表 gonav.category 结构
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
  `parent_id` bigint unsigned DEFAULT NULL COMMENT '父分类的ID，NULL表示没有父分类',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名，唯一',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分类描述',
  `weight` int NOT NULL DEFAULT '0' COMMENT '分类权重，范围0-100，默认0',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0表示禁用，1表示启用',
  `create_user` bigint unsigned NOT NULL COMMENT '创建者ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint unsigned DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_parent_idx` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';

-- 导出  表 gonav.menu 结构
CREATE TABLE IF NOT EXISTS `menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `url` varchar(50) NOT NULL,
  `type` int NOT NULL DEFAULT '0',
  `weight` int NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单-顶部导航菜单项，底部友情链接';


-- 导出  表 gonav.setting 结构
CREATE TABLE IF NOT EXISTS `setting` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL,
  `value_varchar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `value_text` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设置';

-- 正在导出表  gonav.setting 的数据：~9 rows (大约)
INSERT INTO `setting` (`id`, `key`, `value_varchar`, `value_text`) VALUES
	(1, 'announce', NULL, ''),
	(2, 'bottom_company_name', '111222', ''),
	(3, 'bottom_icp', '222333', ''),
	(4, 'bottom_police', '333666', ''),
	(5, 'site_name', 'Go导航', NULL),
	(6, 'site_logo', 'http://localhost/src/assets/logo.png', NULL),
	(7, 'login_github', 'true', ''),
	(8, 'login_linuxdo', 'true', ''),
	(9, 'github_logo', 'true', '');

-- 导出  表 gonav.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电话号码',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '游客' COMMENT '昵称',
  `role` tinyint(1) NOT NULL DEFAULT '0' COMMENT '角色，0表示禁用用户，1表示用户，2表示管理员',
  `github_openid` varchar(50) DEFAULT NULL COMMENT 'GithubOpenID，用户在当前应用的唯一标识',
  `linuxdo_openid` varchar(50) DEFAULT NULL COMMENT 'LinuxDoId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `wechat_openid` (`github_openid`) USING BTREE,
  UNIQUE KEY `linuxdo_openid` (`linuxdo_openid`),
  CONSTRAINT `user_chk_1` CHECK ((`role` in (0,1,2)))
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
