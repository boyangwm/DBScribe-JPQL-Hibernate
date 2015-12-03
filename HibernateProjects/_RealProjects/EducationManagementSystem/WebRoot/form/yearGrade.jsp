<%@ page language="java" import="java.util.List" pageEncoding="gb2312"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="yearGrades" value="<%=session.getAttribute("yearGrade") %>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>yearGrade.jsp</title>

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
    <table align="center" border="1" width="80%">
      <tr>
        <td align="center" bgcolor="#ddf5cd">学年</td>
        <td align="center" bgcolor="#ddf5cd">学期</td>
        <td align="center" bgcolor="#ddf5cd">课程代码</td>
        <td align="center" bgcolor="#ddf5cd">课程名称</td>
        <td align="center" bgcolor="#ddf5cd">课程性质</td>
        <td align="center" bgcolor="#ddf5cd">学分</td>
        <td align="center" bgcolor="#ddf5cd">成绩</td>
      </tr>          
       <%List list=(List)session.getAttribute("yearGrade");
        for(int j=0;j<list.size();j++){%>
        <tr>
           <%Object[] object=(Object[])list.get(j);
    	   for(int i=0;i<object.length;i++){
    		%><td align="center"><%= object[i].toString()%></td>
       <% }%></tr>
       <%  }
    	%>
    </table>
  </body>
</html:html>
