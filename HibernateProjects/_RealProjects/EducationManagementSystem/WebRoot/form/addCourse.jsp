<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for CourseForm form</title>
	
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
	  <center><font size="6">课程录入</font></center>
		<html:form action="/course" method="post">
		<table align="center" border="1">
		  <tr>
		    <td bgcolor="#ddf5cd">课程代码:</td>
			<td><html:text property="id"/><html:errors property="id"/></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">课程名称:</td>
			<td><html:text property="name"/><html:errors property="name"/></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">课程学分:</td>
			<td><html:text property="credit"/><html:errors property="credit"/></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">课程类型:</td>
			<td><html:text property="type"/><html:errors property="type"/></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">上课时间:</td>
			<td><html:text property="time"/><html:errors property="time"/></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">上课地点:</td>
			<td><html:text property="address"/><html:errors property="address"/></td>
		  </tr>
		  <tr>
		    <td bgcolor="#ddf5cd">授课教师:</td>
			<td><html:text property="teacher"/><html:errors property="teacher"/></td>
		  </tr>
		</table><br>	
			<center><input type="submit" value="添加" style="cursor:hand;height:25;width:70;" onclick="openwindow()">
			&nbsp;&nbsp;&nbsp;<input type="reset" value="取消" style="cursor:hand;height:25;width:70;">
			</center>
		</html:form>
	</body>
</html>

