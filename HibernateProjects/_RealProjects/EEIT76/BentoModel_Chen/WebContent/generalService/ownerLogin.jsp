<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.randompwd.model.*" %>
<%@ page import="com.owner.model.*" %>
<html>
<%	
	RandomPwdVO randompwdvo = new RandomPwdVO();	
	RandomPwdSercvice randompwSvc = new RandomPwdSercvice();
	OwnerVO openowner = new OwnerVO();
	OwnerService ownerSvc = new OwnerService();
	String q = request.getParameter("q") ;
	if(q != null){
		randompwdvo = randompwSvc.SelectOne(q);
		String Acc = randompwdvo.getAcc();
		openowner = ownerSvc.getByOwnAcc(Acc);
		ownerSvc.updateOpen(openowner);
		request.setAttribute("openowner", openowner);
	}
 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日日食便當網</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>

	<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<style>
		.jumbotron{
			font-family:微軟正黑體;
			text-align:center;
		}
		
/* 		表格STYLE */


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
	font-size: 30px;
}
/* 表格STYLE */
		
	</style>
</head>
<body style="background:#333">
<!-- 一般會員登入 -->
      <form action="/BentoDelivery/OwnerLoginServlet" method="POST" class="form-signin"  style=" font-family:微軟正黑體; overflow:hidden; margin:100px auto;">
         <h2 class="form-signin-heading" style="color:white;">業主登入${LoginError}</h2>
         <input  name="userId" class="form-control" placeholder="請輸入帳號" required autofocus>
         <input name="pswd" type="password" class="form-control" placeholder="請輸入密碼" required>
         <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
         
      </form>

</body>
</html>