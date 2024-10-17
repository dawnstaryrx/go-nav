# 数据库设计
## 1. USER 用户表

- id: bigint 主键 唯一
- username: varchar(100) 用户名 唯一
- email: varchar(100) 邮箱 唯一
- phone: varchar(20) 手机号 唯一
- password: varchar(255) 密码
- nickname: varchar(20) 昵称
- role: tinyint(1) 0:禁用用户 1:普通用户 2:管理员
- github_openid: varchar(50) GitHub OpenID 唯一
- linuxdo_openid: varchar(50) LinuxDo OpenID 唯一
- create_time: datetime 创建时间
- update_time: datetime 更新时间

```mysql
CREATE TABLE `user` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
	`username` VARCHAR(100) NULL DEFAULT NULL COMMENT '用户名' COLLATE 'utf8mb4_0900_ai_ci',
	`email` VARCHAR(100) NULL DEFAULT NULL COMMENT '电子邮件' COLLATE 'utf8mb4_0900_ai_ci',
	`phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '电话号码' COLLATE 'utf8mb4_0900_ai_ci',
	`password` VARCHAR(255) NULL DEFAULT NULL COMMENT '密码' COLLATE 'utf8mb4_0900_ai_ci',
	`nickname` VARCHAR(20) NOT NULL DEFAULT '游客' COMMENT '昵称' COLLATE 'utf8mb4_0900_ai_ci',
	`role` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '角色，0表示禁用用户，1表示用户，2表示管理员',
	`github_openid` VARCHAR(50) NULL DEFAULT NULL COMMENT 'GithubOpenID，用户在当前应用的唯一标识' COLLATE 'utf8mb4_0900_ai_ci',
	`linuxdo_openid` VARCHAR(50) NULL DEFAULT NULL COMMENT 'LinuxDoId' COLLATE 'utf8mb4_0900_ai_ci',
	`create_time` DATETIME NOT NULL COMMENT '创建时间',
	`update_time` DATETIME NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `email` (`email`) USING BTREE,
	UNIQUE INDEX `username` (`username`) USING BTREE,
	UNIQUE INDEX `phone` (`phone`) USING BTREE,
	UNIQUE INDEX `wechat_openid` (`github_openid`) USING BTREE,
	UNIQUE INDEX `linuxdo_openid` (`linuxdo_openid`) USING BTREE,
	CONSTRAINT `user_chk_1` CHECK ((`role` in (0,1,2)))
)
COMMENT='用户表'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=16
;
```

## 2. CATEGORY分类表

- id: bigint 主键 唯一
- parent_id: bigint 父分类的id，默认为NULL没有父分类
- name: varchar(10) 分类名  唯一
- description: varchar(100) 分类描述
- weight: int 分类权重0-100，默认0
- status: tinyint(1) 0:禁用分类 1:启用分类
- create_user: bigint 创建者id
- create_time: datetime 创建时间
- update_user: bigint 更新者id
- update_time: datetime 更新时间

```mysql
CREATE TABLE `category` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
    `parent_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '父分类的ID，NULL表示没有父分类',
    `name` VARCHAR(10) NOT NULL COMMENT '分类名，唯一' COLLATE 'utf8mb4_0900_ai_ci',
    `description` VARCHAR(100) NULL DEFAULT NULL COMMENT '分类描述' COLLATE 'utf8mb4_0900_ai_ci',
    `weight` INT NOT NULL DEFAULT 0 COMMENT '分类权重，范围0-100，默认0',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0表示禁用，1表示启用',
    `create_user` BIGINT UNSIGNED NOT NULL COMMENT '创建者ID',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_user` BIGINT UNSIGNED NULL COMMENT '更新者ID',
    `update_time` DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `category_name_unique` (`name`) USING BTREE, -- 确保分类名唯一
    INDEX `category_parent_idx` (`parent_id`) USING BTREE -- 便于快速查询父子分类关系
)
COMMENT='分类表'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB;
```

## 3. APP 应用表

- id: bigint 主键  唯一
- name: varchar(30) 应用名  唯一
- url: varchar(100) 应用地址  唯一
- icon_url: varchar(255) 图标路径
- category_id: bigint 分类id 逻辑外键
- description: varchar(100) 应用描述
- weight: int 应用权重0-100，默认0
- click_count: int 点击量
- status: tinyint(1) COMMENT '0:禁用, 1:启用, 2:隐藏, 3:私有' 0:禁用应用 1:启用应用 2: 隐藏应用 3: 私有应用
- create_user: bigint 创建者id
- create_time: datetime 创建时间
- update_user: bigint 更新者id
- update_time: datetime 更新时间

```mysql
CREATE TABLE `app` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
    `name` VARCHAR(30) NOT NULL COMMENT '应用名，唯一' COLLATE 'utf8mb4_0900_ai_ci',
    `url` VARCHAR(100) NOT NULL COMMENT '应用地址，唯一' COLLATE 'utf8mb4_0900_ai_ci',
    `icon_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '图标路径' COLLATE 'utf8mb4_0900_ai_ci',
    `category_id` BIGINT UNSIGNED NOT NULL COMMENT '分类ID，逻辑外键',
    `description` VARCHAR(100) NULL DEFAULT NULL COMMENT '应用描述' COLLATE 'utf8mb4_0900_ai_ci',
    `weight` INT NOT NULL DEFAULT 0 COMMENT '应用权重，范围0-100，默认0',
    `click_count` INT NOT NULL DEFAULT 0 COMMENT '点击量，默认0',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0:禁用, 1:启用, 2:隐藏, 3:私有',
    `create_user` BIGINT UNSIGNED NOT NULL COMMENT '创建者ID',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_user` BIGINT UNSIGNED NULL COMMENT '更新者ID',
    `update_time` DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `app_name_unique` (`name`) USING BTREE, -- 确保应用名唯一
    UNIQUE INDEX `app_url_unique` (`url`) USING BTREE, -- 确保应用地址唯一
    INDEX `app_category_idx` (`category_id`) USING BTREE -- 便于快速按分类查询
)
COMMENT='应用表'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB;
```