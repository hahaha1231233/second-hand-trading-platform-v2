package com.second_hand_trading_platform.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static String url;
    private static String username;
    private static String password;

    static {
        System.out.println("\n🔍 [DatabaseConfig] 开始加载数据库配置...");

        try {
            // 1. 尝试加载配置文件
            System.out.println("1. 查找database.properties文件...");
            InputStream input = DatabaseConfig.class.getClassLoader()
                    .getResourceAsStream("database.properties");

            if (input == null) {
                System.out.println("   ❌ 未找到database.properties文件");
                throw new RuntimeException("database.properties文件未找到");
            }

            System.out.println("   ✅ 找到配置文件");

            // 2. 读取配置
            Properties prop = new Properties();
            prop.load(input);
            input.close();

            url = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");

            // 3. 验证配置
            System.out.println("2. 验证配置...");
            if (url == null || url.isEmpty()) {
                System.out.println("   ❌ db.url 为空");
            } else {
                System.out.println("   ✅ db.url: " + url);
            }

            if (username == null || username.isEmpty()) {
                System.out.println("   ❌ db.username 为空");
            } else {
                System.out.println("   ✅ db.username: " + username);
            }

            if (password == null) {
                System.out.println("   ℹ️ db.password: [null]");
            } else if (password.isEmpty()) {
                System.out.println("   ℹ️ db.password: [空字符串]");
            } else {
                System.out.println("   ✅ db.password: [已设置，长度: " + password.length() + "]");
            }

            // 4. 加载驱动
            System.out.println("3. 加载MySQL驱动...");
            String driver = prop.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
            Class.forName(driver);
            System.out.println("   ✅ 驱动加载成功: " + driver);

            System.out.println("🎉 [DatabaseConfig] 数据库配置加载完成\n");

        } catch (Exception e) {
            System.err.println("\n❌ [DatabaseConfig] 配置加载失败:");
            System.err.println("   错误: " + e.getMessage());
            System.err.println("   异常类型: " + e.getClass().getName());
            e.printStackTrace();
            throw new RuntimeException("数据库配置加载失败", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("\n🔗 [DatabaseConfig] 正在建立数据库连接...");
        System.out.println("   URL: " + url);
        System.out.println("   User: " + username);
        System.out.println("   Password: " + (password == null ? "[null]" : password.isEmpty() ? "[空]" : "***"));

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ [DatabaseConfig] 数据库连接成功!");
            System.out.println("   数据库: " + conn.getCatalog());
            System.out.println("   隔离级别: " + conn.getTransactionIsolation());
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ [DatabaseConfig] 连接失败:");
            System.err.println("   SQL状态: " + e.getSQLState());
            System.err.println("   错误码: " + e.getErrorCode());
            System.err.println("   错误信息: " + e.getMessage());
            throw e;
        }
    }
}