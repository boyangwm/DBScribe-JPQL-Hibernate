<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<jsp:useBean id="student" class="com.jspdev.biyesheji.Student" scope="session"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>viewStuInfo.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body bgcolor="#f1fcfe"><br>
  <center><font size="6" color="#400000">学生个人信息</font></center><br><br>
    <table align="center" border="1" width="35%">
      <tr>
        <td align="center" bgcolor="#ddf5cd">学号:</td>
        <td><%=student.getId() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">姓名:</td>
        <td><%=student.getName() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">性别:</td>
        <td><%=student.getSex() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">年龄:</td>
        <td><%=student.getAge() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">学院:</td>
        <td><%=student.getCollege() %>
      </td> 
      <tr>
        <td align="center" bgcolor="#ddf5cd">专业:</td>
        <td><%=student.getDepartment() %>
      </td> 
      <tr>
        <td align="center" bgcolor="#ddf5cd">联系地址:</td>
        <td><%=student.getAddress() %>
      </td> 
      <tr>
        <td align="center" bgcolor="#ddf5cd">联系电话:</td>
        <td><%=student.getPhone() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">入学时间:</td>
        <td><%=student.getInTime() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">毕业时间:</td>
        <td><%=student.getOutTime() %>
      </td>     
    </table><br>
    <center><a href="changeStuInfo.jsp?id=<%=student.getId() %>">个人信息修改</a></center>
  </body>
</html:html>
