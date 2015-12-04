<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dish.model.*"%>
<%@ page import="java.util.*"%>
<%
	DishService dishsvc = new DishService();
	List<DishVO> list = dishsvc.getAllByRestID(102);
	pageContext.setAttribute("list",list);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script>
	$(function() {
		var objJ;
		$(":text").spinner({
			spin : function(event, ui) {
				if (ui.value > 99) {
					$(this).spinner("value", 0);
					return false;
				} else if (ui.value < 0) {
					$(this).spinner("value", 99);
					return false;
				}
			}
		}).on('blur', function() {
			if ($(this).val() > 99) {
				$(this).val(99)
			} else if ($(this).val() < 0) {
				$(this).val(0)
			}
		});
		$("#buy")
				.on(
						'click',
						function() {
							$("#table1")
									.fadeOut(100)
									.slideUp(
											100,
											function() {
												$("body")
														.append(
																"<table id='table2'><thead><tr><th>品名</th><th>原價</th><th>特價</th><th>數量</th><th>小計</th></tr></thead><tbody></tbody></table>");
												var total = 0;
												var objArray = new Array();
												var index = 0;
												$(":text")
														.each(
																function() {
																	if ($(this)
																			.val() != 0) {
																		var id = $(
																				this)
																				.parents(
																						"tr")
																				.children(
																						"td")
																				.eq(
																						0)
																				.attr(
																						"id");
																		var price = $(
																				this)
																				.parents(
																						"tr")
																				.children(
																						"td")
																				.eq(
																						2)
																				.text();
																		var quantity = $(
																				this)
																				.val();
																		$(
																				"#table2>tbody")
																				.append(
																						'<tr><td id="'+id+'">'
																								+ $(
																										this)
																										.parents(
																												"tr")
																										.children(
																												"td")
																										.eq(
																												0)
																										.text()
																								+ '</td>'
																								+ '<td>'
																								+ $(
																										this)
																										.parents(
																												"tr")
																										.children(
																												"td")
																										.eq(
																												1)
																										.text()
																								+ '</td>'
																								+ '<td>'
																								+ price
																								+ '</td>'
																								+ '<td>'
																								+ quantity
																								+ '</td>'
																								+ '<td>'
																								+ price
																								* quantity
																								+ '</td></tr>');
																		total += price
																				* quantity;
																		var obj = {
																			"id" : id,
																			"price" : price,
																			"quantity" : quantity,
																			"subtotal" : price
																					* quantity
																		};
																		objArray[index] = obj;
																		index += 1;
																	}
																});
												objJ = {
													"orderDetail" : objArray
												};
												$("#table2>tbody")
														.append(
																'<tr><td></td><td></td><td></td><td>總價</td><td>'
																		+ total
																		+ '</td></tr><tr><td></td><td></td><td></td><td><input type="button" id="change" value="修改"></td><td><input type="submit" id="sure" value="確定"></td></tr>');
											});
						});
		$('body').on('click', '#change', function() {
			$("#table2").fadeOut(100).slideUp(100, function() {
				$(this).remove();
				$("#table1").fadeIn(100).slideDown(100);
			});
		});
		$('body').on('click', '#sure', function() {
			//這裡準備丟出所有資料庫需要的資料
			//已準備好需要丟出的json
			console.log(objJ)
		});
	});
</script>
</head>
<body>
<body>
	<table id="table1">
		<thead>
			<tr>
				<th>菜名</th>
				<th>原價</th>
				<th>特價</th>
				<th>數量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dishVO" items="${list}">
				<tr align='center' valign='middle'>
					<td id="${dishVO.dishID}">${dishVO.dishName}</td>
					<td>${dishVO.price}</td>
					<td>${dishVO.specialPrice}</td>
					<td><input type="text" name="value" value="0"></td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td><input type="button" id="buy" value="我要購買"></td>
			</tr>
		</tbody>
	</table>
</body>
</html>