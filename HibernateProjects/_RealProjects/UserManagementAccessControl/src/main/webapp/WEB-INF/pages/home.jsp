<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UMAC Home</title>
</head>
<body>
	<h1>Hi <c:out value = "${username}"/></h1>
	<a href="${pageContext.request.contextPath}/index">Public Area</a>
	<a href="${pageContext.request.contextPath}/logout">Logout</a>
	<a href="${pageContext.request.contextPath}/user-management">User Management</a>
</body>
</html>