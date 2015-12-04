<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.owner.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<title>註冊(Owner)</title>
<%  OwnerVO ownerVO1 = (OwnerVO) request.getAttribute("ownerVO1");%>
</head>
<body>
<h1>業主註冊</h1>
<form METHOD="post"  action="<%=request.getContextPath()%>/register/register.do">
	業主帳號:<input type="text" name="OwnAcc" value="${ownerVO1.getOwnAcc()}">
		   <font color='red'>${errorMsgs.OwnAcc}</font><br>
	業主密碼:<input type="password" name="OwnPwd" value="${ownerVO1.getOwnPwd()}">
		   <font color='red'>${errorMsgs.OwnPwd}</font><br>
	業主信箱:<input type="text" name="OwnEmail" value="${ownerVO1.getOwnEmail()}">
		   <font color='red'>${errorMsgs.OwnEmail}</font><br>
	姓:<input type="text" name="OwnFirstName" value="${ownerVO1.getOwnFirstName()}">
	   <font color='red'>${errorMsgs.OwnFirstName}</font><br>
	名:<input type="text" name="OwnLastName" value="${ownerVO1.getOwnLastName()}">
	   <font color='red'>${errorMsgs.OwnLastName}</font><br>
	性別:<input type="radio" name="OwnGender"  CHECKED value="true">男
		<input type="radio" name="OwnGender"  value="false">女<br>
	<input type="submit" value="送出">
	<input type="hidden" name="action" value="owner_addfirst">
	<input type="hidden" name="OwnOpen" value="false">
</form>
</body>
</html>