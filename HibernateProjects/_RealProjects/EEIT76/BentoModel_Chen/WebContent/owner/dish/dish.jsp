<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.dish.model.*"%>
<%@ page import="java.util.*"%>
<%
	DishService dishsvc = new DishService();
	List<DishVO> list = dishsvc.getAllByRestID(102);
	pageContext.setAttribute("list",list);
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table  border='1' bordercolor='#CCCCFF'>
	<tr>
		<th>菜名</th>
		<th>價錢</th>
		<th>特價</th>
		<th>開始日期</th>
		<th>結束日期</th>
		<th>修改</th>
		<th>刪除</th>
		</tr>
	<c:forEach var="dishVO" items="${list}">
		<tr align='center' valign='middle'>
			<td>${dishVO.dishName}</td>
			<td>${dishVO.price}</td>
			<td id="specialPrice">
				<c:if test="${dishVO.specialPrice == null }">${dishVO.price}</c:if>
				<c:if test="${dishVO.specialPrice != null}">${dishVO.specialPrice}</c:if>
			</td>
			<td>${dishVO.startDate}</td>
			<td>${dishVO.endDate}</td>
			<td>
				<form METHOD="post" ACTION="<%=request.getContextPath()%>/dish/dish.do">
					<input type="submit" value="修改">
					<input type="hidden" name="dishID" value="${dishVO.dishID}">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
					<input type="hidden" name="action"	value="update_dish_get">
				</form>
			</td>
			<td>
				<form METHOD="post" ACTION="<%=request.getContextPath()%>/dish/dish.do">
					<input type="submit" value="刪除">
					<input type="hidden" name="dishID" value="${dishVO.dishID}">
					<input type="hidden" name="restID" value="${dishVo.restID}"/>
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
					<input type="hidden" name="action"	value="delete">
				</form>
			</td>
		</tr>
	</c:forEach>
</table>
	<hr>
	<a href="<%=request.getContextPath()%>/owner/dish/addDish.jsp">新增菜單</a>
</body>
</html>