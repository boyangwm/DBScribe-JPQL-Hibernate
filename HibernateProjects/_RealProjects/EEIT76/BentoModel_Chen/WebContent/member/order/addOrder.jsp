<!-- (還要判斷若現在時間已超過店家建議的最晚訂餐時間要警告) -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.useraddr.model.*"%>
<%@ page import="java.util.Set"%>
<%@page import="java.lang.reflect.Array"%>
<%
	Set<UserAddrVO> set = ((MemberVO) request.getSession().getAttribute("memberVO")).getUserAddrs();
	if(!set.isEmpty()){
		UserAddrVO userAddrVO = (UserAddrVO) (Array.get(set.toArray(), 0));
		pageContext.setAttribute("userAddr", userAddrVO);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日日食便當網</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/jquery-ui-1.10.0.custom.css" />
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/locales/bootstrap-datetimepicker.zh-TW.js" charset="UTF-8"></script>
<script src="<%=request.getContextPath()%>/Script/countyAreaSelector.js"></script>
<style type="text/css">
.contentTable {
	margin: auto;
}
.box{
	margin-bottom: 20px;
    padding: 30px 15px;
    padding-bottom: 80px;
}
</style>

</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<div class="container">

        <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    
                    <h1></h1>
						<div id="choosing" class="contentTable">
							<table id="table1" data-height="500">
								<thead>
									<tr>
										<th data-field="name">品名</th>
										<th data-field="price">原價</th>
										<th data-field="specialPrice">特價</th>
										<th data-field="input">數量</th>
									</tr>
								</thead>
							</table>
							<input type="button" class="btn btn-warning" id="buy" value="我要購買" style="float:right; margin-top:20px;">
						</div>
						<div id="checking" class="contentTable" style="display: none;">
						<div>
							<table id="table2" data-height="200">
								<thead>
									<tr>
										<th data-field="name">品名</th>
										<th data-field="price">原價</th>
										<th data-field="specialPrice">特價</th>
										<th data-field="quantity">數量</th>
										<th data-field="subtotal">小計</th>
									</tr>
								</thead>
							</table>
						</div>
						<div>
							<table id='table3' class='table'>
								<thead>
									<tr>
										<th colspan='3'>請確認以下資訊</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>取餐日期:</td>
										<td colspan="2"><input type="text" id="datepicker" readonly></td>
									</tr>
									<tr>
										<td>取餐時間:</td>
										<td colspan="2"><input type="text" id="timepicker" readonly></td>
									</tr>
									<tr>
										<td>聯絡電話:</td>
										<td><span>${memberVO.memberPhone}</span></td>
										<td><input type='button' id='phone' value='變更'><input
											type='button' id='psure' value='確認' style='display: none;'></td>
									</tr>
									<tr>
										<td>送餐地址:</td>
										<td><span>${userAddr.city}</span><span>${userAddr.area}</span><span>${userAddr.addr}</span></td>
										<td><input type='button' id='addr' value='變更'></td>
									</tr>
									<tr>
										<td>備註:</td>
										<td colspan="2"><textarea cols="60" rows="2" maxlength="60"></textarea></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<input type="button" class="btn btn-warning" id="sure" value="送出訂單" style="float:right; margin-top:5px;">
					</div>
                    
                </div>
            </div>
        </div>
        <div id="changing" class="row" style="display: none;">
            <div class="box">
                <div class="col-lg-12">
                    
                    <div  >
						<table id='table4' class='table'>
							<thead>
								<tr>
									<th colspan='3'>變更地址</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>常用地址:</td>
									<td></td>
									<td><input type='button' id='custom' value='確認'></td>
								</tr>
								<tr>
									<td>輸入地址:</td>
									<td>
										<label for="county">縣市:</label><select id="county"><option>請選擇</option></select><label for="area">區域:</label><select id="area"><option>請選擇</option></select>
										<input type="text" maxlength="50" size="35">
									</td>
									<td><input type='button' id='newAddr' value='新增為常用地址'><input type='button' id='add' value='確認'></td>
								</tr>
							</tbody>
						</table>
					</div>
                    
                </div>
            </div>
        </div>
</div>
<script>
	$(function() {
		var id = [];
		var name = [];
		var price = [];
		var specialPrice = [];
		'<c:forEach var="dishVO" items="${list}">'
		id.push('${dishVO.dishID}');
		name.push('${dishVO.dishName}');
		price.push('${dishVO.price}');
		var special = '<c:choose><c:when test="${dishVO.specialPrice != null }">${dishVO.specialPrice}</c:when><c:otherwise>${dishVO.price}</c:otherwise></c:choose>';
		specialPrice.push(special.trim());
		'</c:forEach>'
		var data1 = [];
		for (var i = 0; i < name.length; i++) {
			data1.push({
				"id" : id[i],
				"name" : name[i],
				"price" : price[i],
				"specialPrice" : specialPrice[i],
				"input" : "<input type='text' name='value'>"
			});
		}
		$('#table1').bootstrapTable({
			data : data1
		});
		$(window).resize(function() {
			$('#table1').bootstrapTable('resetView');
		});
		$("#table1 :text").spinner({
			spin : function(event, ui) {
				if (ui.value > 99) {
					$(this).spinner("value", 0);
					return false;
				} else if (ui.value < 0) {
					$(this).spinner("value", 99);
					return false;
				}
			}
		}).spinner("value",0).on('blur', function() {
			if ($(this).val() > 99) {
				$(this).val(99)
			} else if ($(this).val() < 0) {
				$(this).val(0)
			}
		});
		var countyArray = [];
		var areaArray = [];
		var addrArray = [];
		'<c:forEach var="userAddrs" items="${memberVO.userAddrs}">'
		countyArray.push('${userAddrs.city}');
		areaArray.push('${userAddrs.area}');
		addrArray.push('${userAddrs.addr}');
		'</c:forEach>'
		var eleS = document.createElement('select');
		for (var i = 0; i < countyArray.length; i++) {
			var eleO = document.createElement('option');
			var eleS1 = document.createElement('span');
			var eleS2 = document.createElement('span');
			var eleS3 = document.createElement('span');
			var txtC = document.createTextNode(countyArray[i]);
			var txtA = document.createTextNode(areaArray[i]);
			var txtAddr = document.createTextNode(addrArray[i]);
			eleS1.appendChild(txtC);
			eleS2.appendChild(txtA);
			eleS3.appendChild(txtAddr);
			eleO.appendChild(eleS1);
			eleO.appendChild(eleS2);
			eleO.appendChild(eleS3);
			eleS.appendChild(eleO);
		}
		$('#table4>tbody>tr:eq(0)>td:eq(1)').html(eleS);
		var data3;
		var times = 0;
		$('#buy').on('click',function() {
			var determine = [];
			var data2 = [];
			var total = 0;
			$('#table1 :text').each(function(){
				if($(this).spinner("value") != 0){
					determine.push($(this).spinner("value"));
				}
			});
			if(determine.length == 0){
				alert('您尚未選擇任何餐點')
				}else{
				$('#choosing').fadeOut(100).slideUp(100,function() {
					times++;
					$('#checking').show();
					$('input[name="value"]').each(function() {
						if ($(this).val() != 0) {
							var id = data1[$(this).parents("tr").attr("data-index")].id;
							var name = $(this).parents("tr").children("td").eq(0).text();
							var price = $(this).parents("tr").children("td").eq(1).text();
							var specialPrice = $(this).parents("tr").children("td").eq(2).text();
							var quantity = $(this).val();
							var subtotal = price * quantity;
							total += subtotal;
							data2.push({"id" : id,"name" : name,"price" : price,"specialPrice" : specialPrice,"quantity" : quantity,"subtotal" : subtotal});
							}
						});
					data3 = data2;
					if(times <= 1){
						$('#table2').bootstrapTable({
							data : data2
						});
					}else{
						$('#table2').bootstrapTable('load', data2);
					}
					$('#checking').children('div:eq(0)')
					.after("<div style='float:right'><h5>總價:<span id='total'>"+ total+ "<span></h5><input type='button' class='btn btn-warning' id='change' value='修改訂單' style='margin-top:5px;'></div>");});
			}
		});
		var now = new Date();
		var year = now.getFullYear();
		var month = now.getMonth()+1;
		var date = now.getDate();
		var today = year + "-" + month + "-" + date;
		$('#datepicker').datetimepicker({
			format: "yyyy年MMdd日",
	        language:  'zh-TW',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0,
			startDate: new Date(),
			endDate: new Date(year,month-1,date+6),
	    });
		$('#timepicker').datetimepicker({
			format: "hh:ii",
	        language:  'zh-TW',
	        weekStart: 1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			minuteStep: 30,
			forceParse: 0
	    });
		$('#phone').on('click',function() {
			$(this).hide(0, function() {
				$('#psure').show();
			}).parents('tr').find('span:first-child').hide(0,function() {
				$(this).parent().append('<input type="text" maxlength="15" size="12" value="'+ $(this).text() + '">');
				$(this).remove();
			});
		});
		$('#psure').on('click', function() {
			var str = $(this).parents('tr').find('input[type="text"]').val();
			if(str == null || (str.trim()).length == 0){
				alert("請輸入電話");
			}else{
				$(this).hide(0, function() {
					$('#phone').show();
				}).parents('tr').find('input[type="text"]').hide(0, function() {
					$(this).parent().append('<span>' + $(this).val() + '</span>');
					$(this).remove();
				});
			}
		});
		$('#addr').on('click',function() {
			$(this).hide(0, function() {
				$('#changing').show(100);
			});
		});
		$('#custom').on('click',function(){
			$('#changing').hide(100);
			$('#addr').show(100).parents('tr').find('td:eq(1)').html($('#custom').parents('tr').find(':selected').html());
		});
		$('#newAddr').on('click',function(){
			var county = $('#county').find(':selected').val();
			var countyName = $('#county').find(':selected').text();
			var area = $('#area').find(':selected').text();
			var addr = $('#add').parents('tr').find(':text').val();
			if(county=="請選擇" || area=="請選擇" || addr == null || (addr.trim()).length == 0){
				alert('請輸入完整地址');
			}else if($('#custom').parents('tr').find('option').length >= 3){
				alert('常用地址只能有3個');
			}else{
				$.ajax({
					"url" : "../UserAddrServlet",
					"type" : "post",
					"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
					"dataType" : "json",
					"data" : {
						"MemberAcc" : "${memberVO.memberAcc}",
						"addrID" : "",
						"city" : county,
						"area" : area,
						"addr" : addr
					},
					"success" : function(data) {
						$('#custom').parents('tr').find('select').append('<option><span>'+countyName+'</span><span>'+area+'</span><span>'+addr+'</span></option>');
						alert(data.info);
					}
				});
			}
		})
		$('#add').on('click',function(){
			var county = $('#county').find(':selected').text();
			var area = $('#area').find(':selected').text();
			var addr = $('#add').parents('tr').find(':text').val();
			if(county=="請選擇" || area=="請選擇" || addr == null || (addr.trim()).length == 0){
				alert('請輸入完整地址');
			}else{
				$('#changing').hide(100);
				$('#addr').show(100).parents('tr').find('td:eq(1)').html('<span>'+county+'</span><span>'+area+'</span><span>'+addr+'</span>');
			}
		});
		$('#checking').on('click', '#change', function() {
			$('#checking').fadeOut(100).slideUp(100, function() {
				if($('#psure').attr('style')!="display: none;"){
					$('#psure').hide(0, function() {
						$('#phone').show();
				}).parents('tr').find('input[type="text"]').hide(0, function() {
					$(this).parent().append('<span>' + $(this).val() + '</span>');
					$(this).remove();
				});}
				if($('#addr').attr('style') == "display: none;"){
					$('#changing').hide();
					$('#addr').show();
				}
				$('#change').parent('div').remove();
				$('#choosing').fadeIn(100).slideDown(100);
			});
		});
		var now = new Date();
		var year = now.getFullYear();
	 	var month = now.getMonth()+1;
	 	var date = now.getDate();
	 	var today = year + "-" + month + "-" + date;
		$('#sure').one('click',function() {
			var selectedDate = $('#datepicker').val();
			var selectedTime = $('#timepicker').val();
			if($('#phone').attr('style') == "display: none;" || $('#addr').attr('style') == "display: none;" || selectedDate == "" || selectedTime == ""){
				alert("請確認資訊");
			}else{
				var Year = selectedDate.substr(0,4);
				var Month = selectedDate.substr(5,2);
				var Date = selectedDate.substr(8,2);
				var orderDetail = '{"orderDetail":[';
				for (var i = 0; i < data3.length; i++) {
					orderDetail += '{"id":' + data3[i].id + ',"price":' + data3[i].specialPrice + ',"quantity":' + data3[i].quantity + ',"subtotal":' + data3[i].subtotal + '}';
					if (i < data3.length - 1) {
						orderDetail += ',';
					}
				}
				orderDetail += ']}';
				$.ajax({
					"url" : "commitOrder.jsp",
					"type" : "post",
					"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
					"dataType" : "json",
					"data" : {
						"action" : "add_Order",
						"restID" : "${restaurantVO.restID}",
						"memberAcc" : "${memberVO.memberAcc}",
						"memberPhone" : $('#phone').parents('tr').find('span:first-child').text(),
						"city" : $('#addr').parents('tr').find('span:eq(0)').text(),
						"area" : $('#addr').parents('tr').find('span:eq(1)').text(),
						"addr" : $('#addr').parents('tr').find('span:eq(2)').text(),
						"totalPrice" : $('#total').text(),
						"expectDate" : Year+"-"+Month+"-"+Date,
						"expectTime" : selectedTime+":00",
						"memo" : $('textarea').val(),
						"orderDetail" : orderDetail
					},
					"success" : function(data) {
						if (!data.errorMsgs) {
							$('h1').empty().html(
								data.success).after('<h3>請至會員專區追蹤訂單狀態</h3>');
							$('#checking').remove();
						} else {
							$('h1').empty()
							for (var i = 0; i < data.errorMsgs.length; i++) {
								$('h1').empty().html(
								data.errorMsgs);
							}
						}
					}
				});
			}
		});
	});
</script>
<jsp:include page="../../footer.jsp"></jsp:include>
</body>
</html>