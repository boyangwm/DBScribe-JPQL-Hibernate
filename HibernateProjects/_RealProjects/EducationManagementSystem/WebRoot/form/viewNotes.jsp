<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<jsp:useBean id="note" class="com.jspdev.biyesheji.Note" scope="session"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>viewNotes.jsp</title>

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
  <center><font size="6" color="#400000">近期公告</font></center><br><br>
   <table align="center">
     <tr>
       <td>内容:</td>
       <td>&nbsp;&nbsp;<%=note.getNote().getContent() %></td>
     </tr>
     <tr><td></td></tr>
     <tr><td></td></tr>
     <tr>
       <td>发布时间:</td>
       <td>&nbsp;&nbsp;<%=note.getNote().getTime() %></td>
     </tr>
   </table>    
  </body>
</html:html>
