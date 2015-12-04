<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>忘記密碼</title>
</head>
<body>
會員
<form method="post" action="<%=request.getContextPath()%>/inquiry.do">
	您的帳號:<input type="text" name="Account" value="${param.Account}" />
		   <font color='red'>${errorMsgs.Account}</font><br>
	您的Email:<input type="text" name="Email"  value="${param.Email}" />
		   <font color='red'>${errorMsgs.Email}</font><br>
	<input type="submit" value="送出" />
	<input type="hidden" name="who" value="Member" />
</form>
</body>
</html>