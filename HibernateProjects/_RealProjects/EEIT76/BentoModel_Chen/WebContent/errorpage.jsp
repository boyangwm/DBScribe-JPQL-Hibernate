<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>日日食便當網</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/style.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/scripts.js"></script>
<script src="bootstrap/js/jquery.modal.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="Slick/slick.css"/>
<style>
	.jumbotron{
		font-family:微軟正黑體;
		text-align:center;
	}
</style>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<div class="container ">
<div class="row clearfix">
	<div class="col-md-12 column">
		<div class="jumbotron" style="background-color:#FFFEC0">
			<img src="Image/errorSun.gif">
			<h1 style="font-family:微軟正黑體">系統繁忙中,請稍候再試...</h1>
			<a href="<%=request.getContextPath()%>/index.jsp">回到首頁</a>
		</div>
	    <div></div>
		</div>
	</div>
</div>
<jsp:include page="/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="Slick/slick.js"></script>
</html>