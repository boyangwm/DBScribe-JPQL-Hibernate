<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for StudentsQueryForm form</title>
	</head>
	<body bgcolor="#f1fcfe"><br>
	<center>
		<html:form action="/studentsQuery" target="bot" method="post">
		     学号:<input type="text" name="id" size="20">
              学院:<select name="college">
                    <option value="" selected>--按学院查询--</option>
                    <option value="数学与计算机科学学院">数学与计算机科学学院</option>
                    <option value="物理与光电学院">物理与光电学院</option>
                    <option value="外语学院">外语学院</option>
                 </select>
              专业:<select name="department">
                    <option value="" selected>--按专业查询--</option>
                    <option value="计算机科学与技术">计算机科学与技术</option>
                    <option value="物理">物理</option>
                    <option value="外语">外语</option>
                 </select>
              年级:<select name="inTime">
                    <option value="" selected>--按年级查询--</option>
                    <option value="2004年">2004级</option>
                    <option value="2005年">2005级</option>
                    <option value="2006年">2006级</option>
                 </select>
        <input type="submit" value="查询">
		</html:form>
	</center>	
	</body>
</html>

