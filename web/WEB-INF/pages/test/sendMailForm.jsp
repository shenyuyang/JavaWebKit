<%-- Created by shenyuyang --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>


<form action="<%=request.getContextPath()%>/test/sendMail" method="post">
    <div>
        Smtp Host：<input type="text" name="smtpHost">
    </div>


    <div>
        Sender Email：<input type="text" name="senderMail">
    </div>


    <div>
        Sender Password：<input type="text" name="senderPassword">
    </div>


    <div>
        Receiver Email：<input type="text" name="receiverMail">
    </div>


    <div>
        Title：<input type="text" name="mailTitle">
    </div>

    <div>
        <textarea name="mailBody" style="width: 500px; height: 300px;"></textarea>
    </div>

    <div>
        <button type="submit">发送邮件</button>
    </div>

</form>

</body>
</html>
