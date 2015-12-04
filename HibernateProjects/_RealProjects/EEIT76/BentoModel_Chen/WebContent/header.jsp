<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <title>header</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
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
	
	<style>
		* {
			font-family:微軟正黑體;
			text-align:center;
		}
		
		
		
	</style>
</head>

<body>
<!-- style="background:#FF6B20" -->
<nav class="navbar navbar-default box" style="font-size: 25px; padding:10px 0 10px; ">
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


</body>
</html>