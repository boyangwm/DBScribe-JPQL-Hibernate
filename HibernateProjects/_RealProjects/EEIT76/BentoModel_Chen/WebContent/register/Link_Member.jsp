<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.randompwd.model.*" %>
<%@ page import="com.member.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
RandomPwdVO randompwdvo = new RandomPwdVO();
MemberVO membervo = new MemberVO();
MemberService memberSvc = new MemberService();
RandomPwdSercvice randompwSvc = new RandomPwdSercvice();
%>
<title>Insert title here</title>
</head>
<body>

<%
	String q = request.getParameter("q") ; 
	request.setAttribute("q1", q);
	randompwdvo = randompwSvc.SelectOne(q);
	String Acc = randompwdvo.getAcc();
	membervo = memberSvc.getByMemAcc(Acc);
	memberSvc.updateOpen(membervo);
%>
<h3>感謝您的使用2</h3>
${q1}  ,  
</body>
</html>