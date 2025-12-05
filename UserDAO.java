package com.second_hand_trading_platform.dao;

import com.second_hand_trading_platform.config.DatabaseConfig;
import com.second_hand_trading_platform.model.User;
import com.second_hand_trading_platform.service.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean addUser(User user, String salt) {
        String sql = "INSERT INTO users (username, password, email, phone, salt, create_time) VALUES (?, ?, ?, ?, ?, ?)";

        System.out.println("🔧 [UserDAO.addUser] 开始添加用户");
        System.out.println("   用户名: " + user.getUsername());
        System.out.println("   邮箱: " + user.getEmail());

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, salt);
            pstmt.setTimestamp(6, Timestamp.valueOf(user.getCreateTime()));

            int rows = pstmt.executeUpdate();
            System.out.println("   ✅ 插入成功，影响行数: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ [UserDAO.addUser] 插入失败: " + e.getMessage());
            System.err.println("   SQL状态: " + e.getSQLState());
            System.err.println("   错误码: " + e.getErrorCode());
            return false;
        }
    }

    public User findUserByUsername(String username) {
        System.out.println("🔍 [UserDAO.findUserByUsername] 查找用户: " + username);

        if (username == null || username.trim().isEmpty()) {
            System.out.println("   警告: 用户名为空");
            return null;
        }

        username = username.trim();
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());

                System.out.println("   ✅ 找到用户: " + user.getUsername() + " (ID: " + user.getId() + ")");
                return user;
            } else {
                System.out.println("   ℹ️ 用户不存在: " + username);
                return null;
            }

        } catch (SQLException e) {
            System.err.println("❌ [UserDAO.findUserByUsername] 查询失败: " + e.getMessage());
            return null;
        }
    }

    public String getSaltByUsername(String username) {
        System.out.println("🧂 [UserDAO.getSaltByUsername] 获取用户盐值: " + username);

        String sql = "SELECT salt FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String salt = rs.getString("salt");
                System.out.println("   ✅ 找到盐值: " + (salt != null ? salt.substring(0, Math.min(salt.length(), 10)) + "..." : "null"));
                return salt;
            }

            System.out.println("   ❌ 未找到用户盐值");
            return null;

        } catch (SQLException e) {
            System.err.println("❌ [UserDAO.getSaltByUsername] 获取盐值失败: " + e.getMessage());
            return null;
        }
    }

    public boolean validateUser(String username, String password) {
        System.out.println("🔐 [UserDAO.validateUser] 验证用户登录");
        System.out.println("   用户名: " + username);
        System.out.println("   密码: " + (password != null ? "***" + password.length() + "位***" : "null"));

        String storedHash = getPasswordHash(username);
        String salt = getSaltByUsername(username);

        if (storedHash == null || salt == null) {
            System.out.println("   ❌ 验证失败: 用户不存在或密码/盐值为空");
            System.out.println("     存储的哈希: " + storedHash);
            System.out.println("     盐值: " + salt);
            return false;
        }

        System.out.println("   开始密码验证...");
        boolean isValid = PasswordUtil.verifyPassword(password, storedHash, salt);
        System.out.println("   验证结果: " + (isValid ? "✅ 成功" : "❌ 失败"));
        return isValid;
    }

    private String getPasswordHash(String username) {
        System.out.println("   [内部方法] 获取用户密码哈希: " + username);

        String sql = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("password");
                System.out.println("      找到哈希: " + (hash != null ? hash.substring(0, Math.min(hash.length(), 10)) + "..." : "null"));
                return hash;
            }

            System.out.println("      未找到密码哈希");
            return null;

        } catch (SQLException e) {
            System.err.println("      获取密码哈希失败: " + e.getMessage());
            return null;
        }
    }

    public List<User> getAllUsers() {
        System.out.println("📋 [UserDAO.getAllUsers] 获取所有用户");
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY create_time DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int count = 0;
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                users.add(user);
                count++;
            }

            System.out.println("   找到 " + count + " 个用户");

        } catch (SQLException e) {
            System.err.println("❌ [UserDAO.getAllUsers] 获取用户列表失败: " + e.getMessage());
        }
        return users;
    }
}