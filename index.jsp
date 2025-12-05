<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>二手交易平台 - 首页</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
    .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
    header { background: #2c3e50; color: white; padding: 20px 0; margin-bottom: 30px; }
    nav { margin-top: 20px; }
    nav a { color: white; text-decoration: none; margin-right: 20px; padding: 8px 16px; border-radius: 4px; }
    nav a:hover { background: #34495e; }
    .hero { text-align: center; padding: 60px 20px; background: #f8f9fa; border-radius: 8px; margin-bottom: 40px; }
    .hero h1 { font-size: 3em; margin-bottom: 20px; color: #2c3e50; }
    .hero p { font-size: 1.2em; color: #666; margin-bottom: 30px; }
    .btn { display: inline-block; background: #3498db; color: white; padding: 12px 30px;
      text-decoration: none; border-radius: 4px; font-size: 1.1em; }
    .btn:hover { background: #2980b9; }
    .search-box { margin: 40px 0; text-align: center; }
    .search-box input, .search-box select, .search-box button {
      padding: 12px; margin: 5px; border: 1px solid #ddd; border-radius: 4px; font-size: 1em;
    }
    .search-box input { width: 300px; }
    .search-box button { background: #2ecc71; color: white; border: none; cursor: pointer; }
    .search-box button:hover { background: #27ae60; }
    footer { margin-top: 50px; padding: 20px; text-align: center; color: #7f8c8d; border-top: 1px solid #eee; }
  </style>
</head>
<body>
<div class="container">
  <header>
    <h1>🔍 二手交易平台</h1>
    <p>捡到物品发布 • 丢失物品查找 • 闲置物品交易</p>
    <nav>
      <a href="${pageContext.request.contextPath}/">首页</a>
      <a href="${pageContext.request.contextPath}/search">搜索物品</a>
      <c:choose>
        <c:when test="${not empty sessionScope.username}">
          <span style="color: #95a5a6;">欢迎, ${sessionScope.username}</span>
          <a href="${pageContext.request.contextPath}/item/myItems">我的物品</a>
          <a href="${pageContext.request.contextPath}/item/add">发布物品</a>
          <a href="${pageContext.request.contextPath}/user/profile">个人中心</a>
          <a href="${pageContext.request.contextPath}/user/logout">退出</a>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/user/login">登录</a>
          <a href="${pageContext.request.contextPath}/user/register">注册</a>
        </c:otherwise>
      </c:choose>
    </nav>
  </header>

  <div class="hero">
    <h1>欢迎来到二手交易平台</h1>
    <p>在这里，你可以发布捡到的物品、寻找丢失的物品、交易闲置物品</p>
    <div class="search-box">
      <form action="${pageContext.request.contextPath}/search" method="get">
        <input type="text" name="keyword" placeholder="输入物品名称、描述或地点..." required>
        <select name="category">
          <option value="">所有分类</option>
          <option value="捡到的物品">捡到的物品</option>
          <option value="多余物品">多余物品</option>
        </select>
        <button type="submit">🔍 搜索</button>
      </form>
    </div>
    <c:if test="${empty sessionScope.username}">
      <a href="${pageContext.request.contextPath}/user/register" class="btn">立即注册</a>
      <a href="${pageContext.request.contextPath}/user/login" class="btn" style="background: #95a5a6;">立即登录</a>
    </c:if>
  </div>

  <main>
    <div style="display: flex; justify-content: space-around; margin: 40px 0;">
      <div style="text-align: center; padding: 20px; flex: 1;">
        <h2>🏷️ 发布物品</h2>
        <p>捡到丢失物品？有多余闲置物品？立即发布！</p>
      </div>
      <div style="text-align: center; padding: 20px; flex: 1;">
        <h2>🔎 查找物品</h2>
        <p>丢失物品？需要购买物品？模糊匹配快速查找！</p>
      </div>
      <div style="text-align: center; padding: 20px; flex: 1;">
        <h2>👤 安全交易</h2>
        <p>账号加密存储，安全可靠，诚信交易</p>
      </div>
    </div>
  </main>

  <footer>
    <p>© 2025 二手交易平台 | 诚信交易 • 安全可靠</p>
    <p>系统时间: <%= new java.util.Date() %></p>
  </footer>
</div>
</body>
</html>