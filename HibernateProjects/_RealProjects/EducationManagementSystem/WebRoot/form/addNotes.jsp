<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteForm form</title>
	</head>
	<body bgcolor="#f1fcfe"><br>
	    <center><font size="6">发布新公告</font></center>
		<html:form action="/note" method="post">
		<table align="center" border="1">
		   <tr>
		      <td bgcolor="#ddf5cd">&nbsp;&nbsp;内容 :&nbsp;&nbsp;</td>
		      <td><html:textarea property="content" rows="10" cols="50"/><html:errors property="content"/></td>
		   </tr>
		</table> <br>     
		<center><input type="submit" value="发布">&nbsp;&nbsp;&nbsp;<input type="reset" value="取消"></center>
		</html:form>
	</body>
</html>

