<%--
  Created by IntelliJ IDEA.
  User: yhs
  Date: 10/03/2020
  Time: 1:52 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout Page</title>
</head>
<body>
<h1>Logout Page</h1>

<form action="/customLogout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button type="submit">로그아웃</button>
</form>

</body>
</html>
