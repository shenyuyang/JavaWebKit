<%@ page import="wojilu.web.Html" %>
<%-- Created by shenyuyang --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>



<form action="<%=request.getContextPath()%>/test/xssCheck" method="post">
  <div>
    禁止 html：<input type="text" name="txtTitle">
  </div>



  <div>
    禁止 html
    <textarea name="txtBody" style="width: 500px; height: 300px;"></textarea>
  </div>


  <div>
    允许 Html
    <textarea name="htmlContent" style="width: 500px; height: 300px;"></textarea>
  </div>

  <div><%= Html.csrfToken(request) %></div>


  <div>
    <button type="submit">测试 xss 安全</button>
  </div>

</form>

</body>
</html>
