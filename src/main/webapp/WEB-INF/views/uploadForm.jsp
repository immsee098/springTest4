<%--
  Created by IntelliJ IDEA.
  User: yhs
  Date: 04/03/2020
  Time: 3:31 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="uploadFormAction" method="post" enctype="multipart/form-data">
        <input type="file" name="uploadFile" multiple>
        <button type="submit">Submit</button>
    </form>
    <form enctype="multipart/form-data">
        <input type="file" name="files" multiple>
    </form>
</body>
</html>
