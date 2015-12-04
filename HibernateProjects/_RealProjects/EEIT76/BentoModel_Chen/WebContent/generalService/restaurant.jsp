<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.restaurant.model.*"%>
<%@ page import="com.ad.model.*"%>
<%@ page import="com.favorite.model.*"%>
<%@ page import="java.util.*"%>
<%
	String temp = request.getParameter("restID");
	Integer restID = Integer.parseInt(temp);
	RestaurantService restsvc = new RestaurantService();
	RestaurantVO restaurantVO = restsvc.getOneRestaurant(restID);
 	session.setAttribute("restaurantVO", restaurantVO);
	
 	ADservice aDservice = new ADservice();
	AdVO adVO = aDservice.getByRestID(restID);
	pageContext.setAttribute("adVO",adVO);
	
	FavoriteDAO favoriteDAO = new FavoriteDAO();
	List<FavoriteVO> FavoriteVOs = favoriteDAO.getByRestID(restID);
	pageContext.setAttribute("FavoriteVOs",FavoriteVOs);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日日食便當網</title>
	<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/bootstrap/css/style.css" rel="stylesheet">
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/scripts.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/jquery.modal.js" type="text/javascript" charset="utf-8"></script>
	<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Slick/slick.css"/>
	<style type="text/css">
	.eeit76-bordered-div{
		width:80%;
		margin: 50px auto;
		text-align: left;
	}
	
	li{
		text-align: left;
		font-size: 20px;
	}
	* {
			font-family:微軟正黑體;
		}
	</style>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
	<h1 style="font-family:微軟正黑體;color:white;text-shadow: 2px 2px 3px rgba(255,255,255,0.6);">${restaurantVO.restName}</h1>

	<input type="hidden" id="restID" name="restID" value="${restaurantVO.restID}">
		<div class="eeit76-bordered-div box">
			<h2>店家資訊</h2>
				<ul class="restaurant-ul">
					
					<li>店名:${restaurantVO.restName}</li>
					<li>電話:${restaurantVO.restPhone}</li>
					<li>手機:${restaurantVO.restCel}</li>
					<li>種類:
					<c:forEach var="restKind" items="${restaurantVO.restKinds}">
					${restKind.kindlistVO.kindName},
					</c:forEach>
					</li>
					<li>外送地區:
					<c:forEach var="serviceArea" items="${restaurantVO.serviceAreas}">
					${serviceArea.city}${serviceArea.area},
					</c:forEach>
					</li>					
					<li>午餐請盡量於${restaurantVO.lastOrder_midday}前訂餐,晚餐請盡量於${restaurantVO.lastOrder_night}前訂餐</li>
					<li>廣告:	
						<c:if test="${adVO.treatCaseVO == null || adVO.treatCaseVO.treatID != 2}">暫無廣告</c:if>
                   		<c:if test="${adVO.treatID == 2}">"${adVO.context}"<br><img src='<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() %>/getImage?TreatID=2&RestID=${adVO.restID}' ></c:if>
					</li>
					<c:if test="${memberLoginOK == true }">
						<li>
							<input id="addFav" type="button"  value="收藏此店家">
						</li>
						<c:forEach var="FavoriteVO" items="${FavoriteVOs}">
							<c:if test="${FavoriteVO.memberAcc == memberVO.memberAcc && FavoriteVO.restID == restaurantVO.restID }">
								<li>
									已收藏此店家囉^-^
								</li>
							</c:if>
						</c:forEach>										
					</c:if>
				</ul>	
		</div>
	
		<div class="eeit76-bordered-div box">	
		<h2>評論</h2>	
		<ul style="list-style-type: none">	
		<li id="showDiscuss">	
				
					<c:forEach var="restDiscuss" items="${restaurantVO.restDiscusses}">
					<input type="hidden" name="memberAcc" value="${restDiscuss.memberVO.memberAcc}" id="memberAcc${restDiscuss.restDiscussID}">			
					<ul>
						<li>評價:<c:if test="${!restDiscuss.judge}"><span class="glyphicon glyphicon-thumbs-down"></span></c:if>
							    <c:if test="${restDiscuss.judge}"><span class="glyphicon glyphicon-thumbs-up"></span></c:if></li>
						<li>評論日期:${restDiscuss.judgeDate}</li>	
						<li>會員:${restDiscuss.memberAcc}</li>				
						<li>評論:${restDiscuss.discussion}</li>
						
							<c:if test="${ownerVO.ownAcc == restaurantVO.ownAcc &&  restDiscuss.response==null }">
							<li>
										回覆:<input type="text" id="ownerResponse${restDiscuss.restDiscussID}" name="response" value="${restDiscuss.response}"><input type="button" value="回覆" id="restResponse${restDiscuss.restDiscussID}" title="${restDiscuss.restDiscussID}">    													
							</li>
							</c:if>
							<c:if test="${restDiscuss.response!=null }">		
							<li>
									回覆:${restDiscuss.response}	
							</li>
							</c:if>
						
									
						<c:if test="${ownerVO.ownAcc == restaurantVO.ownAcc && restDiscuss.orderSumID!=null}">
							<li>
								<form action="<%=request.getContextPath()%>/owner/discuss.jsp" method="post">		
									${restDiscuss.orderSumID}
									<input type="hidden" name="orderSumID" value="${restDiscuss.orderSumID}">
									<input type="hidden" name="action" value="getOrder_By_OrderSumID">
								</form>
							</li>
						</c:if>	
						<c:if test="${ownerVO.ownAcc == restaurantVO.ownAcc}">	   
						    <li id="restAccuse${restDiscuss.restDiscussID}" value="${restDiscuss.restDiscussID}">
								檢舉理由:<input type="text" id="accuseText${restDiscuss.restDiscussID}" name="accuseText" value="">
								<input type="button" value="檢舉此評論" id="accuseSend${restDiscuss.restDiscussID}" title="${restDiscuss.restDiscussID}">					
							</li>
									
						</c:if>	
					</ul>	
					<br>
					</c:forEach>			
				</li>
				<c:if test="${memberLoginOK == true }">
					評論發送區
					會員名稱:${memberVO.memberAcc}<br>
					餐廳名稱:${restaurantVO.restName}<br>
					評價: <input type="radio" name="judge" value="true"><span class="glyphicon glyphicon-thumbs-up"></span>  <input type="radio" name="judge" value="false"><span class="glyphicon glyphicon-thumbs-down"></span><br>
					評論: <input type="text" id="discussion" name="discussion" value="${param.discussion}"><br>
					<input type="button" class="btn btn-warning" value="我要評論" id="commitDiscuss" style="margin-top:25px;">
				</c:if>	
		</ul>
		</div>
		
	
		<div class="eeit76-bordered-div box" style="height:450px; overflow:auto;  ">
			
			<h2>菜單</h2>
			<table class="table table-striped" border='1' bordercolor='#CCCCFF' style="font-size: 20px; width:600px; height:400px; overflow:auto; margin: 0 auto;">
				<tr>
					<th>菜名</th>
					<th>價錢</th>
					<th>特價</th>
				</tr>
						<c:forEach var="dish" items="${restaurantVO.dishes}">
							<tr>
								<td>${dish.dishName}</td>
								<td>${dish.price}</td>
								<td id="specialPrice">
									<c:if test="${dish.specialPrice == null }">${dish.price}</c:if>
									<c:if test="${dish.specialPrice != null}">${dish.specialPrice}</c:if>
								</td>
							</tr>
						</c:forEach>	
			</table>
			
		</div>
		<div style="text-align:center;">
			<c:if test="${ownerLoginOK != true }">
				
					<form action="../member/order/purchaseOrder.jsp" method="post" onsubmit="<%request.getSession().setAttribute("action","getDish_For_Add");%>">
							<input type="submit" class="btn btn-warning btn-large" style="margin:20px; font-size:28px;" value="我要訂餐">
					</form>
	
			</c:if>
		</div>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script>
	$(function(){
		
		
		$('#addFav').on('click',function(){
			$.ajax({
				"url" : "../member/AddFavShopServlet",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"restID":$('#restID').val(),
				},
				"success": function(data){
					alert(data.info);
				} 
			})
		})
		
		$('#commitDiscuss').on('click',function(){
			$.ajax({
				"url" : "PostDiscussServlet",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"action":"commit_discuss",
					"restID":$('#restID').val(),
					"judge":$('input[name^="judge"]:checked').val(),
					"discussion":$('#discussion').val(),
				},
				"success": function(data){
					if(!data.errorMessage){
						showDiscuss(data);
					}else{
						console.log("errorInput :");
						alert(data.errorMessage.errorInput);
					}
					
				} 
			})
			showDiscuss = function(data) {	
				var judgeWord = "";
				if(data.Judge){judgeWord = "<span class='glyphicon glyphicon-thumbs-up'></span>"}
				else{judgeWord = "<span class='glyphicon glyphicon-thumbs-down'></span>"}
				$("#showDiscuss").append('<ul>'				
							+ '<li>評價:' + judgeWord + '</li>'
							+ '<li>評論日期:' + data.JudgeDate + '</li>'
							+ '<li>會員:' + data.MemberAcc + '</li>'
							+ '<li>評論:' + data.Discussion + '</li>'			
							+ '</ul><br>');
			}	
		})
		
		$('input[id^="restResponse"]').on('click',function(){		
			$.ajax({
				"url" : "PostDiscussServlet",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"action":"reponseDiscuss",
					"restID":$('#restID').val(),
					"restDiscussID":$(this).attr('title'),
					"response":$('#ownerResponse'+$(this).attr('title')+'').val(),
				},
				"success": function(data){
					console.log("success :");
					if(!data.errorMessage){
						console.log("no error:");
						$('#restResponse'+data.restDiscussID+'').prev().hide();
						$('#restResponse'+data.restDiscussID+'').hide().after(data.ownerResponse);
					}else{
						console.log("errorInput :");
						alert(data.errorMessage.errorInput);
					}
				} 
			})
		})
		
		
		$('input[id^="accuseSend"]').on('click',function(){
			$.ajax({
				"url" : "PostDiscussServlet",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"action":"sendAccuse",
					"restID":$('#restID').val(),
					"restDiscussID":$(this).attr('title'),
					"reason":$('#accuseText'+$(this).attr('title')+'').val(),
					"caseID":1,
					"memberAcc":$('#memberAcc'+$(this).attr('title')+'').val(),
				},
				"success": function(data){
					if(!data.errorMessage){
						$('#accuseSend'+data.restDiscussID+'').prev().hide();
						$('#accuseSend'+data.restDiscussID+'').hide().after(data.reason+"(已檢舉)");
					}else{
						alert(data.errorMessage.errorInput);
					}
				} 
 			})
		})
		
		$('li[id^="restAccuse"]').each(function(){
		
 			'<c:forEach var="accuseVO" items="${restaurantVO.accuses}">'
 				if($(this).val() == '${accuseVO.restDiscussID}'){
					$(this).text("檢舉理由:${accuseVO.reason}(已檢舉)");//測試
 				}
			'</c:forEach>'
			
		});
	})

</script>	
<jsp:include page="../footer.jsp"></jsp:include>	
</body>
</html>