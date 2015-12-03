<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>teachersQuery.jsp</title>

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
	<center>
		<html:form action="/teachersQuery" target="bot" method="post">
		     职工号:<input type="text" name="id" size="20">
              部门:<select name="department">
                    <option value="" selected>-按部门查询-</option>
                    <option value="数学与计算机科学学院">数学与计算机科学学院</option>
                    <option value="物理与光电学院">物理与光电学院</option>
                    <option value="外语学院">外语学院</option>
                 </select>
              职称:<select name="post">
                    <option value="" selected>-按职称查询-</option>
                    <option value="教授">教授</option>
                    <option value="副教授">副教授</option>
                    <option value="讲师">讲师</option>
                    <option value="助教">助教</option>
                 </select>
              年龄:<select name="age">
                    <option value="" selected>-按年龄查询-</option>
                    <option value=30>30-39</option>
                    <option value=40>40-49</option>
                    <option value=50>50-59</option>
                 </select>
        <input type="submit" value="查询">
		</html:form>
	</center>	
	</body>
</html:html>
