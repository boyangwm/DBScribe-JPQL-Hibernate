<%@ page language="java" import="com.jspdev.biyesheji.Teacher" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%Teacher teacher=(Teacher)session.getAttribute("teacher"); %> 
<c:set var="courses" value="<%=teacher.getTeaCourses(teacher.getName()) %>"/>

<html:html> 
	<head>
		<title>JSP for GradeForm form</title>
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
		<html:form action="/grade" method="post">
		<center><font size="6">学生成绩录入</font></center><br>
		<table align="center" border="1">
		  <tr>
		    <td align="center" bgcolor="#ddf5cd">学生学号</td>
		    <td><html:text property="snumber" size="29"/><html:errors property="snumber"/></td>
		  </tr>
		  <tr>
		    <td align="center" bgcolor="#ddf5cd">课程名称</td>
		    <td>
		      <html:select property="cnumber">
		        <html:options collection="courses" property="id" labelProperty="name"/>
		      </html:select>
		    </td>
		  </tr>
		  <tr>
		    <td align="center" bgcolor="#ddf5cd">成绩</td>
		    <td><html:text property="grade" size="29"/><html:errors property="grade"/></td>
		  </tr>  
		   <tr>
		    <td align="center" bgcolor="#ddf5cd">学年</td>
		    <td>
		      <select name="year">
		         <option value="2000~2001">2000~2001</option>
		         <option value="2001~2002">2001~2002</option>
		         <option value="2002~2003">2002~2003</option>
		         <option value="2003~2004">2003~2004</option>
		         <option value="2004~2005">2004~2005</option>
		         <option value="2005~2006">2005~2006</option>
		         <option value="2006~2007">2006~2007</option>
		         <option value="2007~2008">2007~2008</option>
		         <option value="2008~2009">2008~2009</option>
		         <option value="2009~2010">2009~2010</option>
		      </select>
		    </td>
		  </tr> 
		  <tr>
		    <td align="center" bgcolor="#ddf5cd">学期</td>
		    <td>
		      <select name="term">
		         <option value="1">1</option>
		         <option value="2">2</option>
		      </select>
		    </td>      
		  </tr> 
		  <tr>
		    <td align="center" bgcolor="#ddf5cd">是否重修</td>
		    <td>
		      是<input type="radio" name="chongXiu" value="true">&nbsp;&nbsp;
		      否<input type="radio" name="chongXiu" value="false">
		    </td>
		  </tr>           
		</table><br>	
		<center><input type="submit" value="添加" onclick="openwindow()">&nbsp;&nbsp;&nbsp;<input type="reset" value="取消"></center>
		</html:form>
	</body>
</html:html>
