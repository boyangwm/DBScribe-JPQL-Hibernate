<%@ page language="java" import="com.jspdev.biyesheji.Student" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<jsp:useBean id="student" class="com.jspdev.biyesheji.Student" scope="session"/>
<%student=(Student)session.getAttribute("student"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>studentFunction.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    
    <style type="text/css"> 
　　 <!-- 
　　 a:link { text-decoration: none;} 
　　 a:active { text-decoration:blink} 
　　 a:hover { text-decoration:underline;}  
　　 a:visited { text-decoration: none;} 
　　 -－>  
　　 </style>

  </head>
  
  <body bgcolor="#f1fcfe">
  <center>
    <font size="5"><br><br>
       <a href='choseCourse.jsp' target="right">网上选课</a><br><br>
       <a href='viewStuInfo.jsp' target="right">个人信息</a><br><br>
       <a href='changePassword.jsp?stuId=<%=student.getId() %>' target="right">密码修改</a><br><br>
       <a href='viewGrade.jsp' target="right">成绩查询</a><br><br>
       <a href='viewNotes.jsp' target="right">查看公告</a><br><br>
       <a href='addMessage.jsp?name=<%=student.getName() %>' target="right">留言板</a><br><br>
       <a href='../login.jsp' target="_parent">退出系统</a><br>
    </font>
  </center>  
  </body>
</html:html>
