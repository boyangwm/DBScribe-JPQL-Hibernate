<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ad.model.*"%>
<%@ page import="com.restaurant.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	ADservice ADSvc = new ADservice();
	List<AdVO> adlist = ADSvc.getByTreatID(1);
	pageContext.setAttribute("adlist",adlist);
%>
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet"  ="screen">
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<link href="../Css/eeit76-design.css" rel="stylesheet">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日食便當網</title>
<style>
	<style>
	.modal-dialog {
		width:600px;
	}
	.thumbnail {
		margin-bottom:6px;
	}
</style>

</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>


<div class="container eeit76-bordered-div" style="min-height: 680px;	">
	 <c:forEach var="adVO" items="${adlist}" >
		<div class="thumbnail col-lg-3 col-sm-4 col-6" title="${adVO.restaurantVO.restName}">
			<a href="#"><img src="${pageContext.servletContext.contextPath}/getImage?TreatID=1&RestID=${adVO.restID}" alt="..."></a>
			<div class="caption">
				 <h3>${adVO.restaurantVO.restName}</h3>
        		 <p>${adVO.context}<br>
       	   		    優惠至:${adVO.endDate}</p>
        		<p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      		</div>
		</div>
	 </c:forEach>
	


<!--   <div class="row"> -->
<!--     <h1 style="text-align:center;">廣告列表</h1> -->
<!--     <div class="row"> -->
<%--       <c:forEach var="adVO" items="${adlist}" > --%>
<%--      	 <div class="col-lg-3 col-sm-4 col-6"><a href="#" title="${adVO.restaurantVO.restName}"><img src='${pageContext.servletContext.contextPath}/getImage?TreatID=1&RestID=${adVO.restID}' class="thumbnail img-responsive"></a> --%>
<%--      	  	 <p style="color:#777; margin:0">${adVO.restaurantVO.restName}</p> --%>
<!--      	 	 <table style="margin:0 auto; text-align:left;color:#777"> -->
<!--      	 		<tr> -->
<%--      	 			<td>${adVO.context}</td> --%>
<!--      	 		</tr> -->
<!--      	 		<tr> -->
<%--      	 			<td>優惠至:${adVO.endDate}</td> --%>
<!--      	 		</tr> -->
<!--      		 </table> -->
     	
<!--      	 </div>																											 -->
<%--       </c:forEach> --%>
     
<!--     </div> -->

</div>
<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3 class="modal-title">Heading</h3>
	</div>
	<div class="modal-body">
	</div>
	<div class="modal-footer">
		<button class="btn btn-default" data-dismiss="modal">Close</button>
	</div>
   </div>
  </div>
</div>

<script>
$('.thumbnail').click(function(event){
	var temp = $(this).attr("src");
	var ID = temp.substring(41, 45);
	
  	$('.modal-body').empty();
  	var title = $(this).parent('a').attr("title");
  	
  	$(this).parent('a').attr("href","http://localhost:8081/BentoDelivery/generalService/restaurant.jsp?restID="+ID);
  	event.preventDefault();
  	$('.modal-title').html(title);
  	$($(this).parents('div').html()).appendTo('.modal-body');
  	$('#myModal').modal({show:true});
});
</script>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>