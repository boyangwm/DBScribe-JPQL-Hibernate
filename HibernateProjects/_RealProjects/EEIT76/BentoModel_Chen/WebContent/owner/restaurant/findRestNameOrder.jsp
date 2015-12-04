<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ordersum.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>與此人的交易紀錄</title>
</head>
<body>
	<table  border='1' bordercolor='#CCCCFF'>
	<tr>
		<th>訂單編號</th>
		<th>總價</th>
		<th>送餐地址</th>
		<th>顧客姓名</th>
		<th>聯絡電話</th>
		<th>期望送達時間</th>
		<th>訂單成立時間</th>
		<th>訂單狀態</th>
		</tr>
	<c:forEach var="OrderSumVO" items="${OrderSumVOlist}">
		<tr align='center' valign='middle'>
			<td>${OrderSumVO.orderSumID}</td>
			<td>${OrderSumVO.totalPrice}</td>
			<td>${OrderSumVO.city}${OrderSumVO.area}${OrderSumVO.addr}</td>
			<td>${OrderSumVO.memberVO.memberFirstName}${OrderSumVO.memberVO.memberLastName}</td>
			<td>${OrderSumVO.memberPhone}</td>
			<td>${OrderSumVO.expectDate}${OrderSumVO.expectTime}</td>
			<td>${OrderSumVO.orderDate}${OrderSumVO.orderTime}</td>
			<td>${OrderSumVO.orderCondVO.orderCond}</td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>