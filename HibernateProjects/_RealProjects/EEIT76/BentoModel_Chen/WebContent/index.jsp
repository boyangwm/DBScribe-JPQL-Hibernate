<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ad.model.*"%>
<%@ page import="com.restaurant.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.randompwd.model.*" %>
<%@ page import="com.owner.model.*" %>
<%@ page import="com.member.model.*" %>

<html>
<%	RandomPwdVO randompwdvo = new RandomPwdVO();
	MemberVO openmember = new MemberVO();
	MemberService memberSvc = new MemberService();
	OwnerVO openowner = new OwnerVO();
	OwnerVO registerowner = (OwnerVO) request.getAttribute("registerowner");
	MemberVO registermember = (MemberVO) request.getAttribute("registermember");
	OwnerService ownerSvc = new OwnerService();
	RandomPwdSercvice randompwSvc = new RandomPwdSercvice();	%>
<%	String q = request.getParameter("q") ;
	String type = request.getParameter("type") ;
	if("owner".equals(type)){
		randompwdvo = randompwSvc.SelectOne(q);
		String Acc = randompwdvo.getAcc();
		openowner = ownerSvc.getByOwnAcc(Acc);
		ownerSvc.updateOpen(openowner);
		request.setAttribute("openowner", openowner);
	}
	if("member".equals(type)){
		randompwdvo = randompwSvc.SelectOne(q);
		String Acc = randompwdvo.getAcc();
		openmember = memberSvc.getByMemAcc(Acc);
		memberSvc.updateOpen(openmember);
		request.setAttribute("openmember", openmember);
	}    %>
<%	ADservice ADSvc = new ADservice();
	List<AdVO> adlist = ADSvc.getByTreatID(2);
	pageContext.setAttribute("adlist",adlist);  %>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>日日食便當網</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script src="Script/countyAreaSelector.js"></script>
	<link href="<%=request.getContextPath()%>/bootstrap/css/business-casual.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/bootstrap/css/style.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">
	
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
		
/* 		表格STYLE */
		a {
			font-family:微軟正黑體;
			color: #FFFFBB;
		}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
.errorMsg{
	color: #FF0000 ;
	font-size: 30px;
}
.inform{
	color:#FF5511 ;
	font-size: 25px;
}
.footer {
 
  width: 100%;
  /* Set the fixed height of the footer here */
  height: 60px;
/*   background-color: #f5f5f5; */
  text-align:center;
  margin:30px 30px 0 0;
}
/* 表格STYLE */
		
	</style>
</head>
<body>
<nav class="navbar navbar-default box" style="font-size: 25px; padding:10px 0 10px; ">
  <div class="container" >
	<ul class="nav navbar-nav navbar-left">
		 <img style="padding-top:7px;" src="<%=request.getContextPath()%>/bootstrap/img/0008.gif">
         <a class="navbar-brand" style="font-size: 30px;" href="<%=request.getContextPath()%>/index.jsp"><strong>日日食便當網</strong></a>
		<c:if test="${openowner.ownOpen == true || openmember.memberOpen == true }">
		<font class="inform" style="margin-left:10px;">歡迎您加入我們,請安心使用</font>
	</c:if>
	<c:if test="${registerowner.ownOpen == false || registermember.memberOpen == false}">
		<font class="inform" style="margin-left:10px;">歡迎您註冊，請至您的信箱開通帳號</font>
	</c:if>
	<c:if test="${memberVO.memberFirstName!=null}">
		<font class="inform" style="margin-left:10px;">歡迎您:${memberVO.memberFirstName}</font>
	</c:if>
	</ul>
