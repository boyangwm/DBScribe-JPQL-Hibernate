<%@ page language="java" pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html> 
	<head>
		<title>JSP for MessageForm form</title>
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
	 <center><font size="6" color="#400000">留言板</font></center><br><br>
		<html:form action="/message" method="post">
		<center>
		<table align="center" border="1">
		  <tr>
		    <td bgcolor="#ddf5cd">姓名  :</td>
		    <td><html:text property="name" size="35"/><html:errors property="name"/></td>
		  </tr>  
		  <tr>
		   <td bgcolor="#ddf5cd">Email:</td>
		   <td> <html:text property="email" size="35"/><html:errors property="email"/></td>
		  </tr> 
		  <tr>
		   <td bgcolor="#ddf5cd">主题  :</td>
		   <td><html:text property="title" size="35"/><html:errors property="title"/></td>
		  </tr>		  
		  <tr>
		   <td bgcolor="#ddf5cd">内容  :</td>
		   <td><html:textarea property="content" rows="8" cols="34"/><html:errors property="content"/></td>
		  </tr>
		</table><br>
		<input type="hidden" name="time" value=<%=new java.util.Date().toLocaleString() %>> 		  		
		<input type="submit" value="提交留言"  onclick="openwindow()">
		<a href="../../biyesheji/form/viewMessage.jsp">查看留言</a>
		<input type="reset" value="重新填写">
	    </center>	
		</html:form>
	</body>
</html>

