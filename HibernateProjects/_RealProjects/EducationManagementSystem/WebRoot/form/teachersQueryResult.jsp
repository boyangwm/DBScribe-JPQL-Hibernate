<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="teachers" value="<%=session.getAttribute("teachers") %>"/>
<html> 
	<head>
		<title>JSP for DeleteTeachersForm form</title>
 	<script language="javaScript">
    function openwindow(){ 
         window.showModalDialog("../biyesheji/box/deleteSuccess.jsp",window,"status:no;scroll:no;dialogWidth:235px;dialogHeight:100px");
    }             
    
    function opensub(){
      document.form1.submit();
    }   
    </script>
	</head>
	<body bgcolor="#f1fcfe"><br>
    <html:form action="/deleteTeachers" method="post">
    <table align="center" border="1" width="85%">
      <tr align="center">
        <td align="center" bgcolor="#ddf5cd">选择</td>
        <td align="center" bgcolor="#ddf5cd">职工号</td>
        <td align="center" bgcolor="#ddf5cd">姓名</td>
        <td align="center" bgcolor="#ddf5cd">性别</td>
        <td align="center" bgcolor="#ddf5cd">年龄</td>
        <td align="center" bgcolor="#ddf5cd">部门</td>
        <td align="center" bgcolor="#ddf5cd">职称</td>
      </tr>          
      <logic:iterate id="teacher" name="teachers">
      <tr align="center">
        <td><input type="checkbox" name="id" value="<bean:write name="teacher" property="id"/>"></td>
        <td><bean:write name="teacher" property="id"/></td>
        <td><bean:write name="teacher" property="name"/></td>
        <td><bean:write name="teacher" property="sex"/></td>
        <td><bean:write name="teacher" property="age"/></td>
        <td><bean:write name="teacher" property="department"/></td>
        <td><bean:write name="teacher" property="post"/></td>
      </tr>
      </logic:iterate>
    </table><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" value="删除" style="cursor:hand;height:25;width:50;" onclick="openwindow()">
    <input type="reset" value="取消" style="cursor:hand;height:25;width:50;">
    </html:form> 
  </body>
</html>

