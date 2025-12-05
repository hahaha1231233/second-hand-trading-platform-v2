<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册 - 二手交易平台</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .container { max-width: 400px; margin: 50px auto; padding: 20px; }
        .form-container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 30px; color: #2c3e50; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #555; }
        input[type="text"], input[type="password"], input[type="email"] {
            width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 1em;
        }
        button { width: 100%; padding: 12px; background: #3498db; color: white; border: none;
            border-radius: 4px; font-size: 1.1em; cursor: pointer; }
        button:hover { background: #2980b9; }
        .message { padding: 10px; margin-bottom: 20px; border-radius: 4px; }
        .success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .login-link { text-align: center; margin-top: 20px; }
        a { color: #3498db; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .back-link { margin-top: 15px; }
    </style>
</head>
<body>
<div class="container">
    <div class="form-container">
        <h2>用户注册</h2>

        <c:if test="${not empty success}">
            <div class="message success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/user/register" method="post">
            <div class="form-group">
                <label for="username">用户名 *</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="password">密码 *</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="email">邮箱</label>
                <input type="email" id="email" name="email">
            </div>

            <div class="form-group">
                <label for="phone">手机号</label>
                <input type="text" id="phone" name="phone">
            </div>

            <button type="submit">注册</button>
        </form>

        <div class="login-link">
            已有账号？<a href="${pageContext.request.contextPath}/user/login">立即登录</a>
        </div>

        <div class="back-link">
            <a href="${pageContext.request.contextPath}/">← 返回首页</a>
        </div>
    </div>
</div>
</body>
</html>