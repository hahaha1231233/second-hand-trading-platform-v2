/**
 * 二手交易平台 - 前端脚本
 * 提供表单验证、交互功能等
 */

document.addEventListener('DOMContentLoaded', function() {
    console.log('二手交易平台脚本加载完成');

    // 表单验证
    initFormValidation();

    // 搜索框自动聚焦
    initSearchFocus();

    // 确认对话框
    initConfirmDialogs();

    // 响应式导航菜单
    initResponsiveNav();
});

/**
 * 初始化表单验证
 */
function initFormValidation() {
    const forms = document.querySelectorAll('form');

    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!validateForm(this)) {
                event.preventDefault();
                return false;
            }
        });
    });
}

/**
 * 验证表单
 */
function validateForm(form) {
    let isValid = true;
    const requiredFields = form.querySelectorAll('[required]');

    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            showFieldError(field, '此字段为必填项');
            isValid = false;
        } else {
            clearFieldError(field);

            // 特定字段验证
            if (field.type === 'email' && !validateEmail(field.value)) {
                showFieldError(field, '请输入有效的邮箱地址');
                isValid = false;
            }

            if (field.type === 'number' && field.value < 0) {
                showFieldError(field, '价格不能为负数');
                isValid = false;
            }
        }
    });

    // 密码确认验证（如果有的话）
    const password = form.querySelector('input[name="password"]');
    const confirmPassword = form.querySelector('input[name="confirmPassword"]');

    if (password && confirmPassword && confirmPassword.value) {
        if (password.value !== confirmPassword.value) {
            showFieldError(confirmPassword, '两次输入的密码不一致');
            isValid = false;
        }
    }

    return isValid;
}

/**
 * 显示字段错误
 */
function showFieldError(field, message) {
    clearFieldError(field);

    const errorDiv = document.createElement('div');
    errorDiv.className = 'field-error';
    errorDiv.style.color = '#e74c3c';
    errorDiv.style.fontSize = '0.9em';
    errorDiv.style.marginTop = '5px';
    errorDiv.textContent = message;

    field.parentNode.appendChild(errorDiv);
    field.style.borderColor = '#e74c3c';
}

/**
 * 清除字段错误
 */
function clearFieldError(field) {
    const existingError = field.parentNode.querySelector('.field-error');
    if (existingError) {
        existingError.remove();
    }
    field.style.borderColor = '#ddd';
}

/**
 * 验证邮箱格式
 */
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

/**
 * 初始化搜索框自动聚焦
 */
function initSearchFocus() {
    const searchInput = document.querySelector('input[name="keyword"]');
    if (searchInput) {
        searchInput.focus();
        searchInput.select();
    }
}

/**
 * 初始化确认对话框
 */
function initConfirmDialogs() {
    const deleteLinks = document.querySelectorAll('a[onclick*="confirm"]');

    deleteLinks.forEach(link => {
        const originalOnclick = link.getAttribute('onclick');

        link.addEventListener('click', function(e) {
            if (!originalOnclick || !originalOnclick.includes('confirm')) {
                e.preventDefault();
                if (confirm('确定要执行此操作吗？')) {
                    window.location.href = this.href;
                }
            }
        });
    });
}

/**
 * 初始化响应式导航菜单
 */
function initResponsiveNav() {
    const nav = document.querySelector('nav');
    if (!nav) return;

    // 在小屏幕下创建汉堡菜单
    if (window.innerWidth <= 768) {
        const hamburger = document.createElement('button');
        hamburger.innerHTML = '☰';
        hamburger.style.cssText = `
            background: none;
            border: none;
            color: white;
            font-size: 1.5em;
            cursor: pointer;
            padding: 10px;
        `;

        nav.insertBefore(hamburger, nav.firstChild);

        // 隐藏所有链接
        const links = nav.querySelectorAll('a, span');
        links.forEach(link => {
            if (link !== hamburger) {
                link.style.display = 'none';
            }
        });

        // 切换菜单显示
        let isOpen = false;
        hamburger.addEventListener('click', function() {
            isOpen = !isOpen;
            links.forEach(link => {
                if (link !== hamburger) {
                    link.style.display = isOpen ? 'block' : 'none';
                    link.style.margin = '10px 0';
                }
            });
        });
    }
}

/**
 * 显示成功消息
 */
function showSuccess(message) {
    showMessage(message, 'success');
}

/**
 * 显示错误消息
 */
function showError(message) {
    showMessage(message, 'error');
}

/**
 * 显示消息
 */
function showMessage(message, type) {
    const messageDiv = document.createElement('div');
    messageDiv.className = `alert alert-${type}`;
    messageDiv.textContent = message;

    const container = document.querySelector('.container') || document.body;
    container.insertBefore(messageDiv, container.firstChild);

    // 3秒后自动消失
    setTimeout(() => {
        messageDiv.remove();
    }, 3000);
}

/**
 * 价格格式化
 */
function formatPrice(price) {
    return '¥' + parseFloat(price).toFixed(2);
}

/**
 * 日期格式化
 */
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}

/**
 * 防抖函数
 */
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

/**
 * 实时搜索建议
 */
function initSearchSuggestions() {
    const searchInput = document.querySelector('input[name="keyword"]');
    if (!searchInput) return;

    const debouncedSearch = debounce(function() {
        const keyword = searchInput.value.trim();
        if (keyword.length >= 2) {
            fetchSearchSuggestions(keyword);
        }
    }, 300);

    searchInput.addEventListener('input', debouncedSearch);
}

/**
 * 获取搜索建议
 */
function fetchSearchSuggestions(keyword) {
    // 这里可以添加AJAX请求获取搜索建议
    console.log('搜索建议关键词:', keyword);
}