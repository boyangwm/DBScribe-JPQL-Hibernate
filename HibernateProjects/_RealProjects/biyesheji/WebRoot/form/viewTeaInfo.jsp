<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<jsp:useBean id="teacher" class="com.jspdev.biyesheji.Teacher" scope="session"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>viewTeaInfo.jsp</title>

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
  <center><font size="6" color="#400000">教师个人信息</font></center><br><br>
    <table align="center" border="1" width="35%" >
      <tr>
        <td align="center" bgcolor="#ddf5cd">职工号:</td>
        <td><%=teacher.getId() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">姓名:</td>
        <td><%=teacher.getName() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">性别:</td>
        <td><%=teacher.getSex() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">年龄:</td>
        <td><%=teacher.getAge() %>
      </td>  
      <tr>
        <td align="center" bgcolor="#ddf5cd">部门:</td>
        <td><%=teacher.getDepartment() %>
      </td> 
      <tr>
        <td align="center" bgcolor="#ddf5cd">职位:</td>
        <td><%=teacher.getPost() %>
      </td> 
      <tr>
        <td align="center" bgcolor="#ddf5cd">备注:</td>
        <td><%=teacher.getRemark() %></td>  
    </table><br>
    <center><a href="changeTeaInfo.jsp?id=<%=teacher.getId() %>">个人信息修改</a></center>
  </body>
</html:html>
