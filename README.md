# second-hand-trading-platform-v2
一个基于Java Web的二手交易平台

## 🏗️ 系统结构设计

### 技术栈
- **后端**：Java 17, Servlet 6.0, JSP 3.0, JSTL
- **前端**：HTML5, CSS3, JavaScript
- **数据库**：MySQL 8.0, JDBC
- **服务器**：Apache Tomcat 10.1+
- **项目管理**：Maven
- **安全**：SHA-256 密码加密 + 盐值

### 项目架构 (MVC模式)

表示层 (View):
├── /WEB-INF/views/*.jsp# 所有JSP视图页面
└── 使用JSTL标签库展示数据

控制层 (Controller):
├── UserController.java # 处理 /user/* 请求（登录、注册、个人中心）
├── ItemController.java # 处理 /item/* 请求（物品增删改查）
└── SearchController.java # 处理 /search 请求

业务层 (Service):
├── UserService.java # 用户注册、登录、查询业务
├── ItemService.java # 物品相关的业务逻辑
└── PasswordUtil.java # 密码加密、验证工具类

数据访问层 (DAO):
├── UserDAO.java # 操作用户表 (users)
└── ItemDAO.java # 操作物品表 (items)

模型层 (Model):
├── User.java # 用户实体类，对应 users 表
└── Item.java # 物品实体类，对应 items 表

过滤器 (Filter):
├── EncodingFilter.java # 全局编码过滤器 (UTF-8)
└── AuthenticationFilter.java # 认证过滤器，保护 /item/* 和 /user/profile

配置层 (Config):
└── DatabaseConfig.java # 数据库连接配置，读取 database.properties


### 核心JSP页面
- `index.jsp` - 系统首页，含导航和搜索框
- `login.jsp` / `register.jsp` - 用户登录与注册
- `profile.jsp` - 个人中心
- `add_item.jsp` / `edit_item.jsp` - 发布/编辑物品
- `item_list.jsp` - “我的物品”列表
- `search.jsp` - 搜索结果页

### 安全设计
1. **密码安全**：使用 `PasswordUtil` 生成盐值，通过 `SHA-256` 哈希加密存储。
2. **会话管理**：通过 `HttpSession` 管理用户登录状态，设置30分钟超时。
3. **访问控制**：`AuthenticationFilter` 拦截未登录用户对物品和个人中心页面的访问。
4. **输入防护**：所有SQL查询使用 `PreparedStatement`，有效防止SQL注入。

---

## 🗄️ 数据库结构说明

数据库名：`second_hand_db` (建议使用 `utf8mb4` 字符集)

### 1. 用户表 (users)
| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | INT | PRIMARY KEY AUTO_INCREMENT | 用户唯一ID |
| username | VARCHAR(50) | NOT NULL UNIQUE | 用户名，用于登录 |
| password | VARCHAR(255) | NOT NULL | 加密后的密码（SHA-256 + 盐值） |
| email | VARCHAR(100) | DEFAULT NULL | 用户邮箱 |
| phone | VARCHAR(20) | DEFAULT NULL | 用户手机号 |
| salt | VARCHAR(255) | NOT NULL | 用于密码加密的随机盐值 |
| create_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 账户创建时间 |

**建表SQL：**
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    salt VARCHAR(255) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

🔑 测试可能用到的相关账号密码
系统内置的测试账号（密码已加密，可直接用于登录测试）：

用户名	密码	邮箱	手机号	备注
test123	test123	test123@example.com	13800138000	基础测试用户
zhangsan	zhangsan123	zhangsan@example.com	13900139000	普通用户
张三	zhangsan123	zhangsan123@example.com	13800138000	中文用户名测试


测试物品数据示例
登录 test123 账号后，你可能看到以下示例物品：

标题：捡到的iPhone 13
分类：捡到的物品
地点：北京大学图书馆

标题：闲置笔记本电脑
分类：多余物品
价格：4500.00元
地点：北京市海淀区
