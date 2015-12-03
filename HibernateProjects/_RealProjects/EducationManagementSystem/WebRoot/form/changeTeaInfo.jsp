<%@ page language="java" import="com.jspdev.biyesheji.Teacher" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%String id=(String)request.getParameter("id"); %>
<%Teacher teacher=Teacher.getTeacher(id); %> 
<%boolean tag=true; %>
<html> 
	<head>
		<title>JSP for TeaInfoForm form</title>
    <script language="javaScript">
    function openwindow(){ 
         window.showModalDialog("../box/changeSuccess.jsp",window,"status:no;scroll:no;dialogWidth:235px;dialogHeight:100px");
    }             
    
    function opensub(){
      document.form1.submit();
    }   
    </script>
	</head>
	<body bgcolor="#f1fcfe"><br>
	<center><font size="6">个人信息修改</font></center>
		<html:form action="/teaInfo">
		    <table align="center" border="1">	
		    <tr>
		      <td align="center" bgcolor="#ddf5cd">职工号:</td>
		      <td><html:text size="30" property="number" value="<%=teacher.getId() %>" readonly="<%=tag %>"/><html:errors property="number"/></td>
			</tr>		
			<tr>
		      <td align="center" bgcolor="#ddf5cd">姓名:</td>
		      <td><html:text size="30" property="name" value="<%=teacher.getName() %>"/><html:errors property="name"/></td>
			</tr>
			<tr>
		      <td align="center" bgcolor="#ddf5cd">性别:</td>
		      <td><html:text size="30" property="sex" value="<%=teacher.getSex() %>"/><html:errors property="sex"/></td>
			</tr>
			<tr>
		      <td align="center" bgcolor="#ddf5cd">年龄:</td>
		      <td><html:text size="30" property="age" value="<%=teacher.getAge().toString() %>"/><html:errors property="age"/></td>
			</tr>			
			<tr>	
		      <td align="center" bgcolor="#ddf5cd">部门:</td>
		      <td><html:text size="30" property="department" value="<%=teacher.getDepartment() %>"/><html:errors property="department"/></td>
			</tr> 
			<tr>	
		      <td align="center" bgcolor="#ddf5cd">职位:</td>
		      <td><html:text size="30" property="post" value="<%=teacher.getPost() %>"/><html:errors property="post"/></td>
			</tr> 
			<tr>
			  <td align="center" bgcolor="#ddf5cd">备注:</td>
			  <td><html:textarea property="remark" value="<%=teacher.getRemark() %>" rows="5" cols="29"/><html:errors property="remark"/></td>
			</tr>
		  </table><br>	
			<center><input type="submit" value="修改" style="cursor:hand;height:25;width:70;" onclick="openwindow()">&nbsp;&nbsp;&nbsp;<input type="reset" value="取消" style="cursor:hand;height:25;width:70;" ></center>
		</html:form>
	</body>
</html>

