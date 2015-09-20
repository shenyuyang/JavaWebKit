<%-- Created by shenyuyang --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传测试</title>
</head>
<body>


<form action="<%=request.getContextPath()%>/test/saveUpload" method="post" enctype="multipart/form-data">
  选择文件:<input type="file" name="myFile">
  <button type="submit">上传文件</button>
</form>

</body>
</html>
