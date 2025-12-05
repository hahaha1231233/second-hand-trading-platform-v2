<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>发布物品 - 二手交易平台</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .container { max-width: 600px; margin: 30px auto; padding: 20px; }
        .form-container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 30px; color: #2c3e50; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
        input[type="text"], input[type="number"], textarea, select {
            width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 1em;
        }
        textarea { height: 100px; resize: vertical; }
        button { padding: 12px 30px; background: #3498db; color: white; border: none;
            border-radius: 4px; font-size: 1.1em; cursor: pointer; margin-right: 10px; }
        button:hover { background: #2980b9; }
        .btn-cancel { background: #95a5a6; }
        .btn-cancel:hover { background: #7f8c8d; }
        .message { padding: 10px; margin-bottom: 20px; border-radius: 4px; }
        .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .button-group { display: flex; justify-content: space-between; margin-top: 30px; }
        .back-link { margin-top: 15px; }
        a { color: #3498db; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .required::after { content: " *"; color: #e74c3c; }
    </style>
</head>
<body>
<div class="container">
    <div class="form-container">
        <h2>发布物品</h2>

        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/item/add" method="post">
            <div class="form-group">
                <label for="title" class="required">物品标题</label>
                <input type="text" id="title" name="title" required placeholder="例如：捡到的iPhone 13">
            </div>

            <div class="form-group">
                <label for="description" class="required">详细描述</label>
                <textarea id="description" name="description" required placeholder="请详细描述物品特征、发现时间地点等信息"></textarea>
            </div>

            <div class="form-group">
                <label for="category" class="required">物品分类</label>
                <select id="category" name="category" required>
                    <option value="">请选择分类</option>
                    <option value="捡到的物品">捡到的物品</option>
                    <option value="多余物品">多余物品</option>
                </select>
            </div>

            <div class="form-group">
                <label for="price">价格（元）</label>
                <input type="number" id="price" name="price" step="0.01" min="0" placeholder="0.00">
            </div>

            <div class="form-group">
                <label for="location" class="required">地点</label>
                <input type="text" id="location" name="location" required placeholder="例如：北京市海淀区中关村">
            </div>

            <div class="button-group">
                <button type="submit">发布物品</button>
                <a href="${pageContext.request.contextPath}/item/myItems" class="btn-cancel" style="padding: 12px 30px; display: inline-block;">取消</a>
            </div>
        </form>

        <div class="back-link">
            <a href="${pageContext.request.contextPath}/">← 返回首页</a>
        </div>
    </div>
</div>
</body>
</html>