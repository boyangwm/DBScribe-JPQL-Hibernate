<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>JavaMail.html</title>
<meta http-equiv="Content-Type" content="text/html; charset=big5">
</head>
<body>
<h2>利用JavaMail來傳送電子郵件</h2>
<form name="Form" method="post"   action="JavaMail.jsp">
  <p>收信人：<input type="text" name="To" size="30"></p>
  <p>主題：<input type="text" name="Subject" size="30" maxlength="30"></p>
  <p>內容：</p><p><textarea name="Message" cols=40 rows=5></textarea></p>
  <input type="submit" value="傳送">  <input type="reset" value="清除">
</form>
</body>
</html>