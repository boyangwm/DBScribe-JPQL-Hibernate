<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>addTeacher.jsp</title>

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
         window.showModalDialog("../box/addSuccess.jsp",window,"status:no;scroll:no;dialogWidth:235px;dialogHeight:100px");
    }             
    
    function opensub(){
      document.form1.submit();
    }   
    </script>
  </head>
  
  <body bgcolor="#f1fcfe"><br>
    <center><font size="6">添加教师</font></center>
    <form action="/biyesheji/servlet/addTeacherServlet" method="post">
    <table align="center" border="1">
      <tr align="center">
         <td bgcolor="#ddf5cd">职工号:</td>
         <td><input type="text" name="id" size="30"></td>
      </tr>
      <tr align="center">
         <td bgcolor="#ddf5cd">姓名:</td>
         <td><input type="text" name="name" size="30"></td>
      </tr>
      <tr align="center">
         <td bgcolor="#ddf5cd">性别:</td>
         <td><input type="text" name="sex" size="30"></td>
      </tr>
      <tr align="center">
         <td bgcolor="#ddf5cd">部门:</td>
         <td><input type="text" name="department" size="30"></td>
      </tr>
      <tr align="center">
         <td bgcolor="#ddf5cd">职位:</td>
         <td><input type="text" name="post" size="30"></td>
      </tr>
      <tr align="center">
         <td bgcolor="#ddf5cd">备注:</td>
         <td><textarea name="remark" rows="5" cols="29"></textarea></td>
      </tr>
    </table><br>
    <center><input type="submit" value="添加" onclick="openwindow()">&nbsp;&nbsp;&nbsp;<input type="reset" value="取消"></center>
    </form>
  </body>
</html:html>
