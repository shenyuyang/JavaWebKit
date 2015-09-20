<%-- Created by shenyuyang --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String rootPath = request.getContextPath();
%>

<html>
<head>
    <title>首页</title>
    <style>
        .home-item li {margin-bottom: 15px;}
        .home-item li a { text-decoration: none;}
    </style>
</head>
<body>

<h1>hello 无锡</h1>

<ul class="home-item">
    <li><a href="<%=rootPath %>/test/index">hibernate读取</a></li>
    <li><a href="<%=rootPath %>/test/userInfo">用户登录</a></li>
    <li><a href="<%=rootPath %>/test/fileForm">文件上传</a></li>
    <li><a href="<%=rootPath %>/test/sendMailForm">邮件发送</a></li>
    <li><a href="<%=rootPath %>/test/needLogin">需要登录页面</a></li>
    <li><a href="<%=rootPath %>/test/csrfForm">测试CSRF</a></li>
    <li><a href="<%=rootPath %>/test/xssForm">测试XSS</a></li>
    <li><a href="<%=rootPath %>/captcha/form">验证码</a></li>
</ul>

</body>
</html>
