<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ordercond.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.useraddr.model.*"%>
<%@page import="java.lang.reflect.Array"%>
<%@ page import="com.member.model.*"%>
<%@page import="java.sql.Date"%>
<%@ page import="com.favorite.model.*"%>

<%
	  MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
	  String MemberACC = mvo.getMemberAcc();
	  FavoriteDAO dao = new FavoriteDAO();
      List<FavoriteVO> Flist =  dao.getByMemberAcc(MemberACC);
      pageContext.setAttribute("favshop", Flist);
	  
%>


<%UserAddrVO userAddrVO = null;
try{userAddrVO = (UserAddrVO)(Array.get(((MemberVO)request.getSession().getAttribute("memberVO")).getUserAddrs().toArray(),0));}
catch(Exception e){
	
}%>
<%pageContext.setAttribute("userAddr1", userAddrVO); %>
<%UserAddrVO userAddrVO2 = null;
  try {userAddrVO2 = (UserAddrVO)(Array.get(((MemberVO)request.getSession().getAttribute("memberVO")).getUserAddrs().toArray(),1));}
  catch(Exception e){
	  
  }%>
<%if(userAddrVO2!=null){
  pageContext.setAttribute("userAddr2", userAddrVO2);} %>
<%UserAddrVO userAddrVO3 =null;
 try{userAddrVO3 = (UserAddrVO)(Array.get(((MemberVO)request.getSession().getAttribute("memberVO")).getUserAddrs().toArray(),2	));}
 catch(Exception e){
	  
 }%>
<%if(userAddrVO3!=null){
	pageContext.setAttribute("userAddr3", userAddrVO3);}%>

<%
OrderCondService OCservice = new OrderCondService();
List<OrderCondVO> orderCondList = OCservice.getAll();
pageContext.setAttribute("orderCondList", orderCondList);

// 	RestOrderService  service = new RestOrderService();
//     List<OrderSumVO> list = service.getByExpectDate(Integer.valueOf(101),new Date(new java.util.Date().getTime()));
//     OrderSumVO vo = null;
//     if(!list.isEmpty()){
//     	vo = list.get(0);
//     }
//     pageContext.setAttribute("vo",vo);
// 	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="zh-tw">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>日日食便當網</title>
	<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/bootstrap/css/style.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/scripts.js"></script>
<script src="<%=request.getContextPath()%>/Script/jquery-ui.js"></script>
	
	
	<style>
	.nav-tabs > li {
   background: #fffee5;
    background: rgba(255,255,229,0.9);
    border-radius: 4px 4px 0 0;
}
.nav-tabs > li.active {
   background: #fffee5;
    background: rgba(255,255,229,0.9);
    border-radius: 4px 4px 0 0;
}
	.tab-content > .active {
	padding: 25px;
  background: #fffee5;
    background: rgba(255,255,229,0.9);
	}
		.caption{
				font-size:x-large;
				text-align:LEFT;
				font-weight: bold;
				padding-top:15px;
				padding-bottom:15px;
				
		}
		#showOrders{
			margin:auto;
			
		}
		.pointer{
			cursor:pointer;
		}
		
		.test{
			margin:0px auto;
		}
	</style>
</head>

<body style="background: url('../Image/backgroundSun2.png') no-repeat bottom right fixed,rgb(255,255,255);">
<jsp:include page="../header.jsp"></jsp:include>