<%-- 	<c:if test="${ownerVO.ownFirstName!=null}"> --%>
<%-- 		歡迎您:${ownerVO.ownFirstName} --%>
<%-- 	</c:if> --%>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
<!--       <ul class="nav navbar-nav"> -->
<!--         <li><a href="#">Link <span class="sr-only">(current)</span></a></li> -->
<!--         <li><a href="#">Link</a></li> -->
<!--       </ul> -->
      <c:if test="${memberVO==null&&ownerVO==null}">
      	<ul class="nav navbar-nav navbar-right">
      	  <li style="font-size:15px;"><a href="<%=request.getContextPath()%>/generalService/advertisement.jsp">廣告總表</a></li>
      	  <li style="font-size:15px;"><a href="#ex1" rel="modal:open">會員登入</a></li>
      	  <li style="font-size:15px;"><a href="#ex2" rel="modal:open">業主登入</a></li>
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
	
	<c:if test="${logoutMessage!=null}">
		<h3 style="text-align:center;">${logoutMessage}</h3>
		<%session.invalidate(); %>
	</c:if>
	<div class="container" style="font-family:微軟正黑體;">
		<div class="row">
            <div class="boxFocus">
                <div class="col-lg-12 text-center">
                    <h1 class="brand-name" style="font-family:微軟正黑體;">肚子餓了嗎?</h1>
                    <h2 class="brand-before" style="font-family:微軟正黑體;">
                        只要簡單幾個步驟，便當馬上送到你家!
                    </h2>
                    <hr class="tagline-divider">
                    <h3><form action="generalService/searchRestaurant.jsp" method="get">
						<select id="county" name="county" style="font-family:微軟正黑體;">
							<option>請選擇</option>
					</select> <select id="area" name="area" style="font-family:微軟正黑體;">
						<option>請選擇</option>
					</select> <input type="submit" class="btn btn-warning btn-large" value="我要訂便當!" style="margin-bottom:6px">
					<input type="hidden" name="action" value="getRest_By_CityArea">
					</form></h3>
					<c:if test="${!errorMsgs.isEmpty()}">
			<font class="errorMsg">${errorMsgs.OwnAcc}</font>
		</c:if>
		<c:if test="${!errorMsgs.isEmpty()}">
			<font class="errorMsg">${errorMsgs.MemberAcc}</font>
		</c:if>
                </div>
            </div>
        </div>
	    <div class="row">
            <div class="box">
                <div class="col-lg-12 text-center">
                    <div class="slider autoplay">
	 	<c:forEach var="adVO" items="${adlist}" >
	 		<div><p style="color:#777;">${adVO.restaurantVO.restName}</p><a href="<%=request.getContextPath()%>/generalService/restaurant.jsp?restID=${adVO.restID}" title="${adVO.restaurantVO.restName}"><img style="display:inline-block;width:300px;height:240px;" src='<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() %>/getImage?TreatID=2&RestID=${adVO.restID}'></a></div>
	 	</c:forEach>
		
	</div>
                </div>
            </div>
        </div>
		</div>
	<div style="text-align:center ;">
		<h3><strong>
		<a href="generalService/advertisement.jsp">廣告總表</a>｜
		<c:if test="${ownerVO!=null}">
			<a href="owner/owner.jsp">業主專區</a>｜
		</c:if>
		 <c:if test="${memberVO!=null}">
			<a href="member/memberindex.jsp">會員專區</a>｜
		</c:if>
		<c:if test="${memberVO==null&&ownerVO==null}">
		    <a href="#registerM" rel="modal:open" >申請成為一般會員</a>｜
		    <a href="#ex1" rel="modal:open">會員登入</a>｜
			<a href="#registerO" rel="modal:open">申請成為業主</a>｜ 
			<a href="#ex2" rel="modal:open">業主登入</a>｜
		</c:if>
		
	</strong></h3>
		<a style="color:#663333;" accesskey="B" href="manager/manager.jsp">管理員專區｜</a><!-- 在這個標籤加 style="display: none"  跟 accesskey="X(任意英文)" 這樣在頁面上不會看到連結管理專區的超連結，但是按alt+ accesskey(前面設定的) 就會自動點選超連結連道管理專區~~ -->  
		
	</div>
	<div class="footer">
      <div class="container box" style="width:100%; margin-bottom:0; padding-top:5px; padding-bottom:0;">
        <a href="https://www.facebook.com/groups/1475667996043676/">
      		<img src="<%=request.getContextPath()%>/Image/fb17.png" style="width:30px;height:30px;">
      	</a>
      	<a href="mailto:eeit76@gmail.com">
      		<img src="<%=request.getContextPath()%>/Image/email-icon.png" style="width:30px;height:30px;">
      	</a>	
        <p class="text-muted">Copyright &copy; 日日食便當網 2015</p>
      </div>
    </div>
