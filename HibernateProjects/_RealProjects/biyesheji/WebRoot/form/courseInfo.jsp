<%@ page language="java" import="com.jspdev.biyesheji.Course" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%String id=(String)request.getParameter("id"); %>
<%Course course=Course.getCourse(id); %>
<%boolean tag=true; %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="true">
  <head>
    <html:base />
    
    <title>courseInfo.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script language="javaScript">
    function openwindow(){ 
         window.showModalDialog("../box/changeSuccess.jsp",window,"status:no;scroll:no;dialogWidth:235px;dialogHeight:100px");
    }             
    
    function opensub(){
      document.form1.submit();
    }   
    </script>
  </head>
  
  <body bgcolor="#f1fcfe"><br>
    <center><font size="6">课程信息修改</font></center>
		<form action="/biyesheji/servlet/courseInfoServlet" method="post">
		<table align="center" border="1">
		  <tr>
		    <td bgcolor="#ddf5cd">课程代码:<br></td>
			<td><input type="text" name="id" value="<%=course.getId() %>" readonly="<%=tag %>"></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">课程名称:<br></td>
			<td><input type="text" name="name" value="<%=course.getName() %>"></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">课程学分:<br></td>
			<td><input type="text" name="credit" value="<%=course.getCredit() %>"></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">课程类型:<br></td>
			<td><input type="text" name="type" value="<%=course.getType() %>"></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">授课教师:<br></td>
			<td><input type="text" name="teacher" value="<%=course.getTeacher() %>"></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">上课时间:<br></td>
			<td><input type="text" name="time" value="<%=course.getTime() %>"></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">上课地点:<br></td>
			<td><input type="text" name="address" value="<%=course.getAddress() %>"></td>
		  </tr>
		</table><br>
		<input type="hidden" name="cno" value="<%=id %>">		    
			<center><input type="submit" value="修改" style="cursor:hand;height:25;width:70;" onclick="openwindow()">&nbsp;&nbsp;&nbsp;<input type="reset" value="取消" style="cursor:hand;height:25;width:70;"></center>
		</form>
  </body>
</html>
