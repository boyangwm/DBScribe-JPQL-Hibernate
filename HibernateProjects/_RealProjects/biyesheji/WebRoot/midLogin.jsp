<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html> 
	<head>
		<title>loginFrame</title>
	</head>
	<body>
	
	 <center><h1><font size="7" color="#000000">基于Web的教学管理系统

</font></h1></center>
	 <br> <br> <br> 
		<table align="center">
		
		<html:form action="/login" method="post">   
		    <tr><td>&nbsp;&nbsp;用户名: <html:text property="id"/><html:errors 

property="id"/></td>
		   <td>&nbsp;&nbsp;密码: <html:password property="password"/><html:errors 

property="password"/>&nbsp;&nbsp;</td>
		    </tr>
		    <tr><td>&nbsp;&nbsp;&nbsp;
			    <input type="radio" name="type" value="student">学生
			    <input type="radio" name="type" value="teacher">教师
			    <input type="radio" name="type" value="manager">管理员
			    </td>		
			

<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:submit value="登

陆"/>&nbsp;&nbsp;<html:cancel value="取消"/></td>
			</tr>
		</html:form>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
	    </table>
	</body>
</html>