<!-- 業主註冊 -->
	<form  action="<%=request.getContextPath()%>/register.do" method="POST" class="form-signin login_form modal" role="form" id="registerO" style="display:none; overflow:hidden; margin:100px;">
    	<h2 class="form-signin-heading">請輸入資料</h2>
        <input name="OwnAcc" class="form-control" placeholder="請輸入帳號" required autofocus>
        <input name="OwnPwd" type="password" class="form-control" placeholder="請輸入密碼" required>
        <input name ="OwnEmail" class="form-control" placeholder="請輸入Email" required>
        <input name ="OwnLastName" class="form-control" placeholder="請輸入姓氏" required>
        <input name ="OwnFirstName" class="form-control" placeholder="請輸入姓名" required>
       	<div style="height:30px;background-color: #FFFFFF">性別:
       	<label class="radio-inline">
       		<input type="radio" name="gender" value="true" checked > 男
		</label>
		<label class="radio-inline">
			<input type="radio" name="gender" value="false" >女 
		</label >
		<input type="hidden" name="action" value="owner_addfirst">
		</div>
		<button style="background-color:#FF5511" class="btn btn-lg btn-primary btn-block" type="submit">送出</button>
	</form>
<!-- 會員註冊 -->
	<form action="<%=request.getContextPath()%>/member.do" method="POST" class="form-signin login_form modal" role="form" id="registerM" style="display:none; overflow:hidden; margin:100px;">
    	<h2 class="form-signin-heading">請輸入資料</h2>
        <input name="account" class="form-control" placeholder="請輸入帳號" required autofocus>
        <input name="pwd" type="password" class="form-control" placeholder="請輸入密碼" required>
        <input name ="lastname" class="form-control" placeholder="請輸入姓氏" required>
        <input name ="firstname" class="form-control" placeholder="請輸入姓名" required>
        <input name ="phone" class="form-control" placeholder="請輸入電話號碼" required>
        <input name ="cell" class="form-control" placeholder="請輸入手機號碼" required>
        <input name ="email" class="form-control" placeholder="請輸入Email" required>
       	<div style="height:30px;background-color: #FFFFFF">性別:
       	<label  class="radio-inline">
       		<input type="radio" name="gender" value="true" checked >男
		</label>
		<label  class="radio-inline">
			<input type="radio" name="gender" value="false" >女 
		</label>
		</div>
		<button style="background-color:#FF5511" class="btn btn-lg btn-primary btn-block" type="submit">送出</button>
	</form>
<!-- 一般會員登入 -->
      <form action="/BentoDelivery/MemberLoginloginServlet" method="POST" class="form-signin login_form modal" role="form" id="ex1" style="display:none; overflow:hidden; margin:100px;">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input  name="userId" class="form-control" placeholder="請輸入帳號" required autofocus>
        <input name="pswd" type="password" class="form-control" placeholder="請輸入密碼" required>
<!--         <div class="checkbox"> -->
<!--           <label> -->
<!--             <input type="checkbox" value="remember-me"> Remember me -->
<!--           </label> -->
<!--         </div> -->
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>

<!--    業主登入 -->
	  <form action="/BentoDelivery/OwnerLoginServlet" method="POST" class="form-signin login_form modal" role="form" id="ex2" style="display:none; overflow:hidden; margin:100px;">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input  name="userId" class="form-control" placeholder="請輸入業主帳號" required autofocus>
        <input name="pswd" type="password" class="form-control" placeholder="請輸入密碼" required>
<!--         <div class="checkbox"> -->
<!--           <label> -->
<!--             <input type="checkbox" value="remember-me"> Remember me -->
<!--           </label> -->
<!--         </div> -->
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
		
</div>


<script>
$(document).ready(function(){
	$('.autoplay').slick({
		  slidesToShow: 3,
	  	slidesToScroll: 1,
	  	autoplay: true,
	  	autoplaySpeed: 2000,
	});
})
</script>
</body>
<script type="text/javascript" src="Slick/slick.js"></script>
</html>
