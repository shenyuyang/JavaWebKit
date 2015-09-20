<%@ page import="wojilu.web.Html" %>
<%-- Created by shenyuyang --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>



<form action="<%=request.getContextPath()%>/test/csrfCheck" method="post">
  <div>
    Title：<input type="text" name="txtTitle">
  </div>



  <div>
    <textarea name="txtBody" style="width: 500px; height: 300px;"></textarea>
  </div>

  <div><%= Html.csrfToken(request) %></div>


  <div>
    <button type="submit">测试 CSRF 安全</button>
  </div>

</form>

</body>
</html>
