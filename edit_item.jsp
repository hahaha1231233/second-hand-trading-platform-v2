<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑物品 - 二手交易平台</title>
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
        .btn-delete { background: #e74c3c; }
        .btn-delete:hover { background: #c0392b; }
        .message { padding: 10px; margin-bottom: 20px; border-radius: 4px; }
        .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .button-group { display: flex; justify-content: space-between; margin-top: 30px; }
        .back-link { margin-top: 15px; }
        a { color: #3498db; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .required::after { content: " *"; color: #e74c3c; }
        .item-info { background: #f8f9fa; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
        .item-info p { margin: 5px 0; color: #555; }
    </style>
</head>
<body>
<div class="container">
    <div class="form-container">
        <h2>编辑物品</h2>

        <div class="item-info">
            <p><strong>物品ID:</strong> ${item.id}</p>
            <p><strong>发布时间:</strong> ${item.postDate}</p>
            <p><strong>状态:</strong> ${item.status}</p>
        </div>

        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/item/edit" method="post">
            <input type="hidden" name="id" value="${item.id}">

            <div class="form-group">
                <label for="title" class="required">物品标题</label>
                <input type="text" id="title" name="title" value="${item.title}" required>
            </div>

            <div class="form-group">
                <label for="description" class="required">详细描述</label>
                <textarea id="description" name="description" required>${item.description}</textarea>
            </div>

            <div class="form-group">
                <label for="category" class="required">物品分类</label>
                <select id="category" name="category" required>
                    <option value="捡到的物品" <c:if test="${item.category == '捡到的物品'}">selected</c:if>>捡到的物品</option>
                    <option value="多余物品" <c:if test="${item.category == '多余物品'}">selected</c:if>>多余物品</option>
                </select>
            </div>

            <div class="form-group">
                <label for="price">价格（元）</label>
                <input type="number" id="price" name="price" step="0.01" min="0" value="${item.price}">
            </div>

            <div class="form-group">
                <label for="location" class="required">地点</label>
                <input type="text" id="location" name="location" value="${item.location}" required>
            </div>

            <div class="button-group">
                <div>
                    <button type="submit">保存修改</button>
                    <a href="${pageContext.request.contextPath}/item/myItems" style="padding: 12px 30px; display: inline-block;" class="btn-cancel">取消</a>
                </div>
                <a href="${pageContext.request.contextPath}/item/delete?id=${item.id}"
                   onclick="return confirm('确定要删除这个物品吗？此操作不可撤销！')"
                   class="btn-delete" style="padding: 12px 30px; display: inline-block; color: white;">删除</a>
            </div>
        </form>

        <div class="back-link">
            <a href="${pageContext.request.contextPath}/item/myItems">← 返回我的物品</a>
        </div>
    </div>
</div>
</body>
</html>