<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ad.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
AdVO advo = (AdVO) request.getAttribute("advo");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/addAD.do">
		廣告描述:<input type="text" name="Context"  value="${param.Context}" >
			   <font color='red'>${errorMsgs.Context}</font><br>
		選擇圖片:<input type="file" name="image"  />
			   <font color='red'>${errorMsgs.fileName}</font>
			   <font color='red'>${errorMsgs.fileNameerr}</font><br>
		開始日期:<input type="date" name="StartDate"  value="${param.StartDate}" >
			   <font color='red'>${errorMsgs.StarDate}</font><br>
		結束日期:<input type="date" name="EndDate"  value="${param.EndDate}" >
			   <font color='red'>${errorMsgs.EndDate}</font><br>  
		<input type="submit"   value="送出" /><br>
<!-- 		RestID<input type="text" name="RestID" value="" >   先寫死，整合時看要怎麼用 -->
	</form>
</body>
</html>