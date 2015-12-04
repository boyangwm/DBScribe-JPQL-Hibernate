<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dish.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
DishVO dishVO = (DishVO) request.getAttribute("dishVO");
%>
</head>
<body>
<h1>請輸入菜單資料</h1>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dish/dish.do">
<table border="0">
	<c:if test="${dishVO.getDishName()!=null}">
		<tr>
			<td>菜名:</td>
			<td><input type="TEXT" name="dishName" value="<%= (dishVO==null)? "請輸入菜名" : dishVO.getDishName()%>"/>
				<font color='red'>${errorMsgs.Dishname}</font>
			</td>
		</tr>
	</c:if>
	<c:if test="${dishVO.getDishName()==null}">
		<tr>
			<td>菜名:</td><!-- 用placeholder -->
			<td><input type="TEXT" name="dishName" placeholder="請輸入菜名"/>
				<font color='red'>${errorMsgs.Dishname}</font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td>價錢:</td>
		<td><input type="TEXT" name="price" value="${dishVO.getPrice()}" />
		<font color='red'>${errorMsgs.Price}</font></td>
	</tr>	
	<tr>
		<td>特價:</td>
		<td><input type="TEXT" name="specialPrice" value="${dishVO.getSpecialPrice()}" />
		<font color='red'>${errorMsgs.Specialprice}</font></td>
	</tr>
	<tr>
		<td>開始日期:</td>
		<td><input type="TEXT" name="startDate" value="${dishVO.getStartDate()}" />(yyyy-MM-dd)</td>
	</tr>
	<tr>
		<td>結束日期:</td>
		<td><input type="TEXT" name="endDate" value="${dishVO.getEndDate()}" />(yyyy-MM-dd)
		<font color='red'>${errorMsgs.EndDate}</font></td>
	</tr>
	<tr>
		<td>RestID:</td>
		<td><input type="TEXT" name="restID" value="${dishVO.getRestID()}" /></td>
	</tr>
</table>
<br>
	<input type="submit" value="送出新增">
	<input type="hidden" name="restID" value="${dishVo.restID}"/>  
	<input type="hidden" name="action" value="add">
</form>	

</body>
</html>