<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.dish.model.*"%>
<%@ page import="com.owner.model.*"%>
<%@ page import="java.util.*"%>
<%
	OwnerService  service = new OwnerService();
	OwnerVO voSession = (OwnerVO) session.getAttribute("ownerVO");
	String ownacc = voSession.getOwnAcc();
	OwnerVO OwnerVO = service.getByOwnAcc(ownacc);
	pageContext.setAttribute("OwnerVO",OwnerVO);
	
	DishService dishsvc = new DishService();
	List<DishVO> list = dishsvc.getAllByRestID(OwnerVO.getRestaurants().iterator().next().getRestID());
	pageContext.setAttribute("list",list);
	
	DishVO dishVO = (DishVO) request.getAttribute("dishVO");
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
		<tr align='center' valign='middle' >
			<td>${dishVO.dishName}</td>
			<td>${dishVO.price}</td>
			<td id="specialPriceView">
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
	<table border="0">
	
		<tr>
			<td>菜名:</td><!-- 用placeholder -->
			<td><input type="TEXT" name="dishName" placeholder="請輸入菜名" id="dishName"/>
				<font color='red'>${errorMsgs.Dishname}</font>
			</td>
		</tr>
	
		<tr>
			<td>價錢:</td>
			<td><input type="TEXT" name="price" value="${dishVO.getPrice()}" id="price"/>
			<font color='red'>${errorMsgs.Price}</font></td>
		</tr>	
		<tr>
			<td>特價:</td>
			<td><input type="TEXT" name="specialPrice" value="${dishVO.getSpecialPrice()}" id="specialPrice" />
			<font color='red'>${errorMsgs.Specialprice}</font></td>
		</tr>
		<tr>
			<td>開始日期:</td>
			<td><input type="TEXT" name="startDate" value="${dishVO.getStartDate()}" id="startDate" />(yyyy-MM-dd)</td>
		</tr>
		<tr>
			<td>結束日期:</td>
			<td><input type="TEXT" name="endDate" value="${dishVO.getEndDate()}" id="endDate" />(yyyy-MM-dd)
			<font color='red'>${errorMsgs.EndDate}</font></td>
		</tr>
	</table>
	<br>
		<input type="submit" value="送出新增" id="addDishButton">
		<input type="hidden" id="restID" name="restID" value="${OwnerVO.getRestaurants().iterator().next().getRestID()}">
	<div id="showDishes">
	</div>	
	
		
		
		
		
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/scripts.js"></script>		
<script type="text/javascript"></script>		
<script>
$(function(){
	$('#addDishButton').on('click',function(){
		alert("addDishButton");
		$.ajax({
			"url" : "DishServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : "add",
				"dishName" : $('#dishName').val(),
				"price": $('#price').val(),
				"specialPrice" :$('#specialPrice').val(),
				"startDate":$('#startDate').val(),
				"endDate":$('#endDate').val(),
				"restID":$('#restID').val(),
			},
			"success": function(data){
				alert("addDishButton : ");
				showDishes(data);
			} 
			
		})
		
		showDishes = function(data) {	
				$("#showDishes").empty().append("<br><table id='dishlistnow' border='1' bordercolor='#CCCCFF'><thead><tr><th>菜名</th><th>定價</th><th>特價</th><th>開始日期</th><th>結束日期</th><th>修改</th><th>刪除</th></tr></thead><tbody></tbody></table>");
				$.each(data,function() {
					for (var i = 0; i < data.dishes.length; i++) {
						$("#dishlistnow>tbody").append(
								'<tr align="center" valign="middle" id="dishID' + data.dishes[i].dishID + '"><td>' + data.dishes[i].dishName + '</td>'
								+ '<td>' + data.dishes[i].price + '</td>'
								+ '<td>' + data.dishes[i].specialPrice + '</td>'
								+ '<td>' + data.dishes[i].startDate + '</td>'
								+ '<td>' + data.dishes[i].endDate + '</td>'
								+ '<td>' + '<input type="button" value="修改">' + '</td>'
								+ '<td>' + '<input type="button" value="刪除">' + '</td>'
								+'</tr>'
						)							
					}
				});
			
		}
		
	})
	
})
</script>
</body>
</html>