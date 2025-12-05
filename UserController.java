package com.second_hand_trading_platform.controller;

import com.second_hand_trading_platform.model.User;
import com.second_hand_trading_platform.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/*")
public class UserController extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (action) {
            case "/register":
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                break;
            case "/login":
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            case "/profile":
                showProfile(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (action) {
            case "/register":
                register(request, response);
                break;
            case "/login":
                login(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        System.out.println("用户注册: " + username);

        if (userService.register(username, password, email, phone)) {
            request.setAttribute("success", "注册成功，请登录");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "注册失败，用户名可能已存在");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("=== 开始登录 ===");
        System.out.println("用户名: " + username);

        try {
            // 1. 验证登录
            boolean loginResult = userService.login(username, password);

            if (loginResult) {
                System.out.println("✅ 登录验证通过");

                // 2. 获取或创建session
                HttpSession session = request.getSession();

                // 3. 设置session属性
                int userId = userService.getUserId(username);
                session.setAttribute("username", username);
                session.setAttribute("userId", userId);

                // 4. 设置session超时时间（30分钟）
                session.setMaxInactiveInterval(30 * 60);

                System.out.println("Session ID: " + session.getId());
                System.out.println("设置session属性: username=" + username + ", userId=" + userId);

                // 5. 简单重定向
                String redirectPath = request.getContextPath() + "/";
                System.out.println("重定向到: " + redirectPath);

                response.sendRedirect(redirectPath);
                return;

            } else {
                System.out.println("❌ 登录验证失败");

                request.setAttribute("error", "用户名或密码错误");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.err.println("登录过程中发生错误: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("error", "登录过程中发生错误: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            System.out.println("用户登出: " + username);
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        User user = userService.getUserByUsername(username);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}