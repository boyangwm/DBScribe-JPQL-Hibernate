<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dish.model.*"%>
<%
	DishVO dishVO = (DishVO) request.getAttribute("dishVO"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>資料修改:</h3>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dish/dish.do">
<table border="0">
	<tr>
		<td>菜單編號:<font color=red><b>*</b></font></td>
		<td><%=dishVO.getDishID()%></td>
	</tr>
	<tr>
		<td>菜名:</td>
		<td><input type="TEXT" name="dishName" value="<%=dishVO.getDishName()%>" /></td>
	</tr>
	<tr>
		<td>價錢:</td>
		<td><input type="TEXT" name="price" value="<%=dishVO.getPrice()%>" /></td>
	</tr>
	<tr>
		<td>特價:</td>
		<td><input type="TEXT" name="specialPrice" value="<%=dishVO.getSpecialPrice()%>" /></td>
	</tr>
	<tr>
		<td>開始日期:</td>
		<td><input type="TEXT" name="startDate" value="<%=dishVO.getStartDate()%>" /></td>
	</tr>
	<tr>
		<td>結束日期:</td>
		<td><input type="TEXT" name="endDate" value="<%=dishVO.getEndDate()%>" /></td>
	</tr>
</table>
<input type="hidden" name="action" value="update_dish">
<input type="hidden" name="dishID" value="<%=dishVO.getDishID()%>">
<input type="hidden" name="restID" value="102">    <!-- 要改 -->   
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"><!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
<input type="submit" value="送出修改"></FORM>
</FORM>

</body>
</html>