<div class="container" style=" border-bottom:30px;">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="tabbable" id="tabs-797877">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-1" data-toggle="tab">會員基本資料</a>
					</li>
					<li>
						<a href="#panel-2" data-toggle="tab">查詢訂單狀態</a>
					</li>
					<li>
						<a href="#panel-3" data-toggle="tab">管理收藏店家與常用地址</a>
					</li>
	
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-1">
					<div class="test">
						<table class="table table-striped" style="text-align:left;"> 
							<caption class="caption">個人資料</caption>
							
							<tr>
								<td>姓:</td>
								<td><input id="lastname" type="text" value="${memberVO.memberLastName}"></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">名:</td>
								<td><input id="firstname" type="text" value="${memberVO.memberFirstName}"></td>
							</tr>
							<tr>
								<td>電話:</td>
								<td><input id="phone" type="text" value="${memberVO.memberPhone}"></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">手機:</td>
								<td><input id="cel" type="text" value="${memberVO.memberCel}"></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">電子信箱:</td>
								<td><input id="email" type="text" value="${memberVO.memberEmail}"></td>
							</tr>
						</table>
						
							<div style="text-align:center">
								<button id="update" class="btn btn-large btn-warning" type="button">送出修改</button>
							</div>
							
						<table class="table table-striped">
							<caption class="caption">更改密碼</caption>				
							<tr>
								<td style="font-weight: bold;">原有密碼:</td>
								<td><input type="password" id="oldpwd"></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">新密碼:</td>
								<td><input type="password" id="newpwd1"></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">確認新密碼:</td>
								<td><input type="password" id="newpwd2"></td>
							</tr>
						</table>
							<div style="text-align:center">
								<button id="updatePwd" class="btn btn-large  btn-warning" type="button">送出修改</button>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="panel-2" style="min-height: 680px;">
						<h1>檢視訂單</h1>
							<input type="button" id="getAll_For_Display" value="全部">
							<input type="date">
							<input type="button" id="get_By_Expect" value="取餐日期">
							<select id="condSelect">
							<c:forEach var="orderCond" items="${orderCondList}">
								<option id="${orderCond.orderCondID}">${orderCond.orderCond}</option>
							</c:forEach>
							</select>
							<input type="button" id="get_By_Cond" value="訂單狀態">
							店家名稱:<input type="text" maxlength="10" size="8" id="restName">
							<input type="button" id="get_By_Name" value="店家名稱">
							<div id="showOrders" >
								<select id='cond' hidden='true'><c:forEach var='orderCond' items='${orderCondList}'>
								<option id='${orderCond.orderCondID}'>${orderCond.orderCond}</option></c:forEach></select>
								<table id='sum' class='table table-striped' style='display: none;'>
									<thead>
										<tr>
											<th></th><th>訂單編號</th><th>總價</th><th>送餐地址</th><th>店家名稱</th><th>聯絡電話</th>
											<th>期望送達時間</th><th>訂單成立時間</th><th>訂單狀態</th></tr>
									</thead>
									<tbody></tbody>
								</table>
								<span id='errorMsgs'></span>
							</div>
					</div>
					<div class="tab-pane" id="panel-3" style="min-height: 680px;">
						<table class="table table-striped">
						<caption class="caption">常用地址</caption>
								<tr>
									<td>
										常用地址1:&nbsp;&nbsp;
										<select id="city1" name="county" style="font-family:微軟正黑體;">
											<option>${userAddr1.city}</option>
										</select>
										<select id="area1" name="area" style="font-family:微軟正黑體;">
											<option>${userAddr1.area}</option>
										</select>
										<input id="addr1" type="text" value="${userAddr1.addr}">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input name="1" id="${userAddr1.userAddrID}"class="address" type="button" value="新增/送出修改">
									</td>
								</tr>
								<tr>
									<td>
										常用地址2:&nbsp;&nbsp;
										<select id="city2" name="county" style="font-family:微軟正黑體;">
											<option>${userAddr2.city}</option>
										</select>
										<select id="area2" name="area" style="font-family:微軟正黑體;">
											<option>${userAddr2.area}</option>
										</select>
										
										<input id="addr2" type="text" value="${userAddr2.addr}">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input name="2" id="${userAddr3.userAddrID}"class="address" type="button" value="新增/送出修改">
									</td>
								</tr>
								<tr>
									<td>
										常用地址3:&nbsp;&nbsp;
										<select id="city3" name="county" style="font-family:微軟正黑體;">
											<option>${userAddr3.city}</option>
										</select>
										<select id="area3" name="area" style="font-family:微軟正黑體;">
											<option>${userAddr3.area}</option>
										</select>
										<input id="addr3" type="text" value="${userAddr3.addr}">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input name="3" id="${userAddr3.userAddrID}"class="address" type="button" value="新增/送出修改">
									</td>
								</tr>
							
						</table>
						<table class="table table-striped">
							<caption class="caption">喜好店家</caption>
							<c:if test="${favshop[0].restaurantVO.restName==null}">
								<tr>
									<td>暫無收藏店家</td>
								</tr>
							</c:if>
							<c:if test="${favshop[0].restaurantVO.restName!=null}">
								<tr>
									<td>${favshop[0].restaurantVO.restName}
									<td><input id="shop1" type="button" value="刪除"></td>
								<tr>
							</c:if>
							<c:if test="${favshop[1].restaurantVO.restName!=null}">
								<tr>
									<td>${favshop[1].restaurantVO.restName}</td>
									<td><input id="shop2" type="button" value="刪除"></td>
								</tr>
							</c:if>
							<c:if test="${favshop[2].restaurantVO.restName!=null}">
								<tr>
									<td>${favshop[2].restaurantVO.restName}</td>
									<td><input id="shop3" type="button" value="刪除"></td>
								</tr>
							</c:if>
							<c:if test="${favshop[3].restaurantVO.restName!=null}">
								<tr>
									<td>${favshop[3].restaurantVO.restName}</td>
									<td><input id="shop4" type="button" value="刪除"></td>
								</tr>
							</c:if>
							<c:if test="${favshop[4].restaurantVO.restName!=null}">
								<tr>
									<td>${favshop[4].restaurantVO.restName}</td>
									<td><input id="shop5" type="button" value="刪除"></td>
								</tr>
							</c:if>
						
						
						</table>
					</div>
				
				</div>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){
	var memberAcc = "${memberVO.memberAcc}";
	$("#shop1").on('click',function(){
		var ID = "${favshop[0].restaurantVO.restID}";
		$.ajax({
			"url" : "DeleteFavoriteShop",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				memberAcc: memberAcc,
				restID:	ID,			
			},
			"success": function(data){
				alert(data.info);
			} 
		})
		window.location.reload();
	});
	$("#shop2").on('click',function(){
		var ID = "${favshop[1].restaurantVO.restID}";
		$.ajax({
			"url" : "DeleteFavoriteShop",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				memberAcc: memberAcc,
				restID:	ID,			
			},
			"success": function(data){
				alert(data.info);
			} 
		})
		window.location.reload();
	});
	$("#shop3").on('click',function(){
		var ID = "${favshop[2].restaurantVO.restID}";
		$.ajax({
			"url" : "DeleteFavoriteShop",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				memberAcc: memberAcc,
				restID:	ID,			
			},
			"success": function(data){
				alert(data.info);
			} 
		})
		window.location.reload();
	});
	$("#shop4").on('click',function(){
		var ID = "${favshop[3].restaurantVO.restID}";
		$.ajax({
			"url" : "DeleteFavoriteShop",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				memberAcc: memberAcc,
				restID:	ID,			
			},
			"success": function(data){
				alert(data.info);
			} 
		})
		window.location.reload();
	});
	$("#shop5").on('click',function(){
		var ID = "${favshop[4].restaurantVO.restID}";
		$.ajax({
			"url" : "DeleteFavoriteShop",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				memberAcc: memberAcc,
				restID:	ID,			
			},
			"success": function(data){
				alert(data.info);
			} 
		})
		window.location.reload();
	});
	
	$("input[name='1']").on('click',function(){
		
			$.ajax({
				"url" : "UserAddrServlet",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					MemberAcc: memberAcc,
					addrID :this.id,
					city:$('#city1').val(),
					addr:$('#addr1').val(),
					area:$('#area1').val(),
					
				},
				"success": function(data){
					alert(data.info);
				} 
			})
		
	});
	$("input[name='2']").on('click',function(){
		
		$.ajax({
			"url" : "UserAddrServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				MemberAcc: memberAcc,
				addrID :this.id,
				city:$('#city2').val(),
				addr:$('#addr2').val(),
				area:$('#area2').val(),
				
			},
			"success": function(data){
				alert(data.info);
			} 
		})
	
});
	$("input[name='3']").on('click',function(){
		
		$.ajax({
			"url" : "UserAddrServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				MemberAcc: memberAcc,
				addrID :this.id,
				city:$('#city3').val(),
				addr:$('#addr3').val(),
				area:$('#area3').val(),
				
			},
			"success": function(data){
				alert(data.info);
			} 
		})
	
});
	$('#update').on('click',function(){
		$.ajax({
			"url" : "UpdateMember",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				action:"updateInfo",
				"firstname": $('#firstname').val(),
				"lastname": $('#lastname').val(),
				"phone": $('#phone').val(),
				"cell" :$('#cel').val(),
				"email":$('#email').val(),
			},
			"success": function(data){
				alert(data.info);
			} 
		})
	})
	$('#updatePwd').on('click',function(){
		$.ajax({
			"url" : "UpdateMember",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				action:"updatePwd",
				pwd : $('#oldpwd').val(),
				newpwd1: $('#newpwd1').val(),
				newpwd2 :$('#newpwd2').val(),
			},
			"success": function(data){
				alert(data.info);
			} 
		})
	})
})
</script>
<script type="text/javascript">
	$(function() {
		var availableTags = [
		                     "小港督",
		                     "晶美自助餐",
		                     "百品烤肉",
		                    
		                   ];
		                   $( "#restName" ).autocomplete({
		                     source: availableTags
		                   });
		
		
		var memberAcc = "${memberVO.memberAcc}";
		
		//查詢所有訂單
		$('#getAll_For_Display').on('click',function() {
			var action = "getAll_For_member";
			var dataObj={"term1":"","term2":""}
			doAjax(action,dataObj);
		});
		//依取餐日期查詢訂單
		$('#get_By_Expect').on('click',function() {
			var action = "get_By_Expect";
			var dataObj={"term1":$('input[type="date"]').val(),"term2":""}
			doAjax(action,dataObj);
		});
		//依訂單狀態查詢訂單
		$('#get_By_Cond').on('click',function() {
			var action = "get_By_Cond";
			var dataObj={"term1":$('#condSelect').find(':selected').attr("id"),"term2":""}
			doAjax(action,dataObj);
		});
		//依客戶姓名查詢訂單
		$('#get_By_Name').on('click',function() {
			var action = "get_By_Name";
			var dataObj={"term1":$('#restName').val(),"term2":""}
			doAjax(action,dataObj);
		});
		//依客戶電話查詢訂單
		$('#get_By_Phone').on('click',function() {
			var action = "get_By_Phone";
			var dataObj={"term1":$('#phone').val(),"term2":""}
			doAjax(action,dataObj);
		});
		//查詢功能呼叫AJAX的function
		doAjax = function(action,dataObj){
			$.ajax({
				"url" : "MemberOrder",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"action" : action,
					"memberAcc" : memberAcc,
					"term1" : dataObj.term1,
					"term2" : dataObj.term2
				},
				"success" : function(data) {
					showData(data);
				}
			});
		}
		//秀出訂單的function
		showData = function(data) {
			
		if (!data.errorMsgs) {
			if($("#errorMsgs").text() != ''){$("#errorMsgs").empty();}
			if($('#sum').attr('style') == 'display: none;'){$('#sum').show();}
			$("#sum>tbody").empty();
			$.each(data,function() {
				
				for (var i = 0; i < data.orders.length; i++) {
					console.log(data.orders[i].orderSum.TotalPrice);
					$("#sum>tbody").append(
						'<tr id="' + i + '"><td><span class="glyphicon glyphicon-hand-right"></span></td><td>' + data.orders[i].orderSum.OrderSumID + '</td>'
						+ '<td>' + data.orders[i].orderSum.TotalPrice + '</td>'
						+ '<td>' + data.orders[i].orderSum.Address + '</td>'
						+ '<td>' + data.orders[i].orderSum.MemberName + '</td>'
						+ '<td>' + data.orders[i].orderSum.MemberPhone + '</td>'
						+ '<td>' + data.orders[i].orderSum.ExpectDatetime + '</td>'
						+ '<td>' + data.orders[i].orderSum.OrderDatetime + '</td>'
						+ '<td>' + data.orders[i].orderSum.OrderCond + '</td></tr>')
						.append('<tr style="display: none;"><td colspan="10"><table id="detail'+i+'" class="table"><thead><tr><th>品名</th><th>價格</th><th>數量</th><th>小計</th></tr></thead><tbody></tbody></table></td></tr>');
					for (var j = 0; j < data.orders[i].orderdetails.length; j++) {
						console.log(data.orders[i].orderdetails[j].DishName);
						$('#detail'+i+'>tbody').append(
								'<tr><td style="text-align:left">' + data.orders[i].orderdetails[j].DishName + '</td>'
								+ '<td style="text-align:left">' + data.orders[i].orderdetails[j].Price + '</td>'
								+ '<td style="text-align:left">' + data.orders[i].orderdetails[j].Quantity + '</td>'
								+ '<td style="text-align:left">' + data.orders[i].orderdetails[j].Subtotal + '</td></tr>'
								);
					}
					var memo = (data.orders[i].orderSum.Memo == undefined)?"":data.orders[i].orderSum.Memo;
					var memoResponse = (data.orders[i].orderSum.MemoResponse == undefined)?"":data.orders[i].orderSum.MemoResponse;
					$('#detail'+i+'>tbody').append('<tr><td style="text-align:left"  colspan="4">備註:'+memo+'</td></tr><tr><td style="text-align:left" colspan="4">回覆:<span>'+memoResponse+'</span></td></tr>');
				}
			});
		} else {
			if($('#cond').attr('style') != "display: none;"){$('#cond').hide();}
			if($('#change').attr('style') != 'display: none;'){$('#change').hide();}
			if($('#response').attr('style') != 'display: none;'){$('#response').hide();}
			if($('#sum').attr('style') != 'display: none;'){$('#sum').hide();}
			$("#errorMsgs").empty().append(data.errorMsgs);
		}
	}
	$('#showOrders').on('mouseenter','#sum>tbody>tr:even',function(){
		$(this).addClass('pointer');
	}).on('mouseleave','#sum>tbody>tr:odd',function(){
		$(this).removeClass('pointer');
	}).on('click','#sum>tbody>tr:even>td[name!="checkbox"]',function(){
		var details = $(this).parents('tr').next();
		if(details.attr('style') == 'display: none;'){
			$(this).parents('tr').find('span').attr('class','glyphicon glyphicon-hand-down');details.show(300);
			}else{
				details.hide(300,function(){
					$(this).prev().find('span').attr('class','glyphicon glyphicon-hand-right');
				});
			}
	}).on('change','#checkToggle',function() {
		$(':checkbox').prop("checked", $(this).prop("checked"));
	}).on('change','input[type="checkbox"]',function() {
		if($('input[name="order"]:checked').length != 0){
			$('#cond').prop("hidden",false);
			$('#change').prop("hidden",false);
			$('#response').prop("hidden",false);
		}else{
			$('#cond').prop("hidden",true);
			$('#change').prop("hidden",true);
			$('#response').prop("hidden",true);
		}
	}).on('click','#change',function(){
		var array = [];
		$('input[name="order"]').each(function() {
			if ($(this).prop("checked") == true) {
				array.push($(this).parents('tr').children('td:eq(2)').text());
			}
		});
		$.ajax({
			"url" : "treatOrder.jsp",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : "update_By_Cond",
				"restID" : restID,
				"orderSumID" : "[" + array.toString() + "]",
				"orderCondID" : $('#cond').find(':selected').attr("id"),
			},
			"success" : function(data) {
				if(!data.errorMsgs){
					$('input[name="order"]').each(function() {
						if ($(this).prop("checked") == true) {
							$(this).parents('tr').children('td:eq(9)').text(data.orderCond);
						}
					});
				}else{
					alert(data.errorMsgs);
				}
			}
		});
	  })
	$('#commitResponse').on('click',function(){
			var array = [];
			var restID = $('#restID').val();
			$('input[name="order"]').each(function() {
				if ($(this).prop("checked") == true) {
					array.push($(this).parents('tr').children('td:eq(2)').text());
				}
			});
			$.ajax({
				"url" : "treatOrder.jsp",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"action" : "update_By_MemoRes",
					"restID" : restID,
					"orderSumID" : "[" + array.toString() + "]",
					"memoResponse" : $('#responseTA').val(),
				},
				"success" : function(data) {
					if(!data.errorMsgs){
						$('input[name="order"]').each(function() {
							if ($(this).prop("checked") == true) {
								$(this).parents('tr').next().find('span').text(data.memoResponse);
							}
						});
						$('#updateResponse').modal('hide');
					}else{
						alert(data.errorMsgs);
					}
				}
			});
		  });
	})
