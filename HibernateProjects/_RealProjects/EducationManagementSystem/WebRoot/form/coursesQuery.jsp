<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for CoursesQueryForm form</title>
	</head>
	<body bgcolor="#f1fcfe"><br>
	<center>
		<html:form action="/coursesQuery" target="bot" method="post">
			课程代码:<input type="text" name="id" size="30">
              学分:<select name="credit">
                    <option value="" selected>--按课程学分查询--</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                 </select>
              类型:<select name="type">
                    <option value="" selected>--按课程类型查询--</option>
                    <option value="专业必修课">专业必修课</option>
                    <option value="专业选修课">专业选修课</option>
                    <option value="公共选修课">公共选修课</option>
                    <option value="公共必修课">公共必修课</option>
                 </select>
        <input type="submit" value="查询">
		</html:form>
	</center>	
	</body>
</html>

