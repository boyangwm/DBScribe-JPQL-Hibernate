<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>請輸入您的資料</h1>
<form METHOD="post" action="<%=request.getContextPath()%>/member.do">
帳號:<input type="text" name="account" value="${param.account}"><font color='red'>${errorMsgs.errorAcc}</font><br>
密碼:<input type="password" name="pwd" value="${param.pwd}"><font color='red'>${errorMsgs.errorPwd}</font><br>
姓:<input type="text" name="lastname" value="${param.lastname}"><font color='red'>${errorMsgs.errorLast}</font><br>
名:<input type="text" name ="firstname"value="${param.firstname}"><font color='red'>${errorMsgs.errorFirst}</font><br>
電話:<input type="text" name ="phone"value="${param.phone}"><br>
行動電話:<input type="text" name ="cell"value="${param.cell}"><br>
信箱:<input type="text" name ="email" value="${param.email}"><br>
性別:<input  type="radio" name="gender" value="true">男
	<input  type="radio" name="gender" value="false">女<p>
	<input type="submit">
</form>
</body>
</html>