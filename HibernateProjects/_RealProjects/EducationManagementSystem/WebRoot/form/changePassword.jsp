<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html  lang="true">
	<head>
	<html:base />
		<title>JSP for PasswordForm form</title>
		<meta http-equiv="pragma" content="no-cache">
	    <meta http-equiv="cache-control" content="no-cache">
	    <meta http-equiv="expires" content="0">    
	    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	    <meta http-equiv="description" content="This is my page">
    <script language="javaScript">
    function openwindow(){ 
         window.showModalDialog("../biyesheji/box/changeSuccess.jsp",window,"status:no;scroll:no;dialogWidth:235px;dialogHeight:100px");
    }             
    
    function opensub(){
      document.form1.submit();
    }   
    </script>	
	</head>
	<body bgcolor="#f1fcfe"><br>
	<center><font size="6" color="#400000">密码修改</font></center><br><br>
		<html:form action="/password">
		 <input type="hidden" name="stuId" value="<%=request.getParameter("stuId")%>">
		<input type="hidden" name="teaId" value="<%=request.getParameter("teaId")%>">
		<table align="center" border="1">
		  <tr>
		    <td bgcolor="#ddf5cd">输入新密码:</td>
		    <td><input type="password" name="password1"><font color="#ff0000"><html:errors property="password1"/></font></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">确认新密码:</td>
		    <td><input type="password" name="password2"><%if("true".equals(request.getAttribute("flag"))){
         %><font color="#ff0000">*两次输入密码不一致</font> <%} %></td>
		  </tr>
		</table> <br>
		<center>
		        <input type="submit" value="提交" style="cursor:hand;height:25;width:70;">
			    &nbsp;&nbsp;&nbsp;<input type="reset" value="取消" style="cursor:hand;height:25;width:70;">
	    </center>
		</html:form>
	</body>
</html:html>