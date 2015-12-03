<%@ page language="java" import="com.jspdev.biyesheji.Student" pageEncoding="gb2312"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<jsp:useBean id="student" class="com.jspdev.biyesheji.Student" scope="session"/>
<jsp:useBean id="sc" class="com.jspdev.biyesheji.Sc" scope="session"/>
<%student=(Student)session.getAttribute("student");String sno=student.getId(); %>
<c:set var="scs" value="<%=sc.getScs(sno) %>"/>

 
<html> 
	<head>
		<title>JSP for CancelScForm form</title>
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
	<center><font size="6">取消选修课报名</font></center><br>
	<html:form action="/cancelSc" method="post">
	<input type="hidden" name="sno" value="<%=student.getId() %>">
	<table align="center" border="1" width="90%">
      <tr>
        <td align="center" bgcolor="#ddf5cd">选课</td>
        <td align="center" bgcolor="#ddf5cd">课程名称</td>
        <td align="center" bgcolor="#ddf5cd">课程代码</td>
        <td align="center" bgcolor="#ddf5cd">授课老师</td>
        <td align="center" bgcolor="#ddf5cd">课程学分</td>
        <td align="center" bgcolor="#ddf5cd">课程类型</td>
        <td align="center" bgcolor="#ddf5cd">上课时间</td>
        <td align="center" bgcolor="#ddf5cd">上课地点</td>
      </tr>     
      <logic:iterate id="cour" name="scs" >
      <tr>
        <td align="center"><input type="checkbox" name="cno" value="<bean:write name="cour" property="cno"/>"></td>
        <td align="center"><bean:write name="cour" property="name"/></td>
        <td align="center"><bean:write name="cour" property="cno"/></td>
        <td align="center"><bean:write name="cour" property="teacher"/></td>
        <td align="center"><bean:write name="cour" property="credit"/></td>
        <td align="center"><bean:write name="cour" property="type"/></td>
        <td align="center"><bean:write name="cour" property="time"/></td>
        <td align="center"><bean:write name="cour" property="address"/></td>        
      </tr>           
      </logic:iterate> 
    </table><br>
    <center>  <input type="submit" value="退选">&nbsp;&nbsp;<input type="reset" value="取消"></center>
	</html:form>
	</body>
</html>

