<%@ page language="java" import="com.jspdev.biyesheji.Student" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<jsp:useBean id="student" class="com.jspdev.biyesheji.Student" scope="session"/>
<%student=(Student)session.getAttribute("student");String sno=student.getId(); %>
 
<html:html lang="true"> 
	<head>
	<html:base />
		<title>JSP for GradeQueryForm form</title>
	</head>
	<body bgcolor="#f1fcfe"><br>
	<center>
		<html:form action="/gradeQuery" target="bot" method="post"> 
		<html:hidden property="sno" value="<%=sno %>"/><br/>
			学年 : <html:select property="year">
			         <html:option value="2000~2001">2000~2001</html:option>
			         <html:option value="2001~2002">2001~2002</html:option>
			         <html:option value="2002~2003">2002~2003</html:option>
			         <html:option value="2003~2004">2003~2004</html:option>
			         <html:option value="2004~2005">2004~2005</html:option>
			         <html:option value="2005~2006">2005~2006</html:option>
			         <html:option value="2006~2007">2006~2007</html:option>
			         <html:option value="2007~2008">2007~2008</html:option>
			         <html:option value="2008~2009">2008~2009</html:option>
			         <html:option value="2009~2010">2009~2010</html:option>
			      </html:select>
			      
			学期 : <html:select property="term">
	                 <html:option value="1">1</html:option>	
	                 <html:option value="2">2</html:option>
	              </html:select>&nbsp;   	
			<html:submit property="method" style="cursor:hand;height:25;width:70;"><bean:message key="button.termGrade"/></html:submit>&nbsp;
			<html:submit property="method" style="cursor:hand;height:25;width:70;"><bean:message key="button.yearGrade"/></html:submit>&nbsp;
			<html:submit property="method" style="cursor:hand;height:25;width:70;"><bean:message key="button.allGrade"/></html:submit>
		</html:form>
		</center>
	</body>
</html:html>

