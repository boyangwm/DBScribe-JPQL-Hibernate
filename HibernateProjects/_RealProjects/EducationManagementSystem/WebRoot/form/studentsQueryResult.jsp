<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="students" value="<%=session.getAttribute("students") %>"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>studentsQueryResult.jsp</title>

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
         window.showModalDialog("../biyesheji/box/deleteSuccess.jsp",window,"status:no;scroll:no;dialogWidth:235px;dialogHeight:100px");
    }             
    
    function opensub(){
      document.form1.submit();
    }   
    </script>
  </head>
  
  <body bgcolor="#f1fcfe"><br>
    <html:form action="/deleteStudents" method="post">
    <table align="center" border="1" width="85%">
      <tr align="center">
        <td align="center" bgcolor="#ddf5cd">选择</td>
        <td align="center" bgcolor="#ddf5cd">学号</td>
        <td align="center" bgcolor="#ddf5cd">姓名</td>
        <td align="center" bgcolor="#ddf5cd">性别</td>
        <td align="center" bgcolor="#ddf5cd">年龄</td>
        <td align="center" bgcolor="#ddf5cd">专业</td>
        <td align="center" bgcolor="#ddf5cd">学院</td>
        <td align="center" bgcolor="#ddf5cd">入学时间</td>
      </tr>          
      <logic:iterate id="student" name="students">
      <tr align="center">
        <td><input type="checkbox" name="id" value="<bean:write name="student" property="id"/>"></td>
        <td><bean:write name="student" property="id"/></td>
        <td><bean:write name="student" property="name"/></td>
        <td><bean:write name="student" property="sex"/></td>
        <td><bean:write name="student" property="age"/></td>
        <td><bean:write name="student" property="department"/></td>
        <td><bean:write name="student" property="college"/></td>
        <td><bean:write name="student" property="inTime"/></td>
      </tr>
      </logic:iterate>
    </table><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" value="删除" style="cursor:hand;height:25;width:50;" onclick="openwindow()">
    <input type="reset" value="取消" style="cursor:hand;height:25;width:50;">
    </html:form> 
  </body>
</html:html>
