<%@ page import="wojilu.domain.User" %>
<%@ page import="wojilu.web.Html" %>
<%-- Created by shenyuyang --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%


    Object obj = pageContext.getAttribute("user",PageContext.REQUEST_SCOPE);
    User user = obj == null ? null : (User) obj;

%>


<html>
<head>
    <title></title>
</head>
<body>

<div><a href="/test/userLogout">注销</a> </div>


<% if (user != null) { %>
<div>

    欢迎您 <%= user.getName() %>

</div>


<% } else { %>

<form method="post" action="/test/userCheckLogin">
<table>
    <tr>
        <td>用户名</td>
        <td>
            <input type="text" name="name">
        </td>
    </tr>

    <tr>
        <td>密码</td>
        <td>
            <input type="password" name="pwd">
        </td>
    </tr>

    <tr>
        <td></td>
        <td>
            <button type="submit">登录</button>

            <%= wojilu.web.Html.csrfToken(request) %>
        </td>
    </tr>

</table>
</form>
<% } %>

</body>
</html>
