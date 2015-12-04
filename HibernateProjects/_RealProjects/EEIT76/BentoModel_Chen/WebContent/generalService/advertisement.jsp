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
	List<AdVO> adlist = ADSvc.getByTreatID(2);
	pageContext.setAttribute("adlist",adlist);
%>
<link href 	="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet =screen">
<%-- <link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet"> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日日食便當網</title>
<style>
	.modal-dialog {
		width:600px;
	}
	.thumbnail {
		
		margin-bottom:6px;
	}
.footer {
 
  width: 100%;
  /* Set the fixed height of the footer here */
  height: 60px;
  text-align:center;
  margin-top:30px;
  margin-bottom:0;
  padding-top:5px;
  padding-bottom: 0;
}
</style>

</head>
<body style="background:#663333;">
<nav class="navbar navbar-default box" style="font-size: 25px; padding:10px 0 10px; background:#fffee5;">
  <div class="container" >
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header" >
      <img style="padding-top:7px;" src="<%=request.getContextPath()%>/bootstrap/img/0008.gif">
      <a class="navbar-brand" style="font-size: 25px;" href="<%=request.getContextPath()%>/index.jsp"><strong>日日食便當網</strong></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
<!--       <ul class="nav navbar-nav"> -->
<!--         <li><a href="#">Link <span class="sr-only">(current)</span></a></li> -->
<!--         <li><a href="#">Link</a></li> -->
<!--       </ul> -->
      <c:if test="${memberVO==null&&ownerVO==null}">
      	<ul class="nav navbar-nav navbar-right">
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/generalService/advertisement.jsp">廣告總表</a></li>
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/generalService/memberLogin.jsp">會員登入</a></li>
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/generalService/ownerLogin.jsp">業主登入</a></li>
      	</ul>
      </c:if>
      <c:if test="${memberVO!=null}">
      	<ul class="nav navbar-nav navbar-right">
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/generalService/advertisement.jsp">廣告總表</a></li>
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/member/memberindex.jsp">會員專區</a></li>
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/generalService/logout.jsp">登出</a></li>
      	</ul>
      </c:if>
      <c:if test="${ownerVO!=null}">
      	<ul class="nav navbar-nav navbar-right">
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/generalService/advertisement.jsp">廣告總表</a></li>
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/owner/owner.jsp">業主專區</a></li>
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/generalService/logout.jsp">登出</a></li>
      	</ul>
      </c:if>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>



<div class="container" style="min-height: 680px; font-family:微軟正黑體; ">
  <div class="row">
    <h1 style="text-align:center; font-family:微軟正黑體; color:#FFF">廣告總表</h1>
    <div class="row">
      <c:forEach var="adVO" items="${adlist}" >
     	 <div class="col-lg-3 col-sm-4 col-6"  style="text-align:center; margin:0 auto;"><a style="display: inline-block;" href="#" title="${adVO.restaurantVO.restName}"><img  src='<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() %>/getImage?TreatID=2&RestID=${adVO.restID}' class="thumbnail img-responsive"></a>
     	  	 <p style="color:#A9A9A9; margin:0">${adVO.restaurantVO.restName}</p>
     	 	 <table style="margin:0 auto; text-align:left;color:#A9A9A9">
     	 		<tr>
     	 			<td>${adVO.context}</td>
     	 		</tr>
     	 		<tr>
     	 			<td>優惠至:${adVO.endDate}</td>
     	 		</tr>
     		 </table>
     	
     	 </div>																											
      </c:forEach>
     
    </div>

  </div>
  
</div>
 <div class="footer box" style="background:#fffee5;">
      <div class="container">
        <a href="https://www.facebook.com/groups/1475667996043676/">
      		<img src="<%=request.getContextPath()%>/Image/fb17.png" style="width:30px;height:30px;">
      	</a>
      	<a href="mailto:eeit76no1@gmail.com">
      		<img src="<%=request.getContextPath()%>/Image/email-icon.png" style="width:30px;height:30px;">
      	</a>	
        <p class="text-muted">Copyright &copy; 日日食便當網 2015</p>
      </div>
    </div>
<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3 class="modal-title" style="font-weight:bold;">Heading</h3>
	</div>
	<div class="modal-body" style="text-align:center;">
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
	var ID = temp.substring(71, 75);
	
  	$('.modal-body').empty();
  	var title = $(this).parent('a').attr("title");
  	
  	$(this).parent('a').attr("href","<%=request.getContextPath()%>/generalService/restaurant.jsp?restID="+ID);
  	event.preventDefault();
  	$('.modal-title').html(title);
  	$($(this).parents('div').html()).appendTo('.modal-body');
  	$('#myModal').modal({show:true});
});
</script>
</body>
</html>