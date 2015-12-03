<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<jsp:useBean id="student" class="com.jspdev.biyesheji.Student" scope="session"/>
<jsp:useBean id="course" class="com.jspdev.biyesheji.Course" scope="session"/>
<c:set var="courses" value="<%=course.getChoseCourse() %>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>choseCourse.jsp.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
  <center><font size="6">选修课报名</font></center><br>
    <html:form action="/sc" method="post"> 
    <input type="hidden" name="sno" value=<%=student.getId() %> > 
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
      <logic:iterate id="cour" name="courses" >
      <tr>
        <td align="center"><input type="checkbox" name="cno" value="<bean:write name="cour" property="id"/>"></td>
        <td align="center"><bean:write name="cour" property="name"/></td>
        <td align="center"><bean:write name="cour" property="id"/></td>
        <td align="center"><bean:write name="cour" property="teacher"/></td>
        <td align="center"><bean:write name="cour" property="credit"/></td>
        <td align="center"><bean:write name="cour" property="type"/></td>
        <td align="center"><bean:write name="cour" property="time"/></td>
        <td align="center"><bean:write name="cour" property="address"/></td>        
      </tr> 
      </logic:iterate> 
    </table><br>
    <center>
      <input type="submit" value="提交" style="cursor:hand;height:25;width:70;">
      &nbsp;&nbsp;&nbsp;<a href="cancelSc.jsp">取消选课</a>&nbsp;&nbsp;
      <input type="reset" value="取消" style="cursor:hand;height:25;width:70;">
    </center>                
    </html:form>
  </body>
</html:html>
