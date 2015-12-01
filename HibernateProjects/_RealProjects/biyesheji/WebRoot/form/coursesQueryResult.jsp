<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="courses" value="<%=session.getAttribute("courses") %>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>coursesQueryResult.jsp</title>

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
    <html:form action="/deleteCourses" method="post">
    <table align="center" border="1" width="95%">
      <tr align="center">
        <td align="center" bgcolor="#ddf5cd">选择</td>
        <td align="center" bgcolor="#ddf5cd">课程代码</td>
        <td align="center" bgcolor="#ddf5cd">名称</td>
        <td align="center" bgcolor="#ddf5cd">学分</td>
        <td align="center" bgcolor="#ddf5cd">类型</td>
        <td align="center" bgcolor="#ddf5cd">授课老师</td>
        <td align="center" bgcolor="#ddf5cd">上课时间</td>
        <td align="center" bgcolor="#ddf5cd">上课地点</td>
        <td align="center" bgcolor="#ddf5cd">修改课程信息</td>
      </tr>          
      <logic:iterate id="course" name="courses">
      <tr align="center">
        <td><input type="checkbox" name="id" value="<bean:write name="course" property="id"/>"></td>
        <td><bean:write name="course" property="id"/></td>
        <td><bean:write name="course" property="name"/></td>
        <td><bean:write name="course" property="credit"/></td>
        <td><bean:write name="course" property="type"/></td>
        <td><bean:write name="course" property="teacher"/></td>
        <td><bean:write name="course" property="time"/></td>
        <td><bean:write name="course" property="address"/></td>
        <td><a href="courseInfo.jsp?id=<bean:write name="course" property="id"/>">修改</a></td>
      </tr>
      </logic:iterate>
    </table><br>&nbsp;&nbsp;
    <input type="submit" value="删除" style="cursor:hand;height:25;width:50;" onclick="openwindow()">
    <input type="reset" value="取消" style="cursor:hand;height:25;width:50;">
    </html:form> 
  </body>
</html:html>
