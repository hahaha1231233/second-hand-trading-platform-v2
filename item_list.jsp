<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的物品 - 二手交易平台</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
        header { background: #2c3e50; color: white; padding: 20px 0; margin-bottom: 30px; }
        nav { margin-top: 20px; }
        nav a { color: white; text-decoration: none; margin-right: 20px; padding: 8px 16px; border-radius: 4px; }
        nav a:hover { background: #34495e; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .btn-add { background: #2ecc71; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; }
        .btn-add:hover { background: #27ae60; }
        .items-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; }
        .item-card { background: white; border: 1px solid #ddd; border-radius: 8px; padding: 20px; }
        .item-card h3 { color: #2c3e50; margin-bottom: 10px; }
        .item-card p { margin: 8px 0; color: #555; }
        .item-card .price { color: #e74c3c; font-weight: bold; font-size: 1.2em; }
        .item-card .category { display: inline-block; background: #3498db; color: white; padding: 3px 8px; border-radius: 3px; font-size: 0.9em; }
        .item-card .status { display: inline-block; background: #f39c12; color: white; padding: 3px 8px; border-radius: 3px; font-size: 0.9em; }
        .item-card .location { color: #7f8c8d; }
        .item-card .date { color: #95a5a6; font-size: 0.9em; }
        .item-actions { margin-top: 15px; padding-top: 15px; border-top: 1px solid #eee; }
        .item-actions a { color: #3498db; text-decoration: none; margin-right: 15px; }
        .item-actions a:hover { text-decoration: underline; }
        .item-actions a.delete { color: #e74c3c; }
        .no-items { text-align: center; padding: 50px; color: #7f8c8d; font-size: 1.2em; }
        footer { margin-top: 50px; padding: 20px; text-align: center; color: #7f8c8d; border-top: 1px solid #eee; }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>📦 二手交易平台 - 我的物品</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/">首页</a>
            <a href="${pageContext.request.contextPath}/search">搜索物品</a>
            <span style="color: #95a5a6;">欢迎, ${sessionScope.username}</span>
            <a href="${pageContext.request.contextPath}/item/myItems" style="background: #34495e;">我的物品</a>
            <a href="${pageContext.request.contextPath}/item/add">发布物品</a>
            <a href="${pageContext.request.contextPath}/user/profile">个人中心</a>
            <a href="${pageContext.request.contextPath}/user/logout">退出</a>
        </nav>
    </header>

    <main>
        <div class="page-header">
            <h2>我的物品列表</h2>
            <a href="${pageContext.request.contextPath}/item/add" class="btn-add">➕ 发布新物品</a>
        </div>

        <c:choose>
            <c:when test="${not empty items}">
                <div class="items-grid">
                    <c:forEach var="item" items="${items}">
                        <div class="item-card">
                            <h3>${item.title}</h3>
                            <p class="category">${item.category}</p>
                            <p class="status">${item.status}</p>
                            <p>${item.description}</p>
                            <p class="price">价格: ¥<fmt:formatNumber value="${item.price}" pattern="0.00"/></p>
                            <p class="location">📍 ${item.location}</p>
                            <p class="date">发布时间: <fmt:formatDate value="${item.postDate}" pattern="yyyy-MM-dd HH:mm"/></p>
                            <div class="item-actions">
                                <a href="${pageContext.request.contextPath}/item/edit?id=${item.id}">编辑</a>
                                <a href="${pageContext.request.contextPath}/item/delete?id=${item.id}"
                                   onclick="return confirm('确定要删除这个物品吗？')" class="delete">删除</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-items">
                    <p>您还没有发布任何物品</p>
                    <p><a href="${pageContext.request.contextPath}/item/add" class="btn-add">点击这里发布第一个物品</a></p>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

    <footer>
        <p>© 2025 二手交易平台 | 诚信交易 • 安全可靠</p>
        <p>共 ${items.size()} 个物品</p>
    </footer>
</div>
</body>
</html>