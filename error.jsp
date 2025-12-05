<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>错误页面</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }
        .error-container { max-width: 600px; margin: 0 auto; }
        .error-code { font-size: 72px; color: #e74c3c; }
        .error-message { font-size: 24px; margin: 20px 0; }
        .back-link { margin-top: 30px; }
        a { color: #3498db; text-decoration: none; }
    </style>
</head>
<body>
<div class="error-container">
    <div class="error-code">
        <%= request.getAttribute("jakarta.servlet.error.status_code") %>
    </div>
    <div class="error-message">
        <%
            Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
            String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");

            if (statusCode != null) {
                if (statusCode == 404) {
                    out.println("页面未找到");
                } else if (statusCode == 500) {
                    out.println("服务器内部错误");
                } else {
                    out.println("发生错误: " + statusCode);
                }
            } else {
                out.println("未知错误");
            }
        %>
    </div>
    <div class="back-link">
        <a href="${pageContext.request.contextPath}/">返回首页</a>
    </div>
</div>
</body>
</html>