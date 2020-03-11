<%--
  Created by IntelliJ IDEA.
  User: yhs
  Date: 10/03/2020
  Time: 2:50 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<sec:authorize access="isAnonymous()">
    <a href="/customLogin">로그인</a>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <a href="/customLogout">로그아웃</a>
</sec:authorize>

</body>
</html>
