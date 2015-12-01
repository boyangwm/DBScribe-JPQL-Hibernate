<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<jsp:useBean id="sc" class="com.jspdev.biyesheji.Sc" scope="session"/>

<%String cno=request.getParameter("cno"); %>

<c:set var="students" value="<%=sc.getStudents(cno) %>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>choseStudent.jsp</title>

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
         window.showModalDialog("../box/operateSuccess.jsp",window,"status:no;scroll:no;dialogWidth:235px;dialogHeight:100px");
    }             
    
    function opensub(){
      document.form1.submit();
    }   
    </script>
  </head>
  
  <body bgcolor="#f1fcfe"><br>
  <center><font size="6">确定选课学生</font></center>
    <form action="/biyesheji/servlet/f_tScServlet" method="post">
	<table align="center" border="1" width="90%">
      <tr align="center">
        <td bgcolor="#ddf5cd">选择学生</td>
        <td bgcolor="#ddf5cd">学号</td>
        <td bgcolor="#ddf5cd">姓名</td>
        <td bgcolor="#ddf5cd">学院</td>
        <td bgcolor="#ddf5cd">专业</td>
      </tr>  
         
      <logic:iterate id="stu" name="students" >
         
      <tr align="center">
        <td><input type="checkbox" name="sno" value="<bean:write name="stu" property="id"/>"></td>
        <td><bean:write name="stu" property="id"/></td>
        <td><bean:write name="stu" property="name"/></td>
        <td><bean:write name="stu" property="college"/></td>
        <td><bean:write name="stu" property="department"/></td>
      </tr>           
      </logic:iterate> 
    </table><br>
    <center>
      <input type="hidden" name="cno" value="<%=cno %>">
      <input type="submit" value="选择" style="cursor:hand;height:25;width:70;" onclick="openwindow()">
      &nbsp;&nbsp;<a href="viewSelectedStu.jsp?cno1=<%=cno %>">查看已入选学生</a>&nbsp;&nbsp;
      <input type="reset" value="取消" style="cursor:hand;height:25;width:70;">
	</center>
	</form>
  </body>
</html:html>
