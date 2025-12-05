-- 创建数据库
CREATE DATABASE IF NOT EXISTS second_hand_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE second_hand_db;

-- 用户表
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100),
                       phone VARCHAR(20),
                       salt VARCHAR(255) NOT NULL,
                       create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       INDEX idx_username (username)
);

-- 物品表
CREATE TABLE items (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(100) NOT NULL,
                       description TEXT,
                       category VARCHAR(20) NOT NULL COMMENT '捡到的物品/多余物品',
                       status VARCHAR(10) DEFAULT '待交易' COMMENT '待交易/已交易',
                       price DECIMAL(10, 2),
                       location VARCHAR(100),
                       found_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       post_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       INDEX idx_title (title),
                       INDEX idx_category (category),
                       INDEX idx_user (user_id),
                       INDEX idx_post_date (post_date DESC)
);

-- 插入测试数据
INSERT INTO users (username, password, email, phone, salt) VALUES
                                                               ('testuser', 'hashed_password_here', 'test@example.com', '13800138000', 'salt_value_here'),
                                                               ('admin', 'hashed_password_here', 'admin@example.com', '13900139000', 'salt_value_here');

INSERT INTO items (title, description, category, price, location, user_id) VALUES
                                                                               ('捡到的iPhone 13', '在图书馆捡到一部黑色iPhone 13，有蓝色手机壳', '捡到的物品', 0, '学校图书馆三楼', 1),
                                                                               ('闲置的自行车', '九成新山地自行车，因搬家出售', '多余物品', 500, '北京市海淀区', 1),
                                                                               ('丢失的书包', '红色双肩包，内有课本和文具盒', '捡到的物品', 0, '操场看台', 2),
                                                                               ('二手笔记本电脑', '联想ThinkPad，配置良好，适合办公', '多余物品', 2000, '上海市浦东新区', 2);

-- 查看表结构
DESC users;
DESC items;

-- 查看数据
SELECT * FROM users;
SELECT * FROM items;