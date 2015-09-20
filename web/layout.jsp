<%-- Created by shenyuyang --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title><sitemesh:write property='title'/></title>
  <sitemesh:write property='head'/>
</head>
<body>

<div style="margin: 20px;">
  <h1 style="float: left;margin: 0px; padding: 0px;">布局页信息</h1>
  <div style="margin-left: 30px; margin-top: 10px; float: left;"><a href="<%=request.getContextPath()%>/">首页</a> </div>
  <div style="clear: both;"></div>
</div>


<div style="background: #f2f2f2; margin: 20px;padding: 20px;"><sitemesh:write property='body'/></div>

</body>
</html>