<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.restaurant.model.*"%>
<%@ page import="com.kindlist.model.*"%>
<%@ page import="java.util.*"%>
<%
// 	RestaurantService restsvc = new RestaurantService();
// 	RestaurantVO vo = restsvc.getOneRestaurant(102);
// 	pageContext.setAttribute("vo", vo);
	KindlistDAO ko = new KindlistDAO();
    List<KindlistVO> kvos = ko.getAll();
    pageContext.setAttribute("kvos",kvos);
    Object temp = request.getSession().getAttribute("kindidNow");
    if(temp !=null){
	    Integer kindIDNow = Integer.parseInt((String)temp);
	    if(kindIDNow != null){
	    	KindlistVO kvoNow = ko.getByPrimaryKey(kindIDNow);
	    	pageContext.setAttribute("kvoNow",kvoNow);
	    }
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日日食便當網</title>
	<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/bootstrap/css/style.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/scripts.js"></script>
	<script src="../bootstrap/js/jquery.modal.js" type="text/javascript" charset="utf-8"></script>
	<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">
	<style type="text/css">
/* 	.eeit76-bordered-div{ */
 		
/* 		margin: 2.5% 5% 2.5%; */
/* 		text-align: left; */
/* 		font-size: 20px; */
		
/* 	} */
	h3 {
		font-family:微軟正黑體;
	}
	
	</style>
</head>

<body>
<jsp:include page="../header.jsp"></jsp:include>



    <!-- Page Content -->
    <div class="container" style="margin:90px auto; font-size:20px;">

        <div class="row">

            <div class="col-md-3 box" style="padding:25px;">
                <p class="lead">現正搜尋:</p><p class="lead text-primary"><strong>${restCityNow}${areaNumNow}</strong></p>
                <p id="showKindNow" class="text-success" style=" height:20px;"></p>
                <br>
                <hr>
				<div class="list-group">
				<form action="searchRestaurant.jsp" method="get">
				<c:forEach var="kvo" items="${kvos}">	    
					<a class="list-group-item" style="width:50%; float:left; background-color:rgba(255,255,204,0.9)"><input type="checkbox" style="width:20px; height:20px;" value="${kvo.kindID}" id="${kvo.kindID}" name="kindcheck" ><lable for="${kvo.kindID}">${kvo.kindName}</lable></a>  
			    </c:forEach>
			    </div>
            </div>

            <div class="col-md-9">

<!--                 **************************主要內容******************* -->

                <div class="row">
					<c:forEach var="serviceAreaVO" items="${serviceAreaVOlist}">
						<div class="col-sm-4 col-lg-4 col-md-4"  id="Rest${serviceAreaVO.getRestaurantVO().getRestID()}">
                        <div class="thumbnail box">
                            <div class="caption">
<!--                                 <h4 class="pull-right">$24.99</h4> -->
                                <a href="<%=request.getContextPath()%>/generalService/restaurant.jsp?restID=${serviceAreaVO.getRestID()}"><h3><strong>${serviceAreaVO.getRestaurantVO().getRestName()}</strong></h3></a>
                                <p>
                                	<p class="text-success">
                                		<c:forEach var="rvo" items="${serviceAreaVO.getRestaurantVO().getRestKinds()}">	    
												${rvo.kindlistVO.kindName}
										</c:forEach>
								    </p>
								       	外送地區:<br>
								    <em>
										<c:forEach var="rvo" items="${serviceAreaVO.getRestaurantVO().getServiceAreas()}">	    
											&nbsp;${rvo.area}   
										</c:forEach>
									</em>
								</p>
                            </div>
<!--                             <div class="ratings"> -->
<!--                                 <p class="pull-right">15 reviews</p> -->
<!--                                 <p> -->
<!--                                     <span class="glyphicon glyphicon-star"></span> -->
<!--                                     <span class="glyphicon glyphicon-star"></span> -->
<!--                                     <span class="glyphicon glyphicon-star"></span> -->
<!--                                     <span class="glyphicon glyphicon-star"></span> -->
<!--                                     <span class="glyphicon glyphicon-star"></span> -->
<!--                                 </p> -->
<!--                             </div> -->
                        </div>
                    </div>
					</c:forEach>
                </div>

            </div>

        </div>

    </div>
    <!-- /.container -->
<script>
$(function(){
	$('input[name="kindcheck"]:checkbox').on('change',function(){
		var array1=[];
		$('input[name="kindcheck"]:checked').each(function(){array1.push($(this).attr("id"));});
    	
		$.ajax({
			"url" : "searchRestaurant.jsp",
			"type" : "get",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : "getRest_By_Restkind",
				"kind" :array1.toString(),
			},
			"success": function(data){
				showCheckedKind(data);
			} 
		})	
		showCheckedKind = function(data) {
				$('div[id^="Rest"]').hide();	
				$('#showKindNow').empty();
				$.each(data,function() {
					for (var i = 0; i < data.checkedkindlist.length; i++) {	
						$("div[id^='Rest']:contains("+data.checkedkindlist[i].KindName+")").show();
						$('#showKindNow').append(data.checkedkindlist[i].KindName+" ");
					}
				});	
				
		}
	})
	
})
</script>
<jsp:include page="../footer.jsp"></jsp:include>
</body>

</html>