$(function(){
			var countyS = document.getElementById("city1");
			var areaS = document.getElementById("area1");

			$.getJSON("<%=request.getContextPath()%>/Datas/taiwan.json", {}, function(data) {
				if (countyS) {
									$.each(data, function() {
					for (var i = 0; i < data.Taiwan.length; i++) {
						var eleO = document.createElement("option");
						eleO.setAttribute("value",i);
						var txt = document.createTextNode(data.Taiwan[i].County);
						eleO.appendChild(txt);
						countyS.appendChild(eleO);
					}
				});

				countyS.addEventListener("change",getValue,false);

				function getValue (){
					$.each(data, function() {
						$(areaS).empty();
						var county = $("#city1").val();
						for (var i = 0; i < data.Taiwan[county].Area.length; i++) {
							var eleO = document.createElement("option");
							eleO.setAttribute("value",i);
							var txt = document.createTextNode(data.Taiwan[county].Area[i]);
							eleO.appendChild(txt);
							areaS.appendChild(eleO);
							eleO.setAttribute("value", data.Taiwan[county].Area[i]);
						}
					});
				}
				}
				var countyS2 = document.getElementById("city2");
				var areaS2 = document.getElementById("area2");
				if (countyS2) {
									$.each(data, function() {
				for (var i2 = 0; i2 < data.Taiwan.length; i2++) {
					var eleO2 = document.createElement("option");
					eleO2.setAttribute("value",i2);
					var txt2 = document.createTextNode(data.Taiwan[i2].County);
					eleO2.appendChild(txt2);							
					countyS2.appendChild(eleO2);	
				}
			});
				countyS2.addEventListener("change",getValue2,false);
				function getValue2 (){
					$.each(data, function() {
						$(areaS2).empty();
						var county = $("#city2").val();
						for (var i = 0; i < data.Taiwan[county].Area.length; i++) {
							var eleO = document.createElement("option");
							eleO.setAttribute("value",i);
							var txt = document.createTextNode(data.Taiwan[county].Area[i]);
							eleO.appendChild(txt);
							areaS2.appendChild(eleO);
							eleO.setAttribute("value",data.Taiwan[county].Area[i]);								
						}					
					}); 
				}
				}
				var countyS3 = document.getElementById("city3");
				var areaS3 = document.getElementById("area3");
				if (countyS3) {
									$.each(data, function() {
				for (var i2 = 0; i2 < data.Taiwan.length; i2++) {
					var eleO2 = document.createElement("option");
					eleO2.setAttribute("value",i2);
					var txt2 = document.createTextNode(data.Taiwan[i2].County);
					eleO2.appendChild(txt2);							
					countyS3.appendChild(eleO2);	
				}
			});
				countyS3.addEventListener("change",getValue3,false);
				function getValue3 (){
					$.each(data, function() {
						$(areaS3).empty();
						var county = $("#city3").val();
						for (var i = 0; i < data.Taiwan[county].Area.length; i++) {
							var eleO = document.createElement("option");
							eleO.setAttribute("value",i);
							var txt = document.createTextNode(data.Taiwan[county].Area[i]);
							eleO.appendChild(txt);
							areaS3.appendChild(eleO);
							eleO.setAttribute("value",data.Taiwan[county].Area[i]);								
						}					
					}); 
				}
				}
				
			});
			
				
				
		})
</script>
<!-- 下面是show order的js -->
<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>
