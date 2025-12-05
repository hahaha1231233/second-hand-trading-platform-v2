<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人中心 - 二手交易平台</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .container { max-width: 1000px; margin: 30px auto; padding: 20px; }
        .profile-container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { color: #2c3e50; margin-bottom: 30px; border-bottom: 2px solid #eee; padding-bottom: 10px; }
        .profile-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .user-avatar { width: 100px; height: 100px; background: #3498db; border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-size: 2em; }
        .profile-info { flex: 1; margin-left: 30px; }
        .profile-info h3 { margin-bottom: 10px; color: #2c3e50; }
        .info-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-top: 30px; }
        .info-card { background: #f8f9fa; padding: 20px; border-radius: 8px; border-left: 4px solid #3498db; }
        .info-card h4 { color: #2c3e50; margin-bottom: 10px; }
        .info-item { margin: 10px 0; }
        .info-label { font-weight: bold; color: #555; }
        .info-value { color: #333; }
        .stats { display: flex; justify-content: space-around; margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee; }
        .stat-item { text-align: center; }
        .stat-number { font-size: 2em; font-weight: bold; color: #3498db; }
        .stat-label { color: #7f8c8d; }
        .actions { margin-top: 30px; }
        .btn { padding: 10px 20px; background: #3498db; color: white; text-decoration: none; border-radius: 4px; display: inline-block; margin-right: 10px; }
        .btn:hover { background: #2980b9; }
        .btn-logout { background: #e74c3c; }
        .btn-logout:hover { background: #c0392b; }
        .back-link { margin-top: 20px; }
        a { color: #3498db; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="container">
    <div class="profile-container">
        <div class="profile-header">
            <div style="display: flex; align-items: center;">
                <div class="user-avatar">${sessionScope.username.charAt(0)}</div>
                <div class="profile-info">
                    <h3>${sessionScope.username}</h3>
                    <p>二手交易平台用户</p>
                    <p>用户ID: ${user.id}</p>
                </div>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/item/add" class="btn">发布物品</a>
                <a href="${pageContext.request.contextPath}/item/myItems" class="btn">我的物品</a>
                <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-logout">退出登录</a>
            </div>
        </div>

        <h2>个人信息</h2>
        <div class="info-grid">
            <div class="info-card">
                <h4>📧 联系信息</h4>
                <div class="info-item">
                    <span class="info-label">用户名:</span>
                    <span class="info-value">${user.username}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">邮箱:</span>
                    <span class="info-value">${not empty user.email ? user.email : '未设置'}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">手机号:</span>
                    <span class="info-value">${not empty user.phone ? user.phone : '未设置'}</span>
                </div>
            </div>

            <div class="info-card">
                <h4>📅 账户信息</h4>
                <div class="info-item">
                    <span class="info-label">用户ID:</span>
                    <span class="info-value">${user.id}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">注册时间:</span>
                    <span class="info-value"><fmt:formatDate value="${user.createTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
                </div>
                <div class="info-item">
                    <span class="info-label">账户状态:</span>
                    <span class="info-value" style="color: #2ecc71;">正常</span>
                </div>
            </div>

            <div class="info-card">
                <h4>🔐 安全信息</h4>
                <div class="info-item">
                    <span class="info-label">密码强度:</span>
                    <span class="info-value" style="color: #2ecc71;">强</span>
                </div>
                <div class="info-item">
                    <span class="info-label">最后登录:</span>
                    <span class="info-value">刚刚</span>
                </div>
                <div class="info-item">
                    <span class="info-label">安全状态:</span>
                    <span class="info-value" style="color: #2ecc71;">正常</span>
                </div>
            </div>
        </div>

        <div class="stats">
            <div class="stat-item">
                <div class="stat-number">
                    <c:set var="itemCount" value="0" />
                    <c:if test="${not empty sessionScope.userId}">
                        <c:set var="itemCount" value="需要从数据库获取" />
                    </c:if>
                    ${itemCount}
                </div>
                <div class="stat-label">发布的物品</div>
            </div>
            <div class="stat-item">
                <div class="stat-number">0</div>
                <div class="stat-label">交易成功</div>
            </div>
            <div class="stat-item">
                <div class="stat-number">100%</div>
                <div class="stat-label">好评率</div>
            </div>
        </div>

        <div class="back-link">
            <a href="${pageContext.request.contextPath}/">← 返回首页</a>
        </div>
    </div>
</div>
</body>
</html>