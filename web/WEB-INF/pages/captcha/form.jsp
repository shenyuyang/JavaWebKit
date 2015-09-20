<%-- Created by shenyuyang --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>验证码测试</title>
</head>
<body>


<h2>验证码测试</h2>

<form method="post" action="<%= request.getContextPath() %>/captcha/validate">

    <table>
        <tr>
            <td>Email</td>
            <td><input name="email" type="text"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input name="password" type="text"/></td>
        </tr>
        <tr>
            <td style="vertical-align: top;">验证码</td>
            <td>
                <div><input type="text" name="captcha"></div>
                <div><img src="<%= request.getContextPath() %>/captcha/img.jpg"></div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button type="submit">提交</button>
            </td>
        </tr>
    </table>

</form>


</body>
</html>
