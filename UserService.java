package com.second_hand_trading_platform.service;

import com.second_hand_trading_platform.dao.UserDAO;
import com.second_hand_trading_platform.model.User;

public class UserService {
    private UserDAO userDao = new UserDAO();

    public boolean register(String username, String password, String email, String phone) {
        System.out.println("════════════════════════════════════════════");
        System.out.println("🔄 [UserService] 开始用户注册流程");
        System.out.println("════════════════════════════════════════════");
        System.out.println("📋 注册信息:");
        System.out.println("   用户名: " + username);
        System.out.println("   密码: " + (password != null ? "***" + password.length() + "位***" : "null"));
        System.out.println("   邮箱: " + email);
        System.out.println("   手机: " + phone);

        // 1. 检查用户名是否为空
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ [错误] 用户名为空");
            return false;
        }

        username = username.trim();

        // 2. 检查用户是否已存在
        System.out.println("🔍 检查用户名是否已存在: " + username);
        User existingUser = userDao.findUserByUsername(username);

        if (existingUser != null) {
            System.out.println("❌ [错误] 用户名已存在!");
            System.out.println("   现有用户ID: " + existingUser.getId());
            System.out.println("   注册时间: " + existingUser.getCreateTime());
            return false;
        }

        System.out.println("✅ 用户名可用");

        // 3. 生成密码盐和哈希
        System.out.println("🔐 生成密码盐和哈希...");
        try {
            String salt = PasswordUtil.generateSalt();
            String hashedPassword = PasswordUtil.hashPassword(password, salt);

            System.out.println("   生成的盐值: " + salt.substring(0, Math.min(salt.length(), 10)) + "...");
            System.out.println("   哈希密码: " + hashedPassword.substring(0, Math.min(hashedPassword.length(), 10)) + "...");

            // 4. 创建用户对象
            System.out.println("👤 创建用户对象...");
            User user = new User(username, hashedPassword, email, phone);
            System.out.println("   用户对象创建时间: " + user.getCreateTime());

            // 5. 保存到数据库
            System.out.println("💾 保存用户到数据库...");
            boolean result = userDao.addUser(user, salt);

            if (result) {
                System.out.println("🎉 [成功] 用户注册成功!");
                System.out.println("   用户名: " + username);
                System.out.println("   邮箱: " + email);
            } else {
                System.out.println("❌ [失败] 数据库插入失败");
            }

            System.out.println("════════════════════════════════════════════");
            System.out.println("[UserService] 注册流程结束");
            System.out.println("════════════════════════════════════════════\n");

            return result;

        } catch (Exception e) {
            System.err.println("💥 [异常] 注册过程中发生错误:");
            System.err.println("   异常类型: " + e.getClass().getName());
            System.err.println("   错误信息: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String username, String password) {
        System.out.println("════════════════════════════════════════════");
        System.out.println("🔑 [UserService] 开始用户登录流程");
        System.out.println("════════════════════════════════════════════");
        System.out.println("   用户名: " + username);

        boolean result = userDao.validateUser(username, password);

        System.out.println("   登录结果: " + (result ? "✅ 成功" : "❌ 失败"));
        System.out.println("════════════════════════════════════════════\n");

        return result;
    }

    public User getUserByUsername(String username) {
        System.out.println("🔍 [UserService] 获取用户信息: " + username);
        return userDao.findUserByUsername(username);
    }

    public int getUserId(String username) {
        System.out.println("#️⃣ [UserService] 获取用户ID: " + username);
        User user = userDao.findUserByUsername(username);
        int userId = user != null ? user.getId() : -1;
        System.out.println("   用户ID: " + userId);
        return userId;
    }
}