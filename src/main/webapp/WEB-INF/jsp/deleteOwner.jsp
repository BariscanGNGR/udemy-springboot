<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 5.02.2023
  Time: 02:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Delete Owner</title>
</head>
<body>
<form:form modelAttribute="owner" method="post">
    First Name : <form:input path="firstName"/><br/>
    Last Name : <form:input path="lastName"/><br/>
    <form:button name="submit">Delete</form:button>
</form:form>
</body>
</html>
